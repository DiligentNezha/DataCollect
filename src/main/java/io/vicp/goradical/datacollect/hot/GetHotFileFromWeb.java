package io.vicp.goradical.datacollect.hot;

import io.vicp.goradical.datacollect.utils.DbUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetHotFileFromWeb {
	public static void main(String[] args) {
//		getHotFile();
	}
	
	public static void readHotFile() {
		ObjectInputStream ois = null;
		DbUtil dbUtil = new DbUtil();
		try {
			ois = new ObjectInputStream(new FileInputStream(new File("hotfile.txt")));
			Connection connection = dbUtil.getCon();
			String sql = "insert into t_file_info_copy (file_name, type, play_url, download_url) values(?, ?, ?, ?)";
			HotFile hotFile = (HotFile) ois.readObject();
			int total = 0;
			while (hotFile != null) {
				total++;
				String fileName = hotFile.getFileName();
				String fileType = hotFile.getFileType();
				String playUrl = hotFile.getPlayUrl();
				String downloadUrl = hotFile.getDownloadUrl();
//				System.out.println(hotFile);
				
				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, fileName);
				pstmt.setString(2, fileType);
				pstmt.setString(3, playUrl);
				pstmt.setString(4, downloadUrl);
				pstmt.execute();
				hotFile = (HotFile) ois.readObject();
				System.out.println(total);
			}
			dbUtil.closeCon(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void getHotFile() {
		ObjectOutputStream oos = null;
		DbUtil dbUtil = new DbUtil();
		try {
			oos = new ObjectOutputStream(new FileOutputStream(new File("hotfile.txt")));
			
			String baseUrl = "http://www.4567.tv";
			Map<String, Integer> map = getLinkPage(getLinks());
			int totalType = map.size();
			int totalPages = 1;
			// 最大下载标签长度
			int maxDownload = 0;
			// 当前下载标签长度
			int currentDownloadLen = 0;
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				String oldUrl = entry.getKey();
				String newUrl = oldUrl.substring(0, oldUrl.length() - 5);
				for (int i = 1; i <= entry.getValue(); i++) {
					String accessUrl = newUrl + "_" + i + ".html";
					Document doc = Jsoup.connect(accessUrl).ignoreContentType(true).ignoreHttpErrors(true).get();
					Elements elements = doc.select("ul.img-list>li");
					for (Element element : elements) {
						Element tempEle = element.select("h5>a").first();
						String fileName = tempEle.text();
						String playUrl = baseUrl + tempEle.attr("href");
						System.out.println(playUrl);
						Document docTemp = Jsoup.connect(playUrl)
								.ignoreContentType(true)
								.ignoreHttpErrors(true)
								.timeout(36000)
								.get();
						Element tempEle2 = docTemp.select("div.location>a").get(1);
						String fileType = tempEle2.text();
						
						Elements tempEle1 = docTemp.select("div.title>span:contains(下载地址)");
						StringBuffer downloadSB = new StringBuffer();
						for (Element element2 : tempEle1) {
							Element temp = element2.parent().parent();
							downloadSB.append(temp);
						}
						String sql = "insert into t_file_info_copy (file_name, type, play_url, download_url) values(?, ?, ?, ?)";
						HotFile hotFile = new HotFile(fileName, fileType, playUrl, downloadSB.toString());
						oos.writeObject(hotFile);
					}

				}
			}
//			fos.write(Integer.toString(maxDownload).getBytes());
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void getHotFile1() {
		FileOutputStream fos = null;
		DbUtil dbUtil = new DbUtil();
		try {
//			fos = new FileOutputStream(new File("hotfile.txt"));
			String baseUrl = "http://www.4567.tv";
			Map<String, Integer> map = getLinkPage(getLinks());
			int totalType = map.size();
			int totalPages = 1;
			// 最大下载标签长度
			int maxDownload = 0;
			// 当前下载标签长度
			int currentDownloadLen = 0;
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				String oldUrl = entry.getKey();
				String newUrl = oldUrl.substring(0, oldUrl.length() - 5);
				for (int i = 1; i <= entry.getValue(); i++) {
					String accessUrl = newUrl + "_" + i + ".html";
					Document doc = Jsoup.connect(accessUrl).ignoreContentType(true).ignoreHttpErrors(true).get();
					Elements elements = doc.select("ul.img-list>li");
					for (Element element : elements) {
						Element tempEle = element.select("h5>a").first();
						String fileName = tempEle.text();
						String playUrl = baseUrl + tempEle.attr("href");
						Document docTemp = Jsoup.connect(playUrl).ignoreContentType(true).ignoreHttpErrors(true).get();
						Element tempEle2 = docTemp.select("div.location>a").get(1);
						String fileType = tempEle2.text();
						
						Elements tempEle1 = docTemp.select("div.title>span:contains(下载地址)");
						StringBuffer downloadSB = new StringBuffer();
						for (Element element2 : tempEle1) {
							Element temp = element2.parent().parent();
							downloadSB.append(temp);
						}
						String sql = "insert into t_file_info_copy (file_name, type, play_url, download_url) values(?, ?, ?, ?)";
						Connection connection = dbUtil.getCon();
						PreparedStatement pstmt = connection.prepareStatement(sql);
						pstmt.setString(1, fileName);
						pstmt.setString(2, fileType);
						pstmt.setString(3, playUrl);
						pstmt.setString(4, downloadSB.toString());
//						System.out.println(pstmt);
						pstmt.execute();
						dbUtil.closeCon(connection);
//						System.exit(0);
						
//						System.out.println(fileName + "  ----->  " + playUrl);
//						System.out.println(downloadSB);
//						fos.write((fileName + "  --->  " + playUrl + "\n").getBytes());
//						fos.write((downloadSB + "\n").getBytes());
//						currentDownloadLen = downloadSB.length();
//						if (currentDownloadLen > maxDownload) {
//							maxDownload = currentDownloadLen;
//						}
//						System.err.println("current download tags length is " + maxDownload);
						// System.exit(0);
					}

				}
			}
			fos.write(Integer.toString(maxDownload).getBytes());
		} catch (Exception e1) {
			e1.printStackTrace();
		}finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static Map<String, Integer> getLinkPage(List<String> links) {
		Map<String, Integer> linkPageMap = new HashMap<>();
		try {
			for (String string : links) {
				Document doc = Jsoup.connect(string).ignoreContentType(true).get();
				String temp = doc.select("#pages>span").first().text().split("/")[1];
				String totalPage = temp.substring(0, temp.length() - 1);
				linkPageMap.put(string, Integer.parseInt(totalPage));
				// System.out.println(Integer.parseInt(totalPage));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return linkPageMap;
	}

	private static List<String> getLinks() {
		List<String> linksStr = null;
		try {
			linksStr = new ArrayList<>();
			String baseUrl = "http://www.4567.tv";
			Document doc = Jsoup.connect("http://www.4567.tv/html/15.html").ignoreContentType(true).get();
			Elements linksEle = doc.select("p.s>a");
			for (Element element : linksEle) {
				linksStr.add(baseUrl + element.attr("href"));
				// System.out.println(element);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return linksStr;
	}
}

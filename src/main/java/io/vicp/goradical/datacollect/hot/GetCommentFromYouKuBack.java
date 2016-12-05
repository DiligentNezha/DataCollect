package io.vicp.goradical.datacollect.hot;

import io.vicp.goradical.datacollect.utils.DbUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;


public class GetCommentFromYouKuBack {
	public static DbUtil dbUtil = new DbUtil();
	public static PreparedStatement pstmt = null;
	public static Connection connection = null;
	public static ResultSet rs = null;
	
	public static void main(String[] args) {
		getComment();
	}
	
	
	public static void main1(String[] args) {
		String getPlayUrl = "select youku_play_url from t_file_info where file_info_id=?";
		String playUrl = "http://v.youku.com/v_show/id_XMTY0OTU3NzYyMA==.html?from=s1.8-1-1.1&spm=a2h0k.8191407.0.0";
		playUrl = "http://v.youku.com/v_show/id_XMTU3NjI5MjI3Ng==.html?from=s1.8-3-1.1";
//		getComment(playUrl);
		String videoId = getVideoId(playUrl);
		String page = "1";
		String queryStr = "{" + '\"' + "videoid" + '\"' + ":" + '\"' + videoId + '\"' + "," + '\"' + "isAjax" + '\"' + ":1," + '\"' + "page" + '\"' + ":" + page + "}";
		String totalPage = getTotalPage(queryStr, videoId);
		List<JSONObject> pageResults = getPageResults(videoId, Integer.parseInt(totalPage));
		analyPageResults(pageResults, 10);
	}
	
	
	
	public static void analyPageResults(List<JSONObject> pageResults, int fileId) {
		String insertCommentSql = "insert into t_comment_temp values(null, ?, ?, ?, ?, ?)";
		Map<String, String> userIconsMap = new HashMap<>();
		int count = 0;
		try {
			connection = dbUtil.getCon();
			System.err.println("fileId:" + fileId + ",开始写入，请勿停止............");
			for (JSONObject jsonObject : pageResults) {
				try {
					JSONObject userIcons = jsonObject.getJSONObject("userIcons");
					Set<String> keySet = userIcons.keySet();
					for (String string : keySet) {
						userIconsMap.put(string, userIcons.getString(string));
	//					System.out.println(string + " : " + userIcons.getString(string));
					}
					JSONArray datas = jsonObject.getJSONArray("data");
					for(int i = 0, len = datas.size(); i < len; i++) {
						JSONObject data = datas.getJSONObject(i);
						String text = data.getString("text");
						JSONObject user = data.getJSONObject("user");
						String userId = user.getString("userId");
						String userName = user.getString("userName");
						String userHeadPhoto = userIconsMap.get(userId);
	//					System.out.println(userId + " : " + userName + " : " + text + " : " + userHeadPhoto);
						pstmt = connection.prepareStatement(insertCommentSql);
						pstmt.setString(1, userId);
						pstmt.setString(2, userName);
						pstmt.setBlob(3, new ByteArrayInputStream(text.getBytes()));
						pstmt.setString(4, userHeadPhoto);
						pstmt.setInt(5, fileId);
						pstmt.execute();
						count++;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("fileId:" + fileId + "--commentTotal:" + count );
			try {
				dbUtil.closeCon(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void getComment() {
		//1349
		//2130
		int startId = 1349;
		int endId = 1349;
		int fileId = 0;
		String playUrl = null;
		String selectFileIdAndPlayUrl = "select file_info_id, youku_play_url from t_file_info where file_info_id=?";
		try {
			for(int id = startId; id <= endId; id++) {
				connection = dbUtil.getCon();
				pstmt = connection.prepareStatement(selectFileIdAndPlayUrl);
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					fileId = id;
					playUrl = rs.getString("youku_play_url");
				}
				String videoId = getVideoId(playUrl);
				if (videoId != null) {
					String page = "1";
					String queryStr = "{" + '\"' + "videoid" + '\"' + ":" + '\"' + videoId + '\"' + "," + '\"' + "isAjax" + '\"' + ":1," + '\"' + "page" + '\"' + ":" + page + "}";
					String totalPage = getTotalPage(queryStr, videoId);
					if (totalPage != null) {
						List<JSONObject> pageResults = getPageResults(videoId, Integer.parseInt(totalPage));
						analyPageResults(pageResults, fileId);
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}
	
	public static List<JSONObject> getPageResults(String videoId, int totalPage) {
		List<JSONObject> pageResults = new ArrayList<>(120000);
		String jsonRes = null;
		int start = 0;
		int end = 0;
		int i;
		int flag = 1;
		for(i = 1; i <= totalPage; i++) {
			String queryStr = "{" + '\"' + "videoid" + '\"' + ":" + '\"' + videoId + '\"' + "," + '\"' + "isAjax" + '\"' + ":1," + '\"' + "page" + '\"' + ":" + i + "}"; 
			try {
				Document doc = Jsoup.connect("http://comments.youku.com/comments/~ajax/vpcommentContent.html")
						.ignoreContentType(true)
						.ignoreHttpErrors(true)
						.data("__callback", "vpcommentContent_html")
						.data("__ap", queryStr)
						.get();
				jsonRes = doc.body().text();
				start = jsonRes.indexOf('(');
				end = jsonRes.lastIndexOf(')');
				jsonRes = jsonRes.substring(start + 1, end);
				JSONObject jsonObject = JSONObject.fromObject(jsonRes);
				
				JSONObject con = jsonObject.getJSONObject("con");
				JSONObject pageResult = con.getJSONObject("pageResult");
				pageResults.add(pageResult);
//				List<JSONObject> commentData = pageResult.getJSONArray("data");
//				for (JSONObject jsonObj : commentData) {
//					commentList.add(jsonObj.getString("text"));
//				}
//				if (pageResult.size() > 1500) {
//					break;
//				}
			} catch (Exception e) {
//				e.printStackTrace();
				--i;
				flag++;
				if (flag % 5 == 0) {
					i++;
					flag = 1;
				}
			}
		}
		return pageResults;
	}
	
	public static String getTotalPage(String queryStr, String videoId) {
		String jsonRes = null;
		String totalPage = null;
		int start = 0;
		int end = 0;
		int flag = 1;
		while (totalPage == null) {
			try {
				Document doc = Jsoup.connect("http://comments.youku.com/comments/~ajax/vpcommentContent.html")
						.ignoreContentType(true)
						.ignoreHttpErrors(true)
						.data("__callback", "vpcommentContent_html")
						.data("__ap", queryStr)
						.get();
				jsonRes = doc.body().text();
				start = jsonRes.indexOf('(');
				end = jsonRes.lastIndexOf(')');
				jsonRes = jsonRes.substring(start + 1, end);
				JSONObject jsonObject = JSONObject.fromObject(jsonRes);
				JSONObject con = jsonObject.getJSONObject("con");
				totalPage = con.getString("totalPage");
			} catch (Exception e) {
				flag++;
				if (flag % 5 == 0) {
					break;
				}
//				e.printStackTrace();
			}
		}
		return totalPage;
	}
	
	public static String getVideoId(String url) {
		Document doc = null;
		Elements scripts = null;
		String videoId = null;
		try {
			doc = Jsoup.connect(url)
					.ignoreContentType(true)
					.ignoreHttpErrors(true)
					.get();
			scripts = doc.select("script[type=text/javascript]");
			for (Element element : scripts) {
				if (element.data().contains("player_thx")) {
//					System.out.println(element.data());
					String[] temp = element.data().split(";");
					String jsonRes = temp[temp.length - 1];
//					System.out.println(jsonRes);
					int start = jsonRes.indexOf("{");
					jsonRes = jsonRes.substring(start);
//					System.out.println(jsonRes);
					JSONObject jsonObject = JSONObject.fromObject(jsonRes);
					videoId = (String) jsonObject.get("videoId");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return videoId;
	}
}

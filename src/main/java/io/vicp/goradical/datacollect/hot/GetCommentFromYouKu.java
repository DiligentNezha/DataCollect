package io.vicp.goradical.datacollect.hot;

import io.vicp.goradical.datacollect.tools.JDBCTools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetCommentFromYouKu {
	public static PreparedStatement pstmt = null;
	public static Connection connection = null;
	public static ResultSet rs = null;

	public static void main(String[] args) {
	}

	public static void updateFileActionInfo(){
		String selectActionInfoIdAndFileIdSql = "select action_info_id, file_info_id from t_action_info";
		Map<Integer, Integer> actionIdFileIdMap = new HashMap<>(3600);
		connection = JDBCTools.getConnection();
		try {
			pstmt = connection.prepareStatement(selectActionInfoIdAndFileIdSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				actionIdFileIdMap.put(rs.getInt(2), rs.getInt(1));
			}
			Statement statement = connection.createStatement();
			for (Map.Entry<Integer, Integer> entry : actionIdFileIdMap.entrySet()) {
				String updateSql = "update t_file_info set action_info = " + entry.getValue() + " where file_info_id = " + entry.getKey();
				System.out.println(updateSql);
				statement.addBatch(updateSql);
			}
			statement.executeBatch();
			System.out.println(actionIdFileIdMap.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateFilePalyUrl(){
		String selectFileId = "select file_info_id, file_name, youku_play_url from t_file_info where youku_play_url like '%\\%3A%'";
		connection = JDBCTools.getConnection();
		Map<Integer, String> idPlayUrlMap = new HashMap<>(1000);
		try {
			pstmt = connection.prepareStatement(selectFileId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int fileId = rs.getInt(1);
				String playUrl = rs.getString(3);
				playUrl = URLDecoder.decode(playUrl, "UTF-8");
				idPlayUrlMap.put(fileId, playUrl);
			}
			Statement statement = connection.createStatement();
			for (Map.Entry<Integer, String> entry : idPlayUrlMap.entrySet()) {
				int fileId = entry.getKey();
				String playUrl = entry.getValue();
				String updateSql = "update t_file_info set youku_play_url = '" + playUrl.substring(0, playUrl.length() - 1) + "' where file_info_id = " + fileId;
			    System.out.println(updateSql);
				statement.addBatch(updateSql);
			}
			statement.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getFileDetailInfoFromYouKu(){
		//575，584,1118,1803,2670
		//1606,1904,1934,2241,2242,2334,2402,2470,2535,2670,2736,2739,2750,2869,3021,3210,3293,3512,
		Map<Integer, String> idPlayUrl = new HashMap<>(3600);
		idPlayUrl.put(1606,"http://v.youku.com/v_show/id_XNjkzNTY0MDg0.html");
		idPlayUrl.put(1904,"http://v.youku.com/v_show/id_XMjE0NjQwNDg4.html");
		idPlayUrl.put(1934,"http://v.youku.com/v_show/id_XNDc5MjMyNjc2.html");
		idPlayUrl.put(2241,"http://v.youku.com/v_show/id_XNjk2MTA0NzUy.html");
		idPlayUrl.put(2242,"http://v.youku.com/v_show/id_XNTY1ODE1MTI4.html");
		idPlayUrl.put(2334,"http://v.youku.com/v_show/id_XMTY4NzA0NTYyMA==.html");
		idPlayUrl.put(2402,"http://v.youku.com/v_show/id_XMTUwMDA2Mzk2.html");
		idPlayUrl.put(2470,"http://v.youku.com/v_show/id_XMjkxNTQ1OTg0.html");
		idPlayUrl.put(2535,"http://v.youku.com/v_show/id_XMjkyNjIwNDg4.html");
		idPlayUrl.put(2670,"http://v.youku.com/v_show/id_XNzUxNzgxNzIw.html");
		idPlayUrl.put(2736,"http://v.youku.com/v_show/id_XMjkxNTg4MTMy.html");
		idPlayUrl.put(2739,"http://v.youku.com/v_show/id_XMjkyNTM3NTk2.html");
		idPlayUrl.put(2750,"http://v.youku.com/v_show/id_XMjkxOTI2MjMy.html");
		idPlayUrl.put(2869,"http://v.youku.com/v_show/id_XOTU2NjAyNDQw.html");
		idPlayUrl.put(3021,"http://v.youku.com/v_show/id_XNjU4MDc0Mzk2.html");
		idPlayUrl.put(3210,"http://v.youku.com/v_show/id_XMjc3Njg2Mzc2.html");
		idPlayUrl.put(3293,"http://v.youku.com/v_show/id_XODgxNTU1MjQ4.html");
		idPlayUrl.put(3512,"http://v.youku.com/v_show/id_XMjY2NTcxNDY0.html");
		connection = JDBCTools.getConnection();
	/*	String selectFileIdAndPlayUrl = "select file_info_id, youku_play_url from t_file_info";
		try {
			pstmt = connection.prepareStatement(selectFileIdAndPlayUrl);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				idPlayUrl.put(rs.getInt(1), rs.getString(2));
			}
		}catch (Exception e){
			e.printStackTrace();
		}*/
		for (Map.Entry<Integer, String> entry : idPlayUrl.entrySet()){
			int fileInfoId = entry.getKey();
			String playUrl = entry.getValue();
			System.out.println(fileInfoId + ":" + entry.getValue());
			String index = "http://index.youku.com/vr_keyword/id_" + playUrl;
			try {
				Document doc = Jsoup.connect(index)
						.ignoreContentType(true)
						.ignoreHttpErrors(true)
						.timeout(30 * 1000)
						.get();
				Elements player_info = doc.getElementsByClass("player_info");
				if (!"".equals(player_info.text())) {
					Elements lis = doc.getElementsByClass("player_info").first().getElementsByTag("li");
					String temp = lis.get(0).text();
					int sum = Integer.parseInt(temp.substring(4).replace(",", ""));
					temp = lis.get(1).text();
					int comment = Integer.parseInt(temp.substring(3).replace(",", ""));
					temp = lis.get(2).text();
					int like = Integer.parseInt(temp.substring(2).replace(",", ""));
					temp = lis.get(3).text();
					int disLike = Integer.parseInt(temp.substring(2).replace(",", ""));
					temp = lis.get(4).text();
					int collect = Integer.parseInt(temp.substring(3).replace(",", ""));
					System.out.println("fileId:" + fileInfoId + ",sum:" + sum + ",comment:" + comment + ",like:" + like + ",dislike:" + disLike + ",collect:" + collect);
					String insertActionInfoSql = "insert into t_action_info (file_info_id, total_play, total_comment, total_like, total_dislike, total_collect) values (?, ?, ?, ?, ?, ?)";
					pstmt = connection.prepareStatement(insertActionInfoSql, PreparedStatement.RETURN_GENERATED_KEYS);
					pstmt.setInt(1, fileInfoId);
					pstmt.setInt(2, sum);
					pstmt.setInt(3, comment);
					pstmt.setInt(4, like);
					pstmt.setInt(5, disLike);
					pstmt.setInt(6, collect);
				    pstmt.executeUpdate();
				}
			}catch (Exception e1){
				e1.printStackTrace();
			}
		}
		System.out.println(idPlayUrl.size());
	}

	public static void getScrUrlFromYouku() {
		String selectFilePlayUrl = "select youku_play_url from t_file_info where file_info_id=?";
		connection = JDBCTools.getConnection();
		//3178
		int start = 1;
		int end = 3576;

		for (int i = start; i <= end; i++) {
			try {
				pstmt = connection.prepareStatement(selectFilePlayUrl);
				pstmt.setInt(1, i);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					String playUrl = rs.getString(1);
					Document doc = Jsoup.connect(playUrl)
							.ignoreHttpErrors(true)
							.ignoreContentType(true)
							.get();
					System.out.println(doc.getElementById("link2") == null);
					String temp = doc.getElementById("link2").attr("value");
					System.out.println(i + ":" + temp);
					String updateSql = "update t_file_info set youku_src_url=? where file_info_id=" + i;
					pstmt = connection.prepareStatement(updateSql);
					pstmt.setString(1, temp);
					pstmt.execute();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main1(String[] args) {
		String getPlayUrl = "select youku_play_url from t_file_info where file_info_id=?";
		String playUrl = "http://v.youku.com/v_show/id_XMTY0OTU3NzYyMA==.html?from=s1.8-1-1.1&spm=a2h0k.8191407.0.0";
		playUrl = "http://v.youku.com/v_show/id_XMTU3NjI5MjI3Ng==.html?from=s1.8-3-1.1";
//		getComment(playUrl);
		String videoId = getVideoId(playUrl);
		String page = "1";
		String totalPage = getTotalPage(videoId);
		List<JSONArray> pageResults = getPageResults(videoId, Integer.parseInt(totalPage));
		analyPageResults(pageResults, 10);
	}

	public static void analyPageResults(List<JSONArray> pageResults, int fileId) {
		String insertUserSql = "insert into t_user values(null, ?, ?, null, null, ?, ?, ?, ?, null)";
		String insertCommentSql = "insert into t_comment values(null, ?, ?, ?, ?)";
		int count = 0;
		System.err.println("fileId:" + fileId + ",start to write data, don't stop it! Thank you ............");
		connection = JDBCTools.getConnection();
		try {
			for (JSONArray pageResult : pageResults) {
				for(int i = 0; i < pageResult.size(); i++) {
					JSONObject comment = (JSONObject) pageResult.get(i);

					JSONObject user = comment.getJSONObject("user");
					int userId = user.getInt("userId");
					String userName = user.getString("userName");
					int userLevel = user.getInt("userLevel");
					String headPhotoSmall = user.getString("avatarSmall");
					String headPhotoMiddle = user.getString("avatarMiddle");
					String headPhotoLarge = user.getString("avatarLarge");
					pstmt = connection.prepareStatement(insertUserSql);
					pstmt.setInt(1, userId);
					pstmt.setString(2, userName);
					pstmt.setInt(3, userLevel);
					pstmt.setString(4, headPhotoSmall);
					pstmt.setString(5, headPhotoMiddle);
					pstmt.setString(6, headPhotoLarge);
//					System.out.println(pstmt);
					pstmt.executeUpdate();

					Long createTime = comment.getLong("createTime");
					String commentText = comment.getString("content");
					pstmt = connection.prepareStatement(insertCommentSql);
					pstmt.setInt(1, userId);
					pstmt.setInt(2, fileId);
					pstmt.setBlob(3, new ByteArrayInputStream(commentText.getBytes()));
					pstmt.setTimestamp(4, new Timestamp(createTime));
					pstmt.executeUpdate();
//					System.out.println(pstmt);
					count++;
				}
			}
		} catch (Exception e) {
//			e.printStackTrace();
		} finally {
			System.out.println("fileId:" + fileId + "--commentTotal:" + count);
//			JDBCTools.closeResultSetAndStatement(rs, pstmt);
			JDBCTools.closeRsAndStmtAndConn(rs, pstmt, connection);
		}
	}

	public static void getComment() {
		//570
		int startId = 3298;
		//3576
		int endId = 3576;
		int fileId = 0;
		String playUrl = null;
		String selectFileIdAndPlayUrl = "select file_info_id, youku_play_url from t_file_info where file_info_id=?";
		for (int id = startId; id <= endId; id++) {
			try {
				connection = JDBCTools.getConnection();
				pstmt = connection.prepareStatement(selectFileIdAndPlayUrl);
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					fileId = id;
					playUrl = rs.getString(2);
					String videoId = getVideoId(new String(playUrl));
					if (videoId != null) {
						String totalPage = getTotalPage(videoId);
						if (totalPage != null) {
							List<JSONArray> pageResults = getPageResults(videoId, Integer.parseInt(totalPage));
							analyPageResults(pageResults, fileId);
						}
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
//				JDBCTools.closeResultSetAndStatement(rs, pstmt);
				JDBCTools.closeRsAndStmtAndConn(rs, pstmt, connection);
			}
		}
	}

	public static List<JSONArray> getPageResults(String videoId, int totalPage) {
		List<JSONArray> pageResults = new ArrayList<>(totalPage);
		String jsonRes = null;
		int start = 0;
		int end = 0;
		int i;
		int flag = 1;
		for (i = 1; i <= totalPage; i++) {
			try {
				Document doc = Jsoup.connect("http://p.comments.youku.com/ycp/comment/pc/commentList")
						.ignoreContentType(true)
						.ignoreHttpErrors(true)
						.data("jsoncallback", "n_commentList")
						.data("objectId", videoId)
						.data("app", "100-DDwODVkv")
						.data("currentPage", String.valueOf(i))
						.data("pageSize", "30")
						.data("listType", "0")
						.data("sign", "f74cd506e510fdf6b3369f8d60a6d0ae")
						.data("time", "1480082517076")
						.get();
				jsonRes = doc.body().text();
				start = jsonRes.indexOf('(');
				end = jsonRes.lastIndexOf(')');
				jsonRes = jsonRes.substring(start + 1, end);
				JSONObject jsonObject = JSONObject.fromObject(jsonRes);
				JSONObject data = jsonObject.getJSONObject("data");
				JSONArray comment = data.getJSONArray("comment");
				pageResults.add(comment);
				if (pageResults.size() > 1500) {
					break;
				}
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

	public static String getTotalPage(String videoId) {
		String jsonRes = null;
		String totalPage = null;
		int start = 0;
		int end = 0;
		int flag = 1;
		while (totalPage == null) {
			try {
				Document doc = Jsoup.connect("http://p.comments.youku.com/ycp/comment/pc/commentList")
						.ignoreContentType(true)
						.ignoreHttpErrors(true)
						.data("jsoncallback", "n_commentList")
						.data("objectId", videoId)
						.data("app", "100-DDwODVkv")
						.data("currentPage", "1")
						.data("pageSize", "30")
						.data("listType", "0")
						.data("sign", "f74cd506e510fdf6b3369f8d60a6d0ae")
						.data("time", "1480082517076")
						.get();
				jsonRes = doc.body().text();
				start = jsonRes.indexOf('(');
				end = jsonRes.lastIndexOf(')');
				jsonRes = jsonRes.substring(start + 1, end);
				JSONObject jsonObject = JSONObject.fromObject(jsonRes);
				JSONObject date = jsonObject.getJSONObject("data");
				totalPage = date.getString("totalPage");
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

	public static void main4(String[] args) {
		String url = "http://v.youku.com/v_show/id_XMTYzOTU2MDE2OA==.html";
		String videoId = getVideoId(url);
		String totalPage = getTotalPage(videoId);
		List<JSONArray> pageResults = getPageResults(videoId, Integer.parseInt(totalPage));
		analyPageResults(pageResults, 1);
	}

	public static String getVideoId(String url) {
		Document doc = null;
		Elements scripts = null;
		String videoId = null;
		try {
			doc = Jsoup.connect(url)
					.ignoreContentType(true)
					.ignoreHttpErrors(true)
					.timeout(30 * 1000)
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
			getVideoId(url);
		}
		return videoId;
	}
}

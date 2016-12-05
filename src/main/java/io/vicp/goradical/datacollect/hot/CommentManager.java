package io.vicp.goradical.datacollect.hot;

import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class CommentManager {
	public static Connection connection;
	public static PreparedStatement pstmt;
	public static ResultSet rs;
	
	public static Map<Integer, Integer> userUserIdsMap;
	public static List<Integer> oldUserIdsList;
	
	public static void main(String[] args) {
		getUpdateCommentRecordSqlFile();
	}


	public static void getUpdateCommentRecordSqlFile(){
		FileOutputStream fos = null;
		Map<Integer, Integer> userIdAndUserProfileIdMap = new HashMap<>(2100000);
		String selectUserProfileIdAndUserIdSql = "select user_profile_id, user_id from t_user_profile";
		Connection conn;
		Statement stmt = null;
		ResultSet rs = null;
		conn = JDBCTools.getConnection();
		try {
			File file = new File("sql/updateUseIdSql.sql");
			fos = new FileOutputStream(file);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectUserProfileIdAndUserIdSql);
			while (rs.next()) {
				userIdAndUserProfileIdMap.put(rs.getInt(2), rs.getInt(1));
			}
			String selectAllCommentSql = "select * from t_comment_record";
			rs = stmt.executeQuery(selectAllCommentSql);
			while (rs.next()) {
				int commentRecordId = rs.getInt(1);
				int userId = rs.getInt(2);
				Date commentDate = rs.getTimestamp(5);
				String sql = "update t_comment_record set user_id = " + userIdAndUserProfileIdMap.get(userId) + ", comment_date = '" + commentDate + "' where comment_record_id = " + commentRecordId;
				fos.write((sql + ";\n").getBytes());
			}
			System.out.println("finished.......................");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.closeRsAndStmtAndConn(rs, stmt, connection);
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void updateActionInfo(){
		String selectActionInfoSql = "select action_info_id, total_play, total_like, total_dislike from t_action_info";
		List<String> updateActionInfoSqlList = new ArrayList<>(3600);
		connection = JDBCTools.getConnection();
		try {
			pstmt = connection.prepareStatement(selectActionInfoSql);
			rs = pstmt.executeQuery();
			Random random = new Random();
			int actionInfoId, totalPlay, totalLike, totalDislike, totalGlance, totalRecommend;
			while (rs.next()) {
				actionInfoId = rs.getInt(1);
				totalPlay = rs.getInt(2);
				totalLike = rs.getInt(3);
				totalDislike = rs.getInt(4);
				totalGlance = (int) (totalPlay * ((random.nextInt(4) + 8.0) / (random.nextInt(4) + 8)));
				totalRecommend = Math.abs(totalLike - totalDislike) * (totalLike + 5) / (totalDislike + 5) / 80 + random.nextInt(5) + 5;
				String updateActionInfoSql = "update t_action_info set total_glance = " + totalGlance + ", total_recommend = " + totalRecommend + " where action_info_id = " + actionInfoId;
				updateActionInfoSqlList.add(updateActionInfoSql);
			}
			Statement statement = connection.createStatement();
			for (String str : updateActionInfoSqlList) {
				statement.addBatch(str);
			}
			statement.executeBatch();
			System.out.println("finish...............");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTools.closeRsAndStmtAndConn(rs, pstmt, connection);
		}
	}

	public static List<Integer> getFileIdsList(){
		List<Integer> fileIdsList = new ArrayList<>(3600);
		connection = JDBCTools.getConnection();
		String selectFileIdsSql = "select distinct file_id from t_comment_record";
		try {
			pstmt = connection.prepareStatement(selectFileIdsSql);
			rs = pstmt.executeQuery();
			System.out.println("so smart");
			while(rs.next()) {
				fileIdsList.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTools.closeRsAndStmtAndConn(rs, pstmt, connection);
		}
		
		return fileIdsList;
	}
	
	public static void updateOldIdToNewId() {
		File file = new File("userIdsMapAndList/updatelog.txt");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Map<Integer, Integer> userProfileIdsMap = getUserProfileIdsMap();
		List<List<Integer>> commentIdsList = getCommentsIdsList();
		List<String> updateIdsList = new ArrayList<>(commentIdsList.size() + 100);
		System.out.println("commentIdsList:" + commentIdsList.size());
		connection = JDBCTools.getConnection();
		List<Integer> tempList = null;
		int commentId = 0;
		int oldId = 0;
		try {
			Statement stmt = connection.createStatement();
			for(int i = 0, len = commentIdsList.size(); i < len; i++) {
				tempList = commentIdsList.get(i);
				commentId = tempList.get(0);
				oldId = tempList.get(1);
				String tempSql = "update t_comment_temp_copy1 set user_id=" + userProfileIdsMap.get(oldId) + " where id=" + commentId;
				stmt.addBatch(tempSql);
//				System.out.println(tempSql);
			}
			int[] total = stmt.executeBatch();
			for(int i = 0; i < total.length; i++) {
				String temp = "sql" + i + ":" + total[i] + "\n";
				fos.write(temp.getBytes());
			}
			System.out.println("finish..............");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			JDBCTools.closeRsAndStmtAndConn(rs, pstmt, connection);
		}
	}
	
	public static void readUserIdsFromFile() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(new File("userIdsMapAndList/userIdsMapAndList.txt")));
			userUserIdsMap = (Map<Integer, Integer>) ois.readObject();
			oldUserIdsList = (List<Integer>) ois.readObject();
			System.out.println(userUserIdsMap.size());
			System.out.println(oldUserIdsList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void writeUseIdsAsFile() {
		ObjectOutputStream oos = null;
		
		Map<Integer, Integer> userProfileMap = new HashMap<>(1630000);
		List<Integer> oldUserIds = new ArrayList<>(1630000);
		String selectUserProfileSql = "select user_id newId, id oldId from t_user_profile";
		String selectUserIdSql = "select id oldId from t_user_profile";
		try {
			connection = JDBCTools.getConnection();
			pstmt = connection.prepareStatement(selectUserProfileSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Integer newId = rs.getInt(1);
				Integer oldId = rs.getInt(2);
				userProfileMap.put(oldId, newId);
			}
			pstmt = connection.prepareStatement(selectUserIdSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				oldUserIds.add(rs.getInt(1));
			}
			
			try {
				File file = new File("userIdsMapAndList/userIdsMapAndList.txt");
				oos = new ObjectOutputStream(new FileOutputStream(file));
				oos.writeObject(userProfileMap);
				oos.writeObject(oldUserIds);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				oos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCTools.closeRsAndStmtAndConn(rs, pstmt, connection);
		}
	}
	
	public static Map<Integer, Integer> getUserProfileIdsMap(){
		Map<Integer, Integer> userProfileMap = new HashMap<>(1630000);
		String selectUserProfileSql = "select user_id newId, id oldId from t_user_profile";
		try {
			connection = JDBCTools.getConnection();
			pstmt = connection.prepareStatement(selectUserProfileSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Integer newId = rs.getInt(1);
				Integer oldId = rs.getInt(2);
				userProfileMap.put(oldId, newId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTools.closeRsAndStmtAndConn(rs, pstmt, connection);
		}
		return userProfileMap;
	}
	
	public static List<List<Integer>> getCommentsIdsList(){
		List<List<Integer>> commentsIds = new ArrayList<>(2900000);
		String selectCommentsIdsSql = "select id commentId, user_id oldId from t_comment_temp_copy1";
		connection = JDBCTools.getConnection();
		try {
			pstmt = connection.prepareStatement(selectCommentsIdsSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				List<Integer> tempList = new ArrayList<>(2);
				tempList.add(rs.getInt(1));
				tempList.add(rs.getInt(2));
				commentsIds.add(tempList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTools.closeRsAndStmtAndConn(rs, pstmt, connection);
		}
		return commentsIds;
	}
	
	public static void getUserProfile() {
		Map<Integer, Integer> userProfileMap = new HashMap<>(1630000);
		List<Integer> oldUserIds = new ArrayList<>(1630000);
		FileOutputStream fos = null;
		String selectUserProfileSql = "select user_id newId, id oldId from t_user_profile";
		String selectUserIdSql = "select id oldId from t_user_profile";
		try {
			File file = new File("userIdsMapAndList/log.txt");
			fos = new FileOutputStream(file, true);
			connection = JDBCTools.getConnection();
			pstmt = connection.prepareStatement(selectUserProfileSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Integer newId = rs.getInt(1);
				Integer oldId = rs.getInt(2);
				userProfileMap.put(oldId, newId);
			}
			pstmt = connection.prepareStatement(selectUserIdSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				oldUserIds.add(rs.getInt(1));
			}
			List<Integer> sameUserIds = new ArrayList<>();
			String selectSameUserIds = "select id from t_comment_temp where user_id=?";
			for(int i = 0, len = oldUserIds.size(); i < len; i++) {
				String updateUserIdSql = "update t_comment_temp set user_id=? where id in ";
				Integer oldUserId = oldUserIds.get(i);
				Integer newUserId = userProfileMap.get(oldUserId);
				connection = JDBCTools.getConnection();
				pstmt = connection.prepareStatement(selectSameUserIds);
				pstmt.setInt(1, oldUserId);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					sameUserIds.add(rs.getInt(1));
				}
				String logStr = "oleUserId:" + oldUserId + " ,newUserId:" + newUserId + " ,ids:" + sameUserIds;
				System.out.println(logStr);
				fos.write((logStr + "\n").getBytes());
				if (sameUserIds.size() > 0) {
					String temp = sameUserIds.toString();
					temp = "(" + temp.substring(1, temp.length() - 1) + ")";
					updateUserIdSql = updateUserIdSql + temp;
					pstmt = connection.prepareStatement(updateUserIdSql);
					pstmt.setInt(1, newUserId);
					pstmt.execute();
				}
				sameUserIds.clear();
				JDBCTools.closeRsAndStmtAndConn(rs, pstmt, connection);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCTools.closeRsAndStmtAndConn(rs, pstmt, connection);
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

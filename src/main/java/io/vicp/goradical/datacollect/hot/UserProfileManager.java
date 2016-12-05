package io.vicp.goradical.datacollect.hot;

import io.vicp.goradical.datacollect.tools.JDBCTools;
import io.vicp.goradical.datacollect.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserProfileManager {
	public static DbUtil dbUtil = new DbUtil();
	public static Connection connection = null;
	public static PreparedStatement pstmt = null;
	public static ResultSet rs = null;

	public static void main(String[] args) {
	}

	public static List<Integer> getDuplicateUserNameIds() {
		String selectDuplicateUserNameIdSql = "select user_id from t_user_profiletemp group by user_id having count(user_id) > 1";
		List<Integer> duplicateUserNameIdList = new ArrayList<>(500);
		connection = JDBCTools.getConnection();
		try {
			pstmt = connection.prepareStatement(selectDuplicateUserNameIdSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				duplicateUserNameIdList.add(rs.getInt(1));
			}
			for (int i = 0, len = duplicateUserNameIdList.size(); i < len; i++) {
				String selectSameId = "select user_profile_id from t_user_profiletemp where user_id = ?";
				pstmt = connection.prepareStatement(selectSameId);
				pstmt.setInt(1, duplicateUserNameIdList.get(i));
				rs = pstmt.executeQuery();
				rs.next();
				while (rs.next()) {
					String deleteSql = "delete from t_user_profiletemp where user_profile_id=" + rs.getInt(1);
					pstmt = connection.prepareStatement(deleteSql);
					pstmt.execute();
					System.out.println(deleteSql);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTools.closeRsAndStmtAndConn(rs, pstmt, connection);
		}
		return duplicateUserNameIdList;
	}

	public static void userProfileFillNameAndHeadPhoto() {
		String sql = "select distinct user_id from t_comment_temp";
		String selectSql = "select user_id, user_name, user_head_photo from t_comment_temp where user_id=?";
		String insertSql = "insert into t_user_profile (id, user_name, head_photo) values(?, ?, ?)";
		List<Integer> userIds = new ArrayList<>(2900000);
		int total = 1;
		try {
			connection = dbUtil.getCon();
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int userid = rs.getInt(1);
				userIds.add(userid);
			}
			System.out.println("userIds:" + userIds.size());
			int i, len;
			for (i = 0, len = userIds.size(); i < len; i++) {
				pstmt = connection.prepareStatement(selectSql);
				pstmt.setInt(1, userIds.get(i));
				rs = pstmt.executeQuery();
				int userId = 0;
				String userName = null;
				String userHeadPhoto = null;

				if (rs.next()) {
					userId = rs.getInt(1);
					userName = rs.getString(2);
					userHeadPhoto = rs.getString(3);
				}

				pstmt = connection.prepareStatement(insertSql);
				pstmt.setInt(1, userId);
				pstmt.setString(2, userName);
				pstmt.setString(3, userHeadPhoto);
				if (!pstmt.execute()) {
					System.err.println("插入失败！");
				} else {
					total++;
				}
			}
			System.out.println("total:" + total);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(connection);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}

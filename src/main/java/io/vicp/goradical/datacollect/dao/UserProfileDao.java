package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.model.UserProfile;
import io.vicp.goradical.datacollect.tools.JDBCTools;
import io.vicp.goradical.datacollect.tools.ListTool;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserProfileDao {
	private DetailsInfoDao detailsInfoDao;
	private Map<Integer, UserProfile> userProfileMap = new HashMap<>(2000000);

	public UserProfileDao(){
	}

	public UserProfileDao(DetailsInfoDao detailsInfoDao) {
		this.detailsInfoDao = detailsInfoDao;
	}

	//TODO this is test method to test waste time
	public List<UserProfile> getAllUserProfile() {
		List<UserProfile> userProfileList = null;
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selectAllUserProfileSql = "select user_profile_id from t_user_profile";
		conn = JDBCTools.getConnection();
		try {
			pstmt = conn.prepareStatement(selectAllUserProfileSql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			userProfileList = new ArrayList<>(rs.getRow());
			rs.beforeFirst();
			while (rs.next()) {
				UserProfile userProfile = new UserProfile();
				userProfile.setUserProfileId(rs.getInt(1));
				userProfile.setUserId(rs.getInt(2));
				userProfile.setUserName(rs.getString(3));
				userProfile.setEmail(rs.getString(4));
				userProfile.setPassword(rs.getString(5));
				userProfile.setUserLevel(rs.getInt(6));
				userProfile.setHeadPhotoSmall(rs.getString(7));
				userProfile.setHeadPhotoMiddle(rs.getString(8));
				userProfile.setHeadPhotoLarge(rs.getString(9));
				userProfile.setDetailsInfo(detailsInfoDao.getDetailsInfoWithDetailsInfoId(conn, rs.getInt(10)));
				userProfileList.add(userProfile);
				userProfileMap.put(userProfile.getUserProfileId(), userProfile);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.closeRsAndStmtAndConn(rs, pstmt, conn);
		}
		return userProfileList;
	}

	public List<UserProfile> getUserProfileListWithList(Connection conn, List<Integer> userProfileIdList) {
		String userProfileIdStr = ListTool.convertListToString(userProfileIdList);
		List<UserProfile> userProfileList = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selectUserProfileWithIdSql = "select user_profile_id from t_user_profile where user_profile_id in " + userProfileIdStr;
		try {
			pstmt = conn.prepareStatement(selectUserProfileWithIdSql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			userProfileList = new ArrayList<>(rs.getRow());
			rs.beforeFirst();
			while (rs.next()) {
				UserProfile userProfile = getUserProfileWithUserProfileId(conn, rs.getInt(1));
				if (userProfile == null) {
					userProfile = new UserProfile();
					userProfile.setUserProfileId(rs.getInt(1));
					userProfile.setUserId(rs.getInt(2));
					userProfile.setUserName(rs.getString(3));
					userProfile.setEmail(rs.getString(4));
					userProfile.setPassword(rs.getString(5));
					userProfile.setUserLevel(rs.getInt(6));
					userProfile.setHeadPhotoSmall(rs.getString(7));
					userProfile.setHeadPhotoMiddle(rs.getString(8));
					userProfile.setHeadPhotoLarge(rs.getString(9));
					userProfile.setDetailsInfo(detailsInfoDao.getDetailsInfoWithDetailsInfoId(conn, rs.getInt(10)));
					userProfileMap.put(rs.getInt(1), userProfile);
				}
				userProfileList.add(userProfile);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.closeResultSetAndStatement(rs, pstmt);
		}
		return userProfileList;
	}

	public UserProfile getUserProfileWithUserProfileId(Connection conn, int userProfileId) {
		UserProfile userProfile = userProfileMap.get(userProfileId);
		if (userProfile != null) {
			return userProfile;
		}
		Statement stmt = null;
		ResultSet rs = null;
		String selectDetailsInfoWithUserProfileIdSql = "select * from t_user_profile where user_profile_id = " + userProfileId;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(selectDetailsInfoWithUserProfileIdSql);
			if (rs.next()) {
				userProfile = new UserProfile();
				userProfile.setUserProfileId(rs.getInt(1));
				userProfile.setUserId(rs.getInt(2));
				userProfile.setUserName(rs.getString(3));
				userProfile.setEmail(rs.getString(4));
				userProfile.setPassword(rs.getString(5));
				userProfile.setUserLevel(rs.getInt(6));
				userProfile.setHeadPhotoSmall(rs.getString(7));
				userProfile.setHeadPhotoMiddle(rs.getString(8));
				userProfile.setHeadPhotoLarge(rs.getString(9));
				userProfile.setDetailsInfo(detailsInfoDao.getDetailsInfoWithDetailsInfoId(conn, rs.getInt(10)));
				userProfileMap.put(userProfileId, userProfile);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.closeResultSetAndStatement(rs, stmt);
		}
		return userProfile;
	}

	public Map<Integer, UserProfile> getUserProfileMap() {
		return userProfileMap;
	}

	public void setUserProfileMap(Map<Integer, UserProfile> userProfileMap) {
		this.userProfileMap = userProfileMap;
	}

	public DetailsInfoDao getDetailsInfoDao() {
		return detailsInfoDao;
	}

	public void setDetailsInfoDao(DetailsInfoDao detailsInfoDao) {
		this.detailsInfoDao = detailsInfoDao;
	}
}

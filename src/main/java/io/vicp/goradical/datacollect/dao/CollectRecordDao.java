package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.entity.CollectRecord;
import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CollectRecordDao {
	private UserProfileDao userProfileDao;
	private FileInfoDao fileInfoDao;

	public CollectRecordDao() {
	}

	public CollectRecordDao(UserProfileDao userProfileDao, FileInfoDao fileInfoDao) {
		this.userProfileDao = userProfileDao;
		this.fileInfoDao = fileInfoDao;
	}

	public List<CollectRecord> getCollectRecordListWithUserId(Connection conn, int userId) {
		String selectFileCollectSql = "select * from t_collect_record where user_id = " + userId;
		return getCollectRecordListWithSql(conn, selectFileCollectSql);
	}

	public List<CollectRecord> getCollectRecordListWithFileInfoId(Connection conn, int fileInfoId) {
		String selectFileCollectsSql = "select * from t_collect_record where file_id = " + fileInfoId;
		return getCollectRecordListWithSql(conn, selectFileCollectsSql);
	}

	private List<CollectRecord> getCollectRecordListWithSql(Connection conn, String sql) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CollectRecord> collectRecordList = null;
		try {
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			collectRecordList = new ArrayList<>(rs.getRow());
			rs.beforeFirst();
			while (rs.next()) {
				CollectRecord collectRecord = new CollectRecord();
				collectRecord.setCollectRecordId(rs.getInt(1));
				collectRecord.setUserProfile(userProfileDao.getUserProfileWithUserProfileId(conn, rs.getInt(2)));
				collectRecord.setFileInfo(fileInfoDao.getFileInfoWithFileInfoId(rs.getInt(3)));
				collectRecord.setCollectDate(rs.getTimestamp(4));
				collectRecordList.add(collectRecord);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.closeResultSetAndStatement(rs, pstmt);
		}
		return collectRecordList;
	}

	public UserProfileDao getUserProfileDao() {
		return userProfileDao;
	}

	public void setUserProfileDao(UserProfileDao userProfileDao) {
		this.userProfileDao = userProfileDao;
	}

	public FileInfoDao getFileInfoDao() {
		return fileInfoDao;
	}

	public void setFileInfoDao(FileInfoDao fileInfoDao) {
		this.fileInfoDao = fileInfoDao;
	}
}

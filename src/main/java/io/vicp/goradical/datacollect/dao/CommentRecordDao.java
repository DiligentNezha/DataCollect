package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.entity.CommentRecord;
import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentRecordDao {
	private UserProfileDao userProfileDao;
	private FileInfoDao fileInfoDao;

	public CommentRecordDao(){
	}

	public CommentRecordDao(UserProfileDao userProfileDao, FileInfoDao fileInfoDao) {
		this.userProfileDao = userProfileDao;
		this.fileInfoDao = fileInfoDao;
	}

	public int addCommentRecord(Connection conn, CommentRecord commentRecord) {
		PreparedStatement pstmt = null;
		String insertCommentRecordSql = "insert into t_comment_record values (null, ?, ?, ?, now())";
		int success = 0;
		try {
			pstmt = conn.prepareStatement(insertCommentRecordSql);
			pstmt.setInt(1, commentRecord.getUserProfile().getUserProfileId());
			pstmt.setInt(2, commentRecord.getFileInfo().getFileInfoId());
			pstmt.setBlob(3, new ByteArrayInputStream(commentRecord.getComment().getBytes()));
			success = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.closeStatement(pstmt);
		}
		return success;
	}

	public int deleteCommentRecord(Connection conn, int commentRecordId) {
		Statement stmt = null;
		int success = 0;
		String deleteCommentRecordIdSql = "delete from t_comment_record where comment_record_id = " + commentRecordId;
		try {
			stmt = conn.createStatement();
			success = stmt.executeUpdate(deleteCommentRecordIdSql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.closeStatement(stmt);
		}
		return success;
	}

	public List<CommentRecord> getCommentRecordListWithUserId(Connection conn, int userId) {
		String selectFileCommentSql = "select * from t_comment_record where user_id = " + userId;
		return getCommentRecordListWithSql(conn, selectFileCommentSql);
	}

	public List<CommentRecord> getCommentRecordListWithFileInfoId(Connection conn, int fileInfoId) {
		String selectFileCommentsSql = "select * from t_comment_record where file_id = " + fileInfoId;
		return getCommentRecordListWithSql(conn, selectFileCommentsSql);
	}

	//TODO this is a test method to test waste time
	public List<CommentRecord> getAllCommentRecord(Connection conn){
		String selectAllCommentRecordSql = "select * from t_comment_record";
		return getCommentRecordListWithSql(conn, selectAllCommentRecordSql);
	}

	private List<CommentRecord> getCommentRecordListWithSql(Connection conn, String sql) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CommentRecord> commentRecordList = null;
		try {
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			commentRecordList = new ArrayList<>(rs.getRow());
			rs.beforeFirst();
			while (rs.next()) {
				CommentRecord commentRecord = new CommentRecord();
				commentRecord.setCommentRecordId(rs.getInt(1));
				commentRecord.setUserProfile(userProfileDao.getUserProfileWithUserProfileId(conn, rs.getInt(2)));
				commentRecord.setFileInfo(fileInfoDao.getFileInfoWithFileInfoId(rs.getInt(3)));
				Blob blob = rs.getBlob(4);
				String commStr = new String(blob.getBytes(1, (int) blob.length()));
				commentRecord.setComment(commStr);
				commentRecord.setCommentDate(rs.getTimestamp(5));
				commentRecordList.add(commentRecord);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.closeResultSetAndStatement(rs, pstmt);
		}
		return commentRecordList;
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

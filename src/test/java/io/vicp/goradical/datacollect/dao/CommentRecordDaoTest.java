package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.model.CommentRecord;
import io.vicp.goradical.datacollect.model.FileInfo;
import io.vicp.goradical.datacollect.model.UserProfile;
import org.junit.Test;
import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class CommentRecordDaoTest {
	private CommentRecordDao commentRecordDao = DaoManager.getDao(CommentRecordDao.class);

	@Test
	public void getAllCommentRecord() throws Exception {
		Connection conn = JDBCTools.getConnection();
		List<CommentRecord> commentRecordList = commentRecordDao.getAllCommentRecord(conn);
		JDBCTools.closeConnection(conn);
		System.out.println(commentRecordList.size());
	}

	@Test
	public void addCommentRecord() throws Exception {
		CommentRecord commentRecord = new CommentRecord();
		UserProfile userProfile = new UserProfile();
		FileInfo fileInfo = new FileInfo();
		userProfile.setUserProfileId(1);
		fileInfo.setFileInfoId(1);
		commentRecord.setUserProfile(userProfile);
		commentRecord.setFileInfo(fileInfo);
		commentRecord.setComment("Hello");
		commentRecord.setCommentDate(new Date());
		Connection conn = JDBCTools.getConnection();
		System.out.println(commentRecordDao.addCommentRecord(conn, commentRecord));
		JDBCTools.closeConnection(conn);
	}

	@Test
	public void deleteCommentRecord() throws Exception {
		Connection conn = JDBCTools.getConnection();
		System.out.println(commentRecordDao.deleteCommentRecord(conn, 3617644));
		JDBCTools.closeConnection(conn);
	}

	@Test
	public void getCommentRecordListWithUserId() throws Exception {
		Connection conn = JDBCTools.getConnection();
		List<CommentRecord> commentRecordList = commentRecordDao.getCommentRecordListWithUserId(conn, 1);
		JDBCTools.closeConnection(conn);
//		for (int i = 0; i < commentRecordList.size(); i++) {
//			System.out.println(commentRecordList.get(i));
//		}

		System.out.println(commentRecordList.size());
	}

	@Test
	public void getCommentRecordListWithFileInfoId() throws Exception {
		Connection conn = JDBCTools.getConnection();
		List<CommentRecord> commentRecordList = commentRecordDao.getCommentRecordListWithFileInfoId(conn, 1);
		JDBCTools.closeConnection(conn);
//		for (int i = 0; i < commentRecordList.size(); i++) {
//			System.out.println(commentRecordList.get(i));
//		}
		System.out.println(commentRecordList.size());
	}

}
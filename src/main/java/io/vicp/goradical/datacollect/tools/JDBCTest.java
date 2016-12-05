package io.vicp.goradical.datacollect.tools;

import java.sql.*;

public class JDBCTest {

	public void commentTest() {
		Connection connection;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		connection = JDBCTools.getConnection();
		String selectCommentSql = "select comment_record_id, user_id, file_id, comment from t_comment_record where comment_record_id < ? and user_id = ?";
		try {
			pstmt = connection.prepareStatement(selectCommentSql);
			pstmt.setInt(1, 10000);
			pstmt.setInt(2, 5);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int commentRecordId = rs.getInt(1);
				int userId = rs.getInt(2);
				int fileId = rs.getInt(3);
				Blob blob = rs.getBlob(4);
				String comment = new String(blob.getBytes(1, (int) blob.length()));
				System.out.println("commentRecordId:" + commentRecordId + ",userId:" + userId + ",fileId:" + fileId + ",comment:" + comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTools.closeRsAndStmtAndConn(rs, pstmt, connection);
		}
		
	}
	
	public void test() {
		Connection con;
		Statement stmt = null;
		ResultSet rs = null;
		con = JDBCTools.getConnection();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from t_user_profile where user_id");
			int cnt = 1;
			while (rs.next()) {
				System.out.println((cnt++) + ". id:" + rs.getString("id") + " user_name:" + rs.getString("user_name")
						+ " headphoto:" + rs.getString("head_photo"));
			}
			rs.close();
			stmt.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.closeRsAndStmtAndConn(rs, stmt ,con);
		}
	}

}

package io.vicp.goradical.datacollect.other;

import io.vicp.goradical.datacollect.utils.DbUtil;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SqlTest {
	private DbUtil dbUnit = new DbUtil();
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public void testInsertComment() {
		try {
			String text = "😘😘";
			connection = dbUnit.getCon();
			String sql = "insert into t_comment_temp values (null,'abc','abc',?,'abc',1)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setBlob(1, new ByteArrayInputStream(text.getBytes()));
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testGetComment() {
		try {
			connection = dbUnit.getCon();
			String sql = "select * from t_comment_temp";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Blob blob =rs.getBlob("text");
				String temp = new String(blob.getBytes(1, (int) blob.length()));
				System.out.println(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

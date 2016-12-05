package io.vicp.goradical.datacollect.hot;

import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ActionInfoManager {
	public static void main(String[] args) {
		Map<Integer, Integer> commentMap = getRealCommentData();
		for (Map.Entry<Integer, Integer> entry : commentMap.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
	}

	public static void getAcionInfoFromWeb() {

	}

	public static Map<Integer, Integer> getRealCommentData(){
		Connection conn;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "select file_id, count(*) from t_comment_record group by file_id";
		Map<Integer, Integer> commentMap = new HashMap<>(4000);
		conn = JDBCTools.getConnection();
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				commentMap.put(rs.getInt(1), rs.getInt(2));
//				System.out.println("update t_action_info set total_comment = " + rs.getInt(2) + " where file_info_id = " + rs.getInt(1) + ";");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.closeRsAndStmtAndConn(rs, stmt, conn);
		}
		return commentMap;
	}
}

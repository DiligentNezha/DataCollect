package io.vicp.goradical.datacollect.hot;
import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NumberTask implements Runnable{
	private static Map<Integer, Integer> oldIdToNewIdMap = null;
	private static List<Integer> commentIdList = null;
	private static Integer index = 0;
	private static int commentIdListSize = 0;
	static {
		oldIdToNewIdMap = getIdMap();
		commentIdList = getCommentIdList();
		commentIdListSize = commentIdList.size();
	}
	
	@Override
	public void run() {
		while (index < commentIdListSize) {
			synchronized (index) {
				if (index < commentIdListSize) {
					int temp = index++;
				}
			}
		}
	}
	
	private static void updateId() {
		
	}
	
	private static List<Integer> getCommentIdList(){
		List<Integer> commentIdList = new ArrayList<>(3500000);
		String selectCommentIdSql = "select comment_record_id from t_comment";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		conn = JDBCTools.getConnection();
		try {
			pstmt = conn.prepareStatement(selectCommentIdSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				commentIdList.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTools.closeRsAndStmtAndConn(rs, pstmt, conn);
		}
		return commentIdList;
	}
	
	private static Map<Integer, Integer> getIdMap(){
		Map<Integer, Integer> idMap = new HashMap<>(2000000);
		String selectOldIdAndNewIdSql = "select user_id, old_user_id from t_user_temp";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		conn = JDBCTools.getConnection();
		try {
			pstmt = conn.prepareStatement(selectOldIdAndNewIdSql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				oldIdToNewIdMap.put(rs.getInt(2), rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTools.closeRsAndStmtAndConn(rs, pstmt, conn);
		}
		return idMap;
	}
}

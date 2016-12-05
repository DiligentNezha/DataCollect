package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.model.Director;
import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectorDao {
	private static Map<Integer, Director> allDirectorMap;

	public DirectorDao() {
		init();
	}

	private void init(){
		List<Director> directorList = getAllDirector();
		if (allDirectorMap == null) {
			allDirectorMap = new HashMap<>(directorList.size() * 4 / 3);
		} else {
			allDirectorMap.clear();
		}
		for (Director director : directorList) {
			allDirectorMap.put(director.getDirectorId(), director);
		}
	}

	public Map<Integer, Director> getAllDirectorMap() {
		return allDirectorMap;
	}

	public Director getDirectorWithDirectorId(int directorId) {
		return allDirectorMap.get(directorId);
	}

	private List<Director> getAllDirector(){
		List<Director> directorList = null;
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selectAllDirectorSql = "select * from t_director";
		conn = JDBCTools.getConnection();
		try {
			pstmt = conn.prepareCall(selectAllDirectorSql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			directorList = new ArrayList<>(rs.getRow());
			rs.beforeFirst();
			while (rs.next()) {
				Director director = new Director();
				director.setDirectorId(rs.getInt(1));
				director.setDirectorName(rs.getString(2));
				director.setDescription(rs.getString(3));
				directorList.add(director);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.closeRsAndStmtAndConn(rs, pstmt, conn);
		}
		return directorList;
	}
}

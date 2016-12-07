package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.entity.Nation;
import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NationDao {
	private static HashMap<Integer, Nation> allNationMap;

	public NationDao() {
		init();
	}

	private void init(){
		List<Nation> nationList = getAllNation();
		if (allNationMap == null) {
			allNationMap = new HashMap<>(nationList.size() * 4 / 3);
		} else {
			allNationMap.clear();
		}
		for (Nation nation : nationList) {
			allNationMap.put(nation.getNationId(), nation);
		}
	}

	public void updateNationMap(){
		init();
	}

	public HashMap<Integer, Nation> getAllNationMap() {
		return allNationMap;
	}

	public Nation getNationWithNationId(int nationId) {
		return allNationMap.get(nationId);
	}

	private List<Nation> getAllNation() {
		List<Nation> nationList = null;
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selectAllNationSql = "SELECT * FROM t_nation";
		conn = JDBCTools.getConnection();
		try {
			pstmt = conn.prepareCall(selectAllNationSql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			nationList = new ArrayList<>(rs.getRow());
			rs.beforeFirst();
			while (rs.next()) {
				Nation nation = new Nation();
				nation.setNationId(rs.getInt(1));
				nation.setNationName(rs.getString(2));
				nation.setDescription(rs.getString(3));
				nationList.add(nation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.closeResultSetAndStatement(rs, pstmt);
		}
		return nationList;
	}
}

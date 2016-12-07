package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.entity.Type;
import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TypeDao {
	private static HashMap<Integer, Type> allTypeMap;

	public TypeDao() {
		init();
	}

	private void init(){
		List<Type> typeList = getAllType();
		if (allTypeMap == null) {
			allTypeMap = new HashMap<>(typeList.size() * 4 / 3);
		} else {
			allTypeMap.clear();
		}
		for (Type type : typeList) {
			allTypeMap.put(type.getTypeId(), type);
		}
	}

	public void updateTypeMap(){
		init();
	}

	public HashMap<Integer, Type> getAllTypeMap() {
		return allTypeMap;
	}

	public Type getTypeWithTypeId(int typeId) {
		return allTypeMap.get(typeId);
	}

	private List<Type> getAllType() {
		List<Type> typeList = null;
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selectAllTypeSql = "SELECT * FROM t_type";
		conn = JDBCTools.getConnection();
		try {
			pstmt = conn.prepareCall(selectAllTypeSql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			typeList = new ArrayList<>(rs.getRow());
			rs.beforeFirst();
			while (rs.next()) {
				Type type = new Type();
				type.setTypeId(rs.getInt(1));
				type.setTypeName(rs.getString(2));
				type.setDescription(rs.getString(3));
				typeList.add(type);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.closeResultSetAndStatement(rs, pstmt);
		}
		return typeList;
	}
}

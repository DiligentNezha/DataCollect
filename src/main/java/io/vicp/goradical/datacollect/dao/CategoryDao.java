package io.vicp.goradical.datacollect.dao;


import io.vicp.goradical.datacollect.model.Category;
import io.vicp.goradical.datacollect.tools.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryDao {
	private static HashMap<Integer, Category> allCategoryMap;

	public CategoryDao() {
		init();
	}

	private void init(){
		List<Category> categoryList = getAllCategory();
		if (allCategoryMap == null) {
			allCategoryMap = new HashMap<>(categoryList.size() * 4 / 3);
		} else {
			allCategoryMap.clear();
		}
		for (Category category : categoryList) {
			allCategoryMap.put(category.getCategoryId(), category);
		}
	}

	public void updateCategoryMap(){
		init();
	}

	public HashMap<Integer, Category> getAllCategoryMap() {
		return allCategoryMap;
	}

	public Category getCategoryWithCategoryId(int categoryId) {
		return allCategoryMap.get(categoryId);
	}

	private List<Category> getAllCategory() {
		List<Category> categoryList = null;
		Connection conn;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String selectAllCategorySql = "SELECT * FROM t_category";
		conn = JDBCTools.getConnection();
		try {
			pstmt = conn.prepareCall(selectAllCategorySql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			rs.last();
			categoryList = new ArrayList<>(rs.getRow());
			rs.beforeFirst();
			while (rs.next()) {
				Category category = new Category();
				category.setCategoryId(rs.getInt(1));
				category.setCategory(rs.getString(2));
				category.setDescription(rs.getString(3));
				categoryList.add(category);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTools.closeRsAndStmtAndConn(rs, pstmt, conn);
		}
		return categoryList;
	}
}

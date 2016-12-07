package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.entity.Category;
import org.junit.Test;

import java.util.Map;

public class CategoryDaoTest {
	private CategoryDao categoryDao = DaoManager.getDao(CategoryDao.class);

	@Test
	public void getAllCategoryMap() throws Exception {
		Map<Integer, Category> allCategoryMap = categoryDao.getAllCategoryMap();
		for (Map.Entry<Integer, Category> entry : allCategoryMap.entrySet()) {
			System.out.println("categoryId:" + entry.getKey() + ", category:" + entry.getValue());
		}
	}

	@Test
	public void getCategoryWithCategoryId() throws Exception {
		Category category = categoryDao.getCategoryWithCategoryId(1);
		System.out.println(category);
	}

}
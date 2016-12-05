package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.model.Type;
import org.junit.Test;

import java.util.Map;

public class TypeDaoTest {
	private TypeDao typeDao = DaoManager.getDao(TypeDao.class);

	@Test
	public void getAllTypeMap() throws Exception {
		Map<Integer, Type> allTypeMap = typeDao.getAllTypeMap();
		for (Map.Entry<Integer, Type> entry : allTypeMap.entrySet()) {
			System.out.println("typeId:" + entry.getKey() + ", type:" + entry.getValue());
		}
	}

	@Test
	public void getTypeWithTypeId() throws Exception {
		Type type = typeDao.getTypeWithTypeId(1);
		System.out.println(type);
	}

}
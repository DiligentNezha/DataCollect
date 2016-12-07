package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.entity.Nation;
import org.junit.Test;

import java.util.Map;

public class NationDaoTest {
	private NationDao nationDao = DaoManager.getDao(NationDao.class);

	@Test
	public void getAllNationMap() throws Exception {
		Map<Integer, Nation> allNationMap = nationDao.getAllNationMap();
		for (Map.Entry<Integer, Nation> entry : allNationMap.entrySet()) {
			System.out.println("nationId:" + entry.getKey() + ", nation:" + entry.getValue());
		}
	}

	@Test
	public void getNationWithNationId() throws Exception {
		Nation nation = nationDao.getNationWithNationId(1);
		System.out.println(nation);
	}

}
package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.model.Director;
import org.junit.Test;

import java.util.Map;

public class DirectorDaoTest {
	private DirectorDao directorDao = DaoManager.getDao(DirectorDao.class);

	@Test
	public void getAllDirectorMap() throws Exception {
		Map<Integer, Director> allDirectorMap = directorDao.getAllDirectorMap();
		for (Map.Entry<Integer, Director> entry : allDirectorMap.entrySet()) {
			System.out.println("directorId:" + entry.getKey() + ", director:" + entry.getValue());
		}
	}

	@Test
	public void getDirectorWithDirectorId() throws Exception {
		Director director = directorDao.getDirectorWithDirectorId(1);
		System.out.println(director);
	}

}
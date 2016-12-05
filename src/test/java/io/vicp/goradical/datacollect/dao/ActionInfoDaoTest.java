package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.model.ActionInfo;
import org.junit.Test;

import java.util.Map;

public class ActionInfoDaoTest {
	private ActionInfoDao actionInfoDao = DaoManager.getDao(ActionInfoDao.class);

	@Test
	public void getAllActionInfoMap() throws Exception {
		Map<Integer, ActionInfo> allActionInfoMap = actionInfoDao.getAllActionInfoMap();
		for (Map.Entry<Integer, ActionInfo> entry : allActionInfoMap.entrySet()) {
			System.out.println("actionInfoId:" + entry.getKey() + ", actionInfo:" + entry.getValue());
		}
	}

	@Test
	public void getActionInfoWithActionInfoId() throws Exception {
		ActionInfo actionInfo = actionInfoDao.getActionInfoWithActionInfoId(1);
		System.out.println(actionInfo);
	}

}
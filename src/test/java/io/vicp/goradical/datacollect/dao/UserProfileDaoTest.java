package io.vicp.goradical.datacollect.dao;

import io.vicp.goradical.datacollect.tools.JDBCTools;
import io.vicp.goradical.datacollect.model.UserProfile;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class UserProfileDaoTest {
	private UserProfileDao userProfileDao = DaoManager.getDao(UserProfileDao.class);

	//3min 41second 31ms
 	@Test
	public void getAllUserProfile() throws Exception {
		List<UserProfile> userProfileList = userProfileDao.getAllUserProfile();
		System.out.println(userProfileList.size());
	}

	//3min 43second
	@Test
	public void getUserProfileListWithList() throws Exception {
		List<Integer> userProfileIdList = new ArrayList<>(1920000);
		//1916955
		for(int i = 1; i <= 1916955; i++){
			userProfileIdList.add(i);
		}
		Connection conn = JDBCTools.getConnection();
		List<UserProfile> userProfileList = userProfileDao.getUserProfileListWithList(conn, userProfileIdList);
		JDBCTools.closeConnection(conn);
		System.out.println(userProfileList.size());
	}

	@Test
	public void getUserProfileWithUserProfileId() throws Exception {
		Connection conn = JDBCTools.getConnection();
		UserProfile userProfile = userProfileDao.getUserProfileWithUserProfileId(conn, 1);
		JDBCTools.closeConnection(conn);
		System.out.println(userProfile);
	}

}
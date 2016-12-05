package io.vicp.goradical.datacollect.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {
	
	public Connection getCon() throws Exception{
		Class.forName(PropertiesUtil.getValue("jdbcName"));
//		System.out.println(PropertiesUtil.getValue("dbUrl"));
		Connection con = DriverManager.getConnection(PropertiesUtil.getValue("dbUrl"), PropertiesUtil.getValue("dbUserName"), PropertiesUtil.getValue("dbPassword"));
		return con;
	}
	
	public void closeCon(Connection con) throws Exception {
		if (con != null) {
			con.close();
		}
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(new DbUtil().getCon());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

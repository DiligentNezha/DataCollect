package io.vicp.goradical.datacollect.tools;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Test {
	public static void main(String[] args) {
		System.out.println(ConfigReader.getProperty("url"));
		PoolProperties p = new PoolProperties();
		p.setUrl("io.vicp.goradical.datacollect.jdbc:mysql://localhost:3306/pmrs");
		p.setDriverClassName("com.mysql.io.vicp.goradical.datacollect.jdbc.Driver");
		p.setUsername("root");
		p.setPassword("root");
		p.setJmxEnabled(true);
		p.setTestWhileIdle(false);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT 1");
		p.setTestOnReturn(false);
		p.setValidationInterval(30000);
		p.setTimeBetweenEvictionRunsMillis(30000);
		p.setMaxActive(100);
		p.setInitialSize(10);
		p.setMaxWait(10000);
		p.setRemoveAbandonedTimeout(60);
		p.setMinEvictableIdleTimeMillis(30000);
		p.setMinIdle(10);
		p.setLogAbandoned(true);
		p.setRemoveAbandoned(true);
		p.setJdbcInterceptors("org.apache.tomcat.io.vicp.goradical.datacollect.jdbc.pool.interceptor.ConnectionState;"
				+ "org.apache.tomcat.io.vicp.goradical.datacollect.jdbc.pool.interceptor.StatementFinalizer");
		DataSource datasource = new DataSource();
		datasource.setPoolProperties(p);

		Connection con = null;
		try {
			con = datasource.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from t_user_profile where user_id < 100");
			int cnt = 1;
			while (rs.next()) {
				System.out.println((cnt++) + ". id:" + rs.getString("id") + " user_name:" + rs.getString("user_name")
						+ " headphoto:" + rs.getString("head_photo"));
			}
			rs.close();
			st.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null)
				try {
					con.close();
				} catch (Exception ignore) {
			}
		}
	}
}

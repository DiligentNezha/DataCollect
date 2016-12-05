package io.vicp.goradical.datacollect.tools;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTools {
	private static final PoolProperties POOL_PROPERTIES = new PoolProperties();
	private static final DataSource DATA_SOURCE = new DataSource();
	
	static {
		POOL_PROPERTIES.setUrl(ConfigReader.getProperty("url"));
		POOL_PROPERTIES.setDriverClassName(ConfigReader.getProperty("driver"));
		POOL_PROPERTIES.setUsername(ConfigReader.getProperty("username"));
		POOL_PROPERTIES.setPassword(ConfigReader.getProperty("password"));
		POOL_PROPERTIES.setJmxEnabled(new Boolean(ConfigReader.getProperty("jmxEnable")));
		POOL_PROPERTIES.setTestWhileIdle(new Boolean(ConfigReader.getProperty("testWhileIdle")));
		POOL_PROPERTIES.setTestOnBorrow(new Boolean(ConfigReader.getProperty("testOnBorrow")));
		POOL_PROPERTIES.setValidationQuery(ConfigReader.getProperty("validationQuery"));
		POOL_PROPERTIES.setTestOnReturn(new Boolean(ConfigReader.getProperty("testOnreturn")));
		POOL_PROPERTIES.setValidationInterval(new Long(ConfigReader.getProperty("validationInterval")));
		POOL_PROPERTIES.setTimeBetweenEvictionRunsMillis(new Integer(ConfigReader.getProperty("timeBetweenEvictionRunsMillis")));
		POOL_PROPERTIES.setMaxActive(new Integer(ConfigReader.getProperty("maxAction")));
		POOL_PROPERTIES.setInitialSize(new Integer(ConfigReader.getProperty("initialSize")));
		POOL_PROPERTIES.setMaxWait(new Integer(ConfigReader.getProperty("maxWait")));
		POOL_PROPERTIES.setRemoveAbandonedTimeout(new Integer(ConfigReader.getProperty("removeAbandonedTimeout")));
		POOL_PROPERTIES.setMinEvictableIdleTimeMillis(new Integer(ConfigReader.getProperty("minEvictableIdleTimeMillis")));
		POOL_PROPERTIES.setMinIdle(new Integer(ConfigReader.getProperty("minIdle")));
		POOL_PROPERTIES.setLogAbandoned(new Boolean(ConfigReader.getProperty("logAbandoned")));
		POOL_PROPERTIES.setRemoveAbandoned(new Boolean(ConfigReader.getProperty("removeAbandoned")));
		POOL_PROPERTIES.setJdbcInterceptors(ConfigReader.getProperty("jdbcInterceptors"));
		
		DATA_SOURCE.setPoolProperties(POOL_PROPERTIES);
	}
	
	public static final Connection getConnection() {
		Connection con = null;
		try {
			con = DATA_SOURCE.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void closeConnection(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeRsAndStmtAndConn(ResultSet rs, Statement stmt, Connection conn) {
		try {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeStatement(Statement stmt) {
		try {
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeResultSetAndStatement(ResultSet rs, Statement stmt) {
		try {
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

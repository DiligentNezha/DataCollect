package io.vicp.goradical.datacollect.jdbc;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class DataSourceTest {

	public void test() {
		DataSource dataSource = new MysqlConnectionPoolDataSource();
		Connection connection = null;
		try {
			connection = dataSource.getConnection("root", "root");
			System.out.println(connection.getCatalog());
			System.out.println(connection.getClientInfo());
			DatabaseMetaData dmd = connection.getMetaData();
			System.out.println(dmd.getResultSetHoldability());
			PreparedStatement pstmt = connection.prepareStatement("");
			ResultSet rs = pstmt.executeQuery();
			System.out.println(ResultSet.TYPE_SCROLL_INSENSITIVE);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

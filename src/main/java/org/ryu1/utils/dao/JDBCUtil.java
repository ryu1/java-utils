package org.ryu1.utils.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * @author R.Ishitsuka
 * 
 */
public class JDBCUtil {
	private static Logger log = Logger.getLogger(JDBCUtil.class);

	public static Connection getConnection(String url, String user, String password) throws HtkBatchError {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new HtkBatchError("ERROR", e);
		}

		Connection conn = null;

		if (log.isDebugEnabled()) {
			log.debug("url=" + url + ", user=" + user + ", password="
					+ password);
		}

		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO エラー処理
			throw new SQLException("", e);
		}

		return conn;
	}
}

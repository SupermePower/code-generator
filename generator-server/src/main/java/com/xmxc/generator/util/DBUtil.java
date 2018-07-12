package com.xmxc.generator.util;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DBUtil {
    public static final String URL = "jdbc:mysql://39.104.182.12:3306/sitedevelopment?useUnicode=true&amp;characterEncoding=UTF-8&useSSL=false";
    public static final String USER = "root";
    public static final String PASSWORD = "xmxc1234";
    private static Connection conn = null;

    static {
        try {
            //1.加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
            //2. 获得数据库连接
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() {
        return conn;
    }

    public static List<Map<String, String>> query(String tableName) {
        List<Map<String, String>> tableData = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select column_name, data_type, column_type, column_comment, column_key " +
                    "from information_schema.columns where table_name='" + tableName + "'");

            tableData = new ArrayList<>();
            while (rs.next()) {
                Map<String, String> result = new HashMap<>();
                result.put("column_name", rs.getString("column_name"));
                result.put("data_type", rs.getString("data_type"));
                result.put("column_comment", rs.getString("column_comment"));
                result.put("column_key", rs.getString("column_key"));
                tableData.add(result);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, stmt, rs);
        }
        return tableData;
    }

    /**
     * 关闭连接资源
     *
     * @param conn
     * @param stmt
     * @param rs
     */
    private static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

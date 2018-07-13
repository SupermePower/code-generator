package com.xmxc.generator.util;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DBUtil {
    private static Connection conn = null;

    static {
        try {
            //加载驱动程序
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     *
     * @param url      地址
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public static Connection getConnection(String url, String username, String password) {
        try {
            // 获得数据库连接
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 查询指定表结构信息
     *
     * @param url       数据库地址
     * @param username  用户名
     * @param password  密码
     * @param tableName 表名
     * @return
     */
    public static List<Map<String, String>> query(String url, String username, String password, String tableName) {
        List<Map<String, String>> tableData = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection(url, username, password);
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

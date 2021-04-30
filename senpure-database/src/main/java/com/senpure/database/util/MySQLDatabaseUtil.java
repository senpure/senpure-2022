package com.senpure.database.util;


import com.senpure.base.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class MySQLDatabaseUtil {

    protected static final Logger LOGGER = LoggerFactory.getLogger(MySQLDatabaseUtil.class);

    public static void checkAndCreateDatabase(String url, String username, String password) {
        //jdbc.url=jdbc:mysql://localhost:3306/database?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE
        int index = StringUtil.indexOf(url, "/", 1, true);
        String sampleUrl = url.substring(0, index);
        String database = "";
        int j = url.indexOf("?");
        if (j < 0) {
            database = url.substring(index + 1);
        } else {
            database = url.substring(index + 1, j);
        }
        String lowerUrl = url.toLowerCase();
        String serverTimezone = null;
        index = lowerUrl.indexOf("timezone");
        if (index > 0) {
            serverTimezone = findValue(index, url, 9);
        }
        index = lowerUrl.indexOf("encoding");
        String charSet = null;
        if (index > 0) {
            charSet = findValue(index, url, 9);
        }
        Connection connection = null;
        try {
            if (serverTimezone != null) {
                sampleUrl = sampleUrl + "?serverTimezone=" + serverTimezone;
            }
            LOGGER.debug("url {} ", sampleUrl);
            String checkSql = "SELECT information_schema.SCHEMATA.SCHEMA_NAME FROM information_schema.SCHEMATA where SCHEMA_NAME=?";
            try {
                connection = DriverManager.getConnection(sampleUrl, username, password);
            } catch (SQLException e) {
                if (serverTimezone == null) {
                    sampleUrl += "?serverTimezone=UTC";
                    LOGGER.debug("url {} ",sampleUrl);
                    connection = DriverManager.getConnection(sampleUrl, username, password);
                } else {
                    throw e;
                }
            }
            PreparedStatement preparedStatement = connection.prepareStatement(checkSql);
            preparedStatement.setString(1, database);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                LOGGER.debug("[{}]数据库存在", database);
            } else {
                LOGGER.info("[{}]数据库不存在，准备创建数据库", database);
                StringBuilder sb = new StringBuilder();
                sb.append("create DATABASE ");
                sb.append("`");
                sb.append(database);
                sb.append("`");
                if (charSet == null) {
                    //sb.append(" default character set utf8 collate utf8_general_ci");
                    sb.append(" default character set utf8mb4 collate utf8mb4_unicode_ci");
                } else {
                    sb.append(" default character set ");
                    sb.append(charSet.replace("_", "").replace("-", ""));
                }
                String createSql = sb.toString();
                LOGGER.info("创建数据库sql:{}", createSql);
                preparedStatement = connection.prepareStatement(checkSql);
                int update = preparedStatement.executeUpdate(createSql);
                if (update == 1) {
                    LOGGER.info("创建数据库[{}]成功", database);
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String findValue(int index, String url, int offset) {
        int i = url.indexOf("&amp;", index);
        if (i < 0) {
            i = url.indexOf("&", index);
        }
        if (i < 0) {
            return url.substring(index + offset);
        } else {
            return url.substring(index + offset, i);
        }
    }
}

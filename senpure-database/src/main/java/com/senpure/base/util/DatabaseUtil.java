package com.senpure.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import java.sql.*;

/**
 * DatabaseUtil
 *
 * @author senpure
 * @time 2018-06-04 17:04:57
 */
public class DatabaseUtil {

    private static Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);

    public static void checkAndCreateDatabase(DataSourceProperties prop) {
        checkAndCreateDatabase(prop.getUrl(),prop.getUsername(),prop.getPassword());

    }
    public static void checkAndCreateDatabase(String url,String username,String password) {
        int index = StringUtil.indexOf(url, "/", 1, true);
        String sampleUrl = url.substring(0, index);
        String database = "";
        int j = url.indexOf("?");
        if (j < 0) {
            database = url.substring(index + 1);
        } else {
            database = url.substring(index + 1, j);
        }
        url = url.toLowerCase();
        index = url.indexOf("encoding");
        String charSet = null;
        if (index > 0) {
            int i = url.indexOf("&amp;", index);
            if (i < 0) {
                i = url.indexOf("&", index);
            }
            if (i < 0) {
                charSet = url.substring(index + 9);
            } else {
                charSet = url.substring(index + 9, i);
            }
        }
        Connection connection = null;
        try {
            String checkSql = "SELECT information_schema.SCHEMATA.SCHEMA_NAME FROM information_schema.SCHEMATA where SCHEMA_NAME=?";
            connection = DriverManager.getConnection(sampleUrl, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(checkSql);
            preparedStatement.setString(1, database);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                logger.debug("[{}]数据库存在", database);
            } else {
                logger.debug("[{}]数据库不存在，准备创建数据库", database);
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
                logger.debug("创建数据库sql:{}", createSql);
                preparedStatement = connection.prepareStatement(checkSql);
                int update = preparedStatement.executeUpdate(createSql);
                if (update == 1) {
                    logger.debug("创建数据库[{}]成功", database);
                }
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

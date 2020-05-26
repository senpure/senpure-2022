package com.senpure.base;

import com.senpure.base.util.DateFormatUtil;

import java.util.Date;


public class PermissionConstant {

    /**
     * 数据库表前缀
     */
    public static final String DATA_BASE_PREFIX = "SENPURE";

    public static final String NAME = "SENPURE";

    public static final String  ROOT_CONTAINER_NAME = "SENPURE";

    public static final String SEQUENCE_CONTAINER_ID = "container_id";
    public static final String DEFAULT_SERVER_NAME = "application";

    public static final String CONTAINER_SEPARATOR = "-";
    public static final int CONTAINER_LEVEL_ROOT = 0;
    public static final int CONTAINER_LEVEL_COMPANY = 1;
    public static final int CONTAINER_LEVEL_DEPARTMENT = 10;
    public static final int CONTAINER_LEVEL_GROUP = 100;

    public static final Long FOREVER_TIME = 0L;
    public static final Date FOREVER_DATE = new Date(0L);


    public static final String ACCOUNT_STATE_CREATE = "CREATE";
    public static final String ACCOUNT_STATE_NORMAL = "NORMAL";
    public static final String ACCOUNT_STATE_BAN = "BAN";

    public static final String ROOT_ROLE_ID = "root.role.id";
    public static final String ROOT_CONTAINER_ID = "root.container.id";


    public static final String VALUE_TYPE_ACCOUNT_DEFAULT = "ACCOUNT_DEFAULT_VALUE";
    public static final String VALUE_TYPE_SYSTEM = "SYSTEM_VALUE";


    public static final String DATE_FORMAT_KEY = "date_format";
    public static final String DATETIME_FORMAT_KEY = "datetime_format";
    public static final String TIME_FORMAT_KEY = "time_format";

    public static final String PERMISSION_TYPE_NORMAL = "NORMAL";
    public static final String PERMISSION_TYPE_OWNER = "OWNER";
    public static final String PERMISSION_TYPE_IGNORE = "IGNORE";

    public static int ALL_OPTION_INT = -1;
    public static String ALL_OPTION_STRING = "-1";

    public static final String DEFAULT_ACCOUNT = "senpure";

    public static void main(String[] args) {
        System.out.println(DateFormatUtil.format(new Date(0)));

    }
}

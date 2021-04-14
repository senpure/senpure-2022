package com.senpure.io.server;


/**
 * Constant
 */
public class Constant {

    /**
     * 没有找到服务器
     */
    public final static String ERROR_NOT_FOUND_SERVER = "NOT_FOUND_SERVER";
    /**
     * 无法处理ASK请求
     */
    public final static String ERROR_NOT_HANDLE_VALUE_REQUEST = "NOT_HANDLE_VALUE_REQUEST";

    /**
     * 无法处理该请求,服务器没有注册该id
     */
    public final static String ERROR_NOT_HANDLE_REQUEST = "NOT_HANDLE_REQUEST";
    /**
     * 服务器错误
     */
    public final static String ERROR_SERVER_ERROR = "SERVER_ERROR ";

    /**
     * 客户端错误
     */
    public final static String ERROR_CLIENT_ERROR = "CLIENT_ERROR ";


    /**
     * 超时错误
     */
    public final static String ERROR_TIMEOUT = "TIMEOUT ";

    public static final String BREAK_TYPE_ERROR = "ERROR";
    public static final String BREAK_TYPE_USER_OFFlINE = "USER_OFFLINE";
    public static final String BREAK_TYPE_USER_CHANGE = "USER_CHANGE";
    public static final String BREAK_TYPE_USER_LEAVE = "USER_LEAVE";


    public static final String GATEWAY_METADATA_SC_PORT = "scPort";
    public static final String GATEWAY_METADATA_CS_PORT = "csPort";
}

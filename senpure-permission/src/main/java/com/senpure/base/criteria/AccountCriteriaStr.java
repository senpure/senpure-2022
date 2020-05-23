package com.senpure.base.criteria;

import com.senpure.base.criterion.CriteriaStr;
import com.senpure.base.struct.PatternDate;
import com.senpure.base.validator.DynamicDate;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 只记录账号信息相关信息
 *
 * @author senpure
 * @version 2020-5-22 16:52:02
 */
public class AccountCriteriaStr extends CriteriaStr implements Serializable {
    private static final long serialVersionUID = 1379989838L;

    //(主键)
    @ApiModelProperty(value = "(主键)", dataType = "long", example = "666666", position = 30)
    private String id;
    //乐观锁，版本控制
    @ApiModelProperty(hidden = true )
    private String version;
    //账号
    @ApiModelProperty(value = "账号", example = "account", position = 31)
    private String account;
    //密码
    @ApiModelProperty(value = "密码", example = "password", position = 32)
    private String password;
    @ApiModelProperty(example = "name", position = 33)
    private String name;
    @ApiModelProperty(dataType = "date-time", example = "2020-05-22 00:00:00", position = 34)
    private String createDate;
    //createDate 时间格式
    @ApiModelProperty(value = "createDate 格式", example = "yyyy-MM-dd HH:mm:ss", position = 35)
    private String createDatePattern ;
    @DynamicDate
    private final PatternDate createDateValid = new PatternDate();
    //当前ip255.255.255.255
    @ApiModelProperty(value = "当前ip255.255.255.255", example = "ip", position = 36)
    private String ip;
    //数字IP，只存一个最接近真实IP的数据
    @ApiModelProperty(value = "数字IP，只存一个最接近真实IP的数据", dataType = "long", example = "666666", position = 37)
    private String ipNumber;
    //当前来源，火狐，360，手机等
    @ApiModelProperty(value = "当前来源，火狐，360，手机等", example = "source", position = 38)
    private String source;
    //账号禁止登录的说明
    @ApiModelProperty(value = "账号禁止登录的说明", example = "banStr", position = 39)
    private String banStr;
    @ApiModelProperty(dataType = "long", example = "1590076800000", position = 40)
    private String banExpiryTime;
    @ApiModelProperty(dataType = "date-time", example = "2020-05-22 00:00:00", position = 41)
    private String banExpiryDate;
    //banExpiryDate 时间格式
    @ApiModelProperty(value = "banExpiryDate 格式", example = "yyyy-MM-dd HH:mm:ss", position = 42)
    private String banExpiryDatePattern ;
    @DynamicDate
    private final PatternDate banExpiryDateValid = new PatternDate();
    //本次登录时间
    @ApiModelProperty(value = "本次登录时间", dataType = "long", example = "1590076800000", position = 43)
    private String loginTime;
    //本次登录时间
    @ApiModelProperty(value = "本次登录时间", dataType = "date-time", example = "2020-05-22 00:00:00", position = 44)
    private String loginDate;
    //loginDate 时间格式
    @ApiModelProperty(value = "loginDate 格式", example = "yyyy-MM-dd HH:mm:ss", position = 45)
    private String loginDatePattern ;
    @DynamicDate
    private final PatternDate loginDateValid = new PatternDate();
    @ApiModelProperty(example = "loginType", position = 46)
    private String loginType;
    @ApiModelProperty(dataType = "boolean", position = 47)
    private String online;
    @ApiModelProperty(example = "accountState", position = 48)
    private String accountState;
    @ApiModelProperty(example = "client", position = 49)
    private String client;
    @ApiModelProperty(example = "clientVersion", position = 50)
    private String clientVersion;
    @ApiModelProperty(dataType = "int", example = "666666", position = 51)
    private String clientVersionNumber;
    @ApiModelProperty(dataType = "long", example = "1590076800000", position = 52)
    private String lastLoginTime;
    @ApiModelProperty(dataType = "date-time", example = "2020-05-22 00:00:00", position = 53)
    private String lastLoginDate;
    //lastLoginDate 时间格式
    @ApiModelProperty(value = "lastLoginDate 格式", example = "yyyy-MM-dd HH:mm:ss", position = 54)
    private String lastLoginDatePattern ;
    @DynamicDate
    private final PatternDate lastLoginDateValid = new PatternDate();
    @ApiModelProperty(dataType = "long", example = "1590076800000", position = 55)
    private String lastLogoutTime;
    @ApiModelProperty(dataType = "date-time", example = "2020-05-22 00:00:00", position = 56)
    private String lastLogoutDate;
    //lastLogoutDate 时间格式
    @ApiModelProperty(value = "lastLogoutDate 格式", example = "yyyy-MM-dd HH:mm:ss", position = 57)
    private String lastLogoutDatePattern ;
    @DynamicDate
    private final PatternDate lastLogoutDateValid = new PatternDate();
    @ApiModelProperty(example = "lastLoginType", position = 58)
    private String lastLoginType;
    @ApiModelProperty(example = "lastLogoutType", position = 59)
    private String lastLogoutType;
    @ApiModelProperty(example = "lastLoginIp", position = 60)
    private String lastLoginIp;
    @ApiModelProperty(dataType = "long", example = "666666", position = 61)
    private String lastLoginIpNumber;
    @ApiModelProperty(example = "lastLoginSource", position = 62)
    private String lastLoginSource;
    //(外键,modelName:Container,tableName:senpure_container)
    @ApiModelProperty(value = "(外键,modelName:Container,tableName:senpure_container)", dataType = "int", example = "666666", position = 63)
    private String containerId;
    @ApiModelProperty(value = "createDate 开始范围 (>=)", dataType = "date-time", example = "2020-05-22 00:00:00", position = 64)
    private String startCreateDate;
    @ApiModelProperty(value = "createDate 结束范围 (<=)", dataType = "date-time", example = "2020-05-22 23:59:59", position = 65)
    private String endCreateDate;
    @DynamicDate
    private final PatternDate startCreateDateValid = new PatternDate();
    @DynamicDate
    private final PatternDate endCreateDateValid = new PatternDate();
    @ApiModelProperty(value = "banExpiryTime 开始范围 (>=)", dataType = "long", example = "1590076800000", position = 66)
    private String startBanExpiryTime;
    @ApiModelProperty(value = "banExpiryTime 结束范围 (<=)", dataType = "long", example = "1590076800000", position = 67)
    private String endBanExpiryTime;
    @ApiModelProperty(value = "banExpiryDate 开始范围 (>=)", dataType = "date-time", example = "2020-05-22 00:00:00", position = 68)
    private String startBanExpiryDate;
    @ApiModelProperty(value = "banExpiryDate 结束范围 (<=)", dataType = "date-time", example = "2020-05-22 23:59:59", position = 69)
    private String endBanExpiryDate;
    @DynamicDate
    private final PatternDate startBanExpiryDateValid = new PatternDate();
    @DynamicDate
    private final PatternDate endBanExpiryDateValid = new PatternDate();
    @ApiModelProperty(value = "loginTime 开始范围 (>=)", dataType = "long", example = "1590076800000", position = 70)
    private String startLoginTime;
    @ApiModelProperty(value = "loginTime 结束范围 (<=)", dataType = "long", example = "1590076800000", position = 71)
    private String endLoginTime;
    @ApiModelProperty(value = "loginDate 开始范围 (>=)", dataType = "date-time", example = "2020-05-22 00:00:00", position = 72)
    private String startLoginDate;
    @ApiModelProperty(value = "loginDate 结束范围 (<=)", dataType = "date-time", example = "2020-05-22 23:59:59", position = 73)
    private String endLoginDate;
    @DynamicDate
    private final PatternDate startLoginDateValid = new PatternDate();
    @DynamicDate
    private final PatternDate endLoginDateValid = new PatternDate();
    @ApiModelProperty(value = "lastLoginTime 开始范围 (>=)", dataType = "long", example = "1590076800000", position = 74)
    private String startLastLoginTime;
    @ApiModelProperty(value = "lastLoginTime 结束范围 (<=)", dataType = "long", example = "1590076800000", position = 75)
    private String endLastLoginTime;
    @ApiModelProperty(value = "lastLoginDate 开始范围 (>=)", dataType = "date-time", example = "2020-05-22 00:00:00", position = 76)
    private String startLastLoginDate;
    @ApiModelProperty(value = "lastLoginDate 结束范围 (<=)", dataType = "date-time", example = "2020-05-22 23:59:59", position = 77)
    private String endLastLoginDate;
    @DynamicDate
    private final PatternDate startLastLoginDateValid = new PatternDate();
    @DynamicDate
    private final PatternDate endLastLoginDateValid = new PatternDate();
    @ApiModelProperty(value = "lastLogoutTime 开始范围 (>=)", dataType = "long", example = "1590076800000", position = 78)
    private String startLastLogoutTime;
    @ApiModelProperty(value = "lastLogoutTime 结束范围 (<=)", dataType = "long", example = "1590076800000", position = 79)
    private String endLastLogoutTime;
    @ApiModelProperty(value = "lastLogoutDate 开始范围 (>=)", dataType = "date-time", example = "2020-05-22 00:00:00", position = 80)
    private String startLastLogoutDate;
    @ApiModelProperty(value = "lastLogoutDate 结束范围 (<=)", dataType = "date-time", example = "2020-05-22 23:59:59", position = 81)
    private String endLastLogoutDate;
    @DynamicDate
    private final PatternDate startLastLogoutDateValid = new PatternDate();
    @DynamicDate
    private final PatternDate endLastLogoutDateValid = new PatternDate();
    //table [senpure_account][column = account] criteriaOrder
    @ApiModelProperty(value = "account 排序" , allowableValues = "ASC,DESC", position = 82)
    private String accountOrder;
    //table [senpure_account][column = name] criteriaOrder
    @ApiModelProperty(value = "name 排序" , allowableValues = "ASC,DESC", position = 83)
    private String nameOrder;
    //table [senpure_account][column = create_date] criteriaOrder
    @ApiModelProperty(value = "createDate 排序" , allowableValues = "ASC,DESC", position = 84)
    private String createDateOrder;
    //table [senpure_account][column = ban_expiry_date] criteriaOrder
    @ApiModelProperty(value = "banExpiryDate 排序" , allowableValues = "ASC,DESC", position = 85)
    private String banExpiryDateOrder;
    //table [senpure_account][column = login_date] criteriaOrder
    @ApiModelProperty(value = "loginDate 排序" , allowableValues = "ASC,DESC", position = 86)
    private String loginDateOrder;
    //table [senpure_account][column = login_type] criteriaOrder
    @ApiModelProperty(value = "loginType 排序" , allowableValues = "ASC,DESC", position = 87)
    private String loginTypeOrder;
    //table [senpure_account][column = last_login_date] criteriaOrder
    @ApiModelProperty(value = "lastLoginDate 排序" , allowableValues = "ASC,DESC", position = 88)
    private String lastLoginDateOrder;
    //table [senpure_account][column = last_logout_date] criteriaOrder
    @ApiModelProperty(value = "lastLogoutDate 排序" , allowableValues = "ASC,DESC", position = 89)
    private String lastLogoutDateOrder;
    //table [senpure_account][column = last_login_type] criteriaOrder
    @ApiModelProperty(value = "lastLoginType 排序" , allowableValues = "ASC,DESC", position = 90)
    private String lastLoginTypeOrder;
    //table [senpure_account][column = last_logout_type] criteriaOrder
    @ApiModelProperty(value = "lastLogoutType 排序" , allowableValues = "ASC,DESC", position = 91)
    private String lastLogoutTypeOrder;
    //table [senpure_account][column = container_id] criteriaOrder
    @ApiModelProperty(value = "containerId 排序" , allowableValues = "ASC,DESC", position = 92)
    private String containerIdOrder;

    public AccountCriteria toAccountCriteria() {
        AccountCriteria criteria = new AccountCriteria();
        criteria.setPage(Integer.parseInt(getPage()));
        criteria.setPageSize(Integer.parseInt(getPageSize()));
        //(主键)
        if (id != null) {
            criteria.setId(Long.valueOf(id));
        }
        //乐观锁，版本控制
        if (version != null) {
            criteria.setVersion(Integer.valueOf(version));
        }
        //账号
        if (account != null) {
            criteria.setAccount(account);
        }
        //table [senpure_account][column = account] criteriaOrder
        if (accountOrder != null) {
            criteria.setAccountOrder(accountOrder);
        }
        //密码
        if (password != null) {
            criteria.setPassword(password);
        }
        if (name != null) {
            criteria.setName(name);
        }
        //table [senpure_account][column = name] criteriaOrder
        if (nameOrder != null) {
            criteria.setNameOrder(nameOrder);
        }
        if (createDate != null) {
            criteria.setCreateDate(createDateValid.getDate());
        }
        if (startCreateDate != null) {
            criteria.setStartCreateDate(startCreateDateValid.getDate());
        }
        if (endCreateDate != null) {
            criteria.setEndCreateDate(endCreateDateValid.getDate());
        }
        //table [senpure_account][column = create_date] criteriaOrder
        if (createDateOrder != null) {
            criteria.setCreateDateOrder(createDateOrder);
        }
        //当前ip255.255.255.255
        if (ip != null) {
            criteria.setIp(ip);
        }
        //数字IP，只存一个最接近真实IP的数据
        if (ipNumber != null) {
            criteria.setIpNumber(Long.valueOf(ipNumber));
        }
        //当前来源，火狐，360，手机等
        if (source != null) {
            criteria.setSource(source);
        }
        //账号禁止登录的说明
        if (banStr != null) {
            criteria.setBanStr(banStr);
        }
        if (banExpiryTime != null) {
            criteria.setBanExpiryTime(Long.valueOf(banExpiryTime));
        }
        if (startBanExpiryTime != null) {
            criteria.setStartBanExpiryTime(Long.valueOf(startBanExpiryTime));
        }
        if (endBanExpiryTime != null) {
            criteria.setEndBanExpiryTime(Long.valueOf(endBanExpiryTime));
        }
        if (banExpiryDate != null) {
            criteria.setBanExpiryDate(banExpiryDateValid.getDate());
        }
        if (startBanExpiryDate != null) {
            criteria.setStartBanExpiryDate(startBanExpiryDateValid.getDate());
        }
        if (endBanExpiryDate != null) {
            criteria.setEndBanExpiryDate(endBanExpiryDateValid.getDate());
        }
        //table [senpure_account][column = ban_expiry_date] criteriaOrder
        if (banExpiryDateOrder != null) {
            criteria.setBanExpiryDateOrder(banExpiryDateOrder);
        }
        //本次登录时间
        if (loginTime != null) {
            criteria.setLoginTime(Long.valueOf(loginTime));
        }
        if (startLoginTime != null) {
            criteria.setStartLoginTime(Long.valueOf(startLoginTime));
        }
        if (endLoginTime != null) {
            criteria.setEndLoginTime(Long.valueOf(endLoginTime));
        }
        //本次登录时间
        if (loginDate != null) {
            criteria.setLoginDate(loginDateValid.getDate());
        }
        if (startLoginDate != null) {
            criteria.setStartLoginDate(startLoginDateValid.getDate());
        }
        if (endLoginDate != null) {
            criteria.setEndLoginDate(endLoginDateValid.getDate());
        }
        //table [senpure_account][column = login_date] criteriaOrder
        if (loginDateOrder != null) {
            criteria.setLoginDateOrder(loginDateOrder);
        }
        if (loginType != null) {
            criteria.setLoginType(loginType);
        }
        //table [senpure_account][column = login_type] criteriaOrder
        if (loginTypeOrder != null) {
            criteria.setLoginTypeOrder(loginTypeOrder);
        }
        if (online != null) {
            criteria.setOnline(Boolean.valueOf(online));
        }
        if (accountState != null) {
            criteria.setAccountState(accountState);
        }
        if (client != null) {
            criteria.setClient(client);
        }
        if (clientVersion != null) {
            criteria.setClientVersion(clientVersion);
        }
        if (clientVersionNumber != null) {
            criteria.setClientVersionNumber(Integer.valueOf(clientVersionNumber));
        }
        if (lastLoginTime != null) {
            criteria.setLastLoginTime(Long.valueOf(lastLoginTime));
        }
        if (startLastLoginTime != null) {
            criteria.setStartLastLoginTime(Long.valueOf(startLastLoginTime));
        }
        if (endLastLoginTime != null) {
            criteria.setEndLastLoginTime(Long.valueOf(endLastLoginTime));
        }
        if (lastLoginDate != null) {
            criteria.setLastLoginDate(lastLoginDateValid.getDate());
        }
        if (startLastLoginDate != null) {
            criteria.setStartLastLoginDate(startLastLoginDateValid.getDate());
        }
        if (endLastLoginDate != null) {
            criteria.setEndLastLoginDate(endLastLoginDateValid.getDate());
        }
        //table [senpure_account][column = last_login_date] criteriaOrder
        if (lastLoginDateOrder != null) {
            criteria.setLastLoginDateOrder(lastLoginDateOrder);
        }
        if (lastLogoutTime != null) {
            criteria.setLastLogoutTime(Long.valueOf(lastLogoutTime));
        }
        if (startLastLogoutTime != null) {
            criteria.setStartLastLogoutTime(Long.valueOf(startLastLogoutTime));
        }
        if (endLastLogoutTime != null) {
            criteria.setEndLastLogoutTime(Long.valueOf(endLastLogoutTime));
        }
        if (lastLogoutDate != null) {
            criteria.setLastLogoutDate(lastLogoutDateValid.getDate());
        }
        if (startLastLogoutDate != null) {
            criteria.setStartLastLogoutDate(startLastLogoutDateValid.getDate());
        }
        if (endLastLogoutDate != null) {
            criteria.setEndLastLogoutDate(endLastLogoutDateValid.getDate());
        }
        //table [senpure_account][column = last_logout_date] criteriaOrder
        if (lastLogoutDateOrder != null) {
            criteria.setLastLogoutDateOrder(lastLogoutDateOrder);
        }
        if (lastLoginType != null) {
            criteria.setLastLoginType(lastLoginType);
        }
        //table [senpure_account][column = last_login_type] criteriaOrder
        if (lastLoginTypeOrder != null) {
            criteria.setLastLoginTypeOrder(lastLoginTypeOrder);
        }
        if (lastLogoutType != null) {
            criteria.setLastLogoutType(lastLogoutType);
        }
        //table [senpure_account][column = last_logout_type] criteriaOrder
        if (lastLogoutTypeOrder != null) {
            criteria.setLastLogoutTypeOrder(lastLogoutTypeOrder);
        }
        if (lastLoginIp != null) {
            criteria.setLastLoginIp(lastLoginIp);
        }
        if (lastLoginIpNumber != null) {
            criteria.setLastLoginIpNumber(Long.valueOf(lastLoginIpNumber));
        }
        if (lastLoginSource != null) {
            criteria.setLastLoginSource(lastLoginSource);
        }
        //(外键,modelName:Container,tableName:senpure_container)
        if (containerId != null) {
            criteria.setContainerId(Integer.valueOf(containerId));
        }
        //table [senpure_account][column = container_id] criteriaOrder
        if (containerIdOrder != null) {
            criteria.setContainerIdOrder(containerIdOrder);
        }
        return criteria;
    }

    @Override
    protected void rangeStr(StringBuilder sb) {
        if (startCreateDate != null) {
            sb.append("startCreateDate=").append(startCreateDate).append(",");
        }
        if (endCreateDate != null) {
            sb.append("endCreateDate=").append(endCreateDate).append(",");
        }
        if (startBanExpiryTime != null) {
            sb.append("startBanExpiryTime=").append(startBanExpiryTime).append(",");
        }
        if (endBanExpiryTime != null) {
            sb.append("endBanExpiryTime=").append(endBanExpiryTime).append(",");
        }
        if (startBanExpiryDate != null) {
            sb.append("startBanExpiryDate=").append(startBanExpiryDate).append(",");
        }
        if (endBanExpiryDate != null) {
            sb.append("endBanExpiryDate=").append(endBanExpiryDate).append(",");
        }
        if (startLoginTime != null) {
            sb.append("startLoginTime=").append(startLoginTime).append(",");
        }
        if (endLoginTime != null) {
            sb.append("endLoginTime=").append(endLoginTime).append(",");
        }
        if (startLoginDate != null) {
            sb.append("startLoginDate=").append(startLoginDate).append(",");
        }
        if (endLoginDate != null) {
            sb.append("endLoginDate=").append(endLoginDate).append(",");
        }
        if (startLastLoginTime != null) {
            sb.append("startLastLoginTime=").append(startLastLoginTime).append(",");
        }
        if (endLastLoginTime != null) {
            sb.append("endLastLoginTime=").append(endLastLoginTime).append(",");
        }
        if (startLastLoginDate != null) {
            sb.append("startLastLoginDate=").append(startLastLoginDate).append(",");
        }
        if (endLastLoginDate != null) {
            sb.append("endLastLoginDate=").append(endLastLoginDate).append(",");
        }
        if (startLastLogoutTime != null) {
            sb.append("startLastLogoutTime=").append(startLastLogoutTime).append(",");
        }
        if (endLastLogoutTime != null) {
            sb.append("endLastLogoutTime=").append(endLastLogoutTime).append(",");
        }
        if (startLastLogoutDate != null) {
            sb.append("startLastLogoutDate=").append(startLastLogoutDate).append(",");
        }
        if (endLastLogoutDate != null) {
            sb.append("endLastLogoutDate=").append(endLastLogoutDate).append(",");
        }
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("AccountCriteriaStr{");
        if (id != null) {
            sb.append("id=").append(id).append(",");
        }
        if (version != null) {
            sb.append("version=").append(version).append(",");
        }
        if (account != null) {
            sb.append("account=").append(account).append(",");
        }
        if (password != null) {
            sb.append("password=").append(password).append(",");
        }
        if (name != null) {
            sb.append("name=").append(name).append(",");
        }
        if (createDate != null) {
            sb.append("createDate=").append(createDate).append(",");
        }
        if (ip != null) {
            sb.append("ip=").append(ip).append(",");
        }
        if (ipNumber != null) {
            sb.append("ipNumber=").append(ipNumber).append(",");
        }
        if (source != null) {
            sb.append("source=").append(source).append(",");
        }
        if (banStr != null) {
            sb.append("banStr=").append(banStr).append(",");
        }
        if (banExpiryTime != null) {
            sb.append("banExpiryTime=").append(banExpiryTime).append(",");
        }
        if (banExpiryDate != null) {
            sb.append("banExpiryDate=").append(banExpiryDate).append(",");
        }
        if (loginTime != null) {
            sb.append("loginTime=").append(loginTime).append(",");
        }
        if (loginDate != null) {
            sb.append("loginDate=").append(loginDate).append(",");
        }
        if (loginType != null) {
            sb.append("loginType=").append(loginType).append(",");
        }
        if (online != null) {
            sb.append("online=").append(online).append(",");
        }
        if (accountState != null) {
            sb.append("accountState=").append(accountState).append(",");
        }
        if (client != null) {
            sb.append("client=").append(client).append(",");
        }
        if (clientVersion != null) {
            sb.append("clientVersion=").append(clientVersion).append(",");
        }
        if (clientVersionNumber != null) {
            sb.append("clientVersionNumber=").append(clientVersionNumber).append(",");
        }
        if (lastLoginTime != null) {
            sb.append("lastLoginTime=").append(lastLoginTime).append(",");
        }
        if (lastLoginDate != null) {
            sb.append("lastLoginDate=").append(lastLoginDate).append(",");
        }
        if (lastLogoutTime != null) {
            sb.append("lastLogoutTime=").append(lastLogoutTime).append(",");
        }
        if (lastLogoutDate != null) {
            sb.append("lastLogoutDate=").append(lastLogoutDate).append(",");
        }
        if (lastLoginType != null) {
            sb.append("lastLoginType=").append(lastLoginType).append(",");
        }
        if (lastLogoutType != null) {
            sb.append("lastLogoutType=").append(lastLogoutType).append(",");
        }
        if (lastLoginIp != null) {
            sb.append("lastLoginIp=").append(lastLoginIp).append(",");
        }
        if (lastLoginIpNumber != null) {
            sb.append("lastLoginIpNumber=").append(lastLoginIpNumber).append(",");
        }
        if (lastLoginSource != null) {
            sb.append("lastLoginSource=").append(lastLoginSource).append(",");
        }
        if (containerId != null) {
            sb.append("containerId=").append(containerId).append(",");
        }
    }

    @Override
    protected void afterStr(StringBuilder sb) {
        if (accountOrder != null) {
            sb.append("accountOrder=").append(accountOrder).append(",");
        }
        if (nameOrder != null) {
            sb.append("nameOrder=").append(nameOrder).append(",");
        }
        if (createDateOrder != null) {
            sb.append("createDateOrder=").append(createDateOrder).append(",");
        }
        if (banExpiryDateOrder != null) {
            sb.append("banExpiryDateOrder=").append(banExpiryDateOrder).append(",");
        }
        if (loginDateOrder != null) {
            sb.append("loginDateOrder=").append(loginDateOrder).append(",");
        }
        if (loginTypeOrder != null) {
            sb.append("loginTypeOrder=").append(loginTypeOrder).append(",");
        }
        if (lastLoginDateOrder != null) {
            sb.append("lastLoginDateOrder=").append(lastLoginDateOrder).append(",");
        }
        if (lastLogoutDateOrder != null) {
            sb.append("lastLogoutDateOrder=").append(lastLogoutDateOrder).append(",");
        }
        if (lastLoginTypeOrder != null) {
            sb.append("lastLoginTypeOrder=").append(lastLoginTypeOrder).append(",");
        }
        if (lastLogoutTypeOrder != null) {
            sb.append("lastLogoutTypeOrder=").append(lastLogoutTypeOrder).append(",");
        }
        if (containerIdOrder != null) {
            sb.append("containerIdOrder=").append(containerIdOrder).append(",");
        }
        super.afterStr(sb);
    }

    /**
     * get (主键)
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * set (主键)
     *
     * @return
     */
    public AccountCriteriaStr setId(String id) {
        if (id != null && id.trim().length() == 0) {
            return this;
        }
        this.id = id;
        return this;
    }

    /**
     * get 乐观锁，版本控制
     *
     * @return
     */
    public String getVersion() {
        return version;
    }

    /**
     * set 乐观锁，版本控制
     *
     * @return
     */
    public AccountCriteriaStr setVersion(String version) {
        if (version != null && version.trim().length() == 0) {
            return this;
        }
        this.version = version;
        return this;
    }

    /**
     * get 账号
     *
     * @return
     */
    public String getAccount() {
        return account;
    }

    /**
     * set 账号
     *
     * @return
     */
    public AccountCriteriaStr setAccount(String account) {
        if (account != null && account.trim().length() == 0) {
            return this;
        }
        this.account = account;
        return this;
    }

    /**
     * get table [senpure_account][column = account] criteriaOrder
     *
     * @return
     */
    public String getAccountOrder() {
        return accountOrder;
    }

    /**
     * set table [senpure_account][column = account] criteriaOrder DESC||ASC
     *
     * @return
     */
    public AccountCriteriaStr setAccountOrder(String accountOrder) {
        if (accountOrder != null && accountOrder.trim().length() == 0) {
            this.accountOrder = null;
            return this;
        }
        this.accountOrder = accountOrder;
        return this;
    }

    /**
     * get 密码
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * set 密码
     *
     * @return
     */
    public AccountCriteriaStr setPassword(String password) {
        if (password != null && password.trim().length() == 0) {
            return this;
        }
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }


    public AccountCriteriaStr setName(String name) {
        if (name != null && name.trim().length() == 0) {
            return this;
        }
        this.name = name;
        return this;
    }

    /**
     * get table [senpure_account][column = name] criteriaOrder
     *
     * @return
     */
    public String getNameOrder() {
        return nameOrder;
    }

    /**
     * set table [senpure_account][column = name] criteriaOrder DESC||ASC
     *
     * @return
     */
    public AccountCriteriaStr setNameOrder(String nameOrder) {
        if (nameOrder != null && nameOrder.trim().length() == 0) {
            this.nameOrder = null;
            return this;
        }
        this.nameOrder = nameOrder;
        return this;
    }

    public String getCreateDate() {
        return createDate;
    }


    public AccountCriteriaStr setCreateDate(String createDate) {
        if (createDate != null && createDate.trim().length() == 0) {
            return this;
        }
        this.createDate = createDate;
        this.createDateValid.setDateStr(createDate);
        return this;
    }

    public String getCreateDatePattern() {
        return createDatePattern;
    }

    public AccountCriteriaStr setCreateDatePattern(String createDatePattern) {
        if (createDatePattern != null && createDatePattern.trim().length() == 0) {
            return this;
        }
        this.createDatePattern = createDatePattern;
        this.createDateValid.setPattern(createDatePattern);
        this.startCreateDateValid.setPattern(createDatePattern);
        this.endCreateDateValid.setPattern(createDatePattern);
        return this;
    }

    public String getStartCreateDate() {
        return startCreateDate;
    }

    public AccountCriteriaStr setStartCreateDate(String startCreateDate) {
        if (startCreateDate != null && startCreateDate.trim().length() == 0) {
            return this;
        }
        this.startCreateDate = startCreateDate;
        this.startCreateDateValid.setDateStr(startCreateDate);
        return this;
    }

    public String getEndCreateDate() {
        return endCreateDate;
    }

    public AccountCriteriaStr setEndCreateDate(String endCreateDate) {
        if (endCreateDate != null && endCreateDate.trim().length() == 0) {
            return this;
        }
        this.endCreateDate = endCreateDate;
        this.endCreateDateValid.setDateStr(endCreateDate);
        return this;
    }

    /**
     * get table [senpure_account][column = create_date] criteriaOrder
     *
     * @return
     */
    public String getCreateDateOrder() {
        return createDateOrder;
    }

    /**
     * set table [senpure_account][column = create_date] criteriaOrder DESC||ASC
     *
     * @return
     */
    public AccountCriteriaStr setCreateDateOrder(String createDateOrder) {
        if (createDateOrder != null && createDateOrder.trim().length() == 0) {
            this.createDateOrder = null;
            return this;
        }
        this.createDateOrder = createDateOrder;
        return this;
    }

    /**
     * get 当前ip255.255.255.255
     *
     * @return
     */
    public String getIp() {
        return ip;
    }

    /**
     * set 当前ip255.255.255.255
     *
     * @return
     */
    public AccountCriteriaStr setIp(String ip) {
        if (ip != null && ip.trim().length() == 0) {
            return this;
        }
        this.ip = ip;
        return this;
    }

    /**
     * get 数字IP，只存一个最接近真实IP的数据
     *
     * @return
     */
    public String getIpNumber() {
        return ipNumber;
    }

    /**
     * set 数字IP，只存一个最接近真实IP的数据
     *
     * @return
     */
    public AccountCriteriaStr setIpNumber(String ipNumber) {
        if (ipNumber != null && ipNumber.trim().length() == 0) {
            return this;
        }
        this.ipNumber = ipNumber;
        return this;
    }

    /**
     * get 当前来源，火狐，360，手机等
     *
     * @return
     */
    public String getSource() {
        return source;
    }

    /**
     * set 当前来源，火狐，360，手机等
     *
     * @return
     */
    public AccountCriteriaStr setSource(String source) {
        if (source != null && source.trim().length() == 0) {
            return this;
        }
        this.source = source;
        return this;
    }

    /**
     * get 账号禁止登录的说明
     *
     * @return
     */
    public String getBanStr() {
        return banStr;
    }

    /**
     * set 账号禁止登录的说明
     *
     * @return
     */
    public AccountCriteriaStr setBanStr(String banStr) {
        if (banStr != null && banStr.trim().length() == 0) {
            return this;
        }
        this.banStr = banStr;
        return this;
    }

    public String getBanExpiryTime() {
        return banExpiryTime;
    }


    public AccountCriteriaStr setBanExpiryTime(String banExpiryTime) {
        if (banExpiryTime != null && banExpiryTime.trim().length() == 0) {
            return this;
        }
        this.banExpiryTime = banExpiryTime;
        return this;
    }

    public String getStartBanExpiryTime() {
        return startBanExpiryTime;
    }

    public AccountCriteriaStr setStartBanExpiryTime(String startBanExpiryTime) {
        if (startBanExpiryTime != null && startBanExpiryTime.trim().length() == 0) {
            return this;
        }
        this.startBanExpiryTime = startBanExpiryTime;
        return this;
    }

    public String getEndBanExpiryTime() {
        return endBanExpiryTime;
    }

    public AccountCriteriaStr setEndBanExpiryTime(String endBanExpiryTime) {
        if (endBanExpiryTime != null && endBanExpiryTime.trim().length() == 0) {
            return this;
        }
        this.endBanExpiryTime = endBanExpiryTime;
        return this;
    }

    public String getBanExpiryDate() {
        return banExpiryDate;
    }


    public AccountCriteriaStr setBanExpiryDate(String banExpiryDate) {
        if (banExpiryDate != null && banExpiryDate.trim().length() == 0) {
            return this;
        }
        this.banExpiryDate = banExpiryDate;
        this.banExpiryDateValid.setDateStr(banExpiryDate);
        return this;
    }

    public String getBanExpiryDatePattern() {
        return banExpiryDatePattern;
    }

    public AccountCriteriaStr setBanExpiryDatePattern(String banExpiryDatePattern) {
        if (banExpiryDatePattern != null && banExpiryDatePattern.trim().length() == 0) {
            return this;
        }
        this.banExpiryDatePattern = banExpiryDatePattern;
        this.banExpiryDateValid.setPattern(banExpiryDatePattern);
        this.startBanExpiryDateValid.setPattern(banExpiryDatePattern);
        this.endBanExpiryDateValid.setPattern(banExpiryDatePattern);
        return this;
    }

    public String getStartBanExpiryDate() {
        return startBanExpiryDate;
    }

    public AccountCriteriaStr setStartBanExpiryDate(String startBanExpiryDate) {
        if (startBanExpiryDate != null && startBanExpiryDate.trim().length() == 0) {
            return this;
        }
        this.startBanExpiryDate = startBanExpiryDate;
        this.startBanExpiryDateValid.setDateStr(startBanExpiryDate);
        return this;
    }

    public String getEndBanExpiryDate() {
        return endBanExpiryDate;
    }

    public AccountCriteriaStr setEndBanExpiryDate(String endBanExpiryDate) {
        if (endBanExpiryDate != null && endBanExpiryDate.trim().length() == 0) {
            return this;
        }
        this.endBanExpiryDate = endBanExpiryDate;
        this.endBanExpiryDateValid.setDateStr(endBanExpiryDate);
        return this;
    }

    /**
     * get table [senpure_account][column = ban_expiry_date] criteriaOrder
     *
     * @return
     */
    public String getBanExpiryDateOrder() {
        return banExpiryDateOrder;
    }

    /**
     * set table [senpure_account][column = ban_expiry_date] criteriaOrder DESC||ASC
     *
     * @return
     */
    public AccountCriteriaStr setBanExpiryDateOrder(String banExpiryDateOrder) {
        if (banExpiryDateOrder != null && banExpiryDateOrder.trim().length() == 0) {
            this.banExpiryDateOrder = null;
            return this;
        }
        this.banExpiryDateOrder = banExpiryDateOrder;
        return this;
    }

    /**
     * get 本次登录时间
     *
     * @return
     */
    public String getLoginTime() {
        return loginTime;
    }

    /**
     * set 本次登录时间
     *
     * @return
     */
    public AccountCriteriaStr setLoginTime(String loginTime) {
        if (loginTime != null && loginTime.trim().length() == 0) {
            return this;
        }
        this.loginTime = loginTime;
        return this;
    }

    /**
     * get start 本次登录时间
     *
     * @return
     */
    public String getStartLoginTime() {
        return startLoginTime;
    }

    /**
     * set start 本次登录时间
     *
     * @return
     */
    public AccountCriteriaStr setStartLoginTime(String startLoginTime) {
        if (startLoginTime != null && startLoginTime.trim().length() == 0) {
            return this;
        }
        this.startLoginTime = startLoginTime;
        return this;
    }

    /**
     * get end 本次登录时间
     *
     * @return
     */
    public String getEndLoginTime() {
        return endLoginTime;
    }

    /**
     * set end 本次登录时间
     *
     * @return
     */
    public AccountCriteriaStr setEndLoginTime(String endLoginTime) {
        if (endLoginTime != null && endLoginTime.trim().length() == 0) {
            return this;
        }
        this.endLoginTime = endLoginTime;
        return this;
    }

    /**
     * get 本次登录时间
     *
     * @return
     */
    public String getLoginDate() {
        return loginDate;
    }

    /**
     * set 本次登录时间
     *
     * @return
     */
    public AccountCriteriaStr setLoginDate(String loginDate) {
        if (loginDate != null && loginDate.trim().length() == 0) {
            return this;
        }
        this.loginDate = loginDate;
        this.loginDateValid.setDateStr(loginDate);
        return this;
    }

    public String getLoginDatePattern() {
        return loginDatePattern;
    }

    public AccountCriteriaStr setLoginDatePattern(String loginDatePattern) {
        if (loginDatePattern != null && loginDatePattern.trim().length() == 0) {
            return this;
        }
        this.loginDatePattern = loginDatePattern;
        this.loginDateValid.setPattern(loginDatePattern);
        this.startLoginDateValid.setPattern(loginDatePattern);
        this.endLoginDateValid.setPattern(loginDatePattern);
        return this;
    }

    /**
     * get start 本次登录时间
     *
     * @return
     */
    public String getStartLoginDate() {
        return startLoginDate;
    }

    /**
     * set start 本次登录时间
     *
     * @return
     */
    public AccountCriteriaStr setStartLoginDate(String startLoginDate) {
        if (startLoginDate != null && startLoginDate.trim().length() == 0) {
            return this;
        }
        this.startLoginDate = startLoginDate;
        this.startLoginDateValid.setDateStr(startLoginDate);
        return this;
    }

    /**
     * get end 本次登录时间
     *
     * @return
     */
    public String getEndLoginDate() {
        return endLoginDate;
    }

    /**
     * set end 本次登录时间
     *
     * @return
     */
    public AccountCriteriaStr setEndLoginDate(String endLoginDate) {
        if (endLoginDate != null && endLoginDate.trim().length() == 0) {
            return this;
        }
        this.endLoginDate = endLoginDate;
        this.endLoginDateValid.setDateStr(endLoginDate);
        return this;
    }

    /**
     * get table [senpure_account][column = login_date] criteriaOrder
     *
     * @return
     */
    public String getLoginDateOrder() {
        return loginDateOrder;
    }

    /**
     * set table [senpure_account][column = login_date] criteriaOrder DESC||ASC
     *
     * @return
     */
    public AccountCriteriaStr setLoginDateOrder(String loginDateOrder) {
        if (loginDateOrder != null && loginDateOrder.trim().length() == 0) {
            this.loginDateOrder = null;
            return this;
        }
        this.loginDateOrder = loginDateOrder;
        return this;
    }

    public String getLoginType() {
        return loginType;
    }


    public AccountCriteriaStr setLoginType(String loginType) {
        if (loginType != null && loginType.trim().length() == 0) {
            return this;
        }
        this.loginType = loginType;
        return this;
    }

    /**
     * get table [senpure_account][column = login_type] criteriaOrder
     *
     * @return
     */
    public String getLoginTypeOrder() {
        return loginTypeOrder;
    }

    /**
     * set table [senpure_account][column = login_type] criteriaOrder DESC||ASC
     *
     * @return
     */
    public AccountCriteriaStr setLoginTypeOrder(String loginTypeOrder) {
        if (loginTypeOrder != null && loginTypeOrder.trim().length() == 0) {
            this.loginTypeOrder = null;
            return this;
        }
        this.loginTypeOrder = loginTypeOrder;
        return this;
    }

    public String getOnline() {
        return online;
    }


    public AccountCriteriaStr setOnline(String online) {
        if (online != null && online.trim().length() == 0) {
            return this;
        }
        this.online = online;
        return this;
    }

    public String getAccountState() {
        return accountState;
    }


    public AccountCriteriaStr setAccountState(String accountState) {
        if (accountState != null && accountState.trim().length() == 0) {
            return this;
        }
        this.accountState = accountState;
        return this;
    }

    public String getClient() {
        return client;
    }


    public AccountCriteriaStr setClient(String client) {
        if (client != null && client.trim().length() == 0) {
            return this;
        }
        this.client = client;
        return this;
    }

    public String getClientVersion() {
        return clientVersion;
    }


    public AccountCriteriaStr setClientVersion(String clientVersion) {
        if (clientVersion != null && clientVersion.trim().length() == 0) {
            return this;
        }
        this.clientVersion = clientVersion;
        return this;
    }

    public String getClientVersionNumber() {
        return clientVersionNumber;
    }


    public AccountCriteriaStr setClientVersionNumber(String clientVersionNumber) {
        if (clientVersionNumber != null && clientVersionNumber.trim().length() == 0) {
            return this;
        }
        this.clientVersionNumber = clientVersionNumber;
        return this;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }


    public AccountCriteriaStr setLastLoginTime(String lastLoginTime) {
        if (lastLoginTime != null && lastLoginTime.trim().length() == 0) {
            return this;
        }
        this.lastLoginTime = lastLoginTime;
        return this;
    }

    public String getStartLastLoginTime() {
        return startLastLoginTime;
    }

    public AccountCriteriaStr setStartLastLoginTime(String startLastLoginTime) {
        if (startLastLoginTime != null && startLastLoginTime.trim().length() == 0) {
            return this;
        }
        this.startLastLoginTime = startLastLoginTime;
        return this;
    }

    public String getEndLastLoginTime() {
        return endLastLoginTime;
    }

    public AccountCriteriaStr setEndLastLoginTime(String endLastLoginTime) {
        if (endLastLoginTime != null && endLastLoginTime.trim().length() == 0) {
            return this;
        }
        this.endLastLoginTime = endLastLoginTime;
        return this;
    }

    public String getLastLoginDate() {
        return lastLoginDate;
    }


    public AccountCriteriaStr setLastLoginDate(String lastLoginDate) {
        if (lastLoginDate != null && lastLoginDate.trim().length() == 0) {
            return this;
        }
        this.lastLoginDate = lastLoginDate;
        this.lastLoginDateValid.setDateStr(lastLoginDate);
        return this;
    }

    public String getLastLoginDatePattern() {
        return lastLoginDatePattern;
    }

    public AccountCriteriaStr setLastLoginDatePattern(String lastLoginDatePattern) {
        if (lastLoginDatePattern != null && lastLoginDatePattern.trim().length() == 0) {
            return this;
        }
        this.lastLoginDatePattern = lastLoginDatePattern;
        this.lastLoginDateValid.setPattern(lastLoginDatePattern);
        this.startLastLoginDateValid.setPattern(lastLoginDatePattern);
        this.endLastLoginDateValid.setPattern(lastLoginDatePattern);
        return this;
    }

    public String getStartLastLoginDate() {
        return startLastLoginDate;
    }

    public AccountCriteriaStr setStartLastLoginDate(String startLastLoginDate) {
        if (startLastLoginDate != null && startLastLoginDate.trim().length() == 0) {
            return this;
        }
        this.startLastLoginDate = startLastLoginDate;
        this.startLastLoginDateValid.setDateStr(startLastLoginDate);
        return this;
    }

    public String getEndLastLoginDate() {
        return endLastLoginDate;
    }

    public AccountCriteriaStr setEndLastLoginDate(String endLastLoginDate) {
        if (endLastLoginDate != null && endLastLoginDate.trim().length() == 0) {
            return this;
        }
        this.endLastLoginDate = endLastLoginDate;
        this.endLastLoginDateValid.setDateStr(endLastLoginDate);
        return this;
    }

    /**
     * get table [senpure_account][column = last_login_date] criteriaOrder
     *
     * @return
     */
    public String getLastLoginDateOrder() {
        return lastLoginDateOrder;
    }

    /**
     * set table [senpure_account][column = last_login_date] criteriaOrder DESC||ASC
     *
     * @return
     */
    public AccountCriteriaStr setLastLoginDateOrder(String lastLoginDateOrder) {
        if (lastLoginDateOrder != null && lastLoginDateOrder.trim().length() == 0) {
            this.lastLoginDateOrder = null;
            return this;
        }
        this.lastLoginDateOrder = lastLoginDateOrder;
        return this;
    }

    public String getLastLogoutTime() {
        return lastLogoutTime;
    }


    public AccountCriteriaStr setLastLogoutTime(String lastLogoutTime) {
        if (lastLogoutTime != null && lastLogoutTime.trim().length() == 0) {
            return this;
        }
        this.lastLogoutTime = lastLogoutTime;
        return this;
    }

    public String getStartLastLogoutTime() {
        return startLastLogoutTime;
    }

    public AccountCriteriaStr setStartLastLogoutTime(String startLastLogoutTime) {
        if (startLastLogoutTime != null && startLastLogoutTime.trim().length() == 0) {
            return this;
        }
        this.startLastLogoutTime = startLastLogoutTime;
        return this;
    }

    public String getEndLastLogoutTime() {
        return endLastLogoutTime;
    }

    public AccountCriteriaStr setEndLastLogoutTime(String endLastLogoutTime) {
        if (endLastLogoutTime != null && endLastLogoutTime.trim().length() == 0) {
            return this;
        }
        this.endLastLogoutTime = endLastLogoutTime;
        return this;
    }

    public String getLastLogoutDate() {
        return lastLogoutDate;
    }


    public AccountCriteriaStr setLastLogoutDate(String lastLogoutDate) {
        if (lastLogoutDate != null && lastLogoutDate.trim().length() == 0) {
            return this;
        }
        this.lastLogoutDate = lastLogoutDate;
        this.lastLogoutDateValid.setDateStr(lastLogoutDate);
        return this;
    }

    public String getLastLogoutDatePattern() {
        return lastLogoutDatePattern;
    }

    public AccountCriteriaStr setLastLogoutDatePattern(String lastLogoutDatePattern) {
        if (lastLogoutDatePattern != null && lastLogoutDatePattern.trim().length() == 0) {
            return this;
        }
        this.lastLogoutDatePattern = lastLogoutDatePattern;
        this.lastLogoutDateValid.setPattern(lastLogoutDatePattern);
        this.startLastLogoutDateValid.setPattern(lastLogoutDatePattern);
        this.endLastLogoutDateValid.setPattern(lastLogoutDatePattern);
        return this;
    }

    public String getStartLastLogoutDate() {
        return startLastLogoutDate;
    }

    public AccountCriteriaStr setStartLastLogoutDate(String startLastLogoutDate) {
        if (startLastLogoutDate != null && startLastLogoutDate.trim().length() == 0) {
            return this;
        }
        this.startLastLogoutDate = startLastLogoutDate;
        this.startLastLogoutDateValid.setDateStr(startLastLogoutDate);
        return this;
    }

    public String getEndLastLogoutDate() {
        return endLastLogoutDate;
    }

    public AccountCriteriaStr setEndLastLogoutDate(String endLastLogoutDate) {
        if (endLastLogoutDate != null && endLastLogoutDate.trim().length() == 0) {
            return this;
        }
        this.endLastLogoutDate = endLastLogoutDate;
        this.endLastLogoutDateValid.setDateStr(endLastLogoutDate);
        return this;
    }

    /**
     * get table [senpure_account][column = last_logout_date] criteriaOrder
     *
     * @return
     */
    public String getLastLogoutDateOrder() {
        return lastLogoutDateOrder;
    }

    /**
     * set table [senpure_account][column = last_logout_date] criteriaOrder DESC||ASC
     *
     * @return
     */
    public AccountCriteriaStr setLastLogoutDateOrder(String lastLogoutDateOrder) {
        if (lastLogoutDateOrder != null && lastLogoutDateOrder.trim().length() == 0) {
            this.lastLogoutDateOrder = null;
            return this;
        }
        this.lastLogoutDateOrder = lastLogoutDateOrder;
        return this;
    }

    public String getLastLoginType() {
        return lastLoginType;
    }


    public AccountCriteriaStr setLastLoginType(String lastLoginType) {
        if (lastLoginType != null && lastLoginType.trim().length() == 0) {
            return this;
        }
        this.lastLoginType = lastLoginType;
        return this;
    }

    /**
     * get table [senpure_account][column = last_login_type] criteriaOrder
     *
     * @return
     */
    public String getLastLoginTypeOrder() {
        return lastLoginTypeOrder;
    }

    /**
     * set table [senpure_account][column = last_login_type] criteriaOrder DESC||ASC
     *
     * @return
     */
    public AccountCriteriaStr setLastLoginTypeOrder(String lastLoginTypeOrder) {
        if (lastLoginTypeOrder != null && lastLoginTypeOrder.trim().length() == 0) {
            this.lastLoginTypeOrder = null;
            return this;
        }
        this.lastLoginTypeOrder = lastLoginTypeOrder;
        return this;
    }

    public String getLastLogoutType() {
        return lastLogoutType;
    }


    public AccountCriteriaStr setLastLogoutType(String lastLogoutType) {
        if (lastLogoutType != null && lastLogoutType.trim().length() == 0) {
            return this;
        }
        this.lastLogoutType = lastLogoutType;
        return this;
    }

    /**
     * get table [senpure_account][column = last_logout_type] criteriaOrder
     *
     * @return
     */
    public String getLastLogoutTypeOrder() {
        return lastLogoutTypeOrder;
    }

    /**
     * set table [senpure_account][column = last_logout_type] criteriaOrder DESC||ASC
     *
     * @return
     */
    public AccountCriteriaStr setLastLogoutTypeOrder(String lastLogoutTypeOrder) {
        if (lastLogoutTypeOrder != null && lastLogoutTypeOrder.trim().length() == 0) {
            this.lastLogoutTypeOrder = null;
            return this;
        }
        this.lastLogoutTypeOrder = lastLogoutTypeOrder;
        return this;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }


    public AccountCriteriaStr setLastLoginIp(String lastLoginIp) {
        if (lastLoginIp != null && lastLoginIp.trim().length() == 0) {
            return this;
        }
        this.lastLoginIp = lastLoginIp;
        return this;
    }

    public String getLastLoginIpNumber() {
        return lastLoginIpNumber;
    }


    public AccountCriteriaStr setLastLoginIpNumber(String lastLoginIpNumber) {
        if (lastLoginIpNumber != null && lastLoginIpNumber.trim().length() == 0) {
            return this;
        }
        this.lastLoginIpNumber = lastLoginIpNumber;
        return this;
    }

    public String getLastLoginSource() {
        return lastLoginSource;
    }


    public AccountCriteriaStr setLastLoginSource(String lastLoginSource) {
        if (lastLoginSource != null && lastLoginSource.trim().length() == 0) {
            return this;
        }
        this.lastLoginSource = lastLoginSource;
        return this;
    }

    /**
     * get (外键,modelName:Container,tableName:senpure_container)
     *
     * @return
     */
    public String getContainerId() {
        return containerId;
    }

    /**
     * set (外键,modelName:Container,tableName:senpure_container)
     *
     * @return
     */
    public AccountCriteriaStr setContainerId(String containerId) {
        if (containerId != null && containerId.trim().length() == 0) {
            return this;
        }
        this.containerId = containerId;
        return this;
    }

    /**
     * get table [senpure_account][column = container_id] criteriaOrder
     *
     * @return
     */
    public String getContainerIdOrder() {
        return containerIdOrder;
    }

    /**
     * set table [senpure_account][column = container_id] criteriaOrder DESC||ASC
     *
     * @return
     */
    public AccountCriteriaStr setContainerIdOrder(String containerIdOrder) {
        if (containerIdOrder != null && containerIdOrder.trim().length() == 0) {
            this.containerIdOrder = null;
            return this;
        }
        this.containerIdOrder = containerIdOrder;
        return this;
    }

}
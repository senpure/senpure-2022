package com.senpure.base.criteria;

import com.senpure.base.criterion.Criteria;
import com.senpure.base.model.Account;
import com.senpure.base.util.DateFormatUtil;

import java.util.Date;
import java.io.Serializable;

/**
 * 只记录账号信息相关信息
 *
 * @author senpure
 * @version 2020-5-20 18:21:45
 */
public class AccountCriteria extends Criteria implements Serializable {
    private static final long serialVersionUID = 1379989838L;

    //(主键)
    private Long id;
    //乐观锁，版本控制
    private Integer version;
    //账号
    private String account;
    //密码
    private String password;
    private String name;
    private Date createDate;
    private Date startCreateDate;
    private Date endCreateDate;
    //table [senpure_account][column = create_date] criteriaOrder
    private String createDateOrder;
    //当前ip255.255.255.255
    private String ip;
    //数字IP，只存一个最接近真实IP的数据
    private Long ipNumber;
    //当前来源，火狐，360，手机等
    private String source;
    //账号禁止登录的说明
    private String banStr;
    private Long banExpiryTime;
    private Long startBanExpiryTime;
    private Long endBanExpiryTime;
    private Date banExpiryDate;
    private Date startBanExpiryDate;
    private Date endBanExpiryDate;
    //table [senpure_account][column = ban_expiry_date] criteriaOrder
    private String banExpiryDateOrder;
    //本次登录时间
    private Long loginTime;
    private Long startLoginTime;
    private Long endLoginTime;
    //本次登录时间
    private Date loginDate;
    private Date startLoginDate;
    private Date endLoginDate;
    //table [senpure_account][column = login_date] criteriaOrder
    private String loginDateOrder;
    private String loginType;
    private Boolean online;
    private String accountState;
    private String client;
    private String clientVersion;
    private Integer clientVersionNumber;
    private Long lastLoginTime;
    private Long startLastLoginTime;
    private Long endLastLoginTime;
    private Date lastLoginDate;
    private Date startLastLoginDate;
    private Date endLastLoginDate;
    //table [senpure_account][column = last_login_date] criteriaOrder
    private String lastLoginDateOrder;
    private Long lastLogoutTime;
    private Long startLastLogoutTime;
    private Long endLastLogoutTime;
    private Date lastLogoutDate;
    private Date startLastLogoutDate;
    private Date endLastLogoutDate;
    //table [senpure_account][column = last_logout_date] criteriaOrder
    private String lastLogoutDateOrder;
    private String lastLoginType;
    private String lastLogoutType;
    private String lastLoginIp;
    private Long lastLoginIpNumber;
    private String lastLoginSource;
    //(外键,modelName:Container,tableName:senpure_container)
    private Integer containerId;
    //table [senpure_account][column = container_id] criteriaOrder
    private String containerIdOrder;

    public static Account toAccount(AccountCriteria criteria, Account account) {
        account.setId(criteria.getId());
        account.setAccount(criteria.getAccount());
        account.setPassword(criteria.getPassword());
        account.setName(criteria.getName());
        account.setCreateDate(criteria.getCreateDate());
        if (criteria.getCreateDate() != null) {
            account.setCreateTime(criteria.getCreateDate().getTime());
        }
        account.setIp(criteria.getIp());
        account.setIpNumber(criteria.getIpNumber());
        account.setSource(criteria.getSource());
        account.setBanStr(criteria.getBanStr());
        account.setBanExpiryTime(criteria.getBanExpiryTime());
        account.setBanExpiryDate(criteria.getBanExpiryDate());
        if (criteria.getBanExpiryDate() != null) {
            account.setCreateTime(criteria.getBanExpiryDate().getTime());
        }
        account.setLoginTime(criteria.getLoginTime());
        account.setLoginDate(criteria.getLoginDate());
        if (criteria.getLoginDate() != null) {
            account.setCreateTime(criteria.getLoginDate().getTime());
        }
        account.setLoginType(criteria.getLoginType());
        account.setOnline(criteria.getOnline());
        account.setAccountState(criteria.getAccountState());
        account.setClient(criteria.getClient());
        account.setClientVersion(criteria.getClientVersion());
        account.setClientVersionNumber(criteria.getClientVersionNumber());
        account.setLastLoginTime(criteria.getLastLoginTime());
        account.setLastLoginDate(criteria.getLastLoginDate());
        if (criteria.getLastLoginDate() != null) {
            account.setCreateTime(criteria.getLastLoginDate().getTime());
        }
        account.setLastLogoutTime(criteria.getLastLogoutTime());
        account.setLastLogoutDate(criteria.getLastLogoutDate());
        if (criteria.getLastLogoutDate() != null) {
            account.setCreateTime(criteria.getLastLogoutDate().getTime());
        }
        account.setLastLoginType(criteria.getLastLoginType());
        account.setLastLogoutType(criteria.getLastLogoutType());
        account.setLastLoginIp(criteria.getLastLoginIp());
        account.setLastLoginIpNumber(criteria.getLastLoginIpNumber());
        account.setLastLoginSource(criteria.getLastLoginSource());
        account.setContainerId(criteria.getContainerId());
        account.setVersion(criteria.getVersion());
        return account;
    }

    public Account toAccount() {
        Account account = new Account();
        return toAccount(this, account);
    }

    /**
     * 将AccountCriteria 的有效值(不为空),赋值给 Account
     *
     * @return Account
     */
    public Account effective(Account account) {
        if (getId() != null) {
            account.setId(getId());
        }
        if (getAccount() != null) {
            account.setAccount(getAccount());
        }
        if (getPassword() != null) {
            account.setPassword(getPassword());
        }
        if (getName() != null) {
            account.setName(getName());
        }
        if (getCreateDate() != null) {
            account.setCreateDate(getCreateDate());
        }
        if (getIp() != null) {
            account.setIp(getIp());
        }
        if (getIpNumber() != null) {
            account.setIpNumber(getIpNumber());
        }
        if (getSource() != null) {
            account.setSource(getSource());
        }
        if (getBanStr() != null) {
            account.setBanStr(getBanStr());
        }
        if (getBanExpiryTime() != null) {
            account.setBanExpiryTime(getBanExpiryTime());
        }
        if (getBanExpiryDate() != null) {
            account.setBanExpiryDate(getBanExpiryDate());
        }
        if (getLoginTime() != null) {
            account.setLoginTime(getLoginTime());
        }
        if (getLoginDate() != null) {
            account.setLoginDate(getLoginDate());
        }
        if (getLoginType() != null) {
            account.setLoginType(getLoginType());
        }
        if (getOnline() != null) {
            account.setOnline(getOnline());
        }
        if (getAccountState() != null) {
            account.setAccountState(getAccountState());
        }
        if (getClient() != null) {
            account.setClient(getClient());
        }
        if (getClientVersion() != null) {
            account.setClientVersion(getClientVersion());
        }
        if (getClientVersionNumber() != null) {
            account.setClientVersionNumber(getClientVersionNumber());
        }
        if (getLastLoginTime() != null) {
            account.setLastLoginTime(getLastLoginTime());
        }
        if (getLastLoginDate() != null) {
            account.setLastLoginDate(getLastLoginDate());
        }
        if (getLastLogoutTime() != null) {
            account.setLastLogoutTime(getLastLogoutTime());
        }
        if (getLastLogoutDate() != null) {
            account.setLastLogoutDate(getLastLogoutDate());
        }
        if (getLastLoginType() != null) {
            account.setLastLoginType(getLastLoginType());
        }
        if (getLastLogoutType() != null) {
            account.setLastLogoutType(getLastLogoutType());
        }
        if (getLastLoginIp() != null) {
            account.setLastLoginIp(getLastLoginIp());
        }
        if (getLastLoginIpNumber() != null) {
            account.setLastLoginIpNumber(getLastLoginIpNumber());
        }
        if (getLastLoginSource() != null) {
            account.setLastLoginSource(getLastLoginSource());
        }
        if (getContainerId() != null) {
            account.setContainerId(getContainerId());
        }
        if (getVersion() != null) {
            account.setVersion(getVersion());
        }
        return account;
    }

    @Override
    protected void rangeStr(StringBuilder sb) {
        if (startCreateDate != null) {
            sb.append("startCreateDate=").append(DateFormatUtil.getDateFormat(datePattern).format(startCreateDate)).append(",");
        }
        if (endCreateDate != null) {
            sb.append("endCreateDate=").append(DateFormatUtil.getDateFormat(datePattern).format(endCreateDate)).append(",");
        }
        if (startBanExpiryTime != null) {
            sb.append("startBanExpiryTime=").append(startBanExpiryTime).append(",");
        }
        if (endBanExpiryTime != null) {
            sb.append("endBanExpiryTime=").append(endBanExpiryTime).append(",");
        }
        if (startBanExpiryDate != null) {
            sb.append("startBanExpiryDate=").append(DateFormatUtil.getDateFormat(datePattern).format(startBanExpiryDate)).append(",");
        }
        if (endBanExpiryDate != null) {
            sb.append("endBanExpiryDate=").append(DateFormatUtil.getDateFormat(datePattern).format(endBanExpiryDate)).append(",");
        }
        if (startLoginTime != null) {
            sb.append("startLoginTime=").append(startLoginTime).append(",");
        }
        if (endLoginTime != null) {
            sb.append("endLoginTime=").append(endLoginTime).append(",");
        }
        if (startLoginDate != null) {
            sb.append("startLoginDate=").append(DateFormatUtil.getDateFormat(datePattern).format(startLoginDate)).append(",");
        }
        if (endLoginDate != null) {
            sb.append("endLoginDate=").append(DateFormatUtil.getDateFormat(datePattern).format(endLoginDate)).append(",");
        }
        if (startLastLoginTime != null) {
            sb.append("startLastLoginTime=").append(startLastLoginTime).append(",");
        }
        if (endLastLoginTime != null) {
            sb.append("endLastLoginTime=").append(endLastLoginTime).append(",");
        }
        if (startLastLoginDate != null) {
            sb.append("startLastLoginDate=").append(DateFormatUtil.getDateFormat(datePattern).format(startLastLoginDate)).append(",");
        }
        if (endLastLoginDate != null) {
            sb.append("endLastLoginDate=").append(DateFormatUtil.getDateFormat(datePattern).format(endLastLoginDate)).append(",");
        }
        if (startLastLogoutTime != null) {
            sb.append("startLastLogoutTime=").append(startLastLogoutTime).append(",");
        }
        if (endLastLogoutTime != null) {
            sb.append("endLastLogoutTime=").append(endLastLogoutTime).append(",");
        }
        if (startLastLogoutDate != null) {
            sb.append("startLastLogoutDate=").append(DateFormatUtil.getDateFormat(datePattern).format(startLastLogoutDate)).append(",");
        }
        if (endLastLogoutDate != null) {
            sb.append("endLastLogoutDate=").append(DateFormatUtil.getDateFormat(datePattern).format(endLastLogoutDate)).append(",");
        }
    }

    @Override
    protected void beforeStr(StringBuilder sb) {
        sb.append("AccountCriteria{");
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

    /**
     * get (主键)
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * set (主键)
     *
     * @return
     */
    public AccountCriteria setId(Long id) {
        this.id = id;
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
    public AccountCriteria setAccount(String account) {
        if (account != null && account.trim().length() == 0) {
            this.account = null;
            return this;
        }
        this.account = account;
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
    public AccountCriteria setPassword(String password) {
        if (password != null && password.trim().length() == 0) {
            this.password = null;
            return this;
        }
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }


    public AccountCriteria setName(String name) {
        if (name != null && name.trim().length() == 0) {
            this.name = null;
            return this;
        }
        this.name = name;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }


    public AccountCriteria setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public Date getStartCreateDate() {
        return startCreateDate;
    }

    public AccountCriteria setStartCreateDate(Date startCreateDate) {
        this.startCreateDate = startCreateDate;
        return this;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public AccountCriteria setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
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
    public AccountCriteria setCreateDateOrder(String createDateOrder) {
        this.createDateOrder = createDateOrder;
        putSort("create_date", createDateOrder);
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
    public AccountCriteria setIp(String ip) {
        if (ip != null && ip.trim().length() == 0) {
            this.ip = null;
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
    public Long getIpNumber() {
        return ipNumber;
    }

    /**
     * set 数字IP，只存一个最接近真实IP的数据
     *
     * @return
     */
    public AccountCriteria setIpNumber(Long ipNumber) {
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
    public AccountCriteria setSource(String source) {
        if (source != null && source.trim().length() == 0) {
            this.source = null;
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
    public AccountCriteria setBanStr(String banStr) {
        if (banStr != null && banStr.trim().length() == 0) {
            this.banStr = null;
            return this;
        }
        this.banStr = banStr;
        return this;
    }

    public Long getBanExpiryTime() {
        return banExpiryTime;
    }


    public AccountCriteria setBanExpiryTime(Long banExpiryTime) {
        this.banExpiryTime = banExpiryTime;
        return this;
    }

    public Long getStartBanExpiryTime() {
        return startBanExpiryTime;
    }

    public AccountCriteria setStartBanExpiryTime(Long startBanExpiryTime) {
        this.startBanExpiryTime = startBanExpiryTime;
        return this;
    }

    public Long getEndBanExpiryTime() {
        return endBanExpiryTime;
    }

    public AccountCriteria setEndBanExpiryTime(Long endBanExpiryTime) {
        this.endBanExpiryTime = endBanExpiryTime;
        return this;
    }

    public Date getBanExpiryDate() {
        return banExpiryDate;
    }


    public AccountCriteria setBanExpiryDate(Date banExpiryDate) {
        this.banExpiryDate = banExpiryDate;
        return this;
    }

    public Date getStartBanExpiryDate() {
        return startBanExpiryDate;
    }

    public AccountCriteria setStartBanExpiryDate(Date startBanExpiryDate) {
        this.startBanExpiryDate = startBanExpiryDate;
        return this;
    }

    public Date getEndBanExpiryDate() {
        return endBanExpiryDate;
    }

    public AccountCriteria setEndBanExpiryDate(Date endBanExpiryDate) {
        this.endBanExpiryDate = endBanExpiryDate;
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
    public AccountCriteria setBanExpiryDateOrder(String banExpiryDateOrder) {
        this.banExpiryDateOrder = banExpiryDateOrder;
        putSort("ban_expiry_date", banExpiryDateOrder);
        return this;
    }

    /**
     * get 本次登录时间
     *
     * @return
     */
    public Long getLoginTime() {
        return loginTime;
    }

    /**
     * set 本次登录时间
     *
     * @return
     */
    public AccountCriteria setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
        return this;
    }

    public Long getStartLoginTime() {
        return startLoginTime;
    }

    public AccountCriteria setStartLoginTime(Long startLoginTime) {
        this.startLoginTime = startLoginTime;
        return this;
    }

    public Long getEndLoginTime() {
        return endLoginTime;
    }

    public AccountCriteria setEndLoginTime(Long endLoginTime) {
        this.endLoginTime = endLoginTime;
        return this;
    }

    /**
     * get 本次登录时间
     *
     * @return
     */
    public Date getLoginDate() {
        return loginDate;
    }

    /**
     * set 本次登录时间
     *
     * @return
     */
    public AccountCriteria setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
        return this;
    }

    public Date getStartLoginDate() {
        return startLoginDate;
    }

    public AccountCriteria setStartLoginDate(Date startLoginDate) {
        this.startLoginDate = startLoginDate;
        return this;
    }

    public Date getEndLoginDate() {
        return endLoginDate;
    }

    public AccountCriteria setEndLoginDate(Date endLoginDate) {
        this.endLoginDate = endLoginDate;
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
    public AccountCriteria setLoginDateOrder(String loginDateOrder) {
        this.loginDateOrder = loginDateOrder;
        putSort("login_date", loginDateOrder);
        return this;
    }

    public String getLoginType() {
        return loginType;
    }


    public AccountCriteria setLoginType(String loginType) {
        if (loginType != null && loginType.trim().length() == 0) {
            this.loginType = null;
            return this;
        }
        this.loginType = loginType;
        return this;
    }

    public Boolean getOnline() {
        return online;
    }


    public AccountCriteria setOnline(Boolean online) {
        this.online = online;
        return this;
    }

    public String getAccountState() {
        return accountState;
    }


    public AccountCriteria setAccountState(String accountState) {
        if (accountState != null && accountState.trim().length() == 0) {
            this.accountState = null;
            return this;
        }
        this.accountState = accountState;
        return this;
    }

    public String getClient() {
        return client;
    }


    public AccountCriteria setClient(String client) {
        if (client != null && client.trim().length() == 0) {
            this.client = null;
            return this;
        }
        this.client = client;
        return this;
    }

    public String getClientVersion() {
        return clientVersion;
    }


    public AccountCriteria setClientVersion(String clientVersion) {
        if (clientVersion != null && clientVersion.trim().length() == 0) {
            this.clientVersion = null;
            return this;
        }
        this.clientVersion = clientVersion;
        return this;
    }

    public Integer getClientVersionNumber() {
        return clientVersionNumber;
    }


    public AccountCriteria setClientVersionNumber(Integer clientVersionNumber) {
        this.clientVersionNumber = clientVersionNumber;
        return this;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }


    public AccountCriteria setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        return this;
    }

    public Long getStartLastLoginTime() {
        return startLastLoginTime;
    }

    public AccountCriteria setStartLastLoginTime(Long startLastLoginTime) {
        this.startLastLoginTime = startLastLoginTime;
        return this;
    }

    public Long getEndLastLoginTime() {
        return endLastLoginTime;
    }

    public AccountCriteria setEndLastLoginTime(Long endLastLoginTime) {
        this.endLastLoginTime = endLastLoginTime;
        return this;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }


    public AccountCriteria setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
        return this;
    }

    public Date getStartLastLoginDate() {
        return startLastLoginDate;
    }

    public AccountCriteria setStartLastLoginDate(Date startLastLoginDate) {
        this.startLastLoginDate = startLastLoginDate;
        return this;
    }

    public Date getEndLastLoginDate() {
        return endLastLoginDate;
    }

    public AccountCriteria setEndLastLoginDate(Date endLastLoginDate) {
        this.endLastLoginDate = endLastLoginDate;
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
    public AccountCriteria setLastLoginDateOrder(String lastLoginDateOrder) {
        this.lastLoginDateOrder = lastLoginDateOrder;
        putSort("last_login_date", lastLoginDateOrder);
        return this;
    }

    public Long getLastLogoutTime() {
        return lastLogoutTime;
    }


    public AccountCriteria setLastLogoutTime(Long lastLogoutTime) {
        this.lastLogoutTime = lastLogoutTime;
        return this;
    }

    public Long getStartLastLogoutTime() {
        return startLastLogoutTime;
    }

    public AccountCriteria setStartLastLogoutTime(Long startLastLogoutTime) {
        this.startLastLogoutTime = startLastLogoutTime;
        return this;
    }

    public Long getEndLastLogoutTime() {
        return endLastLogoutTime;
    }

    public AccountCriteria setEndLastLogoutTime(Long endLastLogoutTime) {
        this.endLastLogoutTime = endLastLogoutTime;
        return this;
    }

    public Date getLastLogoutDate() {
        return lastLogoutDate;
    }


    public AccountCriteria setLastLogoutDate(Date lastLogoutDate) {
        this.lastLogoutDate = lastLogoutDate;
        return this;
    }

    public Date getStartLastLogoutDate() {
        return startLastLogoutDate;
    }

    public AccountCriteria setStartLastLogoutDate(Date startLastLogoutDate) {
        this.startLastLogoutDate = startLastLogoutDate;
        return this;
    }

    public Date getEndLastLogoutDate() {
        return endLastLogoutDate;
    }

    public AccountCriteria setEndLastLogoutDate(Date endLastLogoutDate) {
        this.endLastLogoutDate = endLastLogoutDate;
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
    public AccountCriteria setLastLogoutDateOrder(String lastLogoutDateOrder) {
        this.lastLogoutDateOrder = lastLogoutDateOrder;
        putSort("last_logout_date", lastLogoutDateOrder);
        return this;
    }

    public String getLastLoginType() {
        return lastLoginType;
    }


    public AccountCriteria setLastLoginType(String lastLoginType) {
        if (lastLoginType != null && lastLoginType.trim().length() == 0) {
            this.lastLoginType = null;
            return this;
        }
        this.lastLoginType = lastLoginType;
        return this;
    }

    public String getLastLogoutType() {
        return lastLogoutType;
    }


    public AccountCriteria setLastLogoutType(String lastLogoutType) {
        if (lastLogoutType != null && lastLogoutType.trim().length() == 0) {
            this.lastLogoutType = null;
            return this;
        }
        this.lastLogoutType = lastLogoutType;
        return this;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }


    public AccountCriteria setLastLoginIp(String lastLoginIp) {
        if (lastLoginIp != null && lastLoginIp.trim().length() == 0) {
            this.lastLoginIp = null;
            return this;
        }
        this.lastLoginIp = lastLoginIp;
        return this;
    }

    public Long getLastLoginIpNumber() {
        return lastLoginIpNumber;
    }


    public AccountCriteria setLastLoginIpNumber(Long lastLoginIpNumber) {
        this.lastLoginIpNumber = lastLoginIpNumber;
        return this;
    }

    public String getLastLoginSource() {
        return lastLoginSource;
    }


    public AccountCriteria setLastLoginSource(String lastLoginSource) {
        if (lastLoginSource != null && lastLoginSource.trim().length() == 0) {
            this.lastLoginSource = null;
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
    public Integer getContainerId() {
        return containerId;
    }

    /**
     * set (外键,modelName:Container,tableName:senpure_container)
     *
     * @return
     */
    public AccountCriteria setContainerId(Integer containerId) {
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
    public AccountCriteria setContainerIdOrder(String containerIdOrder) {
        this.containerIdOrder = containerIdOrder;
        putSort("container_id", containerIdOrder);
        return this;
    }

    /**
     * get 乐观锁，版本控制
     *
     * @return
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * set 乐观锁，版本控制
     *
     * @return
     */
    public AccountCriteria setVersion(Integer version) {
        this.version = version;
        return this;
    }

}
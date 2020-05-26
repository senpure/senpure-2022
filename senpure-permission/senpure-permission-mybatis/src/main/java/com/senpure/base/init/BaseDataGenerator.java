package com.senpure.base.init;

import com.senpure.base.PermissionConstant;
import com.senpure.base.criteria.AccountCriteria;
import com.senpure.base.criteria.SequenceCriteria;
import com.senpure.base.model.*;
import com.senpure.base.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Component
@Order(2)
@EnableConfigurationProperties(BaseDataGenerator.PermissionProperties.class)
public class BaseDataGenerator implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private AccountService accountService;
    @Resource
    private SequenceService sequenceService;
    @Resource
    private ContainerService containerService;
    @Resource
    private RoleService roleService;
    @Resource
    private AccountRoleService accountRoleService;
    @Resource
    private PermissionService permissionService;
    @Resource
    private ContainerPermissionService containerPermissionService;
    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private SystemValueService systemValueService;
    @Resource
    private PermissionProperties permissionProperties;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(ApplicationArguments args) {
        containerIdCheck();
        AccountCriteria accountCriteria = new AccountCriteria();
        String useAccount = permissionProperties.getAccount();
        if (useAccount == null || useAccount.length() < 6) {
            useAccount = PermissionConstant.DEFAULT_ACCOUNT;
            logger.info("系统账号{}", useAccount);
        }

        accountCriteria.setAccount(useAccount);
        Account account = accountService.findOne(accountCriteria);
        if (account == null) {
            Date now = new Date();
            logger.debug("准备生产系统账号");
            //创建顶级容器
            Container container = new Container();
            container.setLevel(PermissionConstant.CONTAINER_LEVEL_ROOT);
            container.setName(PermissionConstant.NAME);
            container.setContainerStructure("");
            container.setRelation(0L);
            container.setDescription("系统根容器");
            container.setCreateDate(now);
            container.setCreateTime(now.getTime());
            containerService.save(container);
            container = containerService.find(container.getId());

            //创建顶级账号
            account = accountService.defaultAccount(now.getTime());
            account.setAccount(useAccount);
            String password = permissionProperties.getPassword();
            if (password == null || password.length() < 6) {
                password = UUID.randomUUID().toString().substring(6).toLowerCase();
                logger.info("系统账号使用密码 {}", password);
            }
            account.setPassword(password);
            account.setContainerId(container.getId());
            account.setName(PermissionConstant.NAME);
            accountService.save(account);
            container.setRelation(account.getId());
            containerService.update(container);
            //创建顶级角色
            Role role = new Role();
            role.setName(PermissionConstant.NAME);
            role.setCreateDate(now);
            role.setCreateTime(now.getTime());
            role.setContainerId(container.getId());
            role.setDescription("系统根角色");
            roleService.save(role);

            //管理账号和角色
            AccountRole accountRole = new AccountRole();
            accountRole.setAccountId(account.getId());
            accountRole.setRoleId(role.getId());
            accountRole.setExpiryDate(PermissionConstant.FOREVER_DATE);
            accountRole.setExpiryTime(PermissionConstant.FOREVER_TIME);
            accountRoleService.save(accountRole);

            //给角色，和容器授予权限
            List<Permission> permissions = permissionService.findAll();
            List<RolePermission> rolePermissions = new ArrayList<>();
            List<ContainerPermission> containerPermissions = new ArrayList<>();
            Container finalContainer = container;
            permissions.forEach((Permission permission) -> {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setPermissionId(permission.getId());
                rolePermission.setRoleId(role.getId());
                rolePermission.setExpiryDate(PermissionConstant.FOREVER_DATE);
                rolePermission.setExpiryTime(PermissionConstant.FOREVER_TIME);
                rolePermissions.add(rolePermission);
                ContainerPermission containerPermission = new ContainerPermission();
                containerPermission.setContainerId(finalContainer.getId());
                containerPermission.setPermissionId(permission.getId());
                containerPermission.setExpiryDate(PermissionConstant.FOREVER_DATE);
                containerPermission.setExpiryTime(PermissionConstant.FOREVER_TIME);
                containerPermissions.add(containerPermission);
            });
            rolePermissionService.save(rolePermissions);
            containerPermissionService.save(containerPermissions);

            //补充系统一些必要的值
            List<SystemValue> systemValues = new ArrayList<>();
            SystemValue systemValue = new SystemValue();
            systemValue.setType(PermissionConstant.VALUE_TYPE_SYSTEM);
            systemValue.setKey(PermissionConstant.ROOT_ROLE_ID);
            systemValue.setValue(role.getId() + "");
            systemValue.setDescription("系统根角色ID");
            systemValues.add(systemValue);
            systemValue = new SystemValue();
            systemValue.setType(PermissionConstant.VALUE_TYPE_SYSTEM);
            systemValue.setKey(PermissionConstant.ROOT_CONTAINER_ID);
            systemValue.setValue(container.getId() + "");
            systemValue.setDescription("系统根容器ID");
            systemValues.add(systemValue);

            SystemValue dateFormat = new SystemValue();
            dateFormat.setType(PermissionConstant.VALUE_TYPE_ACCOUNT_DEFAULT);
            dateFormat.setKey(PermissionConstant.DATE_FORMAT_KEY);
            dateFormat.setValue("yyyy-MM-dd");
            dateFormat.setDescription("例如:2017-05-06");
            systemValues.add(dateFormat);

            systemValue = new SystemValue();
            systemValue.setType(PermissionConstant.VALUE_TYPE_ACCOUNT_DEFAULT);
            systemValue.setKey(PermissionConstant.DATETIME_FORMAT_KEY);
            systemValue.setValue("yyyy-MM-dd HH:mm:ss");
            systemValue.setDescription("例如:2017-05-06 17:55:99");
            systemValues.add(systemValue);

            systemValue = new SystemValue();
            systemValue.setType(PermissionConstant.VALUE_TYPE_ACCOUNT_DEFAULT);
            systemValue.setKey(PermissionConstant.TIME_FORMAT_KEY);
            systemValue.setValue("HH:mm:ss");
            systemValue.setDescription("例如:17:55:99");
            systemValues.add(systemValue);
            systemValueService.save(systemValues);

        }


    }

    private void containerIdCheck() {
        SequenceCriteria criteria = new SequenceCriteria();
        criteria.setType(PermissionConstant.SEQUENCE_CONTAINER_ID);

        Sequence sequence = sequenceService.findOne(criteria);
        if (sequence == null) {
            sequence = new Sequence();
            sequence.setType(PermissionConstant.SEQUENCE_CONTAINER_ID);
            sequence.setSequence(1);
            sequence.setDigit(6);
            sequence.setPrefix("");
            sequence.setSuffix("");
            sequence.setSpan(1);
            sequenceService.save(sequence);
        }

    }

    @ConfigurationProperties(prefix = "senpure.permission")
    public static class PermissionProperties {

        /**
         * 权限系统账号
         */
        private String account = "account";
        /**
         * 权限系统账号密码
         */
        private String password;

        public String getAccount() {
            return account;
        }

        public PermissionProperties setAccount(String account) {
            this.account = account;
            return this;
        }

        public String getPassword() {
            return password;
        }

        public PermissionProperties setPassword(String password) {
            this.password = password;
            return this;
        }
    }

}

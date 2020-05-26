package com.senpure.base.init;

import com.senpure.base.PermissionConstant;
import com.senpure.base.criteria.RolePermissionCriteria;
import com.senpure.base.criteria.SystemValueCriteria;
import com.senpure.base.model.ContainerPermission;
import com.senpure.base.model.Permission;
import com.senpure.base.model.RolePermission;
import com.senpure.base.model.SystemValue;
import com.senpure.base.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
@Order(value = 3)
public class PermissionCompleteGenerator implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private PermissionService permissionService;
    @Resource
    private SystemValueService systemValueService;

    @Resource
    private RolePermissionService rolePermissionService;
    @Resource
    private ContainerPermissionService containerPermissionService;

    @Transactional
    @Override
    public void run(ApplicationArguments args)  {
        List<Permission> allPermissions = permissionService.findAll();
        SystemValueCriteria systemValueCriteria = new SystemValueCriteria();
        systemValueCriteria.setType(PermissionConstant.VALUE_TYPE_SYSTEM);
        systemValueCriteria.setKey(PermissionConstant.ROOT_ROLE_ID);
        systemValueCriteria.setUsePage(false);
        SystemValue topRole = systemValueService.findOne(systemValueCriteria);
        systemValueCriteria.setKey(PermissionConstant.ROOT_CONTAINER_ID);
        SystemValue topContainer = systemValueService.findOne(systemValueCriteria);

        RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();
        rolePermissionCriteria.setUsePage(false);
        Long roleId = Long.valueOf(topRole.getValue());
        rolePermissionCriteria.setRoleId(roleId);
        List<RolePermission> rolePermissions = rolePermissionService.find(rolePermissionCriteria);
        List<RolePermission> saveRolePermissions = new ArrayList<>();
        List<ContainerPermission> saveContainerPermissions = new ArrayList<>();
        for (Permission permission : allPermissions) {
            boolean save = true;
            for (RolePermission rolePermission : rolePermissions) {
                if (rolePermission.getPermissionId().longValue() == permission.getId()) {
                    save = false;
                    break;
                }
            }
            if (save) {
                logger.debug("给系统补上权限{}", permission);
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permission.getId());
                rolePermission.setExpiryDate(PermissionConstant.FOREVER_DATE);
                rolePermission.setExpiryTime(PermissionConstant.FOREVER_TIME);
                saveRolePermissions.add(rolePermission);
                ContainerPermission containerPermission = new ContainerPermission();
                containerPermission.setPermissionId(permission.getId());
                containerPermission.setContainerId(Integer.valueOf(topContainer.getValue()));
                containerPermission.setExpiryDate(PermissionConstant.FOREVER_DATE);
                containerPermission.setExpiryTime(PermissionConstant.FOREVER_TIME);
                saveContainerPermissions.add(containerPermission);
            }
        }
        if (saveRolePermissions.size() > 0) {
            rolePermissionService.save(saveRolePermissions);
            containerPermissionService.save(saveContainerPermissions);
        }
    }
}

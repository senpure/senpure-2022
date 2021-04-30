package com.senpure.base.verify;

import com.senpure.base.model.Container;
import com.senpure.base.model.Role;
import com.senpure.base.service.AccountService;
import com.senpure.base.service.ContainerService;
import com.senpure.base.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ResourceVerifyRoleService extends ResourceVerifySupportService<Long> {

    public static final String VERIFY_NAME = "roleResource";

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;
    @Autowired
    private ContainerService containerService;
    @Override
    public String getName() {
        return VERIFY_NAME;
    }

    @Override
    public boolean verify(long accountId, String resourceId) {

        Role role = roleService.find(Long.valueOf(resourceId));
        Container roleContainer = containerService.find(role.getContainerId());
        Container parent = containerService.find(accountService.find(accountId).getContainerId());
        return  roleContainer.getContainerStructure().contains(parent.getContainerStructure());
    }
}

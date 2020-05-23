package com.senpure.base.verify;


import com.senpure.base.PermissionConstant;
import com.senpure.base.model.Account;
import com.senpure.base.service.AccountService;
import com.senpure.base.service.ContainerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class ResourceVerifyAccountService extends ResourceVerifySupportService<Long> {

    public static final String VERIFY_NAME = "accountResource";


    @Resource
    private AccountService accountService;
    @Resource
    private ContainerService containerService;
    @Override
    public String getName() {
        return VERIFY_NAME;
    }


    @Override
    public boolean verify(long accountId, String resourceId) {

       Long checkId = Long.valueOf(resourceId);
        if (checkId == accountId) {
            return true;
        }
        Account account = accountService.find(accountId);
        Account checkAccount = accountService.find(checkId);
        if (checkAccount == null) {
            return false;
        }
        return containerService.find(checkAccount.getContainerId()).getContainerStructure().contains(PermissionConstant.CONTAINER_SEPARATOR +account.getContainerId());
    }
}

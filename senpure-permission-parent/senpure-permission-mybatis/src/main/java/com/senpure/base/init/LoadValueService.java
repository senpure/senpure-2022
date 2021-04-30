package com.senpure.base.init;


import com.senpure.base.PermissionConstant;
import com.senpure.base.criteria.MenuCriteria;
import com.senpure.base.interceptor.MultipleInterceptor;
import com.senpure.base.model.Menu;
import com.senpure.base.model.SystemValue;
import com.senpure.base.service.AuthorizeService;
import com.senpure.base.service.MenuService;
import com.senpure.base.service.SystemValueService;
import com.senpure.base.struct.KeyValue;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class LoadValueService implements ApplicationRunner {
    @Resource
    private MultipleInterceptor multipleInterceptor;
    @Resource
    private SystemValueService systemValueService;
    @Resource
    private MenuService menuService;

    @Resource
    private AuthorizeService authorizeService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<SystemValue> systemValues = systemValueService.
                findByType(PermissionConstant.VALUE_TYPE_ACCOUNT_DEFAULT);
        List<KeyValue<String, String>> keyValues = new ArrayList<>();
        systemValues.forEach(systemValue -> {
            KeyValue<String, String> keyValue = new KeyValue<>();
            keyValue.setKey(systemValue.getKey());
            keyValue.setValue(systemValue.getValue());
            keyValues.add(keyValue);
        });
        multipleInterceptor.setAccountValues(keyValues);

        MenuCriteria menuCriteria = new MenuCriteria();
        menuCriteria.setUsePage(false);
        menuCriteria.setDirectView(true);
        List<Menu> menus = menuService.find(menuCriteria);
        authorizeService.getDirectMenus().addAll(menus);
        Map<Integer, com.senpure.base.menu.Menu> viewMenus = new HashMap<>(16);

        menus.forEach(menu -> authorizeService.mergeMenu(menu.getId(), viewMenus));

        multipleInterceptor.getMenus().addAll(viewMenus.values());
    }
}

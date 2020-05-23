package com.senpure.base.init;


import com.senpure.base.menu.MenuGenerator;
import com.senpure.base.model.Menu;
import com.senpure.base.util.Pinyin;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(0)
public class MenuParentGenerator implements ApplicationRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(ApplicationArguments args) {
        String[] beans = applicationContext.getBeanNamesForType(Object.class);
        for (String bean : beans) {
            Object o = applicationContext.getBean(bean);
            MenuGenerator menuGenerator = o.getClass().getAnnotation(MenuGenerator.class);
            if (menuGenerator != null) {
                Menu menu = new Menu();
                menu.setDatabaseUpdate(false);
                menu.setDirectView(false);
                menu.setText(menuGenerator.text());

                if (menuGenerator.i18nKey().length() > 0) {
                    menu.setI18nKey(menuGenerator.i18nKey());
                } else {
                    menu.setI18nKey(Pinyin.toAccount(menu.getText())[0].replace(" ", ".").toLowerCase());
                }
                if (menuGenerator.description().length() > 0) {
                    menu.setDescription(menuGenerator.description());
                }
                if (menuGenerator.config().length() > 0) {
                    menu.setConfig(menuGenerator.config());
                }
                menu.setIcon(menuGenerator.icon());
                menu.setId(menuGenerator.id());
                menu.setSort(menuGenerator.sort());
                PermissionsGenerator.checkAndPutMenu(menu);
            }

        }
    }
}

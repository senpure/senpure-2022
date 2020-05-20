package com.senpure.base.service;

import com.senpure.base.criterion.Criteria;
import com.senpure.base.util.Assert;
import com.senpure.base.util.IDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;


public class BaseService {
    protected Logger logger;

    @Autowired
    protected IDGenerator idGenerator;

    public BaseService() {

        logger = LoggerFactory.getLogger(getClass());
    }

    public void checkPage(Criteria criteria, int total) {
        if (criteria.getPage() * criteria.getPageSize() > total) {
            int page = total / criteria.getPageSize();
            if (total % criteria.getPageSize() > 0) {
                page++;
            }
            criteria.setPage(page);
        }
    }

    public <T> void checkPrimaryKey(T model, Long id) {
        if (id == null || id == 0) {
            Assert.error(model.getClass().getName() + "主键不合法" + id);
        }
    }

    public <T> void checkPrimaryKey(T model, Integer id) {
        if (id == null || id == 0) {
            Assert.error(model.getClass().getName() + "主键不合法" + id);
        }
    }

    public <T> void checkPrimaryKey(T model, Serializable id) {
        if (id == null) {
            Assert.error(model.getClass().getName() + "主键不合法" + id);
        }
    }

}

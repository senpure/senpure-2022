package com.senpure.base.verify;

import java.util.List;


public interface ResourceVerifyService<T> {


    public String getName();

    public boolean verify(long accountId, String resourceId);

    public List<T> check(long accountId, List<T> resourceId);
}

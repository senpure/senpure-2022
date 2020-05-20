package com.senpure.base.verify;

import java.util.List;


public interface ResourceVerifyService<T> {


    String getName();

    boolean verify(long accountId, String resourceId);

    List<T> check(long accountId, List<T> resourceId);
}

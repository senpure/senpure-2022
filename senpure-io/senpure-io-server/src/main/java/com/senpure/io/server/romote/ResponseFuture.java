package com.senpure.io.server.romote;

import javax.annotation.Nonnull;

public interface ResponseFuture {


    @Nonnull
    Response get();

    @Nonnull
    Response get(int timeout);

    void setCallback(@Nonnull Callback callback);

    boolean isDone();
}

package com.senpure.reload.watcher;

import com.senpure.reload.ReloadableType;

import java.io.File;

/**
 * FileWatcher
 *
 * @author senpure
 * @time 2020-10-16 16:38:14
 */
public interface FileWatcher {

    void watch(File file, ReloadableType reloadableType);

    void change(File file);
}

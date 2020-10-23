package com.senpure.reload;

import com.senpure.reload.watcher.DefaultFileWatcher;
import com.senpure.reload.watcher.FileWatcher;

/**
 * ReloadContext
 *
 * @author senpure
 * @time 2020-10-19 15:37:23
 */
public class ReloadContext {
    private  static  FileWatcher fileWatcher = new DefaultFileWatcher();

    public static FileWatcher getFileWatcher() {
        return fileWatcher;
    }

    public static void setFileWatcher(FileWatcher fileWatcher) {
        ReloadContext.fileWatcher = fileWatcher;
    }
}

package com.senpure.reload.watcher;

import com.senpure.reload.ReloadableType;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * DefaultFileWatcher
 *
 * @author senpure
 * @time 2020-10-16 16:46:17
 */
public class DefaultFileWatcher implements FileWatcher {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    protected Map<String, WatchFile> watchFileMap = new ConcurrentHashMap<>();


    public DefaultFileWatcher() {
        execute();
    }

    public void execute() {
        Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("senpure-reload-file-wather");


            return thread;
        }).scheduleAtFixedRate(() -> {
            List<WatchFile> changeWatchFiles = new ArrayList<>();
            for (Map.Entry<String, WatchFile> entry : watchFileMap.entrySet()) {
                WatchFile watchFile = entry.getValue();
                long lastTime = watchFile.file.lastModified();
                if (lastTime > watchFile.watchLastModifiedTime) {
                    changeWatchFiles.add(watchFile);
                    watchFile.watchLastModifiedTime = lastTime;
                }
            }

            for (WatchFile watchFile : changeWatchFiles) {

                change(watchFile.file);

            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void watch(File file, ReloadableType reloadableType) {
        String filePath = file.getAbsolutePath();
        if (!file.exists()) {
           // logger.warn("can not watch {}", filePath);
            return;
        }

        logger.debug("watch {}", filePath);
        watchFileMap.computeIfAbsent(filePath, s -> {
            WatchFile watchFile = new WatchFile();
            watchFile.file = file;
            watchFile.watchLastModifiedTime = file.lastModified();
            watchFile.reloadableType = reloadableType;
            return watchFile;
        });
    }

    @Override
    public void change(File file) {

        WatchFile watchFile = watchFileMap.get(file.getAbsolutePath());
        if (watchFile != null) {
            ReloadableType reloadableType = watchFile.reloadableType;
            try {
                reloadableType.redefineClass(FileUtils.readFileToByteArray(file));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    static class WatchFile {
        ReloadableType reloadableType;
        File file;
        long watchLastModifiedTime;

        public ReloadableType getReloadableType() {
            return reloadableType;
        }

        public void setReloadableType(ReloadableType reloadableType) {
            this.reloadableType = reloadableType;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public long getWatchLastModifiedTime() {
            return watchLastModifiedTime;
        }

        public void setWatchLastModifiedTime(long watchLastModifiedTime) {
            this.watchLastModifiedTime = watchLastModifiedTime;
        }
    }
}

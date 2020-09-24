package com.senpure.reload.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * JarInJar
 *
 * @author senpure
 * @time 2020-09-24 14:45:24
 */
public class JarMayInJar {

    private ZipFile zipFile;
    private static final File tempParent;

    static {
        tempParent = new File(System.getProperty("java.io.tmpdir"));
    }

    private final Map<String, ZipEntry> zipEntryMap = new HashMap<>();
    private final Map<String, ZipFile> fileMap = new HashMap<>();

    public JarMayInJar(File file) {

        try {
            zipFile = new ZipFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getBytes(String prefix, String slashname) {
        if (!prefix.isEmpty()) {
            if (prefix.endsWith(".jar")) {
                ZipFile zipFile = getInJarZipFile(prefix);
                ZipEntry zipEntry = zipFile.getEntry(slashname);
                try {
                    return IOUtils.toByteArray(zipFile.getInputStream(zipEntry));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                slashname = prefix + "/" + slashname;
                ZipEntry zipEntry = getZipEntry(slashname);
                try {
                    return IOUtils.toByteArray(zipFile.getInputStream(zipEntry));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            ZipEntry zipEntry = getZipEntry(slashname);
            try {
                return IOUtils.toByteArray(zipFile.getInputStream(zipEntry));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public long getCrc(String prefix, String slashname) {
        if (!prefix.isEmpty()) {
            if (prefix.endsWith(".jar")) {
                ZipFile zipFile = getInJarZipFile(prefix);
                ZipEntry zipEntry = zipFile.getEntry(slashname);
                return zipEntry.getCrc();

            } else {
                slashname = prefix + "/" + slashname;
                ZipEntry zipEntry = getZipEntry(slashname);
                return zipEntry.getCrc();
            }
        } else {
            ZipEntry zipEntry = getZipEntry(slashname);
            return zipEntry.getCrc();
        }

    }

    private File createJarTemp(String name) {

        try {
            File file = File.createTempFile(name.substring(0, name.length() - 4) + "-", ".jar", tempParent);
            file.deleteOnExit();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ZipFile getInJarZipFile(String name) {
        ZipFile zipFile = fileMap.get(name);
        if (zipFile == null) {
            File file = createJarTemp(name);
            if (file != null) {
                try {
                    FileUtils.copyInputStreamToFile(this.zipFile.getInputStream(this.zipFile.getEntry(name)), file);
                    zipFile = new ZipFile(file);
                    fileMap.put(name, zipFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return zipFile;
    }

    private ZipEntry getZipEntry(String name) {
        ZipEntry zipEntry = zipEntryMap.get(name);
        if (zipEntry == null) {
            zipEntry = zipFile.getEntry(name);
            if (zipEntry != null) {
                zipEntryMap.put(name, zipEntry);
            }
        }
        return zipEntry;
    }
}

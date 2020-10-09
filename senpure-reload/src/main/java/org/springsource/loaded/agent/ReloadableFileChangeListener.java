/*
 * Copyright 2010-2012 VMware and contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springsource.loaded.agent;

import com.senpure.reload.util.JarMayInJar;
import org.apache.commons.io.IOUtils;
import org.springsource.loaded.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


/**
 * @author Andy Clement
 * @since 0.5.0
 */
public class ReloadableFileChangeListener implements FileChangeListener {

    private static Logger log = Logger.getLogger(ReloadableFileChangeListener.class.getName());

    private TypeRegistry typeRegistry;

    private Map<File, ReloadableType> correspondingReloadableTypes = new HashMap<File, ReloadableType>();

    Map<File, Set<JarEntry>> watchedJarContents = new HashMap<File, Set<JarEntry>>();
    Map<File, Set<ClassInfo>> watchedJarClasses = new HashMap<File, Set<ClassInfo>>();

    private Map<String, Long> lastJarFileChange = new HashMap<>();

    static class JarEntry {

        final ReloadableType rtype;

        final String slashname;

        long lmt;


        public JarEntry(ReloadableType rtype, String slashname, long lmt) {
            this.rtype = rtype;
            this.slashname = slashname;
            this.lmt = lmt;
        }
    }

    static class ClassInfo {
        long crc;
        String className;
        String slashname;
        ReloadableType rtype;
        String prefix = "";

        @Override
        public String toString() {
            return "ClassInfo{" +
                    "crc=" + crc +
                    ", className='" + className + '\'' +
                    ", slashname='" + slashname + '\'' +
                    ", rtype=" + rtype +
                    ", prefix='" + prefix + '\'' +
                    '}';
        }


    }

    public ReloadableFileChangeListener(TypeRegistry typeRegistry) {
        this.typeRegistry = typeRegistry;
    }

    public void fileChanged(File file) {
       // log.info("====fileChanged " + file.getName());
        if (file.getName().endsWith(".jar")) {
            JarMayInJar jarMayInJar = new JarMayInJar(file);
            Set<ClassInfo> classInfos = watchedJarClasses.get(file);
            if (GlobalConfiguration.isRuntimeLogging && log.isLoggable(Level.INFO)) {
                log.info(" processing change for JAR " + file);
            }

            try {
                for (ClassInfo classInfo : classInfos) {
                    log.info(classInfo.toString());
                    ReloadableType rtype = classInfo.rtype;
                    //  log.info(classInfo.slashname);
                    long crc = jarMayInJar.getCrc(rtype.prefix, classInfo.slashname);
                        if (classInfo.crc != crc) {
                            if (GlobalConfiguration.isRuntimeLogging && log.isLoggable(Level.INFO)) {
                                log.info(" detected update to jar entry. jar=" + file.getName() + " class="
                                        + classInfo.className + "  OLD crc32=" + classInfo.crc
                                        + " NEW crc32=" + crc);
                            }
                            byte[] bytes = jarMayInJar.getBytes(rtype.prefix, classInfo.slashname);
                            rtype.loadNewVersion(Utils.encode(file.lastModified()), bytes);
                            classInfo.crc = crc;
                        } else {
                            log.info(classInfo.className + " crs is same " + crc);
                        }

                }
            } catch (Exception e) {
                e.printStackTrace();
                log.info("error");
            }
        } else {
            if (GlobalConfiguration.isRuntimeLogging && log.isLoggable(Level.INFO)) {
                log.info(" processing change for " + file);
            }
            ReloadableType rtype = correspondingReloadableTypes.get(file);
            typeRegistry.loadNewVersion(rtype, file);
        }
    }

    public void fileChanged2(File file) {
        if (GlobalConfiguration.isRuntimeLogging && log.isLoggable(Level.INFO)) {
            log.info(" processing change for " + file);
        }
        ReloadableType rtype = correspondingReloadableTypes.get(file);
        if (file.getName().endsWith(".jar")) {
            if (GlobalConfiguration.isRuntimeLogging && log.isLoggable(Level.INFO)) {
                log.info(" processing change for JAR " + file);
            }
            try {
                ZipFile zf = new ZipFile(file);
                Set<JarEntry> entriesBeingWatched = watchedJarContents.get(file);
                for (JarEntry entryBeingWatched : entriesBeingWatched) {
                    ZipEntry ze = zf.getEntry(entryBeingWatched.slashname);
                    long lmt = ze.getTime();//getLastModifiedTime().toMillis();
                    if (lmt > entryBeingWatched.lmt) {
                        // entry in jar has been updated
                        if (GlobalConfiguration.isRuntimeLogging && log.isLoggable(Level.INFO)) {
                            log.info(" detected update to jar entry. jar=" + file.getName() + " class="
                                    + entryBeingWatched.slashname + "  OLD LMT=" + new Date(entryBeingWatched.lmt)
                                    + " NEW LMT=" + new Date(lmt));
                        }
                        typeRegistry.loadNewVersion(entryBeingWatched.rtype, lmt, zf.getInputStream(ze));
                        entryBeingWatched.lmt = lmt;
                    }
                }
                zf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            typeRegistry.loadNewVersion(rtype, file);
        }
    }


    public void register(ReloadableType rtype, File file) {
       // log.info("====register " + file.getName());
        if (file.getName().endsWith(".jar")) {
            String slashname = rtype.getSlashedName() + ".class";
            URL url = rtype.getTypeRegistry().getClassLoader().getResource(slashname);
            byte[] bytes = null;
            if (url != null) {
                try {
                    bytes = IOUtils.toByteArray(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bytes != null) {
                CRC32 crc32 = new CRC32();
                crc32.update(bytes);
                ClassInfo classInfo = new ClassInfo();
                classInfo.crc = crc32.getValue();
                classInfo.slashname = slashname;
                classInfo.className = rtype.getName();
                classInfo.rtype = rtype;
                classInfo.prefix = rtype.prefix;
                if (GlobalConfiguration.isRuntimeLogging && log.isLoggable(Level.INFO)) {
                    log.info("====register " + file.getName() + " " + classInfo);

                }
                 Set<ClassInfo> classInfos = watchedJarClasses.computeIfAbsent(file, file1 -> new HashSet<>());
                classInfos.add(classInfo);
            }
        } else {
            correspondingReloadableTypes.put(file, rtype);
        }
    }

    public void register2(ReloadableType rtype, File file) {


        if (file.getName().endsWith(".jar")) {
            // Compute the last mod time of the entry in the jar
            try {
                ZipFile zf = new ZipFile(file);
                String slashname = rtype.getSlashedName() + ".class";
                ZipEntry ze = zf.getEntry(slashname);
                long lmt = ze.getTime();//LastModifiedTime().toMillis();
                JarEntry je = new JarEntry(rtype, slashname, lmt);
                zf.close();
                Set<JarEntry> jarEntries = watchedJarContents.get(file);
                if (jarEntries == null) {
                    jarEntries = new HashSet<JarEntry>();
                    watchedJarContents.put(file, jarEntries);
                }
                jarEntries.add(je);
                if (GlobalConfiguration.isRuntimeLogging && log.isLoggable(Level.INFO)) {
                    log.info(" watching jar file entry. Jar=" + file + "  file=" + rtype.getSlashedName() + " lmt="
                            + lmt);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            correspondingReloadableTypes.put(file, rtype);
        }
    }
}

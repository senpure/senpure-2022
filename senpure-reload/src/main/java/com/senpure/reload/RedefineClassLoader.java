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

package com.senpure.reload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLClassLoader;

public class RedefineClassLoader extends URLClassLoader {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final URL[] NO_URLS = new URL[0];

	private int definedCount = 0;

	public RedefineClassLoader(ClassLoader classloader) {
		super(NO_URLS, classloader);
	}

	public Class<?> defineClass(String name, byte[] bytes) {
		definedCount++;
		logger.info("use ClassLoader defineClass {}",name);
		return super.defineClass(name+"$$$"+definedCount, bytes, 0, bytes.length);
	}

	public int getDefinedCount() {
		return definedCount;
	}

}

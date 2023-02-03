/*
 * Copyright 2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.api.internal.cache

import org.gradle.integtests.fixtures.AbstractIntegrationSpec

class CacheMarkingStrategyIntegrationTest extends AbstractIntegrationSpec {
    @Override
    def setup() {
        executer.requireOwnGradleUserHomeDir()
    }

    def writeInitScript(String markingStrategy) {
        def initDir = new File(executer.gradleUserHomeDir, "init.d")
        initDir.mkdirs()
        new File(initDir, "cache-settings.gradle") << """
            beforeSettings { settings ->
                settings.caches {
                    markingStrategy = MarkingStrategy.$markingStrategy
                }
            }
        """
    }

    def "directory '#directory' is #expectedState when markingStrategy = #markingStrategy and directory created = #createDir"() {
        writeInitScript(markingStrategy)
        def dir = new File(executer.gradleUserHomeDir, directory)
        if (createDir) {
            dir.mkdirs()
        }

        when:
        succeeds("help")

        then:
        new File(dir, "CACHEDIR.TAG").exists() == (expectedState == "marked")

        where:
        directory       | markingStrategy | createDir | expectedState
        // Wrapper Dists not used by test framework, so not marked if not created
        "wrapper/dists" | "NONE"          | false     | "not marked"
        "wrapper/dists" | "NONE"          | true      | "not marked"
        "wrapper/dists" | "CACHEDIR_TAG"  | false     | "not marked"
        "wrapper/dists" | "CACHEDIR_TAG"  | true      | "marked"
        // JDKs are not used by this test, so not marked if not created
        "jdks"          | "NONE"          | false     | "not marked"
        "jdks"          | "NONE"          | true      | "not marked"
        "jdks"          | "CACHEDIR_TAG"  | false     | "not marked"
        "jdks"          | "CACHEDIR_TAG"  | true      | "marked"
        // Caches will be generated by test framework, so marked even if not created
        "caches"        | "NONE"          | false     | "not marked"
        "caches"        | "NONE"          | true      | "not marked"
        "caches"        | "CACHEDIR_TAG"  | false     | "marked"
        "caches"        | "CACHEDIR_TAG"  | true      | "marked"
        // Daemon logs will be generated by test framework sometimes, so marked even if not created
        "daemon"        | "NONE"          | false     | "not marked"
        "daemon"        | "NONE"          | true      | "not marked"
        "daemon"        | "CACHEDIR_TAG"  | false     | (executer.isUseDaemon() ? "marked" : "not marked")
        "daemon"        | "CACHEDIR_TAG"  | true      | "marked"
    }

    def "only specific directories are marked by Gradle as caches"() {
        def dirsThatShouldBeMarked = [
            "caches",
            "daemon",
            "jdks",
            "wrapper/dists",
        ]
        def dirsThatShouldNotBeMarked = [
            "build-scan-data",
            "enterprise",
            "init.d",
            "native",
            "nodejs",
            "notifications",
            "undefined-build",
            "workers",
            "wrapper",
            "yarn",
        ]
        writeInitScript("CACHEDIR_TAG")
        for (dir in (dirsThatShouldBeMarked + dirsThatShouldNotBeMarked)) {
            new File(executer.gradleUserHomeDir, dir).mkdirs()
        }

        when:
        succeeds("help")

        then:
        for (dir in dirsThatShouldBeMarked) {
            assert new File(new File(executer.gradleUserHomeDir, dir), "CACHEDIR.TAG").exists()
        }
        for (dir in dirsThatShouldNotBeMarked) {
            assert !new File(new File(executer.gradleUserHomeDir, dir), "CACHEDIR.TAG").exists()
        }
    }
}
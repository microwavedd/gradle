/*
 * Copyright 2015 the original author or authors.
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

package org.gradle.tooling.internal.provider.test;

import org.gradle.tooling.internal.protocol.events.InternalTestDescriptor;
import org.gradle.tooling.internal.protocol.test.InternalDebugOptions;
import org.gradle.tooling.internal.protocol.test.InternalJvmTestRequest;

import java.util.Collection;
import java.util.List;

/**
 * @since 2.7-rc-1
 */
public interface ProviderInternalTestExecutionRequest {
    Collection<InternalTestDescriptor> getTestExecutionDescriptors();
    Collection<String> getTestClassNames();
    Collection<InternalJvmTestRequest> getInternalJvmTestRequests(Collection<InternalJvmTestRequest> defaults);
    InternalDebugOptions getDebugOptions();
    List<String> getTestTasks();
}

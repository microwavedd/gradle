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

package org.gradle.internal.build.event.types;

import org.gradle.api.NonNullApi;
import org.gradle.tooling.internal.protocol.problem.InternalTypeValidationData;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@NonNullApi
public class DefaultTypeValidationData implements InternalTypeValidationData, Serializable {
    private final String pluginId;
    private final String propertyName;
    private final String parentPropertyName;
    private final String typeName;

    public DefaultTypeValidationData(String pluginId, String propertyName, String parentPropertyName, String typeName) {
        this.pluginId = pluginId;
        this.propertyName = propertyName;
        this.parentPropertyName = parentPropertyName;
        this.typeName = typeName;
    }

    @Override
    public String getPluginId() {
        return pluginId;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public String getParentPropertyName() {
        return parentPropertyName;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public Map<String, Object> getAsMap() {
        Map<String, Object> result = new HashMap<>();
        result.put("pluginId", pluginId);
        result.put("propertyName", propertyName);
        result.put("parentPropertyName", parentPropertyName);
        result.put("typeName", typeName);
        return result;
    }
}

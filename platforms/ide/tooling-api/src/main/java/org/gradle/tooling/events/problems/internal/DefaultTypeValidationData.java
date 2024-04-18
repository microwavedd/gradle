/*
 * Copyright 2024 the original author or authors.
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

package org.gradle.tooling.events.problems.internal;

import org.gradle.tooling.events.problems.TypeValidationData;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DefaultTypeValidationData implements TypeValidationData, Serializable {
    private final String pluginId;
    private final String propertyName;
    private final String parentPropertyName;
    private final String typeName;

    public DefaultTypeValidationData(
        @Nullable String pluginId,
        @Nullable String propertyName,
        @Nullable String parentPropertyName,
        @Nullable String typeName
    ) {
        this.pluginId = pluginId;
        this.propertyName = propertyName;
        this.parentPropertyName = parentPropertyName;
        this.typeName = typeName;
    }

    @Override
    @Nullable
    public String getPluginId() {
        return pluginId;
    }

    @Override
    @Nullable
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    @Nullable
    public String getParentPropertyName() {
        return parentPropertyName;
    }

    @Override
    @Nullable
    public String getTypeName() {
        return typeName;
    }

    @Override
    public Map<String, Object> getAsMap() {
        Map<String, Object> result = new HashMap<>();
        if (pluginId != null) {
            result.put("pluginId", pluginId);
        }
        if (parentPropertyName != null) {
            result.put("parentPropertyName", parentPropertyName);
        }
        if (propertyName != null) {
            result.put("propertyName", propertyName);
        }
        if (typeName != null) {
            result.put("typeName", typeName);
        }
        return result;
    }
}

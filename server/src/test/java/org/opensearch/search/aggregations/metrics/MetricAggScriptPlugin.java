/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 */

/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/*
 * Modifications Copyright OpenSearch Contributors. See
 * GitHub history for details.
 */

package org.opensearch.search.aggregations.metrics;

import org.opensearch.script.MockScriptPlugin;
import org.opensearch.search.lookup.LeafDocLookup;
import org.opensearch.test.OpenSearchTestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Provides a number of dummy scripts for tests.
 *
 * Each script provided allows for an {@code inc} parameter which will
 * be added to each value read from a document.
 */
public class MetricAggScriptPlugin extends MockScriptPlugin {

    /** The name of the script engine type this plugin provides. */
    public static final String METRIC_SCRIPT_ENGINE = "metric_scripts";

    /** Script to take a field name in params and sum the values of the field. */
    public static final String SUM_FIELD_PARAMS_SCRIPT = "sum_field_params";

    /** Script to sum the values of a field named {@code values}. */
    public static final String SUM_VALUES_FIELD_SCRIPT = "sum_values_field";

    /** Script to return the value of a field named {@code value}. */
    public static final String VALUE_FIELD_SCRIPT = "value_field";

    /** Script to return the {@code _value} provided by aggs framework. */
    public static final String VALUE_SCRIPT = "_value";

    /** Script to return a random double */
    public static final String RANDOM_SCRIPT = "Math.random()";

    @Override
    public String pluginScriptLang() {
        return METRIC_SCRIPT_ENGINE;
    }

    @Override
    protected Map<String, Function<Map<String, Object>, Object>> pluginScripts() {
        Map<String, Function<Map<String, Object>, Object>> scripts = new HashMap<>();
        Function<Map<String, Object>, Integer> getInc = vars -> {
            if (vars == null || vars.containsKey("inc") == false) {
                return 0;
            } else {
                return ((Number) vars.get("inc")).intValue();
            }
        };
        BiFunction<Map<String, Object>, String, Object> sum = (vars, fieldname) -> {
            int inc = getInc.apply(vars);
            LeafDocLookup docLookup = (LeafDocLookup) vars.get("doc");
            List<Long> values = new ArrayList<>();
            for (Object v : docLookup.get(fieldname)) {
                values.add(((Number) v).longValue() + inc);
            }
            return values;
        };
        scripts.put(SUM_FIELD_PARAMS_SCRIPT, vars -> {
            String fieldname = (String) vars.get("field");
            return sum.apply(vars, fieldname);
        });
        scripts.put(SUM_VALUES_FIELD_SCRIPT, vars -> sum.apply(vars, "values"));
        scripts.put(VALUE_FIELD_SCRIPT, vars -> sum.apply(vars, "value"));
        scripts.put(VALUE_SCRIPT, vars -> {
            int inc = getInc.apply(vars);
            return ((Number) vars.get("_value")).doubleValue() + inc;
        });
        return scripts;
    }

    @Override
    protected Map<String, Function<Map<String, Object>, Object>> nonDeterministicPluginScripts() {
        Map<String, Function<Map<String, Object>, Object>> scripts = new HashMap<>();

        scripts.put("Math.random()", vars -> OpenSearchTestCase.randomDouble());

        return scripts;
    }
}

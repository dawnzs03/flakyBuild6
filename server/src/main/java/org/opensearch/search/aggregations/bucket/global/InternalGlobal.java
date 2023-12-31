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

package org.opensearch.search.aggregations.bucket.global;

import org.opensearch.core.common.io.stream.StreamInput;
import org.opensearch.search.aggregations.InternalAggregations;
import org.opensearch.search.aggregations.bucket.InternalSingleBucketAggregation;

import java.io.IOException;
import java.util.Map;

/**
 * A global scope get (the document set on which we aggregate is all documents in the search context (ie. index + type)
 * regardless the query.
 *
 * @opensearch.internal
 */
public class InternalGlobal extends InternalSingleBucketAggregation implements Global {
    InternalGlobal(String name, long docCount, InternalAggregations aggregations, Map<String, Object> metadata) {
        super(name, docCount, aggregations, metadata);
    }

    /**
     * Read from a stream.
     */
    public InternalGlobal(StreamInput in) throws IOException {
        super(in);
    }

    @Override
    public String getWriteableName() {
        return GlobalAggregationBuilder.NAME;
    }

    @Override
    protected InternalSingleBucketAggregation newAggregation(String name, long docCount, InternalAggregations subAggregations) {
        return new InternalGlobal(name, docCount, subAggregations, getMetadata());
    }
}

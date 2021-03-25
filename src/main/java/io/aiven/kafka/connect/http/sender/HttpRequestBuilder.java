/*
 * Copyright 2021 Aiven Oy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.aiven.kafka.connect.http.sender;

import java.net.http.HttpRequest;
import java.time.Duration;

import io.aiven.kafka.connect.http.config.HttpSinkConfig;

interface HttpRequestBuilder {

    String HEADER_AUTHORIZATION = "Authorization";

    String HEADER_CONTENT_TYPE = "Content-Type";

    Duration REQUEST_HTTP_TIMEOUT = Duration.ofSeconds(30);

    HttpRequest.Builder build(final HttpSinkConfig config);

    HttpRequestBuilder DEFAULT_HTTP_REQUEST_BUILDER = config ->
            HttpRequest.newBuilder(config.httpUri()).timeout(REQUEST_HTTP_TIMEOUT);

    HttpRequestBuilder AUTH_HTTP_REQUEST_BUILDER = config -> {
        final var requestBuilder =
                DEFAULT_HTTP_REQUEST_BUILDER.build(config)
                        .header(HEADER_AUTHORIZATION, config.headerAuthorization());
        if (config.headerContentType() != null) {
            requestBuilder.header(HEADER_CONTENT_TYPE, config.headerContentType());
        }
        return requestBuilder;
    };

}

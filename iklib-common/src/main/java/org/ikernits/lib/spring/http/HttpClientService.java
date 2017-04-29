package org.ikernits.lib.spring.http;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;
import com.google.gson.JsonParseException;
import org.ikernits.lib.common.GsonUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface HttpClientService {

    enum HttpRequestType {
        Get, Post, Put, Delete
    }

    class HttpRequest {
        private final URI uri;
        private final HttpRequestType type;
        private final boolean multipart;
        private final Map<String, String> headers;
        private final Map<String, Object> params;

        public static Builder builder(String url) {
            return new Builder(url);
        }

        public static Builder get(String url) {
            return new Builder(url)
                .setType(HttpRequestType.Get);
        }

        public static Builder post(String url) {
            return new Builder(url)
                .setType(HttpRequestType.Post);
        }

        private HttpRequest(HttpRequestType type, boolean multipart, URI uri, Map<String, String> headers, Map<String, Object> params) {
            this.type = type;
            this.multipart = multipart;
            this.uri = uri;
            this.headers = ImmutableMap.copyOf(headers);
            this.params = ImmutableMap.copyOf(params);
        }

        public String getUrl() {
            return uri.toString();
        }

        public URI getUri() {
            return uri;
        }

        public HttpRequestType getType() {
            return type;
        }

        public boolean isMultipart() {
            return multipart;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public static class Builder {
            private HttpRequestType type = HttpRequestType.Get;
            private URI uri = null;
            private boolean multipart;
            private Map<String, String> headers = new HashMap<>();
            private Map<String, Object> params = new HashMap<>();

            public Builder(String url) {
                try {
                    this.uri = new URI(url);
                } catch (URISyntaxException e) {
                    throw new IllegalArgumentException("Invalid URL: '" + url + "'");
                }            }

            public Builder setType(HttpRequestType type) {
                this.type = type;
                return this;
            }

            public Builder setMultipart(boolean multipart) {
                this.multipart = multipart;
                return this;
            }

            public Builder addHeader(String name, String value) {
                headers.put(name, value);
                return this;
            }

            public Builder addParam(String name, String value) {
                params.put(name, value);
                return this;
            }

            public Builder addParam(String name, InputStream inputStream) {
                params.put(name, inputStream);
                return this;
            }

            public Builder addParam(String name, byte[] data) {
                params.put(name, data);
                return this;
            }

            public Builder addParam(String name, File file) {
                params.put(name, file);
                return this;
            }

            public Builder addJsonParam(String name, Object value) {
                params.put(name, GsonUtils.gson.toJson(value));
                return this;
            }

            public HttpRequest build() {
                return new HttpRequest(
                    type, multipart, uri, headers, params
                );
            }


        }
    }

    class HttpResponse {
        private final Integer code;
        private final Map<String, List<String>> headers;
        private final byte[] body;

        public HttpResponse(Integer code, Map<String, List<String>> headers, byte[] body) {
            this.code = code;
            this.headers = headers;
            this.body = body;
        }

        public Integer getCode() {
            return code;
        }

        public Map<String, List<String>> getHeaders() {
            return headers;
        }

        public String getFirstHeader(String name) {
            return headers.getOrDefault(name, ImmutableList.of()).stream()
                .findFirst().orElse(null);
        }

        public byte[] getBody() {
            return body;
        }

        public String getBodyAsString() {
            return new String(body, Charsets.UTF_8);
        }
    }


    HttpResponse execute(HttpRequest request);

    default <T> T executeWithJsonResponse(HttpRequest request, Class<T> responseType) {
        return executeWithJsonResponse(request, responseType, r -> true);
    }

    default <T> T executeWithJsonResponse(HttpRequest request, Class<T> responseType, Predicate<T> verifier) {
        return executeWithJsonResponse(request, TypeToken.of(responseType), verifier);
    }

    default <T> T executeWithJsonResponse(HttpRequest request, TypeToken<T> responseType) {
        return executeWithJsonResponse(request, responseType, r -> true);
    }

    default <T> T executeWithJsonResponse(HttpRequest request, TypeToken<T> responseType, Predicate<T> verifier) {
        HttpResponse response = execute(request);
        T result;
        try {
            result = GsonUtils.gson.fromJson(response.getBodyAsString(), responseType.getType());
        } catch (JsonParseException ex) {
            throw new HttpClientException(
                HttpClientException.Type.ParseError,
                request,
                response,
                "Failed to parse json response of type '" + responseType.getType().getTypeName() + "'",
                ex
            );
        }

        if (!verifier.test(result)) {
            throw new HttpClientException(
                HttpClientException.Type.VerifyError,
                request,
                response,
                "Failed to verify response of type '" + responseType.getType().getTypeName() + "'",
                null
            );
        }

        return result;
    }

    class HttpClientException extends RuntimeException {
        public enum Type {
            IoFailed, Timeout, BadUri, BadHttpCode, ParseError, VerifyError
        }

        private final Type type;
        private final HttpRequest request;
        private final HttpResponse response;

        public HttpClientException(Type type, HttpRequest request, HttpResponse response, String message, Throwable cause) {
            super(message, cause);
            this.type = type;
            this.request = request;
            this.response = response;
        }

        public Type getType() {
            return type;
        }

        public HttpRequest getRequest() {
            return request;
        }

        public HttpResponse getResponse() {
            return response;
        }
    }
}

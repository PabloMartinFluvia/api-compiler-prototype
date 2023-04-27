package com.example.demoJdoodle;

import com.example.demoJdoodle.compilers.*;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;

@Component
public class Proxy {

    private final WorkingApisSpecs specsProvider;
    private WebClient client;

    public Proxy(WorkingApisSpecs workingApisSpec) {
        specsProvider = workingApisSpec;

        client = WebClient.create(); //TODO: configure + optimize
    }

    public Mono<ApiResponseBody> requestRunnable(ApiCompiler apiCompiler, ApiRequestBody body){
        ApiSpec apiSpec = specsProvider.getConnectionSpecs(apiCompiler);
        WebClient.RequestHeadersSpec<?> headersSpec = client
                .method(apiSpec.getRequestMethod())
                .uri(apiSpec.getRequestUrl())
                .contentType(apiSpec.getRequestContentType())
                .bodyValue(body);
        return initHeaders(headersSpec,apiSpec)
                .retrieve()
                .bodyToMono(ApiResponseBody.class);
    }

    private WebClient.RequestHeadersSpec<?> initHeaders(
            WebClient.RequestHeadersSpec<?> headersSpec,
            ApiSpec apiSpec){

        Map<String,String> apiHeadersMap =apiSpec.getCommonHeaders();
        Set<String> requiredApiHeaders = apiHeadersMap.keySet();
        for (String requiredHeader : requiredApiHeaders){
            String valueOfRequierdHeader = apiHeadersMap.get(requiredHeader);
            headersSpec = headersSpec
                    .header(requiredHeader,valueOfRequierdHeader);
        }
        return headersSpec;
    }
}

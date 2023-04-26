package com.example.demoJdoodle.actual;

import com.example.demoJdoodle.actual.compilers.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class DemoService {

    private final Proxy proxy;

    private ApiCompiler apiTarget;

    private final String requestBodysPath = "json/requestBodys";

    //TODO: previous steps.
    public Mono<ApiResponseBody> demoOnlineCoderCompiler(int indexToTest){
        apiTarget = ApiCompiler.ONLINE_CODE_COMPILER;

        String compilerFolder = "/online_coder_compiler";
        String bodysJson = "/examples.json";
        String jsonPath = requestBodysPath+compilerFolder+bodysJson;
        ApiRequestBody requestBody = initBody(jsonPath, ApiRequestBody[].class)[indexToTest];
        //TODO: change previous steps. From this point our app is designed for work once:
        //  a) we know to which api use (one object of the enum ApiCompiler)
        //  b) The request body is ready (schema maybe depends on api used)
        return callProxy(requestBody);
    }

    //ApiCompiler: specifies which one we want to use. requestBody: content to POST
    private Mono<ApiResponseBody> callProxy(ApiRequestBody requestBody){
        return proxy.requestRunnable(apiTarget, requestBody);
    }

    private <T> T initBody(String jsonPath, Class<T> targetClass){
        ObjectMapper mapper = new ObjectMapper();
        try {
            InputStream inputStream = new ClassPathResource(jsonPath).getInputStream();
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            String resourceString = FileCopyUtils.copyToString(reader);
            return mapper.readValue(resourceString, targetClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

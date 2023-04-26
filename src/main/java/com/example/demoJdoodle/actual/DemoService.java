package com.example.demoJdoodle.actual;

import com.example.demoJdoodle.actual.apicompiler.*;
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

    private final String requestBodysPath = "json/requestBodys";

    public Mono<ApiResponseBody> demoOnlineCoderCompiler(int indexToTest){
        String compilerFolder = "/online_coder_compiler";
        String bodysJson = "/examples.json";
        String jsonPath = requestBodysPath+compilerFolder+bodysJson;
        ApiRequestBody requestBody = initBody(jsonPath, ApiRequestBody[].class)[indexToTest];
        return callProxy(ApiCompiler.ONLINE_CODE_COMPILER, requestBody);
    }

    //ApiCompiler: specifies which one we want to use. requestBody: content to POST
    private Mono<ApiResponseBody> callProxy(ApiCompiler apiCompiler, ApiRequestBody requestBody){
        return proxy.requestRunnable(apiCompiler, requestBody);
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

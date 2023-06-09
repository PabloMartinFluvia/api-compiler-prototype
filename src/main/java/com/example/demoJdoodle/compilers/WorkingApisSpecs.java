package com.example.demoJdoodle.compilers;

import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WorkingApisSpecs {
    
    private Map<ApiCompiler, ApiSpec> specsProvider;

    @PostConstruct
    private void init(){
        specsProvider = new HashMap<>();
        specsProvider.put(ApiCompiler.ONLINE_CODE_COMPILER, initOnlineCodeCompilerDetails());
        //and others...
    }

    //TODO: values from properties?
    private ApiSpec initOnlineCodeCompilerDetails() {
        Map<String,String> commonHeaders = new HashMap<>();
        commonHeaders.put("X-RapidAPI-Key","2a497261e9msh06d1774713adaedp177aa0jsnfc89ff1d7af0");
        commonHeaders.put("X-RapidAPI-Host", "online-code-compiler.p.rapidapi.com");
        return ApiSpec.builder()
                .commonHeaders(commonHeaders)
                .requestMethod(HttpMethod.POST)
                .requestUrl("https://online-code-compiler.p.rapidapi.com/v1/")
                .requestContentType(MediaType.APPLICATION_JSON)
                .languagesMethod(HttpMethod.GET)
                .languagesUrl("https://online-code-compiler.p.rapidapi.com/v1/languages/")
                .build();
    }

    public ApiSpec getConnectionSpecs(ApiCompiler apiCompiler){
        ApiSpec specs = specsProvider.get(apiCompiler);
        if(specs != null){
            return specs;
        }else {
            throw new RuntimeException(apiCompiler.name() + "has no specifications stored");
        }
    }
}

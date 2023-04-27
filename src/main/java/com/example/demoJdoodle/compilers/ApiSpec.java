package com.example.demoJdoodle.compilers;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.Map;

@Builder
@Getter
public class ApiSpec {

    //Generalizar / Augmentar / Modificar  según necesidad

    private Map<String,String> commonHeaders; //in both endpoints


    private HttpMethod requestMethod;

    private String requestUrl;

    private MediaType requestContentType; //in header or by .accept



    private HttpMethod languagesMethod;

    private String languagesUrl;

}

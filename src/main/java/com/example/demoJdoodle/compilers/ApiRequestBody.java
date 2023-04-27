package com.example.demoJdoodle.compilers;


import lombok.*;

@Getter
@Setter
public class ApiRequestBody {

    private String language; //use lowercase + see resources.json.languages and check used compiler json.

    private String version; // technology Version Index Number or "latest"

    private String code; //see example resource

    private String input; // online code compiler: stdin value (User input)

    public ApiRequestBody() {
        //empty constructor for deserialization from demos json resources
    }
}

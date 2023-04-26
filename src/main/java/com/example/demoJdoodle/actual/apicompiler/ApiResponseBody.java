package com.example.demoJdoodle.actual.apicompiler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApiResponseBody {

    private String cpuTime;

    private String memory;

    private String output;

    private Object language; //see shchema in test.resources.json
}

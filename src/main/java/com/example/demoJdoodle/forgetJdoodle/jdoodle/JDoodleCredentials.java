package com.example.demoJdoodle.forgetJdoodle.jdoodle;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JDoodleCredentials {

    private String clientId = "Pablo Martin";//"paumf@hotmail.com"; //autowired from properites

    private String clientSecret="challengeapi2"; // autowired from properties
}


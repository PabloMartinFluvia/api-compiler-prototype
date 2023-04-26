package com.example.demoJdoodle.forgetJdoodle.jdoodle;

import com.example.demoJdoodle.forgetJdoodle.model.Challenge;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
//for mapping in tests:
@NoArgsConstructor
@Setter
public class JDoodleRequestDto {

    private String clientId;

    private String clientSecret;

    private String script; //program to compile (and execute)

    //private String stdin; //???

    private String language; //see suported languages in https://docs.jdoodle.com/integrating-compiler-ide-to-your-application/compiler-api/rest-api

    private String versionIndex; // the version index of the language to be used (ídem)

    //private boolean compileOnly = false; //default. True if only wanted compilation without execution

    public JDoodleRequestDto(Challenge challenge, JDoodleCredentials credentials) {
        clientId = credentials.getClientId();
        clientSecret = credentials.getClientSecret();
        script = challenge.getApplicationToRunAsString();
        language = challenge.getLanguage().asLowercase();
        versionIndex = challenge.getVersionIndex();
    }
}

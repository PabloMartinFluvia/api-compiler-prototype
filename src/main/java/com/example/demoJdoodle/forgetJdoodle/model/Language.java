package com.example.demoJdoodle.forgetJdoodle.model;

import java.util.Map;

public enum Language {
    JAVA("java", Map.of("JDK 1.8.0_66",0,"JDK 9.0.1",1,"JDK 10.0.1",2,"JDK 11.0.4",3, "JDK 17.0.1", 4)),
    PHP("php", Map.of("5.6.16",0,"7.1.11",1,"7.2.5",2,"7.3.10",3,"8.0.13",4)),

    //TODO: jdoodle don't say anything about javascript, only node. It's a problem?
    //https://docs.jdoodle.com/integrating-compiler-ide-to-your-application/languages-and-versions-supported-in-api-and-plugins
    NODEJS("nodejs",Map.of("6.3.1",0,"9.2.0",1,"10.1.0",2,"12.11.1",3,"17.1.0",4)),

    //TODO: ask wich one?
    PYTHON2("python2", Map.of("2.7.11",0,"2.7.15",1,"2.7.16",2,"2.7.18",3)),
    PYTHON3("python3",Map.of("3.5.1",0,"3.6.3",1,"3.6.5",2,"3.7.4",3,"3.9.9",4));

    private String value;
    private Map<String, Integer> allowedVersions;

    Language(String value, Map<String, Integer> allowedVersions) {
        this.value = value;
        this.allowedVersions = allowedVersions;
    }

    public String asLowercase(){
        return this.value;
    }

    @Override
    public String toString() {
        return this.name();
    }

    public Map<String, Integer> getAllowedVersions(){
        return allowedVersions;
    }
}

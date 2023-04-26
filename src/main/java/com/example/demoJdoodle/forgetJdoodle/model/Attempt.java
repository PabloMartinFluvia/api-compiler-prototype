package com.example.demoJdoodle.forgetJdoodle.model;

public class Attempt {

    private Object code; //TODO: decide type for store the user code

    private Language language; //see suported languages in https://docs.jdoodle.com/integrating-compiler-ide-to-your-application/compiler-api/rest-api

    private String languageVersion; // the version index of the language to be used (ídem)

    private String runnableOutput; //output from jdoodle dto

    //TODO: how decide wich version of the language is going to use jdoodle
    public Attempt(Object code, Language language, String languageVersion) {
        this.code = code;
        this.language = language;
        this.languageVersion = languageVersion;
    }

    public Attempt(Object code, Language language) {
        this.code = code;
        this.language = language;
        //use random version available (used for tests).
        this.languageVersion = language.getAllowedVersions().keySet().stream().findFirst().orElseThrow(() -> new RuntimeException(language.asLowercase()+" does not contain any valid version"));
    }

    public Object getCode(){
        return code;
    }

    public Language getLanguage() {
        return language;
    }

    public String getVersionIndex() {
        Integer versionIndex = language.getAllowedVersions().get(languageVersion);
        if(versionIndex != null){
            return versionIndex.toString();
        }else {
            throw new RuntimeException("Jdoodle does not accept "+languageVersion+" for "+language.toString());
        }
    }

    public String getRunnableOutput() {
        return runnableOutput;
    }

    public void setRunnableOutput(String runnableOutput) {
        this.runnableOutput = runnableOutput;
    }
}

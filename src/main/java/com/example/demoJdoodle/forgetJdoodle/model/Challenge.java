package com.example.demoJdoodle.forgetJdoodle.model;

import com.example.demoJdoodle.forgetJdoodle.helpers.CodeConverter;

public class Challenge {

    private Attempt attempt;

    private ChallengeValidator validator;

    private CodeConverter codeConverter;

    public Challenge(ChallengeValidator validator) {
        this.validator = validator;
        codeConverter = new CodeConverter(validator.getInputArguments());
    }

    public void storeApiOutput(String jDoodleResults){
        attempt.setRunnableOutput(jDoodleResults);
    }

    public String getApiOutput(){
        return attempt.getRunnableOutput();
    }

    public boolean isValidResults(){
        return validator.isValidResults(attempt.getRunnableOutput());
    }

    public String getApplicationToRunAsString() {
        return codeConverter.codeToRunnableApplication(attempt);
    }

    public Language getLanguage() {
        return attempt.getLanguage();
    }

    public String getVersionIndex() {
        return attempt.getVersionIndex();
    }

    public void setAttempt(Attempt attempt) {
        this.attempt = attempt;
    }

    //for testing purposes (code converter mocked)
    public void setCodeConverter(CodeConverter codeConverter) {
        this.codeConverter = codeConverter;
    }
}

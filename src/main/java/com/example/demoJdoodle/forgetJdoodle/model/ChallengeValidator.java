package com.example.demoJdoodle.forgetJdoodle.model;

public class ChallengeValidator {

    private final Object inputArguments; // to insert in "application to run" -> jdoodle needs it to execute (to provide a result)

    private final  Object expectedResults; //to compare with the results in jdoodle response

    private String runnableOutput;

    public ChallengeValidator(Object inputArguments, Object expectedResults) {
        this.inputArguments = inputArguments;
        this.expectedResults = expectedResults;
    }

    public boolean isValidResults(String runnableOutput) {
        //TODO: map jDoodleResults to the expected type + check them comparing to expected results
        return false;
    }

    public Object getInputArguments() {
        return inputArguments;
    }

    public String getRunnableOutput() {
        return runnableOutput;
    }
}

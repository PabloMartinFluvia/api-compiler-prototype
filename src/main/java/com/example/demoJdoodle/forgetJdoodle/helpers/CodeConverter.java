package com.example.demoJdoodle.forgetJdoodle.helpers;

import com.example.demoJdoodle.forgetJdoodle.model.Attempt;
import com.example.demoJdoodle.forgetJdoodle.model.Language;

public class CodeConverter {

    private Object args;

    public CodeConverter(Object inputArgs) {
        this.args = inputArgs;
    }

    public String codeToRunnableApplication(Attempt attempt) {
        Object code = attempt.getCode();
        Language language = attempt.getLanguage();
        //TODO: parse code to runnable app.
        // Note that language used (stored in attempt) determine
        // how transformation should be done.
        // Also must include input args.
        return null;
    }
}

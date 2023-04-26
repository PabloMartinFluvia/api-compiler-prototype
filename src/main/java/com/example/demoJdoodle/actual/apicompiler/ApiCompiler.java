package com.example.demoJdoodle.actual.apicompiler;

//Source: https://rapidapi.com/search/online%2Bcompiler
public enum ApiCompiler {

    //meh... not tried
    //CODE_X("https://rapidapi.com/jaagravseal03/api/codex7/details"),
    //5000/day + 0,05$ each extra + needs credit card for free singup + no php neither javascript/node

    //testing...
    ONLINE_CODE_COMPILER("https://rapidapi.com/Glavier/api/online-code-compiler/");
    //500/day + free singup + javascrip -> node.js

    //more to add...

    private String docsWeb;

    ApiCompiler(String docsWeb) {
        this.docsWeb = docsWeb;
    }

    public String getDocsWeb() {
        return docsWeb;
    }
}

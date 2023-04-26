package com.example.demoJdoodle.forgetJdoodle.jdoodle;

import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
public class JDoodleResponseDto {

    //params allays (success or failed execution)

    private Integer statusCode; // HTTP status

    //params when execution ok

    private String output; //results can be of any type (jdoodle provides as String)

    private Number memory; // Memory used by the program

    private Number cpuTime; // CPU Time used by the program

    // Only when the "compileOnly" option in the request is true. 1 - error. 0 - success.
    private int compilationStatus;


    //params when execution fails

    private String error; //message

    public String getOutput() {
        return output;
    }
}

package com.example.demoJdoodle.forgetJdoodle.services;

import com.example.demoJdoodle.forgetJdoodle.helpers.CodeConverter;
import com.example.demoJdoodle.forgetJdoodle.model.Attempt;
import com.example.demoJdoodle.forgetJdoodle.model.Challenge;
import com.example.demoJdoodle.forgetJdoodle.model.ChallengeValidator;
import com.example.demoJdoodle.forgetJdoodle.model.Language;
import com.example.demoJdoodle.forgetJdoodle.proxy.ChallengeProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AttemptServiceTest {

    @Autowired
    private AttemptService attemptService;

    @MockBean
    private ChallengeProxy challengeProxy;

    private Challenge challenge;

    @Mock
    private ChallengeValidator challengeValidator;

    @Mock
    private CodeConverter codeConverter;

    private static List<Attempt> attempts = new ArrayList<>();

    private static List<String> scripts = new ArrayList<>();

    private static List<String> expectedJDoodleResult = new ArrayList<>();


    @BeforeAll
    static void setup(){
        //todo: init / configure bean credentials

        attempts.add(new Attempt(null, Language.PHP));
        scripts.add("<?php echo \"hello\"; ?>");
        expectedJDoodleResult.add("hello");
    }

    @ParameterizedTest
    @MethodSource("attemptsArguments")
    void tryAttemptTest(Attempt attempt, String script, String result){
        Object challengeId = 1;
        challenge = new Challenge(challengeValidator);
        challenge.setCodeConverter(codeConverter);
        when(challengeProxy.readChallengeById(challengeId)).thenReturn(challenge);
        when(challengeValidator.getInputArguments()).thenReturn(null);
        when(codeConverter.codeToRunnableApplication(attempt)).thenReturn(script);
        when(challengeValidator.isValidResults(any(String.class))).thenReturn(true);


        Mono<Challenge> success = attemptService.tryAttempt(attempt,challengeId);
        StepVerifier.create(success)
                .assertNext(challenge -> {
                    System.out.println(result);
                    Assertions.assertEquals(result,challenge.getApiOutput());
                })
                .verifyComplete();
    }


    private static Stream<Arguments> attemptsArguments(){
        return Stream.of(
                Arguments.of(attempts.get(0), scripts.get(0), expectedJDoodleResult.get(0))
        );
    }
}

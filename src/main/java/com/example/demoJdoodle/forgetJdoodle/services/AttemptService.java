package com.example.demoJdoodle.forgetJdoodle.services;

import com.example.demoJdoodle.forgetJdoodle.jdoodle.JDoodleCredentials;
import com.example.demoJdoodle.forgetJdoodle.jdoodle.JDoodleRequestDto;
import com.example.demoJdoodle.forgetJdoodle.jdoodle.JDoodleResponseDto;
import com.example.demoJdoodle.forgetJdoodle.model.Attempt;
import com.example.demoJdoodle.forgetJdoodle.model.Challenge;
import com.example.demoJdoodle.forgetJdoodle.proxy.ChallengeProxy;
import com.example.demoJdoodle.forgetJdoodle.proxy.JdoodleProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AttemptService {

    private final JdoodleProxy jdoodleProxy;

    private final ChallengeProxy challengeProxy;

    private final JDoodleCredentials credentials;

    public Mono<Challenge> tryAttempt(Attempt attempt, Object challengeId){
        //TODO: modify logic -> 100% reactive
        Challenge challenge = challengeProxy.readChallengeById(challengeId);
        //TODO: some validation to verify this attemp is valid for this Challenge
        challenge.setAttempt(attempt);
        JDoodleRequestDto jDoodleRequest = new JDoodleRequestDto(challenge,credentials);
        System.out.println(""+jDoodleRequest);
        Mono<JDoodleResponseDto> jDoodleResponseMono = jdoodleProxy.postResultsFromAttemp(jDoodleRequest);
        //Mono<JDoodleResponseDto> jDoodleResponseMono = null;
        return jDoodleResponseMono
                .map(jDoodleResponse -> {
                    challenge.storeApiOutput(jDoodleResponse.getOutput());
                    return challenge;
                });
    }
}

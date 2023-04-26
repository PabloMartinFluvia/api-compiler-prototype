package com.example.demoJdoodle.forgetJdoodle.proxy;

import com.example.demoJdoodle.forgetJdoodle.jdoodle.JDoodleRequestDto;
import com.example.demoJdoodle.forgetJdoodle.jdoodle.JDoodleResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.FileCopyUtils;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JdoodleProxyTest {

    @Autowired
    private JdoodleProxy proxy;


    @Test
    void postResultsFromAttempTest() throws JsonProcessingException {
        String dto = init("json/requestDoodle.json");

        JDoodleRequestDto requestDto = new ObjectMapper().readValue(dto, JDoodleRequestDto.class);
        System.out.println(requestDto);
        Mono<JDoodleResponseDto> response = proxy.postResultsFromAttemp(requestDto);
        StepVerifier.create(response)
                .verifyComplete();
    }

    private String init(String jsonPath){
        try {
            InputStream inputStream = new ClassPathResource(jsonPath).getInputStream();
            Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

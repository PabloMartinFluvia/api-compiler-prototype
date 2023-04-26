package com.example.demoJdoodle.forgetJdoodle.proxy;

import com.example.demoJdoodle.forgetJdoodle.jdoodle.JDoodleRequestDto;
import com.example.demoJdoodle.forgetJdoodle.jdoodle.JDoodleResponseDto;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class JdoodleProxy {

    private WebClient client;

    private final Integer MAX_BYTES_IN_MEMORY;

    private final String jdoodleUri;

    private static final Logger log = LoggerFactory.getLogger(JdoodleProxy.class);



    public JdoodleProxy(){
        //TODO: init constants from properties config (Autowired as arg)
        MAX_BYTES_IN_MEMORY= 30000000; //from properties config, in milis
        jdoodleUri = "https://api.jdoodle.com/v1/execute";
        final Integer CONNECTION_TIMEOUT = 30000; //from properties config, in milis

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECTION_TIMEOUT)
                .responseTimeout(Duration.ofMillis(CONNECTION_TIMEOUT))
                .compress(true)
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)));

        client = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                //.exchangeStrategies(ExchangeStrategies.builder()
                //        .codecs(this::acceptedCodecs)
                //        .build())
                .build();

    }

    /*
    //TODO: check if mediaType.textPlain is valid?? (docs says accept produces only application json)
    private void acceptedCodecs(ClientCodecConfigurer clientCodecConfigurer) {
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        clientCodecConfigurer.defaultCodecs().maxInMemorySize(MAX_BYTES_IN_MEMORY);
        clientCodecConfigurer.customCodecs().registerWithDefaultConfig(new Jackson2JsonDecoder(mapper, MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
    }
    */

    //webclient configured for work only with jdoodle (from DOCS in https://docs.jdoodle.com/integrating-compiler-ide-to-your-application/compiler-api/rest-api)
    //curl -H "Content-Type: application/json;
    // charset=UTF-8"
    // -X POST
    // -d '{"clientId": "YourClientId","clientSecret":"YourClientSecret","script":"","language":"php","versionIndex":"0"}'
    // https://api.jdoodle.com/v1/execute
    public Mono<JDoodleResponseDto> postResultsFromAttemp(JDoodleRequestDto requestBody){
        log.info("Proxy: Executing remote invocation to " + jdoodleUri);
        //TODO: try catch NullPointException (if string is null) and IllegalArgumentException if String not valid.
        // Obs: IllegalArgumentException wraps URISyntaxExcpetion (see javadoc)
        URI uri = URI.create(jdoodleUri);

        return client.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .bodyValue(requestBody)
                .header("a","a")

                .retrieve()
                .bodyToMono(JDoodleResponseDto.class);

                /*

                //OR: if we know jdoodle always provides status OK when execution suceeds
                //Obs: seems a successful execution is always HTTPStatus.OK (see docs)
                //alsco could be: 401 (unauthorized: credentials fails) + 429 (daily limit reached)
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToMono(JDoodleResponseDto.class);
                    }
                    else {
                        return response.createError();
                    }
                });

                 */
    }

}

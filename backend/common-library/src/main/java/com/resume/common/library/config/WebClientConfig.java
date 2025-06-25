package com.resume.common.library.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.time.Duration;

@Configuration
@Slf4j
@ConditionalOnProperty(prefix = "openai", name = "api-key")
public class WebClientConfig {

    @Value("${openai.api-key}")
    private String apiKey;
    private static final int CONNECT_TIMEOUT_MS = 5_000;
    private static final int IO_TIMEOUT_S = 30;

    @Bean
    public WebClient openAiWebClient() {

        TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT_MS)
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(IO_TIMEOUT_S))
                        .addHandlerLast(new WriteTimeoutHandler(IO_TIMEOUT_S))
                );

        HttpClient httpClient = HttpClient.from(tcpClient)
                .responseTimeout(Duration.ofSeconds(IO_TIMEOUT_S));

        return WebClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .build();
    }
}

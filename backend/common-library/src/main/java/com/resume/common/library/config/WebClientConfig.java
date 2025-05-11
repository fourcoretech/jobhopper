package com.resume.common.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        TcpClient tcpClient = TcpClient.create()
                .option(io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000) // Connection timeout: 10 seconds
                .doOnConnected(connection ->
                        connection.addHandlerLast(new io.netty.handler.timeout.ReadTimeoutHandler(20)) // Read timeout: 20 seconds
                                .addHandlerLast(new io.netty.handler.timeout.WriteTimeoutHandler(20))); // Write timeout: 20 seconds

        HttpClient httpClient = HttpClient.from(tcpClient);

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}

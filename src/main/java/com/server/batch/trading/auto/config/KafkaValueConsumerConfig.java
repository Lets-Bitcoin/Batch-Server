package com.server.batch.trading.auto.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.batch.trading.auto.domain.repository.BitcoinRepository;
import com.server.batch.trading.auto.domain.repository.EthereumRepository;
import com.server.batch.trading.auto.domain.repository.SolanaRepository;
import com.server.batch.trading.auto.dto.ValueSendRequestDto;
import com.server.batch.trading.auto.util.CoinUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaValueConsumerConfig {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final BitcoinRepository bitcoinRepository;
    private final EthereumRepository ethereumRepository;
    private final SolanaRepository solanaRepository;

    @KafkaListener(topics = "${spring.kafka.template.default-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(LinkedHashMap<String, Object> message,
                        @Headers MessageHeaders messageHeaders) {

        try {
            ValueSendRequestDto dto = objectMapper.convertValue(message, ValueSendRequestDto.class);

            if (dto.getMarket().equals(CoinUtil.BITCOIN)) {
                bitcoinRepository.save(dto.toBitcoin());
            } else if (dto.getMarket().equals(CoinUtil.ETHEREUM)) {
                ethereumRepository.save(dto.toEthereum());
            } else if (dto.getMarket().equals(CoinUtil.SOLANA)) {
                solanaRepository.save(dto.toSolana());
            }

            log.info("consumer: success >>> message: {}, headers: {}", message.toString(), messageHeaders);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

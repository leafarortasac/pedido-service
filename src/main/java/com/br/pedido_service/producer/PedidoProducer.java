package com.br.pedido_service.producer;

import com.br.pedido_service.config.RabbitMQConfig;
import com.br.shared.contracts.model.PedidoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoProducer {

    private final RabbitTemplate rabbitTemplate;

    public void enviaPedidoParaFila(PedidoRepresentation pedido) {
        log.info("Encaminhando pedido {} para a fila de processamento", pedido.getId());

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                pedido
        );
    }
}
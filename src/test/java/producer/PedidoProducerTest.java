package producer;

import com.br.pedido_service.config.RabbitMQConfig;
import com.br.pedido_service.producer.PedidoProducer;
import com.br.shared.contracts.model.PedidoRepresentation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PedidoProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private PedidoProducer pedidoProducer;

    @Test
    @DisplayName("Deve enviar pedido para a exchange correta no RabbitMQ")
    void deveEnviarPedidoParaFila() {

        var pedido = new PedidoRepresentation();
        pedido.setId("PED-123");

        pedidoProducer.enviaPedidoParaFila(pedido);
        
        verify(rabbitTemplate, times(1)).convertAndSend(
                eq(RabbitMQConfig.EXCHANGE_NAME),
                eq(RabbitMQConfig.ROUTING_KEY),
                eq(pedido)
        );
    }
}

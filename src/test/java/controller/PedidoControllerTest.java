package controller;

import com.br.pedido_service.controller.PedidoController;
import com.br.pedido_service.producer.PedidoProducer;
import com.br.shared.contracts.model.PedidoRepresentation;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoControllerTest {

    @Mock
    private PedidoProducer producer;

    @InjectMocks
    private PedidoController controller;

    @Test
    @DisplayName("Deve processar lote de pedidos aleatórios com sucesso")
    void deveProcessarLoteComSucesso() {

        var generator = new EasyRandom();

        var lista = generator.objects(PedidoRepresentation.class, 5).toList();

        var response = controller.recebePedidos(lista);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(producer, times(5)).enviaPedidoParaFila(any());
    }

    @Test
    @DisplayName("Deve retornar 500 Internal Server Error quando houver falha no producer")
    void deveRetornarError() {

        var lista = List.of(new PedidoRepresentation());
        doThrow(new RuntimeException("Falha no Rabbit")).when(producer).enviaPedidoParaFila(any());

        var response = controller.recebePedidos(lista);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}

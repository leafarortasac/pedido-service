package com.br.pedido_service.controller;

import com.br.pedido_service.producer.PedidoProducer;
import com.br.shared.contracts.api.PedidosApi;
import com.br.shared.contracts.model.PedidoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class PedidoController implements PedidosApi {

    private final PedidoProducer pedidoProducer;

    @Override
    public ResponseEntity<Void> recebePedidos(final List<PedidoRepresentation> list) {
        log.info("Recebendo lote com {} pedidos.", list.size());

        try {
            list.forEach(pedidoProducer::enviaPedidoParaFila);

            return ResponseEntity.status(HttpStatus.ACCEPTED).build();

        } catch (Exception e) {
            log.error("Erro ao enviar lote para a fila: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

Pedido Service (Producer) 📦🚀

O Pedido Service atua como o ponto de entrada (Producer) para o processamento de pedidos na arquitetura de microsserviços. Sua principal responsabilidade é receber requisições via API REST, validar a autenticação do cliente e encaminhar os dados de forma assíncrona para uma fila no RabbitMQ.

🎯 Responsabilidades

Gateway de Entrada: Ponto central para recepção de novos pedidos.
Segurança (M2M): Integração com o IAM-Service para validação de Tokens JWT.
Mensageria: Publicação de mensagens na fila pedido.queue via Exchange dedicada.
Arquitetura Assíncrona: O serviço apenas posta na fila e libera o cliente, garantindo baixa latência e escalabilidade.

🔐 Segurança e Autenticação

Este serviço é protegido e exige um Token JWT válido para qualquer operação de escrita.
Validador: O serviço consome as chaves/segredos compartilhados com o IAM-Service.
Header Requerido: Authorization: Bearer <TOKEN_JWT>

🛠️ Tecnologias

Java 21
Spring Boot 3
Spring AMQP (RabbitMQ): Para postagem das mensagens.
Spring Security: Proteção dos endpoints.
Shared Contracts: Uso de bibliotecas de modelos compartilhados.
Swagger/OpenAPI: Documentação e teste da API.

📡 Endpoints e Payload
Documentação Swagger
Com o serviço rodando, a documentação interativa pode ser acessada em: 🔗 http://localhost:8081/swagger-ui.html

[POST] Enviar Pedidos
Endpoint: /v1/pedidos

O serviço aceita uma lista de pedidos para processamento em lote. Abaixo, um exemplo de payload para execução:
      
    JSON
      [
        {
          "id": "65bef1a8e4b0a1a2b3c4d501",
          "codfilial": "000001",
          "cliente": { "id": "65bef1a8e4b0a1a2b3c4d5c1", "codigo": "000101", "nome": "Ambev Centro" },
          "itens": [
            { "id": "65bef1a8e4b0a1a2b3c4d5a1", "item": 1, "produto": { "id": "65bef1a8e4b0a1a2b3c4d5p1", "codigo": "000501", "nome": "Skol 350ml" }, "qtde": 10.0, "precoUnitario": 3.50, "codfilial": "000001" },
            { "id": "65bef1a8e4b0a1a2b3c4d5a2", "item": 2, "produto": { "id": "65bef1a8e4b0a1a2b3c4d5p2", "codigo": "000502", "nome": "Brahma 350ml" }, "qtde": 5.0, "precoUnitario": 4.00, "codfilial": "000001" }
          ]
        },
        {
          "id": "65bef1a8e4b0a1a2b3c4d506",
          "codfilial": "000002",
          "cliente": { "id": "65bef1a8e4b0a1a2b3c4d5c6", "codigo": "000201", "nome": "Ambev Norte" },
          "itens": [
            { "id": "65bef1a8e4b0a1a2b3c4d5a7", "item": 1, "produto": { "id": "65bef1a8e4b0a1a2b3c4d5p1", "codigo": "000501", "nome": "Skol 350ml" }, "qtde": 100.0, "precoUnitario": 3.40, "codfilial": "000002" }
          ]
        }
      ]
  
🔄 Fluxo de Dados

O Cliente obtém um token no IAM-Service.
O Cliente faz um POST para o Pedido-Service com o payload de pedidos.
O Pedido-Service valida o token e a estrutura do JSON.
O Pedido-Service converte o objeto e publica na fila do RabbitMQ.

📦 Execução Local
Compile os contratos compartilhados: mvn clean install no projeto shared-contracts.

Execute o serviço:
Bash
mvn spring-boot:run

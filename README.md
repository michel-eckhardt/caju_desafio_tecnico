# Desafio Técnico - CAJU

## Índice
1. [Introdução](#introdução)
2. [Tecnologias Utilizadas](#tecnologias-utilizadas)
3. [Arquitetura](#arquitetura)
4. [Instruções de Configuração](#instruções-de-configuração)
5. [Instruções de Execução](#instruções-de-execução)
6. [Testes](#testes)
7. [Decisões Técnicas](#decisões-técnicas)
8. [Próximos Passos](#próximos-passos)

## Introdução
Este projeto foi desenvolvido como parte do desafio técnico para a vaga de Backend na Caju. O objetivo do desafio foi criar um serviço HTTP  que processe a transaction payload JSON.
``` bash
{
	"account": "123",
	"totalAmount": 100.00,
	"mcc": "5811",
	"merchant": "PADARIA DO ZE               SAO PAULO BR"
}
```
O autorizador funciona da seguinte forma:
-  Recebe a transação
-  Usa a MCC para mapear a transação para uma categoria de benefícios
-  Caso tente passar uma determinada transação, mas a pessoa não tenha saldo, verifica na listagem de L3 se o merchant passado tem uma categoria setada. Se tiver, usa. Se não tiver, tenta usar o saldo de CASH
-  Aprova ou rejeita a transação
-  Caso a transação seja aprovada, o saldo da categoria mapeada deverá ser diminuído.


## Tecnologias Utilizadas
- **Java 11**: Linguagem de programação principal.
- **Spring Boot 2.3.1.RELEASE**: Framework para desenvolvimento da aplicação.
- **Lombok**: Biblioteca para redução de boilerplate.
- **JUnit 5**: Framework de testes unitários.
- **Mockito**: Framework para criação de mocks nos testes.
- **Springfox**: Para documentação do swagger
- **Banco h2**: Banco de dados

## Arquitetura
A aplicação foi desenvolvida seguindo o padrão REST. Abaixo, uma breve descrição dos principais componentes:

- **Controller**: Responsável por validar as requisições http.
- **Service**: Contém a lógica de negócio.
- **Repository**: Interface para interação com o banco de dados.

## Instruções de Configuração
### Pré-requisitos
- JDK 11+
- Maven compativel com a versão do jdk

### Configuração
1. Clone o repositório:
   ```bash
   git clone https://github.com/michel-eckhardt/caju_desafio_tecnico.git
   cd auth
   ```
2. Instale as dependências do projeto:
   ```bash
   mvn clean install
   ```
## Instruções de Execução
### Executando a Aplicação
1. Para iniciar a aplicação, use o comando:
   ```bash
   mvn spring-boot:run
   ```
2. A aplicação estará disponível em: http://localhost:8080/auth/v1

### Endpoints Principais
- **SWAGGER** 
http://localhost:8080/auth/swagger-ui/
- **GET /account/{accountId}**: Retorna a conta por ID.
- **POST /authorizer**: Realiza uma transação.

## Testes
### Para executar os testes unitários, utilize o comando:

   ```bash
   mvn test
   ```

## Decisões Técnicas

### Durante o desenvolvimento do projeto, algumas decisões importantes foram tomadas:
- Uso de Lombok: Para simplificar a criação de classes de modelo.
- Padrão Strategy: Implementado para manter o codigo limpo e boas praticas de programação.
- Para buscar clientes cadastrados no case L3, foi usado um mok simulando uma requisição HTTP em uma api de customer.

## L4 Questão Aberta - Transações simultâneas
### Transações simultâneas: 
Dado que o mesmo cartão de crédito pode ser utilizado em diferentes serviços online,
existe uma pequena mas existente probabilidade de ocorrerem duas transações ao mesmo tempo. O que você faria para
garantir que apenas uma transação por conta fosse processada em um determinado momento?
Esteja ciente do fato de que todas as solicitações de transação são síncronas e devem ser processadas rapidamente
(menos de 100 ms), ou a transação atingirá o timeout.

### Solução
Utilizar uma fila distribuída para gerenciar as transações.
As transações são enfileiradas e processadas uma a uma.

### Como funciona:
Cada transação é colocada em uma fila específica para a conta.
Um worker processa as transações da fila, garantindo que apenas uma transação por vez seja processada.
Isso pode ser implementado usando ferramentas como Redis, Kafka, ou SQS (Amazon Simple Queue Service).

- ***Vantagens***: Simples de implementar, escalável e garante processamento ordenado.
- ***Desvantagens***: Pode introduzir alguma latência extra, dependendo da fila.
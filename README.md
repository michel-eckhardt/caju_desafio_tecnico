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
-  Usa apenas a MCC para mapear a transação para uma categoria de benefícios
-  Aprova ou rejeita a transação
-  Caso a transação seja aprovada, o saldo da categoria mapeada deverá ser diminuído.
- 


## Tecnologias Utilizadas
- **Java 11**: Linguagem de programação principal.
- **Spring Boot 2.3.1.RELEASE**: Framework para desenvolvimento da aplicação.
- **Lombok**: Biblioteca para redução de boilerplate.
- **JUnit 5**: Framework de testes unitários.
- **Mockito**: Framework para criação de mocks nos testes.
- [Outras tecnologias ou bibliotecas usadas]

## Arquitetura
A aplicação foi desenvolvida seguindo o padrão [MVC/DDD/etc.]. Abaixo, uma breve descrição dos principais componentes:

- **Controller**: Responsável por [descrição].
- **Service**: Contém a lógica de negócio, como [descrição].
- **Repository**: Interface para interação com o banco de dados [se aplicável].
- **Strategy Pattern**: Implementado para [descrição de uso do padrão de estratégia].

## Instruções de Configuração
### Pré-requisitos
- [Software/versões necessárias]
- [Comandos para instalação]

### Configuração
1. Clone o repositório:
   ```bash
   git clone [URL do repositório]
   cd [nome-do-repositório]

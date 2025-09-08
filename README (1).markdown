# Microsserviço de Alertas de Ações B3

Um microsserviço para monitorar preços de ações da B3 e enviar alertas por e-mail utilizando **Spring Boot**, **AWS SES** e **JPA/Hibernate**. O sistema verifica automaticamente os preços das ações favoritas dos usuários e dispara alertas quando atingem os valores-alvo definidos.

## Funcionalidades

- Consulta preços de ações em tempo real via [Brapi.dev API](https://brapi.dev/).
- Registro de ações favoritas com preço-alvo de compra e venda.
- Rotina agendada (`@Scheduled`) para monitoramento periódico dos preços.
- Envio automático de e-mails usando **AWS SES**.
- Logs detalhados e tratamento de exceções.
- Configuração modular, separando gateway de e-mail, serviço de alertas e controller.

## Tecnologias

- **Java 22**
- **Spring Boot 3**
- **Spring Data JPA / Hibernate**
- **MySQL** (adaptável para PostgreSQL)
- **AWS SES** para envio de e-mails
- **Brapi.dev API** para cotação de ações
- **Maven** como gerenciador de dependências

## Como Baixar

1. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/microsservico-acoes-b3.git
cd microsservico-acoes-b3
```

2. Abra o projeto na sua IDE preferida (IntelliJ, VSCode, etc.) ou no terminal.

## Configuração

### Banco de Dados

1. Edite o arquivo `application.yml` com suas credenciais do MySQL:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/b3_alerts
    username: root
    password: sua_senha
  jpa:
    hibernate:
      ddl-auto: update
```

2. Crie o banco de dados no MySQL:

```sql
CREATE DATABASE b3_alerts;
```

### AWS SES

Configure as credenciais do AWS SES no ambiente:

```bash
export AWS_ACCESS_KEY_ID=SEU_ACCESS_KEY
export AWS_SECRET_ACCESS_KEY=SEU_SECRET_KEY
export AWS_REGION=us-east-1
```

**Nota**: No modo sandbox do AWS SES, todos os e-mails remetentes e destinatários devem estar verificados no console AWS SES.

## Como Rodar

Execute o comando abaixo para iniciar o microsserviço:

```bash
mvn spring-boot:run
```

A rotina automática de monitoramento será iniciada e verificará os preços das ações registradas. Se algum preço atingir o valor-alvo, um alerta será enviado por e-mail.
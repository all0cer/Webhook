# Webhook Fluig

Aplicação desenvolvida para receber as notificações de pagamentos para os processos do Fluig.

---

## 🔨 Build
Para criar a imagem docker, execute o seguinte comando na raíz do projeto:

- **docker build** -t gitlab.sebrae.com.br:5050/rn/webhook-fluig .

## 🚀 Execução
Para criar e iniciar o container da aplicação:
- **docker run** -d --name webhook-fluig -p 8080:8080 gitlab.sebrae.com.br:5050/rn/webhook-fluig
---

### POST
`/cielo/notificacao` <br/>
`/bb/notificacao` <br/>
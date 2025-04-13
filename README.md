# Projeto Spring Boot com Spring AI e Ollama

Este projeto é uma aplicação Spring Boot que utiliza o [Spring AI](https://docs.spring.io/spring-ai/) para interagir com modelos de linguagem LLM, utilizando o [Ollama](https://ollama.com/) como backend local para execução dos modelos.

## Instalação do Ollama

Para utilizar este projeto, é necessário ter o Ollama instalado localmente.

### 1. Instale o Ollama

Acesse o site oficial e siga as instruções para o seu sistema operacional:

🔗 https://ollama.com/download

### 2. Inicie o Ollama com o modelo desejado

O modelo padrão usado neste projeto é o `llama3`. Você pode iniciar o Ollama com:

```bash
ollama serve
```

> Dica: Para baixar o modelo antes de rodar:
>
> ```bash
> ollama pull llama3
> ```

### Variáveis de Ambiente

Antes de rodar a aplicação, configure as seguintes variáveis de ambiente no seu sistema ou em um arquivo `.env`:

```env
SPRING_AI_OLLAMA_CHAT_OPTIONS_MODEL=llama3:latest
SPRING_AI_OLLAMA_TEMPERATURE=0.3
SPRING_AI_OLLAMA_CONTEXT_SIZE=4096
SPRING_AI_OLLAMA_BASE_URL=http://localhost:6000
```

> Certifique-se de que o Ollama esteja escutando na porta `6000`. Você pode configurar isso ao iniciar o servidor Ollama, se necessário.

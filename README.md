# Projeto DDD-2026 - Monolito Spring Boot + React

[![CI](https://github.com/MarlonPasseri/TP1-PB-DDD-/actions/workflows/ci.yml/badge.svg)](https://github.com/MarlonPasseri/TP1-PB-DDD-/actions/workflows/ci.yml)
[![Java 17](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-18-61DAFB?logo=react&logoColor=111111)](https://react.dev/)
[![Vite](https://img.shields.io/badge/Vite-5-646CFF?logo=vite&logoColor=white)](https://vitejs.dev/)
[![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?logo=apachemaven&logoColor=white)](https://maven.apache.org/)

## Sobre o projeto

Este repositorio entrega a Etapa 1 do TP com uma aplicacao monolitica composta por:

- Backend Spring Boot com arquitetura em camadas (`controller`, `service`, `repository`).
- API REST para gerenciamento de tarefas.
- Frontend React para consumo da API e interacao do usuario.
- Base de DDD com separacao por contexto (`tarefas`) e estrutura pronta para evolucao.

O objetivo desta etapa e consolidar fundamentos de:

- autoconfiguracao do Spring Boot,
- gerenciamento de dependencias,
- padronizacao de APIs REST,
- codigo limpo e manutencao,
- modelagem de dominio pensando em migracao futura para microsservicos.

## Competencias atendidas

- Implementacao de aplicacao Spring Boot em camadas.
- Uso de auto-configuracao e starters oficiais.
- Gerenciamento de dependencias com Maven e NPM.
- API REST com Spring MVC e validacao de payload.
- Tratamento global de erros com contrato padrao.
- Modelagem de dominio com contexto delimitado (DDD enxuto).
- Frontend React integrado ao backend.

## Arquitetura e organizacao

### Backend (monolito)

Pacotes principais:

- `com.tp1.monolito.tarefas.domain`: entidades e contratos do dominio.
- `com.tp1.monolito.tarefas.application`: servicos, DTOs e mapeamentos.
- `com.tp1.monolito.tarefas.infrastructure`: persistencia JPA.
- `com.tp1.monolito.tarefas.interfaces`: camada REST.
- `com.tp1.monolito.shared`: excecoes e padrao de resposta de erro.

Decisoes tecnicas:

- `Task` como entidade principal.
- `TaskStatus` como enum de negocio (`TODO`, `IN_PROGRESS`, `DONE`).
- DTOs (`TaskRequest`, `TaskResponse`) para nao expor entidade JPA diretamente.
- `@ControllerAdvice` para padronizar erros HTTP.
- H2 em memoria para execucao simples nesta etapa.

### Frontend (React)

- Vite para build e dev server.
- Axios para chamadas HTTP.
- Componentes separados:
  - `TaskForm` para criacao/edicao.
  - `TaskList` para listagem e acoes.
- Proxy `/api` no Vite para integrar localmente com backend em `:8080`.

## Estrutura de pastas

```text
.
|-- backend
|   |-- pom.xml
|   `-- src
|       |-- main
|       |   |-- java/com/tp1/monolito
|       |   `-- resources/application.yml
|       `-- test
|           |-- java/com/tp1/monolito
|           `-- resources/application-test.yml
|-- frontend
|   |-- package.json
|   |-- vite.config.js
|   `-- src
|       |-- api/taskApi.js
|       |-- components/TaskForm.jsx
|       |-- components/TaskList.jsx
|       |-- App.jsx
|       `-- main.jsx
`-- README.md
```

## Tecnologias

- Backend: Java 17, Spring Boot 3.3.5, Spring Web, Spring Data JPA, Bean Validation, H2.
- Frontend: React 18, Vite 5, Axios.
- Testes: JUnit 5, Mockito, Spring Boot Test + MockMvc.

## Como executar

### Pre-requisitos

- Java 17+
- Maven 3.9+
- Node.js 20+

### 1) Backend

```bash
cd backend
mvn spring-boot:run
```

Disponivel em: `http://localhost:8080`

### 2) Frontend

```bash
cd frontend
npm install
npm run dev
```

Disponivel em: `http://localhost:5173`

## API REST

Base URL: `http://localhost:8080/api/tasks`

- `GET /api/tasks`: lista tarefas ordenadas por prazo e id.
- `GET /api/tasks/{id}`: busca tarefa por id.
- `POST /api/tasks`: cria uma nova tarefa.
- `PUT /api/tasks/{id}`: atualiza tarefa existente.
- `DELETE /api/tasks/{id}`: remove tarefa.

### Exemplo de criacao

```json
{
  "title": "Entregar Etapa 1",
  "description": "Aplicacao monolitica com Spring Boot e React",
  "status": "TODO",
  "dueDate": "2030-01-10"
}
```

### Exemplo de resposta

```json
{
  "id": 1,
  "title": "Entregar Etapa 1",
  "description": "Aplicacao monolitica com Spring Boot e React",
  "status": "TODO",
  "dueDate": "2030-01-10",
  "createdAt": "2030-01-02T10:15:30",
  "updatedAt": "2030-01-02T10:15:30"
}
```

### Validacoes

- `title`: obrigatorio, maximo 120 caracteres.
- `description`: opcional, maximo 500 caracteres.
- `status`: obrigatorio (`TODO`, `IN_PROGRESS`, `DONE`).
- `dueDate`: obrigatorio, data atual ou futura.

## Testes e qualidade

Backend:

- Testes unitarios de servico.
- Testes de integracao de controller com MockMvc.

Comando:

```bash
cd backend
mvn test
```

Frontend:

```bash
cd frontend
npm run build
```

## CI

O projeto inclui pipeline GitHub Actions em `.github/workflows/ci.yml` com:

- build e testes do backend (`mvn test`);
- build do frontend (`npm run build`);
- execucao automatica em `push` e `pull_request`.

## Evolucao para microsservicos (proximas etapas)

Este monolito ja foi estruturado para facilitar separacao futura por bounded contexts:

- extracao do contexto `tarefas` para servico independente;
- troca do H2 por PostgreSQL;
- adicao de gateway, descoberta de servicos e mensageria;
- autenticacao/autorizacao centralizada.

## Autores

- Turma TP1 PB-2026

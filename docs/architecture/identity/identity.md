# Domínio: Agora Identity

Este serviço atua como o Provedor de Identidade (IdP) e Single Sign-On (SSO) de todo o ecossistema.  
Ele é o único sistema que conhece as credenciais de acesso do cliente.

## Índice
1. [Estrutura de Domínio](#1-estrutura-de-domínio)
2. [Máquina de Estados](#2-máquina-de-estados)
3. [Fluxo Crítico: Criação](#3-fluxo-crítico-criação)

---

## 1. Estrutura de Domínio

O diagrama de classes reflete o modelo principal, blindando o estado interno via métodos que representam intenções reais de negócio.

```mermaid
classDiagram
    direction TB

    class AuditableEntity {
        <<Abstract>>
        -LocalDateTime createdAt
        -LocalDateTime updatedAt
        -String createdBy
        -String updatedBy
    }

    class Identity {
        <<Entity>>
        -UUID id
        -String cpf
        -String email
        -String password
        -IdentityStatus status
        +changePassword(String newPassword)
        +changeEmail(String newEmail)
        +activate()
        +deactivate()
        +suspend()
    }

    class IdentityStatus {
        <<Enumeration>>
        ACTIVE
        DEACTIVATED
        SUSPENDED
    }

    Identity --|> AuditableEntity : inherits
    Identity --> IdentityStatus : possesses
```

## 2. Máquina de Estados

Define as transições permitidas para a entidade principal, garantindo que operações ilegais sejam barradas logo na camada de domínio.

```mermaid
stateDiagram-v2
    direction LR
    
    [*] --> ACTIVE : Criação inicial
    ACTIVE --> SUSPENDED : suspend()
    SUSPENDED --> ACTIVE : activate()
    ACTIVE --> DEACTIVATED : deactivate()
    
    note right of DEACTIVATED
        Geralmente um estado terminal (Soft Delete).
        A reativação exige intervenção via suporte administrativo.
    end note
```

## 3. Fluxo Crítico: Criação

O diagrama abaixo mapeia o caminho ideal da criação de uma nova identidade, comprovando o isolamento das camadas.

```mermaid
sequenceDiagram
    autonumber
    actor C as Cliente
    participant API as IdentityController
    participant SVC as IdentityService
    participant MAP as IdentityMapper
    participant REP as IdentityRepository (Porta)
    participant DB as Banco de Dados

    C->>API: POST /identity (IdentityCreateRequestDto)
    API->>SVC: create(requestDto)

    SVC->>MAP: toEntity(requestDto)
    MAP-->>SVC: Identity (Domain Entity)

%% Validações de Negócio
    SVC->>REP: existsByEmail(email)
    REP-->>SVC: false
    SVC->>REP: existsByCpf(cpf)
    REP-->>SVC: false

%% Persistência
    SVC->>REP: save(Identity)
    REP->>DB: INSERT INTO identities
    DB-->>REP: success
    REP-->>SVC: Identity (com UUID gerado)

%% Retorno Seguro
    SVC->>MAP: toResponseDto(Identity)
    MAP-->>SVC: IdentityResponseDto

    SVC-->>API: IdentityResponseDto
    API-->>C: 201 Created + JSON
```
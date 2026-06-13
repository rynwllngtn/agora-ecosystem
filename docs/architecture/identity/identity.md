# Domínio: Agora Identity

Este serviço atua como o Provedor de Identidade (IdP) e Single Sign-On (SSO) de todo o ecossistema.  
Ele é o único sistema que conhece as credenciais de acesso do cliente.

## Diagrama de Classes

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
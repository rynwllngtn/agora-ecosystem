# Agora Common

Biblioteca base de infraestrutura e domínios partilhados para o ecossistema Agora.  
Este módulo não é um serviço executável, mas sim uma dependência projetada para fornecer ferramentas universais.

## Como Integrar nos Microsserviços

### 1. Adicionar a Dependência
Adicione o `agora-common` no `pom.xml` do serviço alvo:

```xml
<dependency>
    <groupId>dev.rynwllngtn</groupId>
    <artifactId>agora-common</artifactId>
</dependency>
```

### 2. Configuração Obrigatória
Por padrão, o Spring Boot apenas procura componentes a partir do caminho de onde a classe `Main` está a ser executado.  
Para que algumas coisas funcione, **é necessário** instruir o Spring a rastrear o pacote base desta biblioteca, adicionando o parâmetro `scanBasePackages` na anotação `@SpringBootApplication`:

```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "dev.rynwllngtn.common", 
    "com.example.demo"
})
public class Application {
    static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
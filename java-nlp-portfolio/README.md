# Java NLP Portfolio

Projeto **didático e básico de Processamento de Linguagem Natural (NLP)** em **Java + Maven**, pensado para uso em **portfólio profissional**.

O objetivo é mostrar de forma clara:

- Estrutura de um projeto Maven bem organizada
- Uso de uma biblioteca de NLP (Apache OpenNLP)
- Pipeline simples: pré-processamento, tokenização, detecção de sentenças, POS tagging, NER

---

## 1. Requisitos de instalação

- **Java JDK 17+**
  - Verifique a versão instalada:
    ```bash
    java -version
    ```
  - Se não tiver instalado, você pode usar (exemplos):
    - Ubuntu/Debian:
      ```bash
      sudo apt update
      sudo apt install openjdk-17-jdk
      ```
    - Fedora:
      ```bash
      sudo dnf install java-17-openjdk-devel
      ```

- **Maven 3.8+**
  - Verifique se o Maven está instalado:
    ```bash
    mvn -version
    ```
  - Em distribuições baseadas em Debian/Ubuntu:
    ```bash
    sudo apt update
    sudo apt install maven
    ```

- **IDE recomendada (opcional, mas ótimo para portfólio)**
  - IntelliJ IDEA Community
  - Eclipse
  - VS Code com extensão de Java

---

## 2. Estrutura do projeto

Principais pastas:

- `src/main/java/com/portfolio/nlp`
  - `App.java` — aplicação principal (CLI com menu) demonstrando o pipeline
  - `TextPreprocessor.java` — pré-processamento (normalização, remoção de pontuação, stopwords)
  - `OpenNlpService.java` — encapsula chamadas ao Apache OpenNLP (sentenças, tokens, POS, NER)
- `src/main/resources/models`
  - `README_MODELS.txt` — explica quais modelos binários do OpenNLP baixar e onde colocar
- `src/test/java/com/portfolio/nlp`
  - `TextPreprocessorTest.java` — teste simples com JUnit 5

Arquivo de build:

- `pom.xml` — configuração Maven com:
  - Dependência **Apache OpenNLP (`opennlp-tools`)**
  - Dependência de testes **JUnit 5**
  - Plugins para compilação e execução (`maven-compiler-plugin`, `maven-jar-plugin`, `exec-maven-plugin`)

---

## 3. Dependências de NLP (Apache OpenNLP)

As dependências já estão declaradas no `pom.xml`:

- **Apache OpenNLP (biblioteca Java)**
  - GroupId: `org.apache.opennlp`
  - ArtifactId: `opennlp-tools`

Além da biblioteca, para usar **POS tagging** e **NER** você precisa dos **modelos pré-treinados**.

### 3.1. Baixar modelos pré-treinados

1. Acesse o site do Apache OpenNLP (procure por “OpenNLP models download”).
2. Baixe, por exemplo, os modelos em inglês:
   - `en-sent.bin` — detecção de sentenças
   - `en-pos-maxent.bin` — POS tagging
   - `en-ner-person.bin` — reconhecimento de entidades do tipo PESSOA
3. Copie os arquivos para:
   - `src/main/resources/models/en-sent.bin`
   - `src/main/resources/models/en-pos-maxent.bin`
   - `src/main/resources/models/en-ner-person.bin`

O código do projeto já espera esses caminhos. Se os arquivos não existirem, você verá uma mensagem de erro amigável explicando como resolver.

---

## 4. Como compilar e rodar

Na raiz do projeto (`java-nlp-portfolio`):

### 4.1. Compilar

```bash
mvn clean compile
```

### 4.2. Rodar testes

```bash
mvn test
```

### 4.3. Executar a aplicação (modo mais simples durante o desenvolvimento)

```bash
mvn exec:java
```

### 4.4. Gerar JAR executável

```bash
mvn package
```

O JAR será gerado em:

- `target/java-nlp-portfolio-1.0.0-SNAPSHOT.jar`

Para rodar o JAR:

```bash
java -jar target/java-nlp-portfolio-1.0.0-SNAPSHOT.jar
```

---

## 5. O que o projeto demonstra (para portfólio)

- **Pré-processamento (`TextPreprocessor`)**
  - Conversão para minúsculas
  - Remoção de acentos
  - Remoção de pontuação
  - Remoção de stopwords básicas em português e inglês

- **NLP com Apache OpenNLP (`OpenNlpService`)**
  - **Detecção de sentenças**: quebra um texto em frases
  - **Tokenização**: divide texto em tokens/palavras
  - **POS Tagging**: atribui classes gramaticais aos tokens (quando modelos estão presentes)
  - **NER (PESSOAS)**: identifica nomes de pessoas no texto (quando modelos estão presentes)

- **Aplicação de linha de comando (`App`)**
  - Menu simples que permite:
    - Ver o texto de exemplo
    - Rodar apenas pré-processamento
    - Rodar detecção de sentenças
    - Rodar tokenização
    - Rodar POS tagging (se modelos instalados)
    - Rodar NER para pessoas (se modelos instalados)

- **Testes automatizados (JUnit 5)**
  - `TextPreprocessorTest` mostra validações básicas do pipeline de pré-processamento.

---

## 6. Sugestões de como usar no seu portfólio

- **GitHub/GitLab**
  - Suba este projeto em um repositório público.
  - Escreva uma descrição em inglês e em português explicando que é um projeto educacional de NLP com Java.

- **LinkedIn**
  - Faça um post curto comentando:
    - Por que escolheu Java + OpenNLP
    - O que o projeto demonstra (pipeline básico de NLP)
    - Link para o repositório

- **Possíveis extensões**
  - Adicionar suporte a outros idiomas (PT-BR com modelos específicos)
  - Criar uma API REST (Spring Boot) expondo endpoints de NLP
  - Integrar com um pequeno front-end para demonstração em navegador

---

## 7. Resumo rápido para rodar do zero

1. Instale **JDK 17+** e **Maven**.
2. Clone ou copie este projeto para sua máquina.
3. (Opcional, mas recomendado) Baixe os modelos do OpenNLP e coloque em `src/main/resources/models`.
4. Rode:
   ```bash
   mvn clean package
   mvn exec:java
   ```
5. Use o menu da aplicação para testar as funções de NLP.


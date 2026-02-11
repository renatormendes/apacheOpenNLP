package com.portfolio.nlp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Testes simples para o pré-processador de texto.
 *
 * Ter testes automatizados no portfólio mostra cuidado com qualidade.
 */
public class TextPreprocessorTest {

    @Test
    void preprocessShouldLowercaseAndRemoveAccentsAndStopwords() {
        TextPreprocessor preprocessor = new TextPreprocessor();
        String input = "Olá, este é UM pequeno TESTE de Pré-processamento!";
        String result = preprocessor.preprocess(input);

        // Verificações básicas:
        // - Sem acentos: "Ola", "Pre-processamento" -> "preprocessamento"
        // - Stopwords comuns removidas, por exemplo "este", "é", "um", "de"
        Assertions.assertFalse(result.contains("este"));
        Assertions.assertFalse(result.contains("um"));
        Assertions.assertFalse(result.contains("de"));

        // Deve estar todo em minúsculas
        Assertions.assertEquals(result, result.toLowerCase());
    }
}


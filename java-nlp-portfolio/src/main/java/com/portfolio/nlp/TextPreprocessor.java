package com.portfolio.nlp;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Classe responsável por operações básicas de pré-processamento de texto.
 *
 * Este é um exemplo didático para portfólio, cobrindo:
 * - normalização (remoção de acentos)
 * - conversão para minúsculas
 * - remoção de pontuação simples
 * - remoção de stopwords em português e inglês
 */
public class TextPreprocessor {

    private final Set<String> stopwords;

    public TextPreprocessor() {
        this.stopwords = buildDefaultStopwords();
    }

    public TextPreprocessor(Set<String> customStopwords) {
        this.stopwords = customStopwords != null ? customStopwords : buildDefaultStopwords();
    }

    /**
     * Pipeline completo de pré-processamento.
     *
     * @param text texto de entrada bruto
     * @return texto limpo e normalizado
     */
    public String preprocess(String text) {
        if (text == null) {
            return "";
        }

        String normalized = normalize(text);
        String noPunctuation = removePunctuation(normalized);
        return removeStopwords(noPunctuation);
    }

    /**
     * Remove acentos e converte para minúsculas.
     */
    public String normalize(String text) {
        String lower = text.toLowerCase(Locale.ROOT);
        String normalized = Normalizer.normalize(lower, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{M}", "");
    }

    /**
     * Remove pontuação básica mantendo espaços.
     */
    public String removePunctuation(String text) {
        return text.replaceAll("[\\p{Punct}]", " ")
                   .replaceAll("\\s+", " ")
                   .trim();
    }

    /**
     * Remove stopwords simples em PT/EN.
     */
    public String removeStopwords(String text) {
        return Arrays.stream(text.split("\\s+"))
                     .filter(token -> !stopwords.contains(token))
                     .collect(Collectors.joining(" "));
    }

    private Set<String> buildDefaultStopwords() {
        // Lista mínima de stopwords para demonstração (pode ser expandida depois)
        String[] pt = {
                "a", "o", "as", "os", "de", "da", "do", "das", "dos",
                "e", "ou", "em", "para", "por", "com", "sem",
                "um", "uma", "uns", "umas",
                "que", "se", "na", "no", "nas", "nos"
        };

        String[] en = {
                "a", "an", "the", "and", "or", "in", "on", "at", "for",
                "of", "to", "with", "without", "is", "are", "was", "were"
        };

        Set<String> set = new HashSet<>();
        set.addAll(Arrays.asList(pt));
        set.addAll(Arrays.asList(en));
        return set;
    }
}


package com.portfolio.nlp;

import java.util.List;
import java.util.Scanner;

/**
 * Classe principal com um menu simples em linha de comando
 * para demonstrar o pipeline de NLP.
 *
 * Este tipo de aplicação é excelente para portfólio pois mostra:
 * - Estrutura de projeto Maven
 * - Uso de uma biblioteca de NLP (Apache OpenNLP)
 * - Boas práticas básicas de organização de código
 */
public class App {

    private static final String SAMPLE_TEXT =
            "Olá, meu nome é Renato. Trabalho com ciência de dados em São Paulo. " +
            "Gosto de aprender NLP em Java usando Apache OpenNLP.";

    public static void main(String[] args) {
        TextPreprocessor preprocessor = new TextPreprocessor();
        OpenNlpService nlpService = new OpenNlpService();

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Java NLP Portfolio ===");
        System.out.println("Texto de exemplo usado nas demonstrações:");
        System.out.println("\"" + SAMPLE_TEXT + "\"");
        System.out.println();

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Escolha uma opção: ");
            String option = scanner.nextLine().trim();

            try {
                switch (option) {
                    case "1" -> demoPreprocess(preprocessor);
                    case "2" -> demoSentenceDetection(nlpService);
                    case "3" -> demoTokenization(nlpService);
                    case "4" -> demoPosTagging(nlpService);
                    case "5" -> demoNamedEntityRecognition(nlpService);
                    case "0" -> {
                        running = false;
                        System.out.println("Saindo. Obrigado por testar o projeto!");
                    }
                    default -> System.out.println("Opção inválida.\n");
                }
            } catch (Exception e) {
                System.err.println("Erro durante a execução da opção: " + e.getMessage());
                e.printStackTrace(System.err);
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("----- MENU -----");
        System.out.println("1 - Pré-processar texto (normalizar + remover pontuação + stopwords)");
        System.out.println("2 - Detectar sentenças (OpenNLP)");
        System.out.println("3 - Tokenizar texto (OpenNLP)");
        System.out.println("4 - POS Tagging (OpenNLP) *requer modelos*");
        System.out.println("5 - NER - Pessoas (OpenNLP) *modelo não disponível para PT*");
        System.out.println("0 - Sair");
        System.out.println();
    }

    private static void demoPreprocess(TextPreprocessor preprocessor) {
        System.out.println("\n[DEMO] Pré-processamento de texto");
        String processed = preprocessor.preprocess(SAMPLE_TEXT);
        System.out.println("Texto original : " + SAMPLE_TEXT);
        System.out.println("Texto processado: " + processed);
    }

    private static void demoSentenceDetection(OpenNlpService nlpService) {
        System.out.println("\n[DEMO] Detecção de sentenças (Sentence Detection)");
        nlpService.loadModels(); // garante que o modelo de sentenças esteja carregado
        String[] sentences = nlpService.detectSentences(SAMPLE_TEXT);
        for (int i = 0; i < sentences.length; i++) {
            System.out.println("Sentença " + (i + 1) + ": " + sentences[i]);
        }
    }

    private static void demoTokenization(OpenNlpService nlpService) {
        System.out.println("\n[DEMO] Tokenização de texto");
        String[] tokens = nlpService.tokenize(SAMPLE_TEXT);
        System.out.println("Tokens:");
        for (String token : tokens) {
            System.out.print("[" + token + "] ");
        }
        System.out.println();
    }

    private static void demoPosTagging(OpenNlpService nlpService) {
        System.out.println("\n[DEMO] POS Tagging");
        nlpService.loadModels();
        String[] tokens = nlpService.tokenize(SAMPLE_TEXT);
        List<String> tagged = nlpService.posTag(tokens);
        System.out.println("Tokens com tags (token/TAG):");
        tagged.forEach(System.out::println);
    }

    private static void demoNamedEntityRecognition(OpenNlpService nlpService) {
        System.out.println("\n[DEMO] Reconhecimento de Entidades Nomeadas (PESSOAS)");
        nlpService.loadModels();
        String[] tokens = nlpService.tokenize(SAMPLE_TEXT);
        List<String> names = nlpService.findPersonNames(tokens);

        if (names.isEmpty()) {
            System.out.println("Nenhuma pessoa encontrada no texto de exemplo.");
        } else {
            System.out.println("Pessoas encontradas:");
            names.forEach(name -> System.out.println("- " + name));
        }
    }
}


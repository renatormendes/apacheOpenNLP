package com.portfolio.nlp;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.Span;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Serviço de alto nível encapsulando o uso do Apache OpenNLP.
 *
 * Para rodar POS tagging e NER, é necessário baixar os modelos pré-treinados
 * e colocá-los em src/main/resources/models.
 *
 * Exemplo de modelos (inglês) que você pode usar:
 * - en-sent.bin        (sentenças)
 * - en-token.bin       (tokenização, se quiser usar TokenizerModel)
 * - en-pos-maxent.bin  (POS tagging)
 * - en-ner-person.bin  (Reconhecimento de pessoas)
 * -
 * Modelos em português disponíveis:
 * - opennlp-pt-ud-gsd-sentence-1.3-2.5.4.bin (sentenças)
 * - opennlp-pt-ud-gsd-pos-1.3-2.5.4.bin (POS tagging)
 * 
 * NOTA: Modelos de NER para português não estão disponíveis como downloads pré-treinados
 * do Apache OpenNLP. O modelo de NER é opcional e não impedirá o uso das outras funcionalidades.
 *  
 */
public class OpenNlpService {

    private SentenceDetectorME sentenceDetector;
    private POSTaggerME posTagger;
    private NameFinderME nameFinderPerson;

    /**
     * Carrega modelos apenas quando necessários. 
     * O modelo de NER é opcional (não disponível para português como download pré-treinado).
     * Caso os modelos obrigatórios não sejam encontrados, um erro amigável é lançado.
     */
    public void loadModels() {
        this.sentenceDetector = new SentenceDetectorME(loadSentenceModel("models/opennlp-pt-ud-gsd-sentence-1.3-2.5.4.bin"));
        this.posTagger = new POSTaggerME(loadPosModel("models/opennlp-pt-ud-gsd-pos-1.3-2.5.4.bin"));
        // NER é opcional - modelo não disponível para português como download pré-treinado
        TokenNameFinderModel nerModel = tryLoadNameFinderModel("models/opennlp-pt-ud-gsd-ner-1.3-2.5.4.bin");
        if (nerModel != null) {
            this.nameFinderPerson = new NameFinderME(nerModel);
        }
    }

    public String[] detectSentences(String text) {
        ensureSentenceDetector();
        return sentenceDetector.sentDetect(text);
    }

    public String[] tokenize(String text) {
        // para simplificar o exemplo, usamos o SimpleTokenizer que não requer modelo.
        return SimpleTokenizer.INSTANCE.tokenize(text);
    }

    public List<String> posTag(String[] tokens) {
        ensurePosTagger();
        String[] tags = posTagger.tag(tokens);
        List<String> tagged = new ArrayList<>();
        for (int i = 0; i < tokens.length; i++) {
            tagged.add(tokens[i] + "/" + tags[i]);
        }
        return tagged;
    }

    public List<String> findPersonNames(String[] tokens) {
        if (nameFinderPerson == null) {
            throw new IllegalStateException(
                "Modelo de NER (Reconhecimento de Entidades Nomeadas) não disponível.\n" +
                "Modelos de NER para português não estão disponíveis como downloads pré-treinados do Apache OpenNLP.\n" +
                "Para usar NER em português, você precisaria treinar seu próprio modelo ou usar modelos alternativos.\n" +
                "As outras funcionalidades (sentenças, tokenização, POS tagging) continuam funcionando normalmente."
            );
        }
        Span[] spans = nameFinderPerson.find(tokens);
        List<String> names = new ArrayList<>();
        for (Span span : spans) {
            String[] entityTokens = Arrays.copyOfRange(tokens, span.getStart(), span.getEnd());
            names.add(String.join(" ", entityTokens));
        }
        return names;
    }

    private SentenceModel loadSentenceModel(String path) {
        try (InputStream is = getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalStateException(buildMissingModelMessage(path));
            }
            return new SentenceModel(is);
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao carregar modelo de sentenças: " + path, e);
        }
    }

    private POSModel loadPosModel(String path) {
        try (InputStream is = getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalStateException(buildMissingModelMessage(path));
            }
            return new POSModel(is);
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao carregar modelo de POS: " + path, e);
        }
    }

    /**
     * Tenta carregar o modelo de NER, mas retorna null se não encontrado (não lança exceção).
     * Isso permite que o NER seja opcional, já que modelos pré-treinados de NER para português
     * não estão disponíveis como downloads do Apache OpenNLP.
     */
    private TokenNameFinderModel tryLoadNameFinderModel(String path) {
        try (InputStream is = getResourceAsStream(path)) {
            if (is == null) {
                return null; // Modelo não encontrado, mas não é obrigatório
            }
            return new TokenNameFinderModel(is);
        } catch (IOException e) {
            // Se houver erro ao carregar, retorna null (modelo opcional)
            return null;
        }
    }

    private InputStream getResourceAsStream(String path) {
        return Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(path);
    }

    private String buildMissingModelMessage(String path) {
        return "Modelo '" + path + "' não encontrado no classpath.\n" +
                "Baixe o modelo correspondente do Apache OpenNLP e coloque-o em src/main/resources/" + path + ".\n" +
                "Veja o README para instruções detalhadas.";
    }

    private void ensureSentenceDetector() {
        if (sentenceDetector == null) {
            throw new IllegalStateException("SentenceDetector não carregado. Chame loadModels() antes de usar detectSentences().");
        }
    }

    private void ensurePosTagger() {
        if (posTagger == null) {
            throw new IllegalStateException("POSTagger não carregado. Chame loadModels() antes de usar posTag().");
        }
    }

}


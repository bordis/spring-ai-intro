package br.com.bordi.spring_ai_intro.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bordi.spring_ai_intro.core.model.AnswerRecord;
import br.com.bordi.spring_ai_intro.core.model.GetCapitalInfo;
import br.com.bordi.spring_ai_intro.core.model.GetCapitalRequest;
import br.com.bordi.spring_ai_intro.core.model.GetCapitalResponse;
import br.com.bordi.spring_ai_intro.core.model.GetQuestionsResponse;

@Service
public class OllamaService {

    private final ChatModel chatModel;

    public OllamaService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCapitalPrompt;

    @Value("classpath:templates/get-capital-with-info-prompt.st")
    private Resource getCapitalWithInfoPrompt;

    @Value("classpath:templates/get-capital-json-prompt.st")
    private Resource getCapitalJsonPrompt;

    @Value("classpath:templates/get-capital-converter-prompt.st")
    private Resource getCapitalConverterPrompt;

    @Value("classpath:templates/get-questions-converter-prompt.st")
    private Resource getQuestionsConverterPrompt;

    @Value("classpath:templates/get-capital-with-info-converter-prompt.st")
    private Resource getCapitalInfoConverterPrompt;

    @Autowired
    ObjectMapper objectMapper;

    public AnswerRecord getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatModel.call(prompt);
        return new AnswerRecord(response.getResults().get(0).getOutput().getText());
    }

    public AnswerRecord getCapital(String stateOrCountry) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", stateOrCountry));
        System.out.println(prompt);
        ChatResponse response = chatModel.call(prompt);
        return new AnswerRecord(response.getResults().get(0).getOutput().getText());
    }

    public AnswerRecord getCapitalWithInfo(String stateOrCountry) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalWithInfoPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", stateOrCountry));
        System.out.println(prompt);
        ChatResponse response = chatModel.call(prompt);
        return new AnswerRecord(response.getResults().get(0).getOutput().getText());
    }

    // para testar o retorno no formato json, a função receber e retonar uma string
    public String getAnswerString(String stateOrCountry) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalJsonPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", stateOrCountry));
        ChatResponse response = chatModel.call(prompt);
        String capital = "";
        try {
            JsonNode jsonNode = objectMapper.readTree(response.getResults().get(0).getOutput().getText());
            capital = jsonNode.get("name").asText();

        } catch (JsonProcessingException e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
        }
        return capital;
    }

    // passing a parser output
    public GetCapitalResponse getCapitalResponseConverter(GetCapitalRequest getCapitalRequest) {
        BeanOutputConverter<GetCapitalResponse> parser = new BeanOutputConverter<>(GetCapitalResponse.class);

        String format = parser.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalConverterPrompt);

        Prompt prompt = promptTemplate
                .create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry(), "format", format));

        System.out.println(prompt);

        ChatResponse response = chatModel.call(prompt);

        System.out.println(response.getResults().get(0).getOutput().getText());

        return parser.convert(response.getResults().get(0).getOutput().getText());
    }

    // passing a parser output
    public List<GetQuestionsResponse> getQuestionResponseConverter() {

        // monta um convertedor de saida para o formato desejado
        BeanOutputConverter<GetQuestionsResponse[]> parser = new BeanOutputConverter<>(GetQuestionsResponse[].class);

        String format = parser.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(getQuestionsConverterPrompt);

        // cria o prompt com o formato desejado
        Prompt prompt = promptTemplate.create(Map.of("format", format));

        ChatResponse response = chatModel.call(prompt);

        String retorno = response.getResults().get(0).getOutput().getText();

        // converte o retorno para o formato desejado
        GetQuestionsResponse[] questions = parser.convert(retorno);

        return List.of(questions);
    }


    public GetCapitalInfo getCapitalWithInfoConverter(String stateOrCountry) {
        BeanOutputConverter<GetCapitalInfo> parser = new BeanOutputConverter<>(GetCapitalInfo.class);
        String format = parser.getFormat();
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalInfoConverterPrompt);

        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", stateOrCountry, "format", format));
        System.out.println(prompt);
        ChatResponse response = chatModel.call(prompt);

        GetCapitalInfo capitalInfo = parser.convert(response.getResults().get(0).getOutput().getText());

        return capitalInfo;
    }

}

package br.com.bordi.spring_ai_intro.core.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import br.com.bordi.spring_ai_intro.core.model.AnswerRecord;

@Service
public class OllamaService {

    private final ChatModel chatModel;

    public OllamaService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public AnswerRecord getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();
        ChatResponse response = chatModel.call(prompt);
        return new AnswerRecord(response.getResults().get(0).getOutput().getText());
    }

}

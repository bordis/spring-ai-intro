package br.com.bordi.spring_ai_intro.core.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetQuestionsResponse(@JsonPropertyDescription("Esse é a pergunta")String question) {
    

}

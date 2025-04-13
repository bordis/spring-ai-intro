package br.com.bordi.spring_ai_intro.core.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetCapitalResponse(@JsonPropertyDescription("Esse é o nome da cidade") String answer) {

}

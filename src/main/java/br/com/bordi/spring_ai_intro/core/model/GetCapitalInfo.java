package br.com.bordi.spring_ai_intro.core.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetCapitalInfo(
    @JsonPropertyDescription("This is the name of the capital")String capital,
    @JsonPropertyDescription("This is the total population of the city")String population,
    @JsonPropertyDescription("This is the name of the region")String region,
    @JsonPropertyDescription("This is the language of the country where the capital is locate")String language,
    @JsonPropertyDescription("This is the currency whhere the capital is locate")String currency
) {

}

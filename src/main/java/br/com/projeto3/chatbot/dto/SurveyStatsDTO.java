package br.com.projeto3.chatbot.dto;

public record SurveyStatsDTO(
    Long id, 
    String title, 
    Long responseCount
) {}
package br.com.projeto3.chatbot.dto;

public record QuestionStatsDTO(
    Long id, 
    String text, 
    Long responseCount
) {}
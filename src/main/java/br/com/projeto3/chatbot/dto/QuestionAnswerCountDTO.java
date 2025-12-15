package br.com.projeto3.chatbot.dto;

public record QuestionAnswerCountDTO(
    Long questionId, 
    String questionText, 
    String responseText, 
    Long count
) {}
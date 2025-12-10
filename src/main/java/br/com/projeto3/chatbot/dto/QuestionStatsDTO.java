package br.com.projeto3.chatbot.dto;

public class QuestionStatsDTO {

    private Long questionId;
    private String questionText;
    private Long totalAnswers;

    public QuestionStatsDTO(Long questionId, String questionText, Long totalAnswers) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.totalAnswers = totalAnswers;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public Long getTotalAnswers() {
        return totalAnswers;
    }
}

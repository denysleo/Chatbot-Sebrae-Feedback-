package br.com.projeto3.chatbot.dto;

public class QuestionAnswerCountDTO {

    private Long questionId;
    private String questionText;
    private String answerText;
    private Long totalAnswers;

    public QuestionAnswerCountDTO(Long questionId, String questionText, String answerText, Long totalAnswers) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.answerText = answerText;
        this.totalAnswers = totalAnswers;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public Long getTotalAnswers() {
        return totalAnswers;
    }
}

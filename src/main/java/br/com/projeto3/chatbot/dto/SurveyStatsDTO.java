package br.com.projeto3.chatbot.dto;

public class SurveyStatsDTO {

    private Long surveyId;
    private String surveyTitle;
    private Long totalAnswers;

    public SurveyStatsDTO(Long surveyId, String surveyTitle, Long totalAnswers) {
        this.surveyId = surveyId;
        this.surveyTitle = surveyTitle;
        this.totalAnswers = totalAnswers;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public Long getTotalAnswers() {
        return totalAnswers;
    }
}

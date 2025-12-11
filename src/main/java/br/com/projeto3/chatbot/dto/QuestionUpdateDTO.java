package br.com.projeto3.chatbot.dto;

public class QuestionUpdateDTO {
    private String text;
    private Integer points;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
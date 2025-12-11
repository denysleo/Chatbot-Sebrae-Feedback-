package br.com.projeto3.chatbot.dto;

import java.time.LocalDateTime;

public class FeedbackDTO {
    private String userName;
    private Integer ranking;
    private Integer score;
    private String comment;
    private String type;
    private LocalDateTime date;

    public FeedbackDTO() {
    }

    public FeedbackDTO(String userName, Integer ranking, Integer score, String comment, String type, LocalDateTime date) {
        this.userName = userName;
        this.ranking = ranking;
        this.score = score;
        this.comment = comment;
        this.type = type;
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
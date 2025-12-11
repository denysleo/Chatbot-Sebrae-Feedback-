package br.com.projeto3.chatbot.dto;

public class RankingDTO {
    private String userName;
    private Long points;
    private Integer position;
    private Integer level;

    public RankingDTO(String userName, Long points, Integer position, Integer level) {
        this.userName = userName;
        this.points = points;
        this.position = position;
        this.level = level;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
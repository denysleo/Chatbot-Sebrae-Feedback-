package br.com.projeto3.chatbot.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String responseText;
    private LocalDateTime respondedAt = LocalDateTime.now();

    @ManyToOne
    private Usuario user;

    @ManyToOne
    private Question question;
}
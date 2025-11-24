package br.com.projeto3.chatbot.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text; 
    
    @ManyToOne
    private Survey survey;
}
package br.com.projeto3.chatbot.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; 
    private String description;
    private String icon; 
}
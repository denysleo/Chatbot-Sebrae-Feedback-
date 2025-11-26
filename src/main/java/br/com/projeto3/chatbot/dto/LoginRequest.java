package br.com.projeto3.chatbot.dto;
import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String senha;
}
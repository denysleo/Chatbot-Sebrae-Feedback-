package br.com.projeto3.chatbot.dto;

import lombok.Data;

@Data
public class WhatsAppMessageRequest {

    private String to;

   
    private String text;
}

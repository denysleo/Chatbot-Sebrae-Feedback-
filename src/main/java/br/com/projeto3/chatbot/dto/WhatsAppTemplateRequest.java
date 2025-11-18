package br.com.projeto3.chatbot.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class WhatsAppTemplateRequest {
    private String to;
    private String templateName;
 
    private String languageCode;
    
    private List<Map<String, Object>> components;
}

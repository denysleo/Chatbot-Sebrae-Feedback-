package br.com.projeto3.chatbot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
public class WhatsAppService {

    private static final Logger logger = LoggerFactory.getLogger(WhatsAppService.class);

    private final RestTemplate restTemplate;

    @Value("${whatsapp.api.url}")
    private String apiUrl;

    @Value("${whatsapp.phone-number-id}")
    private String phoneNumberId;

    @Value("${whatsapp.access-token}")
    private String accessToken;

    @Value("${whatsapp.mock:false}")
    private boolean mock;

    public WhatsAppService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> sendTextMessage(String to, String text) {

        if (mock) {
            logger.info("WhatsApp mock mode enabled - simulating send to {}", to);
            String simulated = String.format("{\"messaging_product\":\"whatsapp\",\"contacts\":[{\"input\":\"%s\"}],\"messages\":[{\"id\":\"%s\"}]}", to, java.util.UUID.randomUUID().toString());
            return ResponseEntity.ok(simulated);
        }

        if (phoneNumberId == null || phoneNumberId.isBlank()) {
            logger.error("whatsapp.phone-number-id is not configured");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("whatsapp.phone-number-id is not configured");
        }
        if (accessToken == null || accessToken.isBlank()) {
            logger.error("whatsapp.access-token is not configured");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("whatsapp.access-token is not configured");
        }

        String url = String.format("%s/v17.0/%s/messages", apiUrl, phoneNumberId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        Map<String, Object> body = new HashMap<>();
        body.put("messaging_product", "whatsapp");
        body.put("to", to);
        body.put("type", "text");

        Map<String, String> textObj = new HashMap<>();
        textObj.put("body", text);
        body.put("text", textObj);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            logger.debug("Calling WhatsApp API: url={} to={} body={}", url, to, body);
            ResponseEntity<String> resp = restTemplate.postForEntity(url, request, String.class);
            logger.debug("WhatsApp API response: status={} body={}", resp.getStatusCode(), resp.getBody());
            return resp;
        } catch (org.springframework.web.client.HttpStatusCodeException e) {

            logger.error("WhatsApp API returned error: status={} body={}", e.getStatusCode(), e.getResponseBodyAsString());
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (org.springframework.web.client.ResourceAccessException e) {

            logger.error("Resource access error when calling WhatsApp API: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Resource access error: " + e.getMessage());
        } catch (org.springframework.web.client.RestClientException e) {
            logger.error("RestClientException when calling WhatsApp API: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("RestClientException: " + e.getMessage());
        }
    }

    public ResponseEntity<String> sendTemplateMessage(String to, String templateName, String languageCode, List<Map<String, Object>> components) {
        if (mock) {
            logger.info("WhatsApp mock mode enabled - simulating template send to {} template={}", to, templateName);
            String simulated = String.format("{\"messages\":[{\"id\":\"%s\",\"template\":{\"name\":\"%s\"}}]}", java.util.UUID.randomUUID().toString(), templateName);
            return ResponseEntity.ok(simulated);
        }

        if (phoneNumberId == null || phoneNumberId.isBlank()) {
            logger.error("whatsapp.phone-number-id is not configured");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("whatsapp.phone-number-id is not configured");
        }
        if (accessToken == null || accessToken.isBlank()) {
            logger.error("whatsapp.access-token is not configured");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("whatsapp.access-token is not configured");
        }

        String url = String.format("%s/v17.0/%s/messages", apiUrl, phoneNumberId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        Map<String, Object> body = new java.util.HashMap<>();
        body.put("messaging_product", "whatsapp");
        body.put("to", to);
        body.put("type", "template");

        Map<String, Object> template = new java.util.HashMap<>();
        template.put("name", templateName);
        Map<String, String> lang = new java.util.HashMap<>();
        lang.put("code", languageCode != null ? languageCode : "pt_BR");
        template.put("language", lang);
        if (components != null && !components.isEmpty()) {
            template.put("components", components);
        }

        body.put("template", template);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            logger.debug("Calling WhatsApp API (template): url={} to={} template={}", url, to, templateName);
            ResponseEntity<String> resp = restTemplate.postForEntity(url, request, String.class);
            logger.debug("WhatsApp API response (template): status={} body={}", resp.getStatusCode(), resp.getBody());
            return resp;
        } catch (org.springframework.web.client.HttpStatusCodeException e) {
            logger.error("WhatsApp API returned error (template): status={} body={}", e.getStatusCode(), e.getResponseBodyAsString());
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        } catch (org.springframework.web.client.ResourceAccessException e) {
            logger.error("Resource access error when calling WhatsApp API (template): {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Resource access error: " + e.getMessage());
        } catch (org.springframework.web.client.RestClientException e) {
            logger.error("RestClientException when calling WhatsApp API (template): {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("RestClientException: " + e.getMessage());
        }
    }
}

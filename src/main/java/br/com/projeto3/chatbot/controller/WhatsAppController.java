package br.com.projeto3.chatbot.controller;

import br.com.projeto3.chatbot.dto.WhatsAppMessageRequest;
import br.com.projeto3.chatbot.dto.WhatsAppTemplateRequest;
import br.com.projeto3.chatbot.service.WhatsAppService;
import br.com.projeto3.chatbot.service.GamificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/whatsapp")
public class WhatsAppController {

    private static final Logger logger = LoggerFactory.getLogger(WhatsAppController.class);

    private final WhatsAppService whatsAppService;
    private final GamificationService gamificationService;

    @Value("${whatsapp.webhook.verify-token}")
    private String webhookVerifyToken;

    public WhatsAppController(WhatsAppService whatsAppService, GamificationService gamificationService) {
        this.whatsAppService = whatsAppService;
        this.gamificationService = gamificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody WhatsAppMessageRequest request) {

        if (request.getTo() == null || request.getText() == null) {
            return ResponseEntity.badRequest().body("'to' and 'text' são obrigatórios");
        }

        ResponseEntity<String> resp = whatsAppService.sendTextMessage(request.getTo(), request.getText());
        return ResponseEntity.status(resp.getStatusCode()).body(resp.getBody());
    }

    @PostMapping("/send-template")
    public ResponseEntity<String> sendTemplate(@RequestBody WhatsAppTemplateRequest request) {
        if (request.getTo() == null || request.getTemplateName() == null) {
            return ResponseEntity.badRequest().body("'to' and 'templateName' são obrigatórios");
        }

        ResponseEntity<String> resp = whatsAppService.sendTemplateMessage(request.getTo(), request.getTemplateName(), request.getLanguageCode(), request.getComponents());
        return ResponseEntity.status(resp.getStatusCode()).body(resp.getBody());
    }

    @GetMapping("/webhook")
    public ResponseEntity<String> verifyWebhook(@RequestParam Map<String, String> params) {

        String mode = params.getOrDefault("hub.mode", "");
        String token = params.getOrDefault("hub.verify_token", "");
        String challenge = params.getOrDefault("hub.challenge", "");

        logger.info("Webhook verification request: mode={}, token={}", mode, token);

        if ("subscribe" .equals(mode) && webhookVerifyToken != null && webhookVerifyToken.equals(token)) {
            return ResponseEntity.ok(challenge);
        }

        return ResponseEntity.status(403).body("Verification failed");
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> receiveWebhook(@RequestBody Map<String, Object> payload) {
        logger.info("Received WhatsApp webhook payload: {}", payload);

     
        try {
            var entry = (java.util.List<Object>) payload.get("entry");
            if (entry != null && !entry.isEmpty()) {
                Object firstEntry = entry.get(0);
                if (firstEntry instanceof java.util.Map) {
                    var entryMap = (java.util.Map<String, Object>) firstEntry;
                    var changes = (java.util.List<Object>) entryMap.get("changes");
                    if (changes != null && !changes.isEmpty()) {
                        var change = (java.util.Map<String, Object>) changes.get(0);
                        var value = (java.util.Map<String, Object>) change.get("value");
                        if (value != null) {
                            var messages = (java.util.List<Object>) value.get("messages");
                            if (messages != null && !messages.isEmpty()) {
                                var message = (java.util.Map<String, Object>) messages.get(0);
                                String from = (String) message.get("from");
                     
                                String text = null;
                                if (message.get("text") instanceof java.util.Map) {
                                    var textObj = (java.util.Map<String, Object>) message.get("text");
                                    text = (String) textObj.get("body");
                                }

                                logger.info("Incoming message from {}: {}", from, text);

                            
                                if (from != null) {
                                    gamificationService.awardPoints(from, 10);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("Failed to parse webhook payload: {}", e.getMessage());
        }

        return ResponseEntity.ok("EVENT_RECEIVED");
    }
}

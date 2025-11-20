package br.com.projeto3.chatbot.service;

import org.springframework.stereotype.Component;

@Component
public class MessageRouter {

    public enum Intent {
        GREETING,
        YES,
        NO,
        MENU,
        RANKING,
        MEDALS,
        NUMBER_1_5,
        FREE_TEXT,
        UNKNOWN
    }

    public Intent detectIntent(String text) {
        if (text == null || text.isBlank()) return Intent.UNKNOWN;

        String t = text.trim().toLowerCase();

        if (t.matches("\\b(oi|ola|olá|bom dia|boa tarde|boa noite)\\b")) return Intent.GREETING;
        if (t.equals("sim")) return Intent.YES;
        if (t.equals("nao") || t.equals("não")) return Intent.NO;
        if (t.equals("menu")) return Intent.MENU;
        if (t.equals("ranking")) return Intent.RANKING;
        if (t.equals("medalhas")) return Intent.MEDALS;

        if (t.matches("^[1-5]$")) return Intent.NUMBER_1_5;

        if (t.codePoints().anyMatch(cp -> Character.isSurrogate((char) cp) || cp > 0x1F000)) return Intent.FREE_TEXT;

        return Intent.FREE_TEXT;
    }
}
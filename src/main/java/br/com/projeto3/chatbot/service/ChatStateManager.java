package br.com.projeto3.chatbot.service;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatStateManager {

    private final Map<String, UserSession> sessions = new ConcurrentHashMap<>();

    public UserSession getSession(String phone) {
        return sessions.computeIfAbsent(phone, k -> new UserSession());
    }

    public void clearSession(String phone) {
        sessions.remove(phone);
    }

    public static class UserSession {
        public State state = State.NONE;
        public Integer q1 = null;
        public String q2 = null;
        public String q3 = null;
        public int accumulatedPoints = 0;
        public Instant lastUpdate = Instant.now();
    }

    public enum State {
        NONE,
        AGUARDANDO_Q1,
        AGUARDANDO_Q2,
        AGUARDANDO_Q3
    }
}
package br.com.projeto3.chatbot.service;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatFlowService {

    private final ChatStateManager stateManager;
    private final MessageRouter router;
    private final WhatsAppService whatsAppService;           
    private final GamificationService gamificationService;  

    private final Map<String, Integer> fakeRank = new HashMap<>();
    private final Map<String, String> fakeMedals = new HashMap<>();

    public ChatFlowService(ChatStateManager stateManager,
                           MessageRouter router,
                           WhatsAppService whatsAppService,
                           GamificationService gamificationService) {
        this.stateManager = stateManager;
        this.router = router;
        this.whatsAppService = whatsAppService;
        this.gamificationService = gamificationService;

        fakeRank.put("5511999990001", 320);
        fakeRank.put("5511888887777", 240);
        fakeRank.put("5511777771234", 150);

        fakeMedals.put("5511999990001", "🏅 PIONEIRO, 🥇 TOP");
        fakeMedals.put("5511888887777", "🥈 ATIVO");
    }

    public String processMessage(String phone, String text) {
        if (text == null) text = "";

        var session = stateManager.getSession(phone);
        var intent = router.detectIntent(text);

        if (intent == MessageRouter.Intent.MENU) {
            session.state = ChatStateManager.State.NONE;
            return menuText();
        }
        if (intent == MessageRouter.Intent.RANKING) {
            return gerarRanking();
        }
        if (intent == MessageRouter.Intent.MEDALS) {
            return medalsFor(phone);
        }

        switch (session.state) {
            case NONE:
                return handleNoneState(session, intent, text);
            case AGUARDANDO_Q1:
                return handleQ1(session, text);
            case AGUARDANDO_Q2:
                return handleQ2(session, text);
            case AGUARDANDO_Q3:
                return handleQ3(session, text);
            default:
                session.state = ChatStateManager.State.NONE;
                return "Erro interno — digite MENU para começar novamente.";
        }
    }

    private String handleNoneState(ChatStateManager.UserSession session, MessageRouter.Intent intent, String text) {
        if (intent == MessageRouter.Intent.GREETING) {
            return "Olá! 👋 Quer participar de uma pesquisa rápida? Responda SIM ou NÃO.";
        }
        if (intent == MessageRouter.Intent.YES) {
            session.state = ChatStateManager.State.AGUARDANDO_Q1;
            return pergunta1();
        }
        if (intent == MessageRouter.Intent.NO) {
            return "Tudo bem — quando quiser, chame-me novamente 😊";
        }
        return "Não entendi. Digite MENU para ver as opções ou SIM para iniciar uma pesquisa.";
    }

    private String handleQ1(ChatStateManager.UserSession session, String text) {
        if (!text.matches("^[1-5]$")) {
            return "Envie uma nota entre 1 e 5 (onde 5 é o melhor).";
        }
        session.q1 = Integer.parseInt(text);
        session.state = ChatStateManager.State.AGUARDANDO_Q2;

        safeAwardPoints(session, 10);

        return pergunta2();
    }

    private String handleQ2(ChatStateManager.UserSession session, String text) {
        session.q2 = text.trim();
        session.state = ChatStateManager.State.AGUARDANDO_Q3;
        safeAwardPoints(session, 5);
        return pergunta3();
    }

    private String handleQ3(ChatStateManager.UserSession session, String text) {
        session.q3 = text.trim();
        session.state = ChatStateManager.State.NONE;

        safeAwardPoints(session, 10);

        String summary = String.format("Obrigado! Respostas: nota=%d; motivo=%s; sugestão=%s",
                session.q1, defaultIfNull(session.q2), defaultIfNull(session.q3));

        fakeMedals.putIfAbsent("simulated:" + UUID.randomUUID().toString(), "🥉 Participante");

        return "🎉 Obrigado por responder!\n" + summary + "\n\nVocê ganhou +25 XP. Digite MENU para outras opções.";
    }

    private String pergunta1() {
        return "Pergunta 1/3 — De 1 a 5, como você avalia nosso serviço?";
    }

    private String pergunta2() {
        return "Pergunta 2/3 — Pode dizer rapidamente o motivo da sua nota?";
    }

    private String pergunta3() {
        return "Pergunta 3/3 — O que podemos melhorar?";
    }

    private String menuText() {
        return """
                📋 MENU
                • digite SIM — iniciar pesquisa
                • digite RANKING — ver ranking
                • digite MEDALHAS — ver suas medalhas
                • digite MENU — ver este menu
                """;
    }

    private String gerarRanking() {
        var entries = fakeRank.entrySet().stream()
                .sorted(Map.Entry.<String,Integer>comparingByValue().reversed())
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder("🏆 RANKING GERAL\n");
        int pos = 1;
        for (var e : entries) {
            sb.append(String.format("%d. %s — %d pontos\n", pos++, e.getKey(), e.getValue()));
        }
        return sb.toString();
    }

    private String medalsFor(String phone) {
        String m = fakeMedals.get(phone);
        if (m == null) return "Você ainda não tem medalhas — participe mais pesquisas!";
        return "🏅 Suas medalhas:\n" + m;
    }

    private void safeAwardPoints(ChatStateManager.UserSession session, int pts) {
        try {
            session.accumulatedPoints += pts;
        } catch (Exception e) {
        }
    }

    private String defaultIfNull(Object o) {
        return o == null ? "-" : o.toString();
    }
}
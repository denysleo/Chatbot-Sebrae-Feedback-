// APENAS RODAS O CODIGO PARA TESTAR.

package br.com.projeto3.chatbot;

import br.com.projeto3.chatbot.service.ChatFlowService;
import br.com.projeto3.chatbot.service.ChatStateManager;
import br.com.projeto3.chatbot.service.MessageRouter;
import br.com.projeto3.chatbot.service.GamificationService;
import br.com.projeto3.chatbot.service.WhatsAppService;

import java.util.Scanner;

public class TesteChatManual {

    public static void main(String[] args) {

        ChatStateManager stateManager = new ChatStateManager();
        MessageRouter router = new MessageRouter();

        WhatsAppService whatsAppService = null; 
        GamificationService gamificationService = null;

        ChatFlowService chat = new ChatFlowService(
                stateManager,
                router,
                whatsAppService,
                gamificationService
        );

        Scanner sc = new Scanner(System.in);
        String numero = "5511999990001";

        System.out.println("=== SIMULADOR DO CHATFLOW ===");

        while (true) {
            System.out.print("Você: ");
            String msg = sc.nextLine();

            String resposta = chat.processMessage(numero, msg);

            System.out.println("Bot: " + resposta);
        }
    }
}
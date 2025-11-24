package br.com.projeto3.chatbot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return """
            <html>
                <head>
                    <title>Chatbot Sebrae - Status</title>
                    <style>
                        body { font-family: Arial, sans-serif; background-color: #f0f2f5; text-align: center; padding: 50px; }
                        .container { background: white; padding: 40px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); max-width: 600px; margin: auto; }
                        h1 { color: #2e7d32; }
                        .status { font-weight: bold; color: #198754; background: #d1e7dd; padding: 10px; border-radius: 5px; display: inline-block; margin-bottom: 20px; }
                        p { color: #555; line-height: 1.6; }
                        code { background: #eee; padding: 2px 5px; border-radius: 4px; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>🤖 Chatbot Gamificado</h1>
                        <div class="status">✅ SISTEMA ONLINE</div>
                        
                        <p>Bem-vindo ao Backend da Plataforma de Pesquisa Gamificada.</p>
                        
                        <hr>
                        
                        <h3>🛠 Como testar:</h3>
                        <p>Esta é uma API REST. Utilize o <b>Thunder Client</b> ou <b>Postman</b>.</p>
                        <p style="text-align: left; margin-left: 20px;">
                            👉 <b>Webhook WhatsApp:</b> <code>POST /api/whatsapp/webhook</code><br>
                            👉 <b>Cadastro Admin:</b> <code>POST /api/auth/cadastro-admin</code><br>
                            👉 <b>Vincular Telefone:</b> <code>POST /api/auth/link-phone</code>
                        </p>
                    </div>
                </body>
            </html>
        """;
    }
}
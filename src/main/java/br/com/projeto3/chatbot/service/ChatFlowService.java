package br.com.projeto3.chatbot.service;

import br.com.projeto3.chatbot.model.*;
import br.com.projeto3.chatbot.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatFlowService {

    private final ChatStateManager stateManager;
    private final MessageRouter router;
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UsuarioRepository usuarioRepository;
    private final PhonePointsRepository phonePointsRepository; 
    private final GamificationService gamificationService;

    public ChatFlowService(ChatStateManager stateManager,
                           MessageRouter router,
                           SurveyRepository surveyRepository,
                           QuestionRepository questionRepository,
                           AnswerRepository answerRepository,
                           UsuarioRepository usuarioRepository,
                           PhonePointsRepository phonePointsRepository, 
                           GamificationService gamificationService) {
        this.stateManager = stateManager;
        this.router = router;
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.usuarioRepository = usuarioRepository;
        this.phonePointsRepository = phonePointsRepository; 
        this.gamificationService = gamificationService;
    }

    public String processMessage(String phone, String text) {
        if (text == null) text = "";

        var session = stateManager.getSession(phone);
        var intent = router.detectIntent(text);

     
        if (session.state == ChatStateManager.State.NONE) {
            if (intent == MessageRouter.Intent.MENU) return menuText();
            
         
            if (intent == MessageRouter.Intent.RANKING) {
                return gerarRanking(phone);
            }
     
            
            if (intent == MessageRouter.Intent.MEDALS) return "🏅 Medalhas em breve!";
            
            if (intent == MessageRouter.Intent.GREETING) {
                return "Olá! 👋 Temos uma nova pesquisa disponível. Topa participar? Responda SIM.";
            }
            if (intent == MessageRouter.Intent.YES) {
                return startSurvey(session, phone);
            }
            if (intent == MessageRouter.Intent.NO) {
                return "Tudo bem! Quando quiser, chame de novo. 😊";
            }
            return "Não entendi. Digite MENU para ver as opções.";
        }

        if (session.state == ChatStateManager.State.IN_SURVEY) {
            return handleSurveyAnswer(session, phone, text);
        }

        return "Erro interno. Digite MENU.";
    }


    private String gerarRanking(String phone) {
    
        PhonePoints userPoints = phonePointsRepository.findByPhoneNumber(phone).orElse(null);

        if (userPoints == null) {
            return "Você ainda não participou de nenhuma pesquisa para ter ranking. Digite SIM para começar!";
        }

       
        long usersAhead = phonePointsRepository.countUsersWithMorePoints(userPoints.getPoints());
        long myRank = usersAhead + 1;

        return "🏆 *SEU RANKING ATUAL* 🏆\n\n" +
               "👤 Posição: " + myRank + "º lugar\n" +
               "⭐ Pontos: " + userPoints.getPoints() + "\n" +
               "🏅 Nível: " + userPoints.getLevel() + "\n\n" +
               "Continue respondendo para subir!";
    }
   

    private String startSurvey(ChatStateManager.UserSession session, String phone) {
        List<Survey> surveys = surveyRepository.findAll();
        if (surveys.isEmpty()) {
            return "Ops! Não há pesquisas ativas no momento. Tente mais tarde.";
        }
        
        Survey survey = surveys.get(0); 
        List<Question> questions = questionRepository.findBySurveyId(survey.getId());

        if (questions.isEmpty()) {
            return "Esta pesquisa ainda não tem perguntas cadastradas. Desculpe!";
        }

        session.state = ChatStateManager.State.IN_SURVEY;
        session.currentSurveyId = survey.getId();
        session.currentQuestionIndex = 0;
        session.accumulatedPoints = 0;

        return "Iniciando a pesquisa: *" + survey.getTitle() + "*\n\n" + 
               "1️⃣ " + questions.get(0).getText();
    }

    private String handleSurveyAnswer(ChatStateManager.UserSession session, String phone, String text) {
        List<Question> questions = questionRepository.findBySurveyId(session.currentSurveyId);
        
        if (session.currentQuestionIndex >= questions.size()) {
            session.state = ChatStateManager.State.NONE;
            return "Pesquisa já finalizada. Digite MENU.";
        }

        Question answeredQuestion = questions.get(session.currentQuestionIndex);

        saveAnswer(phone, answeredQuestion, text);

        int points = 10;
        gamificationService.awardPoints(phone, points);
        session.accumulatedPoints += points;

        session.currentQuestionIndex++;

        if (session.currentQuestionIndex < questions.size()) {
            Question nextQuestion = questions.get(session.currentQuestionIndex);
            return "✅ Registrado!\n\n" + 
                   (session.currentQuestionIndex + 1) + "️⃣ " + nextQuestion.getText();
        } else {
            session.state = ChatStateManager.State.NONE;
            return "🎉 Pesquisa concluída! Muito obrigado.\n" +
                   "Você ganhou um total de " + session.accumulatedPoints + " pontos nesta sessão.\n" +
                   "Digite RANKING para ver sua posição!";
        }
    }

    private void saveAnswer(String phone, Question question, String text) {
        try {
            Answer answer = new Answer();
            answer.setResponseText(text);
            answer.setRespondedAt(LocalDateTime.now());
            answer.setQuestion(question);

            usuarioRepository.findByPhoneNumber(phone).ifPresent(answer::setUser);

            answerRepository.save(answer);
        } catch (Exception e) {
            System.err.println("Erro ao salvar resposta: " + e.getMessage());
        }
    }

    private String menuText() {
        return """
                📋 MENU
                • digite SIM — iniciar pesquisa
                • digite RANKING — ver ranking
                • digite MEDALHAS — ver medalhas
                """;
    }
}
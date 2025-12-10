package br.com.projeto3.chatbot.service;

import br.com.projeto3.chatbot.dto.SurveyStatsDTO;
import br.com.projeto3.chatbot.dto.QuestionStatsDTO;
import br.com.projeto3.chatbot.dto.QuestionAnswerCountDTO;
import br.com.projeto3.chatbot.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminStatsService {

    private final AnswerRepository answerRepository;

    public AdminStatsService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    /**
     * Estatísticas agregadas por empresa:
     * - total de respostas
     * - lista de pesquisas com total de respostas
     */
    public Map<String, Object> getCompanyStats(Long companyId) {
        Long totalAnswers = answerRepository.countAnswersByCompany(companyId);
        List<SurveyStatsDTO> surveys = answerRepository.findSurveyStatsByCompany(companyId);

        Map<String, Object> result = new HashMap<>();
        result.put("companyId", companyId);
        result.put("totalAnswers", totalAnswers);
        result.put("surveys", surveys);
        return result;
    }

    /**
     * Estatísticas detalhadas por pesquisa:
     * - total de respostas por pergunta
     * - distribuição das respostas por texto
     */
    public Map<String, Object> getSurveyStats(Long surveyId) {
        List<QuestionStatsDTO> questions = answerRepository.findQuestionStatsBySurvey(surveyId);
        List<QuestionAnswerCountDTO> distribution = answerRepository.findAnswerDistributionBySurvey(surveyId);

        Map<String, Object> result = new HashMap<>();
        result.put("surveyId", surveyId);
        result.put("questions", questions);
        result.put("distribution", distribution);
        return result;
    }
}

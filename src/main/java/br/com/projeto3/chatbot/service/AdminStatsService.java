package br.com.projeto3.chatbot.service;

import br.com.projeto3.chatbot.dto.QuestionAnswerCountDTO;
import br.com.projeto3.chatbot.dto.QuestionStatsDTO;
import br.com.projeto3.chatbot.dto.SurveyStatsDTO;
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

    public Map<String, Object> getCompanyStats(Long companyId) {
        Long totalAnswers = answerRepository.countAnswersByCompany(companyId);
        List<SurveyStatsDTO> surveys = answerRepository.findSurveyStatsByCompany(companyId);

        Map<String, Object> response = new HashMap<>();
        response.put("totalAnswers", totalAnswers);
        response.put("surveys", surveys);

        return response;
    }

    public Map<String, Object> getSurveyStats(Long surveyId) {
        List<QuestionStatsDTO> questions = answerRepository.findQuestionStatsBySurvey(surveyId);
        List<QuestionAnswerCountDTO> distribution = answerRepository.findAnswerDistributionBySurvey(surveyId);

        Map<String, Object> response = new HashMap<>();
        response.put("questions", questions);
        response.put("distribution", distribution);

        return response;
    }
}
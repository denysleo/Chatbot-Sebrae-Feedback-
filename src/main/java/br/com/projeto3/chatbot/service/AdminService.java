package br.com.projeto3.chatbot.service;

import br.com.projeto3.chatbot.model.*;
import br.com.projeto3.chatbot.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final CompanyRepository companyRepository;
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;

    public AdminService(CompanyRepository companyRepository, SurveyRepository surveyRepository, QuestionRepository questionRepository) {
        this.companyRepository = companyRepository;
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
    }

    // --- EMPRESA ---
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public List<Company> listCompanies() {
        return companyRepository.findAll();
    }


    public Survey createSurvey(Long companyId, Survey survey) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        survey.setCompany(company);
        return surveyRepository.save(survey);
    }

    public List<Survey> listSurveysByCompany(Long companyId) {
        return surveyRepository.findByCompanyId(companyId);
    }


    public Question addQuestion(Long surveyId, Question question) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Pesquisa não encontrada"));
        question.setSurvey(survey);
        return questionRepository.save(question);
    }
    
    public List<Question> listQuestionsBySurvey(Long surveyId) {
        return questionRepository.findBySurveyId(surveyId);
    }
}
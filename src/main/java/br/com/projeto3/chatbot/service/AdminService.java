package br.com.projeto3.chatbot.service;

import br.com.projeto3.chatbot.dto.QuestionUpdateDTO;
import br.com.projeto3.chatbot.dto.SurveyUpdateDTO;
import br.com.projeto3.chatbot.model.*;
import br.com.projeto3.chatbot.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final CompanyRepository companyRepository;
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public AdminService(CompanyRepository companyRepository, 
                        SurveyRepository surveyRepository, 
                        QuestionRepository questionRepository,
                        AnswerRepository answerRepository) {
        this.companyRepository = companyRepository;
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public Survey createSurvey(Long companyId, Survey survey) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
        survey.setCompany(company);
        return surveyRepository.save(survey);
    }

    public Question addQuestion(Long surveyId, Question question) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Pesquisa não encontrada"));
        question.setSurvey(survey);
        return questionRepository.save(question);
    }

    public List<Company> listCompanies() {
        return companyRepository.findAll();
    }

    public List<Survey> listSurveysByCompany(Long companyId) {
        return surveyRepository.findByCompanyId(companyId);
    }

    public List<Question> listQuestionsBySurvey(Long surveyId) {
        return questionRepository.findBySurveyId(surveyId);
    }

    public List<Answer> listAnswersBySurvey(Long surveyId) {
        return answerRepository.findByQuestionSurveyId(surveyId);
    }

    public Optional<Survey> findSurveyById(Long id) {
        return surveyRepository.findById(id);
    }

    public Survey updateSurvey(Long id, SurveyUpdateDTO dto) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pesquisa não encontrada"));
        
        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            survey.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            survey.setDescription(dto.getDescription());
        }
        return surveyRepository.save(survey);
    }

    public void deleteSurvey(Long id) {
        if (surveyRepository.existsById(id)) {
            surveyRepository.deleteById(id);
        } else {
            throw new RuntimeException("Pesquisa não encontrada para exclusão");
        }
    }

    public Question updateQuestion(Long id, QuestionUpdateDTO dto) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pergunta não encontrada"));

        if (dto.getText() != null && !dto.getText().isBlank()) {
            question.setText(dto.getText());
        }
        
        return questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        if (questionRepository.existsById(id)) {
            questionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Pergunta não encontrada para exclusão");
        }
    }
}
package br.com.projeto3.chatbot.controller;

import br.com.projeto3.chatbot.dto.FeedbackDTO;
import br.com.projeto3.chatbot.dto.QuestionUpdateDTO;
import br.com.projeto3.chatbot.dto.SurveyUpdateDTO;
import br.com.projeto3.chatbot.model.*;
import br.com.projeto3.chatbot.repository.AnswerRepository;
import br.com.projeto3.chatbot.service.AdminService;
<<<<<<< HEAD
import br.com.projeto3.chatbot.service.AdminStatsService;
=======
>>>>>>> 9aef59dd031bc495c4f18778d3289561d14eea7a
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
<<<<<<< HEAD
    private final AdminStatsService adminStatsService;
    private final AnswerRepository answerRepository;

    public AdminController(AdminService adminService, AdminStatsService adminStatsService, AnswerRepository answerRepository) {
        this.adminService = adminService;
        this.adminStatsService = adminStatsService;
        this.answerRepository = answerRepository;
=======

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
>>>>>>> 9aef59dd031bc495c4f18778d3289561d14eea7a
    }

    @PostMapping("/companies")
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        return ResponseEntity.ok(adminService.createCompany(company));
    }

    @PostMapping("/companies/{companyId}/surveys")
    public ResponseEntity<Survey> createSurvey(@PathVariable Long companyId, @RequestBody Survey survey) {
        return ResponseEntity.ok(adminService.createSurvey(companyId, survey));
    }

    @PostMapping("/surveys/{surveyId}/questions")
    public ResponseEntity<Question> addQuestion(@PathVariable Long surveyId, @RequestBody Question question) {
        return ResponseEntity.ok(adminService.addQuestion(surveyId, question));
    }

    @GetMapping("/companies")
    public ResponseEntity<List<Company>> listAllCompanies() {
        return ResponseEntity.ok(adminService.listCompanies());
    }

    @GetMapping("/companies/{companyId}/surveys")
    public ResponseEntity<List<Survey>> listSurveys(@PathVariable Long companyId) {
        return ResponseEntity.ok(adminService.listSurveysByCompany(companyId));
    }

    @GetMapping("/surveys/{surveyId}/questions")
    public ResponseEntity<List<Question>> listQuestions(@PathVariable Long surveyId) {
        return ResponseEntity.ok(adminService.listQuestionsBySurvey(surveyId));
    }

    @GetMapping("/surveys/{surveyId}/answers")
    public ResponseEntity<List<Answer>> listAnswers(@PathVariable Long surveyId) {
        return ResponseEntity.ok(adminService.listAnswersBySurvey(surveyId));
    }
<<<<<<< HEAD

    @GetMapping("/companies/{companyId}/stats")
    public ResponseEntity<?> getCompanyStats(@PathVariable Long companyId) {
        return ResponseEntity.ok(adminStatsService.getCompanyStats(companyId));
    }

    @GetMapping("/surveys/{surveyId}/stats")
    public ResponseEntity<?> getSurveyStats(@PathVariable Long surveyId) {
        return ResponseEntity.ok(adminStatsService.getSurveyStats(surveyId));
    }

    @GetMapping("/surveys/{id}")
    public ResponseEntity<Survey> getSurveyById(@PathVariable Long id) {
        return adminService.findSurveyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/surveys/{id}")
    public ResponseEntity<Survey> updateSurvey(@PathVariable Long id, @RequestBody SurveyUpdateDTO dto) {
        return ResponseEntity.ok(adminService.updateSurvey(id, dto));
    }

    @DeleteMapping("/surveys/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long id) {
        adminService.deleteSurvey(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody QuestionUpdateDTO dto) {
        return ResponseEntity.ok(adminService.updateQuestion(id, dto));
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        adminService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<List<FeedbackDTO>> getFeedbacks(
            @RequestParam(required = false, defaultValue = "ALL") String period,
            @RequestParam(required = false, defaultValue = "NPS") String type
    ) {
        LocalDateTime startDate = LocalDateTime.of(1970, 1, 1, 0, 0);

        if ("WEEK".equals(period)) startDate = LocalDateTime.now().minusWeeks(1);
        else if ("MONTH".equals(period)) startDate = LocalDateTime.now().minusMonths(1);
        else if ("YEAR".equals(period)) startDate = LocalDateTime.now().minusYears(1);

        List<Answer> answers = answerRepository.findAnswersAfterDate(startDate);

        List<FeedbackDTO> dtos = answers.stream().map(a -> {
            int score = 0;
            try {
                score = Integer.parseInt(a.getResponseText().trim());
            } catch (Exception e) {
            }

            return new FeedbackDTO(
                    a.getUser() != null ? a.getUser().getNome() : "Anônimo",
                    10,
                    score,
                    a.getResponseText(),
                    "NPS",
                    a.getRespondedAt()
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
=======
>>>>>>> 9aef59dd031bc495c4f18778d3289561d14eea7a
}
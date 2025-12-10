package br.com.projeto3.chatbot.controller;

import br.com.projeto3.chatbot.model.*;
import br.com.projeto3.chatbot.service.AdminService;
import org.springframework.http.ResponseEntity;
import br.com.projeto3.chatbot.service.AdminStatsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final AdminStatsService adminStatsService;

    public AdminController(AdminService adminService, AdminStatsService adminStatsService) {
        this.adminService = adminService;
        this.adminStatsService = adminStatsService;
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
    
    // Estatísticas agregadas de uma empresa
    @GetMapping("/companies/{companyId}/stats")
    public ResponseEntity<?> getCompanyStats(@PathVariable Long companyId) {
        return ResponseEntity.ok(adminStatsService.getCompanyStats(companyId));
    }

    // Estatísticas detalhadas de uma pesquisa
    @GetMapping("/surveys/{surveyId}/stats")
    public ResponseEntity<?> getSurveyStats(@PathVariable Long surveyId) {
        return ResponseEntity.ok(adminStatsService.getSurveyStats(surveyId));
    }
}

package br.com.projeto3.chatbot.controller;

import br.com.projeto3.chatbot.model.*;
import br.com.projeto3.chatbot.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
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


    @GetMapping("/companies/{companyId}/surveys")
    public ResponseEntity<List<Survey>> listSurveys(@PathVariable Long companyId) {
        return ResponseEntity.ok(adminService.listSurveysByCompany(companyId));
    }
}
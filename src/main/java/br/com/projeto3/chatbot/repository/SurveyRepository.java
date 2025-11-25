package br.com.projeto3.chatbot.repository;

import br.com.projeto3.chatbot.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findByCompanyId(Long companyId);
}
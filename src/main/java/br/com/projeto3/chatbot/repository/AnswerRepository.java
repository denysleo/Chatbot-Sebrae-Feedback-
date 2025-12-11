package br.com.projeto3.chatbot.repository;

import br.com.projeto3.chatbot.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    
  
    List<Answer> findByUserId(Long userId);

    
    List<Answer> findByQuestionSurveyId(Long surveyId);
}
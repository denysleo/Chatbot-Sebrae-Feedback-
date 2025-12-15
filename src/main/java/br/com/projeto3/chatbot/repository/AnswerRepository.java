package br.com.projeto3.chatbot.repository;

import br.com.projeto3.chatbot.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

=======
import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    
  
>>>>>>> 9aef59dd031bc495c4f18778d3289561d14eea7a
    List<Answer> findByUserId(Long userId);

    
    List<Answer> findByQuestionSurveyId(Long surveyId);
<<<<<<< HEAD

    @Query("SELECT a FROM Answer a WHERE a.respondedAt >= :startDate")
    List<Answer> findAnswersAfterDate(@Param("startDate") LocalDateTime startDate);

    @Query("""
            SELECT new br.com.projeto3.chatbot.dto.SurveyStatsDTO(
                s.id,
                s.title,
                COUNT(a.id)
            )
            FROM Answer a
            JOIN a.question q
            JOIN q.survey s
            JOIN s.company c
            WHERE c.id = :companyId
            GROUP BY s.id, s.title
            """)
    List<SurveyStatsDTO> findSurveyStatsByCompany(@Param("companyId") Long companyId);

    @Query("""
            SELECT new br.com.projeto3.chatbot.dto.QuestionStatsDTO(
                q.id,
                q.text,
                COUNT(a.id)
            )
            FROM Answer a
            JOIN a.question q
            JOIN q.survey s
            WHERE s.id = :surveyId
            GROUP BY q.id, q.text
            ORDER BY q.id
            """)
    List<QuestionStatsDTO> findQuestionStatsBySurvey(@Param("surveyId") Long surveyId);

    @Query("""
            SELECT new br.com.projeto3.chatbot.dto.QuestionAnswerCountDTO(
                q.id,
                q.text,
                a.responseText,
                COUNT(a.id)
            )
            FROM Answer a
            JOIN a.question q
            JOIN q.survey s
            WHERE s.id = :surveyId
            GROUP BY q.id, q.text, a.responseText
            ORDER BY q.id, a.responseText
            """)
    List<QuestionAnswerCountDTO> findAnswerDistributionBySurvey(@Param("surveyId") Long surveyId);

    @Query("""
            SELECT COUNT(a.id)
            FROM Answer a
            JOIN a.question q
            JOIN q.survey s
            JOIN s.company c
            WHERE c.id = :companyId
            """)
    Long countAnswersByCompany(@Param("companyId") Long companyId);
=======
>>>>>>> 9aef59dd031bc495c4f18778d3289561d14eea7a
}
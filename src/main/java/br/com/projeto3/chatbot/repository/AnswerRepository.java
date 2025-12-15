package br.com.projeto3.chatbot.repository;

import br.com.projeto3.chatbot.dto.QuestionAnswerCountDTO;
import br.com.projeto3.chatbot.dto.QuestionStatsDTO;
import br.com.projeto3.chatbot.dto.SurveyStatsDTO;
import br.com.projeto3.chatbot.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByUserId(Long userId);

    List<Answer> findByQuestionSurveyId(Long surveyId);

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
}
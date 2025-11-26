package br.com.projeto3.chatbot.repository;

import br.com.projeto3.chatbot.model.PhonePoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PhonePointsRepository extends JpaRepository<PhonePoints, Long> {
    
    Optional<PhonePoints> findByPhoneNumber(String phoneNumber);

   
    @Query("SELECT COUNT(p) FROM PhonePoints p WHERE p.points > :points")
    long countUsersWithMorePoints(@Param("points") Integer points);
}
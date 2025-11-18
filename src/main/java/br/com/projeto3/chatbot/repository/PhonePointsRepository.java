package br.com.projeto3.chatbot.repository;

import br.com.projeto3.chatbot.model.PhonePoints;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PhonePointsRepository extends JpaRepository<PhonePoints, Long> {
    Optional<PhonePoints> findByPhoneNumber(String phoneNumber);
}

package br.com.projeto3.chatbot.repository;

import br.com.projeto3.chatbot.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
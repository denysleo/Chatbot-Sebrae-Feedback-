package br.com.projeto3.chatbot.repository;

import br.com.projeto3.chatbot.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByPhoneNumber(String phoneNumber);
}
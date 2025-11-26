package br.com.projeto3.chatbot.controller;

import br.com.projeto3.chatbot.dto.LoginRequest;
import br.com.projeto3.chatbot.model.PhonePoints;
import br.com.projeto3.chatbot.model.Usuario;
import br.com.projeto3.chatbot.repository.PhonePointsRepository;
import br.com.projeto3.chatbot.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PhonePointsRepository phonePointsRepository;
    @Autowired private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Usuario> optUser = usuarioRepository.findByEmail(request.getEmail());

        if (optUser.isPresent()) {
            Usuario user = optUser.get();
           
            if (passwordEncoder.matches(request.getSenha(), user.getSenha())) {
                return ResponseEntity.ok(user); 
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }

   
    @GetMapping("/profile/{email}")
    public ResponseEntity<?> getProfileData(@PathVariable String email) {
        Optional<Usuario> optUser = usuarioRepository.findByEmail(email);
        if (optUser.isEmpty()) return ResponseEntity.notFound().build();

        Usuario user = optUser.get();
     
        PhonePoints points = null;
        if (user.getPhoneNumber() != null) {
            points = phonePointsRepository.findByPhoneNumber(user.getPhoneNumber()).orElse(null);
        }

        return ResponseEntity.ok(Map.of(
            "user", user,
            "ranking", points != null ? points : "Sem pontuação"
        ));
    }

  
    @PostMapping("/cadastro-admin")
    public ResponseEntity<String> registerAdmin(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            return new ResponseEntity<>("Erro: Email já está em uso!", HttpStatus.BAD_REQUEST);
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
     
        if (usuario.getRole() == null) usuario.setRole(br.com.projeto3.chatbot.model.Role.CHATBOT_USER);
        usuarioRepository.save(usuario);
        return new ResponseEntity<>("Usuário registrado!", HttpStatus.CREATED);
    }
}
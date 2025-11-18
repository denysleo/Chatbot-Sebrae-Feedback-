package br.com.projeto3.chatbot.controller;

import br.com.projeto3.chatbot.model.Role;
import br.com.projeto3.chatbot.model.Usuario;
import br.com.projeto3.chatbot.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/cadastro-admin")
    public ResponseEntity<String> registerAdmin(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            return new ResponseEntity<>("Erro: Email já está em uso!", HttpStatus.BAD_REQUEST);
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setRole(Role.ADMIN);
        usuarioRepository.save(usuario);
        return new ResponseEntity<>("Usuário ADMIN registrado com sucesso!", HttpStatus.CREATED);
    }

    @PostMapping("/link-phone")
    public ResponseEntity<String> linkPhone(@RequestBody Map<String, String> body, Principal principal) {
        String phone = body.get("phoneNumber");
        if (phone == null || phone.isBlank()) {
            return new ResponseEntity<>("Número de telefone inválido", HttpStatus.BAD_REQUEST);
        }

  
        String email = principal.getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<>("Usuário não encontrado", HttpStatus.UNAUTHORIZED);
        }

    
        if (usuarioRepository.findByPhoneNumber(phone).isPresent()) {
            return new ResponseEntity<>("Número já vinculado a outro usuário", HttpStatus.CONFLICT);
        }

        usuario.setPhoneNumber(phone);
        usuarioRepository.save(usuario);
        return new ResponseEntity<>("Telefone vinculado com sucesso", HttpStatus.OK);
    }
}
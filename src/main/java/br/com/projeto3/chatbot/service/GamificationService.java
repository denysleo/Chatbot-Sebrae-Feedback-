package br.com.projeto3.chatbot.service;

import br.com.projeto3.chatbot.model.PhonePoints;
import br.com.projeto3.chatbot.repository.PhonePointsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class GamificationService {

    private final PhonePointsRepository phonePointsRepository;
    private final WhatsAppService whatsAppService;
    private final br.com.projeto3.chatbot.repository.UsuarioRepository usuarioRepository;

    public GamificationService(PhonePointsRepository phonePointsRepository, WhatsAppService whatsAppService, br.com.projeto3.chatbot.repository.UsuarioRepository usuarioRepository) {
        this.phonePointsRepository = phonePointsRepository;
        this.whatsAppService = whatsAppService;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public PhonePoints awardPoints(String phoneNumber, int pointsToAdd) {
        Optional<PhonePoints> opt = phonePointsRepository.findByPhoneNumber(phoneNumber);
        PhonePoints pp;
        if (opt.isPresent()) {
            pp = opt.get();
            int oldPoints = pp.getPoints();
            int newPoints = oldPoints + pointsToAdd;
            pp.setPoints(newPoints);
            int oldLevel = pp.getLevel();
            int newLevel = calculateLevel(newPoints);
            pp.setLevel(newLevel);
            phonePointsRepository.save(pp);

            if (newLevel > oldLevel) {
             
                String msg = String.format("Parabéns! Você subiu para o nível %d 🎉 (total %d pontos)", newLevel, newPoints);
                whatsAppService.sendTextMessage(phoneNumber, msg);
            }
        } else {
            pp = new PhonePoints();
            pp.setPhoneNumber(phoneNumber);
            pp.setPoints(pointsToAdd);
            pp.setLevel(calculateLevel(pointsToAdd));
            phonePointsRepository.save(pp);

            String msg = String.format("Bem-vindo! Você ganhou %d pontos e está no nível %d 🎉", pointsToAdd, pp.getLevel());
            whatsAppService.sendTextMessage(phoneNumber, msg);
        }

     
        usuarioRepository.findByPhoneNumber(phoneNumber).ifPresent(u -> {

            System.out.println("Associated phone to user: " + u.getEmail());
        });

        return pp;
    }

    private int calculateLevel(int points) {
  
        return (points / 100) + 1;
    }
}

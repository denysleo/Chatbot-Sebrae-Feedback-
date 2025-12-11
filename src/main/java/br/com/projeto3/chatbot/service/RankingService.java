package br.com.projeto3.chatbot.service;

import br.com.projeto3.chatbot.dto.RankingDTO;
import br.com.projeto3.chatbot.model.PhonePoints;
import br.com.projeto3.chatbot.model.Usuario;
import br.com.projeto3.chatbot.repository.PhonePointsRepository;
import br.com.projeto3.chatbot.repository.UsuarioRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RankingService {

    private final PhonePointsRepository phonePointsRepository;
    private final UsuarioRepository usuarioRepository;

    public RankingService(PhonePointsRepository phonePointsRepository, UsuarioRepository usuarioRepository) {
        this.phonePointsRepository = phonePointsRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<RankingDTO> getRanking(String period) {
        List<PhonePoints> allPoints = phonePointsRepository.findAll(Sort.by(Sort.Direction.DESC, "points"));
        List<RankingDTO> rankingList = new ArrayList<>();

        int position = 1;
        for (PhonePoints pp : allPoints) {
            String name = "Usuário Desconhecido";
            
            Usuario user = usuarioRepository.findByPhoneNumber(pp.getPhoneNumber()).orElse(null);
            if (user != null) {
                name = user.getNome();
            } else {
                name = formatPhone(pp.getPhoneNumber());
            }

            Long pointsAsLong = pp.getPoints() != null ? pp.getPoints().longValue() : 0L;

            rankingList.add(new RankingDTO(
                name,
                pointsAsLong,
                position++,
                pp.getLevel()
            ));
        }

        if (rankingList.isEmpty()) {
            rankingList.add(new RankingDTO("Denys Sales", 800L, 1, 5));
            rankingList.add(new RankingDTO("Hugo Gomes", 770L, 2, 4));
        }

        return rankingList;
    }

    private String formatPhone(String phone) {
        if (phone == null) return "Anônimo";
        if (phone.length() > 4) {
            return phone.substring(0, phone.length() - 4) + "****";
        }
        return phone;
    }
}
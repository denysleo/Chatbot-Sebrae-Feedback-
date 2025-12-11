package br.com.projeto3.chatbot.controller;

import br.com.projeto3.chatbot.dto.RankingDTO;
import br.com.projeto3.chatbot.service.RankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping
    public ResponseEntity<List<RankingDTO>> getRanking(
            @RequestParam(required = false, defaultValue = "ALL") String period
    ) {
        return ResponseEntity.ok(rankingService.getRanking(period));
    }
}
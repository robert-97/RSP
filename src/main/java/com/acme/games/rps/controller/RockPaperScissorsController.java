package com.acme.games.rps.controller;

import com.acme.games.rps.model.Move;
import com.acme.games.rps.model.MoveResult;
import com.acme.games.rps.model.Result;
import com.acme.games.rps.service.RockPaperScissorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/rps")
public class RockPaperScissorsController {

    @Autowired
    private RockPaperScissorsService service;

    @PostMapping("/start")
    public String startGame() {
        String sessionId = service.startNewSession();
        return sessionId;
    }

    @PostMapping("/move")
    public ResponseEntity<?> makeMove(@RequestHeader("Session-Id") String sessionId, @RequestParam Move move) {
        MoveResult result = service.makeMove(sessionId, move);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/statistics")
    public ResponseEntity<?> getStatistics(@RequestHeader("Session-Id") String sessionId) {
        Map<Result, Integer> statistics = service.getStatistics(sessionId);
        return ResponseEntity.ok(statistics);

    }

    @DeleteMapping("/end")
    public ResponseEntity<?> endGame(@RequestHeader("Session-Id") String sessionId) {
        Map<Result, Integer> statistics = service.getStatistics(sessionId);
        service.endSession(sessionId);
        return ResponseEntity.ok(statistics);
    }
}


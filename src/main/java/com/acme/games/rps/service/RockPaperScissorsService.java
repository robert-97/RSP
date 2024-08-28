package com.acme.games.rps.service;

import com.acme.games.rps.model.GameSession;
import com.acme.games.rps.model.Move;
import com.acme.games.rps.model.MoveResult;
import com.acme.games.rps.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class RockPaperScissorsService {
    @Autowired
    private RedisTemplate<String, GameSession> userSessions;

    private final Random random = new Random();

    /**
     * Starts a new game session and returns a unique session ID.
     */
    public String startNewSession() {
        String sessionId = UUID.randomUUID().toString();
        userSessions.opsForValue().set(sessionId, new GameSession());
        return sessionId;
    }

    /**
     * Ends an existing game session by removing it from the userSessions.
     */
    public void endSession(String sessionId) {
        userSessions.delete(sessionId);
    }

    /**
     * Plays a move in the existing session.
     */
    public MoveResult makeMove(String sessionId, Move userMove) {
        GameSession session = userSessions.opsForValue().get(sessionId);
        if (session == null) {
            throw new IllegalStateException("Invalid session ID");
        }
        Move computerMove = this.predictMove();
        Result result = Move.determineResult(userMove, computerMove);
        session.update(userMove, computerMove, result);
        userSessions.opsForValue().set(sessionId, session);
        return new MoveResult(userMove, computerMove, result, session.getStatistics());
    }

    private Move predictMove() {
        Move[] moves = Move.values();
        int index = random.nextInt(moves.length);
        return moves[index];
    }

    /**
     * Retrieves game statistics for the session.
     */
    public Map<Result, Integer> getStatistics(String sessionId) {
        GameSession session = userSessions.opsForValue().get(sessionId);
        if (session == null) {
            throw new IllegalStateException("Invalid session ID");
        }
        return session.getStatistics();
    }
}


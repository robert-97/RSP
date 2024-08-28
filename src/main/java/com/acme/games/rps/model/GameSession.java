package com.acme.games.rps.model;

import java.io.Serializable;
import java.util.*;

public class GameSession implements Serializable {
    private List<Move> userMoves = new ArrayList<>();
    private List<Move> computerMoves = new ArrayList<>();
    private Map<Result, Integer> statistics = new EnumMap<>(Result.class);

    public void update(Move userMove, Move computerMove, Result result) {
        userMoves.add(userMove);
        computerMoves.add(computerMove);
        statistics.put(result, statistics.getOrDefault(result, 0) + 1);
    }

    public Map<Result, Integer> getStatistics() {
        return Collections.unmodifiableMap(statistics);
    }
}


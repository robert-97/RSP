package com.acme.games.rps.model;

import java.util.Map;

public record MoveResult(Move playerMove, Move computerMove, Result result, Map<Result, Integer> statistics) {
}

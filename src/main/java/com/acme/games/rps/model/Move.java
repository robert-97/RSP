package com.acme.games.rps.model;

public enum Move {
    ROCK {
        @Override
        public boolean beats(Move other) {
            return other == SCISSORS;
        }
    },
    PAPER {
        @Override
        public boolean beats(Move other) {
            return other == ROCK;
        }
    },
    SCISSORS {
        @Override
        public boolean beats(Move other) {
            return other == PAPER;
        }
    };

    /**
     * Abstract method to determine if the current move beats another move.
     * Each enum constant overrides this method with its specific logic.
     */
    public abstract boolean beats(Move other);
    /**
     * Method to determine the result of a game between two moves.
     */
    public static Result determineResult(Move playerMove, Move computerMove) {
        if (playerMove == computerMove) {
            return Result.DRAW;
        } else if (playerMove.beats(computerMove)) {
            return Result.WIN;
        } else {
            return Result.LOSS;
        }
    }

}

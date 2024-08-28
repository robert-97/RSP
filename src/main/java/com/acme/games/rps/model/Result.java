package com.acme.games.rps.model;

public enum Result {
    WIN("You win"),
    LOSS("You lose"),
    DRAW("It's a draw");

    private final String message;

    Result(String message) {
        this.message = message;
    }

    /**
     * Returns a user-friendly message for the result.
     */
    public String getMessage() {
        return message;
    }
}


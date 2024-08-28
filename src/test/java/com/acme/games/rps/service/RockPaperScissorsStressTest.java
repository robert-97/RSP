package com.acme.games.rps.service;

import com.acme.games.rps.model.Move;
import com.acme.games.rps.service.RockPaperScissorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RockPaperScissorsStressTest {

    @Autowired
    private RockPaperScissorsService service;

    private static final int NUM_USERS = 1000;
    private static final int NUM_MOVES = 10;

    public void stressTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_USERS);

        List<Runnable> tasks = new ArrayList<>();

        for (int i = 0; i < NUM_USERS; i++) {
            tasks.add(() -> {
                String sessionId = service.startNewSession();
                for (int j = 0; j < NUM_MOVES; j++) {
                    Move move = Move.values()[(int) (Math.random() * Move.values().length)];
                    service.makeMove(sessionId, move);
                }
                service.getStatistics(sessionId);
                service.endSession(sessionId);
            });
        }

        // Submit all tasks to the executor service
        tasks.forEach(executorService::submit);

        // Wait for all tasks to complete
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        Assert.isTrue(executorService.isTerminated(), "All tasks should have completed");
    }

    public static void main(String[] args) throws InterruptedException {
        RockPaperScissorsStressTest stressTest = new RockPaperScissorsStressTest();
        stressTest.stressTest();
        System.out.println("Stress test completed.");
    }
}

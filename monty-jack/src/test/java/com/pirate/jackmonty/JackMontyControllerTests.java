package com.pirate.jackmonty;

import com.pirate.jackmonty.model.Result;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JackMontyControllerTests {

    private static final Logger logger = LoggerFactory.getLogger(JackMontyControllerTests.class);

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    //Test for the standard Jack monty simulation
    @Test
    public void jackMontyStandardSimulation() {
        String url = "http://localhost:%d/jack-monty?islands=%s&excluded=%s".formatted(port, 3, 1);
        double riches = getRiches(url, 10000);
        double mathProb = 2 / (double) 3;
        assertTrue(Math.abs(riches - mathProb) < 0.02);
        assertTrue(0.66 <= riches && riches <= 0.677, "Percentage not in range");
    }

    //Test for the standard Jack monty simulation with a higher number of islands
    @Test
    public void jackMontyGenericSimulationHighNbrIsland() {
        int islands = 1000;
        int excluded = islands - 2;
        String url = "http://localhost:%d/jack-monty?islands=%s&excluded=%s".formatted(port, islands, excluded);
        double riches = getRiches(url, 100);
        double mathProb = (islands - 1) / (double) islands;
        assertTrue(Math.abs(riches - mathProb) < 0.02);
        assertTrue(0.98 <= riches && riches <= 1, "Percentage not in range");
    }

    //Test for the standard Jack monty simulation with a higher number of islands and P excluded islands
    @Test
    public void jackMontySimulationWithLessExcludedIslandsThanNormal() {
        int islands = 100;
        int excluded = islands / 2;
        String url = "http://localhost:%d/jack-monty?islands=%s&excluded=%s".formatted(port, islands, excluded);
        double riches = getRiches(url, 10000);
        double mathProb = (1 / (double) islands) * ((islands - 1) / ((double) islands - excluded - 1));
        logger.debug("Math prob : {}", mathProb);
        logger.debug("Sim prob : {}", riches);
        assertTrue(Math.abs(riches - mathProb) < 0.02);
    }

    /**
     * A simple loop to calculate the probability of a number of simulations.
     * Calls the endpoint of the url and validates that the result is not null.
     *
     * @param url                server api call URL
     * @param numberOfSimulation the number of simulations called
     * @return the percentage of finding treasure when switching island
     */
    private double getRiches(String url, double numberOfSimulation) {
        int stayed = 0;
        int switched = 0;
        for (int i = 0; i < numberOfSimulation; i++) {
            var res = this.restTemplate.getForObject(url, Result.class);
            assertNotNull(res);
            if (res.isStayed()) {
                stayed++;
            } else if (res.isSwitched()) {
                switched++;
            }
        }
        double riches = switched / numberOfSimulation;
        logger.debug("""
                    
                    Api runs: %f
                    Stayed : %d
                    Switched : %d
                    Percentage of treasure found, when switching : %f
                """.formatted(numberOfSimulation, stayed, switched, riches));
        return riches;
    }
}
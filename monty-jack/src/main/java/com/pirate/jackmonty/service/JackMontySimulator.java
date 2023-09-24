package com.pirate.jackmonty.service;

import com.pirate.jackmonty.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.TreeSet;
import java.util.stream.IntStream;

/**
 * Service class that executes the simulations
 */
@Service
public class JackMontySimulator {

    private static final Logger logger = LoggerFactory.getLogger(JackMontySimulator.class);

    /**
     * A logging function that prints the result, helpful for debugging
     *
     * @param result the object to log
     */
    private static void logResult(Result result) {
        logger.trace("""
                   
                   Simulation result
                   switched : %b
                   stayed : %b
                """.formatted(result.isSwitched(), result.isStayed()));
    }

    /**
     * Runs a JackMontySimulation with given nbr of islands and excluded islands
     * <p>
     * This will run a simulation with randomized initial island and treasure island
     *
     * @return the result of the simulation
     */
    public Result runGenericJackMontySimulation(Integer islands, Integer nbrExcludedIslands) {
        //A little experiment, where monty does not remove all but one door.
        if (islands - 2 != nbrExcludedIslands) {
            return smallNumberOfExcludedIslandsSimulation(islands, nbrExcludedIslands);
        }
        return defaultSimulation(islands);
    }

    /**
     * The standard simulation where the pirate Jack removes all but one door.
     * The treasure probability will be (N-1)/N, and will converge to 1 for large N, where N is the number of islands
     *
     * @param islands the number of islands
     * @return the result of the simulation
     */
    private Result defaultSimulation(Integer islands) {
        SecureRandom random = new SecureRandom();
        //Island with the treasure
        int treasureIsland = random.nextInt(islands);
        //The initial pick
        int pickedIsland = random.nextInt(islands);
        //Holder object for the result
        Result result = new Result();
        //Treasure if we stayed
        result.setStayed(treasureIsland == pickedIsland);
        //Treasure if we switched
        result.setSwitched(treasureIsland != pickedIsland);
        //Log the result
        logResult(result);
        return result;
    }

    /**
     * This is the n-island simulation where the pirate Jack removes P islands.
     * The treasure probability will be (1/N) * ((N-1)/(N-P-1)),
     * and will always be larger than 1/N
     * See https://en.wikipedia.org/wiki/Monty_Hall_problem and N doors
     *
     * @param islands the number of islands
     * @return the result of the simulation
     */
    private Result smallNumberOfExcludedIslandsSimulation(Integer islands, Integer nbrExcludedIslands) {
        SecureRandom random = new SecureRandom();
        //Island with the treasure
        int treasureIsland = random.nextInt(islands);
        //The initial pick
        int pickedIsland = random.nextInt(islands);

        //Let's follow the logic that we only make the choice when Jack has excluded all "bad" islands.
        TreeSet<Integer> excludedIslands = new TreeSet<>();
        excludedIslands.add(pickedIsland);
        excludedIslands.add(treasureIsland);

        //Randomize a number of islands excluding the previous ones and the treasure and picked island
        IntStream.range(0, nbrExcludedIslands)
                .map(i -> randomWithExclusions(random, 0, islands - 1, excludedIslands))
                .forEach(excludedIslands::add);

        //For the last pick remove the treasure island if it is not the picked one
        if (treasureIsland != pickedIsland) {
            excludedIslands.remove(treasureIsland);
        }
        //Randomize one of the remaining islands
        int lastIsland = randomWithExclusions(random, 0, islands - 1, excludedIslands);

//        logger.debug("""
//                Roll results
//                treasureIsland : %d
//                pickedIsland : %d
//                lastIsland : %d
//                """.formatted(treasureIsland, pickedIsland, lastIsland));

        //Holder object for the result
        Result result = new Result();
        //Treasure if we stayed
        result.setStayed(treasureIsland == pickedIsland);
        //Treasure if we switched
        result.setSwitched(treasureIsland == lastIsland);
        //Log the result
        logResult(result);
        return result;
    }

    /**
     * A method that randomizes with exclusions
     *
     * @param random          A randomizer object
     * @param start           Start of the random range
     * @param end             End of the random range
     * @param excludedNumbers A list of the excluded numbers
     * @return A random number
     */
    public int randomWithExclusions(SecureRandom random, int start, int end, TreeSet<Integer> excludedNumbers) {
        int ran = start + random.nextInt(end - start + 1 - excludedNumbers.size());
        for (int ex : excludedNumbers) {
            if (ran < ex) {
                return ran;
            }
            ran++;
        }
        return ran;
    }
}

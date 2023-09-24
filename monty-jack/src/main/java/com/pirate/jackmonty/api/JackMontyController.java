package com.pirate.jackmonty.api;

import com.pirate.jackmonty.exception.BadNbrOfIslandsException;
import com.pirate.jackmonty.model.Result;
import com.pirate.jackmonty.service.JackMontySimulator;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ⠀⠀⠀⠀⠀⠀⠀⠀⣀⣤⣤⣤⣤⣀⡀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⢀⣴⣿⣿⣿⡿⢿⣿⣿⣿⣷⣄⠀⠀⠀⠀⠀
 * ⠀⣠⣤⣶⣶⣿⣿⣿⣿⣯⠀⠀⣽⣿⣿⣿⣿⣷⣶⣤⣄⠀
 * ⢸⣿⣿⣿⣿⣿⣿⣿⣿⡅⠉⠉⢨⣿⣿⣿⣿⣿⣿⣿⣿⡇
 * ⠈⠻⣿⣿⣿⣿⣿⣿⣿⣥⣴⣦⣬⣿⣿⣿⣿⣿⣿⣿⠟⠁
 * ⠀⠀⢸⣿⡿⠿⠿⠿⠿⠿⠿⠿⢿⣿⣿⣿⠿⢿⣿⡇⠀⠀
 * ⠀⣠⣾⣿⠂⠀⠀⣤⣄⠀⠀⢰⣿⣿⣿⣿⡆⠐⣿⣷⣄⠀
 * ⠀⣿⣿⡀⠀⠀⠈⠿⠟⠀⠀⠈⠻⣿⣿⡿⠃⠀⢀⣿⣿⠀
 * ⠀⠘⠻⢿⣷⡀⠀⠀⠀⢀⣀⣀⠀⠀⠀⠀⢀⣾⡿⠟⠃⠀
 * ⠀⠀⠀⠸⣿⣿⣷⣦⣾⣿⣿⣿⣿⣦⣴⣾⣿⣿⡇⠀⠀⠀
 * ⠀⠀⠀⠀⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠈⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⠁⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠈⠉⠛⠛⠛⠛⠋⠉⠀⠀⠀⠀⠀⠀⠀
 * Argh I be a pirate
 * <p>
 * Api controller for the Jack Monty problem, based on the Monty Hall problem
 * But it has more pirates.
 */
@RestController
@RequestMapping("/jack-monty")
@RequiredArgsConstructor
public class JackMontyController {

    @NonNull
    private final JackMontySimulator simulator;

    /**
     * Api endpoint that runs a JackMontySimulation with given nbr of islands and excluded islands
     * <p>
     *
     * @return the result of the simulation
     */
    @GetMapping
    public Result getGenericJackMontySimulationResult(@RequestParam("islands") @Min(3) @Max(267570) @DefaultValue("3") Integer islands,
                                                      @RequestParam("excluded") @Min(2) @DefaultValue("1") Integer excluded) throws BadNbrOfIslandsException {
        if (islands - 2 < excluded) {
            throw new BadNbrOfIslandsException("Excluded islands cannot be greater than N - 2 where N is number of islands");
        }
        return simulator.runGenericJackMontySimulation(islands, excluded);
    }
}

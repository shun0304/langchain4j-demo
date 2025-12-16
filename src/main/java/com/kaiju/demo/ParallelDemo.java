package com.kaiju.demo;

import com.kaiju.model.EveningPlan;
import com.kaiju.service.EveningPlannerAgent;
import com.kaiju.service.FoodExpert;
import com.kaiju.service.MovieExpert;
import dev.langchain4j.agentic.AgenticServices;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import static com.kaiju.config.LlmConfig.getChatModel;

public class ParallelDemo {
    private static void parallelDemo() {
        FoodExpert foodExpert = AgenticServices
                .agentBuilder(FoodExpert.class)
                .chatModel(getChatModel())
                .outputKey("meals")
                .build();

        MovieExpert movieExpert = AgenticServices
                .agentBuilder(MovieExpert.class)
                .chatModel(getChatModel())
                .outputKey("movies")
                .build();

        EveningPlannerAgent eveningPlannerAgent = AgenticServices
                .parallelBuilder(EveningPlannerAgent.class)
                .subAgents(foodExpert, movieExpert)
                .executor(Executors.newFixedThreadPool(2))
                .outputKey("plans")
                .output(agenticScope -> {
                    List<String> movies = agenticScope.readState("movies", List.of());
                    List<String> meals = agenticScope.readState("meals", List.of());

                    List<EveningPlan> moviesAndMeals = new ArrayList<>();
                    for (int i = 0; i < movies.size(); i++) {
                        if (i >= meals.size()) {
                            break;
                        }
                        moviesAndMeals.add(new EveningPlan(movies.get(i), meals.get(i)));
                    }
                    return moviesAndMeals;
                })
                .build();

        List<EveningPlan> plans = eveningPlannerAgent.plan("romantic");
        System.out.println(plans);
    }

    public static void main(String[] args) {
        parallelDemo();
    }
}

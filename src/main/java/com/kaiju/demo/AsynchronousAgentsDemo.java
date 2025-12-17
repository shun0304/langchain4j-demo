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

public class AsynchronousAgentsDemo {

    private static void asynchronousAgentsDemo() {
        FoodExpert foodExpert = AgenticServices
                .agentBuilder(FoodExpert.class)
                .chatModel(getChatModel())
                .async(true)
                .outputKey("meals")
                .build();

        MovieExpert movieExpert = AgenticServices
                .agentBuilder(MovieExpert.class)
                .chatModel(getChatModel())
                .async(true)
                .outputKey("movies")
                .build();

        EveningPlannerAgent eveningPlannerAgent = AgenticServices
                .sequenceBuilder(EveningPlannerAgent.class)
                .subAgents(foodExpert, movieExpert)
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
        asynchronousAgentsDemo();
    }
}

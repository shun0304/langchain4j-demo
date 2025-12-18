package com.kaiju.service;

import com.kaiju.model.EveningPlan;
import dev.langchain4j.agentic.declarative.Output;
import dev.langchain4j.agentic.declarative.ParallelAgent;
import dev.langchain4j.agentic.declarative.ParallelExecutor;
import dev.langchain4j.agentic.declarative.SubAgent;
import dev.langchain4j.service.V;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public interface DeclarativeEveningPlannerAgent {
    @ParallelAgent(
            outputKey = "plans",
            subAgents = {
                    @SubAgent(type = DeclarativeFoodExpert.class),
                    @SubAgent(type = DeclarativeMovieExpert.class)
            })
    List<EveningPlan> plan(@V("mood") String mood);

    @ParallelExecutor
    static Executor executor() {
        return Executors.newFixedThreadPool(2);
    }

    @Output
    static List<EveningPlan> createPlans(@V("movies") List<String> movies,
                                         @V("meals") List<String> meals) {
        System.out.println("Creating plans...");
        System.out.println("Movies: " + movies);
        System.out.println("Meals: " + meals);
        List<EveningPlan> moviesAndMeals = new ArrayList<>();
//        for (int i = 0; i < movies.size(); i++) {
//            if (i >= meals.size()) {
//                break;
//            }
//            moviesAndMeals.add(new EveningPlan(movies.get(i), meals.get(i)));
//        }
        return moviesAndMeals;
    }
}

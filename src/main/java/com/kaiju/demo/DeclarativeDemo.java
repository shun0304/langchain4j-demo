package com.kaiju.demo;

import com.kaiju.model.EveningPlan;
import com.kaiju.service.DeclarativeEveningPlannerAgent;
import com.kaiju.service.DeclarativeFoodExpert;
import com.kaiju.service.DeclarativeMovieExpert;
import dev.langchain4j.agentic.AgenticServices;

import java.util.List;

import static com.kaiju.config.LlmConfig.getChatModel;

public class DeclarativeDemo {
    private static void declarativeDemo() {
        DeclarativeEveningPlannerAgent eveningPlannerAgent = AgenticServices
                .createAgenticSystem(DeclarativeEveningPlannerAgent.class, getChatModel());
        List<EveningPlan> plans = eveningPlannerAgent.plan("romantic");
        System.out.println("plans: " + plans);
    }

    private static void foodExpertDemo() {
        DeclarativeFoodExpert build = AgenticServices.agentBuilder(DeclarativeFoodExpert.class)
                .build();
        List<String> romantic = build.findMeal("romantic");
        System.out.println(romantic);
    }

    private static void movieExpertDemo() {
        DeclarativeMovieExpert build = AgenticServices.agentBuilder(DeclarativeMovieExpert.class)
                .build();
        List<String> romantic = build.findMovie("romantic");
        System.out.println(romantic);
    }

    public static void main(String[] args) {
                foodExpertDemo();
        movieExpertDemo();
        declarativeDemo();

    }
}

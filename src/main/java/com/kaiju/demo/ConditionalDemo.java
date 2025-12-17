package com.kaiju.demo;

import com.kaiju.enums.RequestCategory;
import com.kaiju.service.*;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;

import static com.kaiju.config.LlmConfig.getChatModel;

public class ConditionalDemo {
    private static void conditionalDemo() {
        CategoryRouter routerAgent = AgenticServices
                .agentBuilder(CategoryRouter.class)
                .chatModel(getChatModel())
                .outputKey("category")
                .build();

        MedicalExpert medicalExpert = AgenticServices
                .agentBuilder(MedicalExpert.class)
                .chatModel(getChatModel())
                .outputKey("response")
                .build();
        LegalExpert legalExpert = AgenticServices
                .agentBuilder(LegalExpert.class)
                .chatModel(getChatModel())
                .outputKey("response")
                .build();
        TechnicalExpert technicalExpert = AgenticServices
                .agentBuilder(TechnicalExpert.class)
                .chatModel(getChatModel())
                .outputKey("response")
                .build();

        UntypedAgent expertsAgent = AgenticServices.conditionalBuilder()
                .subAgents( agenticScope -> agenticScope.readState("category", RequestCategory.UNKNOWN) == RequestCategory.MEDICAL, medicalExpert)
                .subAgents( agenticScope -> agenticScope.readState("category", RequestCategory.UNKNOWN) == RequestCategory.LEGAL, legalExpert)
                .subAgents( agenticScope -> agenticScope.readState("category", RequestCategory.UNKNOWN) == RequestCategory.TECHNICAL, technicalExpert)
                .build();

        ExpertRouterAgent expertRouterAgent = AgenticServices
                .sequenceBuilder(ExpertRouterAgent.class)
                .subAgents(routerAgent, expertsAgent)
                .outputKey("response")
                .build();

        String response = expertRouterAgent.ask("where can i use java?");
        System.out.println(response);
    }

    public static void main(String[] args) {
        conditionalDemo();
    }
}

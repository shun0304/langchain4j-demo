package com.kaiju.service;

import com.kaiju.enums.RequestCategory;
import dev.langchain4j.agentic.declarative.ActivationCondition;
import dev.langchain4j.agentic.declarative.ConditionalAgent;
import dev.langchain4j.agentic.declarative.SubAgent;
import dev.langchain4j.service.V;

public interface ExpertAgent {
    @ConditionalAgent(outputKey = "response",
            subAgents = {@SubAgent(type = MedicalExpert.class),
                    @SubAgent(type = TechnicalExpert.class),
                    @SubAgent(type = LegalExpert.class)})
    String askExpert(@V("request") String request);

    @ActivationCondition(MedicalExpert.class)
    static boolean activateMedical(@V("category") RequestCategory category) {
        return category == RequestCategory.MEDICAL;
    }

    @ActivationCondition(TechnicalExpert.class)
    static boolean activateTechnical(@V("category") RequestCategory category) {
        return category == RequestCategory.TECHNICAL;
    }

    @ActivationCondition(LegalExpert.class)
    static boolean activateLegal(@V("category") RequestCategory category) {
        return category == RequestCategory.LEGAL;
    }
}

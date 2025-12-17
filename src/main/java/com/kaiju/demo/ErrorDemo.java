package com.kaiju.demo;

import com.kaiju.service.AgentListener;
import com.kaiju.service.AudienceEditor;
import com.kaiju.service.CreativeWriter;
import com.kaiju.service.StyleEditor;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import dev.langchain4j.agentic.agent.AgentRequest;
import dev.langchain4j.agentic.agent.AgentResponse;
import dev.langchain4j.agentic.agent.ErrorRecoveryResult;
import dev.langchain4j.agentic.agent.MissingArgumentException;

import java.util.Map;

import static com.kaiju.config.LlmConfig.getChatModel;

public class ErrorDemo {
    private static void errorHandleDemo(){
        CreativeWriter creativeWriter = AgenticServices
                .agentBuilder(CreativeWriter.class)
                .chatModel(getChatModel())
                .outputKey("story")
                .build();

        AudienceEditor audienceEditor = AgenticServices
                .agentBuilder(AudienceEditor.class)
                .chatModel(getChatModel())
                .outputKey("story")
                .build();

        StyleEditor styleEditor = AgenticServices
                .agentBuilder(StyleEditor.class)
                .chatModel(getChatModel())
                .outputKey("story")
                .build();
        UntypedAgent novelCreator = AgenticServices
                .sequenceBuilder()
                .subAgents(creativeWriter, audienceEditor, styleEditor)
                .errorHandler(errorContext -> {
                    if (errorContext.agentName().equals("generateStory") &&
                            errorContext.exception() instanceof MissingArgumentException mEx && mEx.argumentName().equals("topic")) {
                        errorContext.agenticScope().writeState("topic", "dragons and wizards");
//                        errorRecoveryCalled.set(true);
                        return ErrorRecoveryResult.retry();
                    }
                    return ErrorRecoveryResult.throwException();
                })
                .outputKey("story")
                .build();

        Map<String, Object> input = Map.of(
                // missing "topic" entry to trigger an error
                // "topic", "dragons and wizards",
                "style", "fantasy",
                "audience", "young adults"
        );
        String story = (String) novelCreator.invoke(input);
        System.out.println(story);
    }

    public static void main(String[] args) {
        errorHandleDemo();
    }
}

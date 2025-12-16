package com.kaiju.demo;

import com.kaiju.service.CreativeWriter;
import dev.langchain4j.agentic.AgenticServices;

import static com.kaiju.config.LlmConfig.getChatModel;

public class AgentDemo {
    public static void agentDemo(){
        CreativeWriter creativeWriter = AgenticServices
                .agentBuilder(CreativeWriter.class)
                .chatModel(getChatModel())
//                .outputKey("story")
                .build();
        String generateStory = creativeWriter.generateStory("小白兔爱吃草");
        System.out.println(generateStory);
    }

    public static void main(String[] args) {
        agentDemo();
    }
}

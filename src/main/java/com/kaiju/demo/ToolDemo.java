package com.kaiju.demo;

import com.kaiju.service.BaseAssistant;
import com.kaiju.service.MemoryAssistant;
import com.kaiju.tool.Tools;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.service.AiServices;

import static com.kaiju.config.LlmConfig.getChatModel;

public class ToolDemo {
    public static void toolChatDemo(){
        BaseAssistant assistant = AiServices.builder(BaseAssistant.class)
                .chatModel(getChatModel())
                .tools(new Tools())
                .build();
        String answer = assistant.chat("What is 1+2 and 3*4?");
        System.out.println(answer);
    }

    public static void main(String[] args) {
        toolChatDemo();
    }
}

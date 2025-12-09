package com.kaiju.demo;

import com.kaiju.service.MemoryAssistant;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.service.AiServices;

import java.util.List;

import static com.kaiju.config.LlmConfig.getChatModel;

public class MemoryDemo {

    public static void memoryChatDemo() {
        MemoryAssistant assistant = AiServices.builder(MemoryAssistant.class)
                .chatModel(getChatModel())
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
                .build();
        assistant.chat(1, "Hello, my name is Klaus");
        assistant.chat(2, "Hello, my name is Francine");
        String chat = assistant.chat(1, "Hello, what's my name");
        System.out.println(chat);
        List<ChatMessage> messagesWithKlaus = assistant.getChatMemory(1).messages();
        System.out.println(messagesWithKlaus);
        assistant.evictChatMemory(1);
        assistant.chat(1, "Hello, what's my name");
        assistant.chat(1, "Hello, my name is Klaus");
        assistant.chat(1, "Hello, what's my name");
        messagesWithKlaus = assistant.getChatMemory(1).messages();
        System.out.println(messagesWithKlaus);
    }

    public static void main(String[] args) {
        memoryChatDemo();
    }
}

package com.kaiju.demo;

import com.kaiju.constant.AIConstants;
import dev.langchain4j.data.message.*;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.kaiju.config.LlmConfig.getChatModel;
import static com.kaiju.config.LlmConfig.getStreamChatModel;
import static dev.langchain4j.internal.Utils.readBytes;
import static dev.langchain4j.model.LambdaStreamingResponseHandler.onPartialResponseAndError;

public class ChatDemo {

    private static void chatdemo1() {
        String response = getChatModel().chat("说你好");
        System.out.println(response);
    }

    private static void chatdemo2() {
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(SystemMessage.from("你是一个天气预报员"));
        messages.add(UserMessage.from("北京明天天气如何"));
        ChatResponse chatResponse = getChatModel().chat(messages);
        System.out.println(chatResponse);
        // 获取 AI 回复
        AiMessage aiMsg = chatResponse.aiMessage();
        System.out.println(aiMsg);
        System.out.println(aiMsg.text());
    }

    private static void chatdemo3() {
        UserMessage firstUserMessage = UserMessage.from("Hello, my name is Klaus");
        AiMessage firstAiMessage = getChatModel().chat(firstUserMessage).aiMessage(); // Hi Klaus, how can I help you?
        System.out.println(firstAiMessage);
        UserMessage secondUserMessage = UserMessage.from("What is my name?");
        AiMessage secondAiMessage = getChatModel().chat(firstUserMessage, firstAiMessage, secondUserMessage).aiMessage(); // Klaus
        System.out.println(secondAiMessage);
    }

    private static void chatImageDemo() {
        UserMessage userMessage = UserMessage.from(
                TextContent.from("Describe the following image"),
                ImageContent.from("https://example.com/cat.jpg")
        );
        ChatResponse chatResponse = getChatModel().chat(userMessage);
        System.out.println(chatResponse);
    }

    private static void chatImageDemo2() {
        byte[] imageBytes = readBytes("/home/me/cat.jpg");
        String base64Data = Base64.getEncoder().encodeToString(imageBytes);
        ImageContent imageContent = ImageContent.from(base64Data, "image/jpg");
        UserMessage userMessage = UserMessage.from(imageContent);
        ChatResponse chatResponse = getChatModel().chat(userMessage);
        System.out.println(chatResponse);
    }

    private static void streamDemo() {
        getStreamChatModel().chat("Tell me a joke",
                onPartialResponseAndError(System.out::print, Throwable::printStackTrace));
    }

    public static void main(String[] args) {
//        chatdemo1();
//        chatdemo2();
//        chatdemo3();
        streamDemo();
    }
}

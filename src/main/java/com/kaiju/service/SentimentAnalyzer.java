package com.kaiju.service;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

@AiService(
        wiringMode = EXPLICIT,  // 显式装配模式
        chatModel = "openAiChatModel"             // 阻塞式模型名称（需与配置中定义的 bean 名称一致）
)
public interface SentimentAnalyzer {
    @UserMessage("Does {{it}} has a positive sentiment?")
    boolean isPositive(String text);

}

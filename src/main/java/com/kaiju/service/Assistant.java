package com.kaiju.service;


import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

import static dev.langchain4j.service.spring.AiServiceWiringMode.*;

@AiService(
        wiringMode = EXPLICIT,  // 显式装配模式
        chatModel = "openAiChatModel"             // 阻塞式模型名称（需与配置中定义的 bean 名称一致）
)
public interface Assistant {
    @SystemMessage("你是一个java开发专家")
    String chat(@UserMessage String userMessage);
}

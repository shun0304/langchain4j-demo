package com.kaiju.service;


import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

import static dev.langchain4j.service.spring.AiServiceWiringMode.EXPLICIT;

public interface BaseAssistant {
    @SystemMessage("你是一个java开发专家")
    String chat(@UserMessage String userMessage);

}

package com.kaiju.controller;

import com.kaiju.config.LlmConfig;
import com.kaiju.enums.Priority;
import com.kaiju.service.Assistant;
import com.kaiju.service.PriorityAnalyzer;
import com.kaiju.service.SentimentAnalyzer;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class AssistantController {
    @Autowired
    private Assistant assistant;
    @Autowired
    private SentimentAnalyzer sentimentAnalyzer;
    @Autowired
    private LlmConfig llmConfig;

    @GetMapping("/demo")
    public String demo(){
        return "哈哈哈";
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(name="message",required = true) String message) {
        return assistant.chat(message);
    }
}

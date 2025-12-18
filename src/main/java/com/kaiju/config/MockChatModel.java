package com.kaiju.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.internal.chat.ChatCompletionRequest;

import java.util.ArrayList;
import java.util.List;

public class MockChatModel implements ChatModel {

    private final List<String> outputs;

    public MockChatModel(List<String> outputs) {
        this.outputs = outputs;
    }
}

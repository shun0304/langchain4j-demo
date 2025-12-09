package com.kaiju.config;

import com.kaiju.constant.AIConstants;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static dev.langchain4j.model.chat.Capability.RESPONSE_FORMAT_JSON_SCHEMA;

@Configuration
public class LlmConfig {

    @Bean
    public OpenAiChatModel openAiChatModel() {
        return OpenAiChatModel.builder()
                .apiKey(AIConstants.API_KEY)
                .modelName(AIConstants.modelName)
                .baseUrl(AIConstants.BASE_URL)
                .temperature(0.7)
                .build();
    }

    public static OpenAiChatModel getChatModel() {
        return OpenAiChatModel.builder()
                .apiKey(AIConstants.API_KEY)
                .modelName(AIConstants.modelName)
                .baseUrl(AIConstants.BASE_URL)
                .build();
    }

    public static StreamingChatModel getStreamChatModel() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(AIConstants.API_KEY)
                .modelName(AIConstants.modelName)
                .baseUrl(AIConstants.BASE_URL)
                .build();
    }

    public static OpenAiChatModel getJsonChatModel() {
        return OpenAiChatModel.builder()
                .apiKey(AIConstants.API_KEY)
                .modelName(AIConstants.modelName)
                .baseUrl(AIConstants.BASE_URL)
                .supportedCapabilities(RESPONSE_FORMAT_JSON_SCHEMA)
                .strictJsonSchema(true)
                .build();
    }
}

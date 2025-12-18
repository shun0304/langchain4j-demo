package com.kaiju.service;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

import java.util.List;

import static com.kaiju.config.LlmConfig.getChatModel;

public interface DeclarativeMovieExpert {
    @UserMessage("""
        You are a great evening planner.
        Propose a list of 3 movies matching the given mood.
        The mood is {{mood}}.
        Provide a list with the 3 items and nothing else.
        """)
    @Agent(outputKey = "movies")
    List<String> findMovie(@V("mood") String mood);

    @ChatModelSupplier
    static ChatModel chatModel() {
        return getChatModel();
    }
}

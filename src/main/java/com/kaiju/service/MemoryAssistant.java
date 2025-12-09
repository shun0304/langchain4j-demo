package com.kaiju.service;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.memory.ChatMemoryAccess;

public interface MemoryAssistant extends ChatMemoryAccess {
    String chat(@MemoryId int memoryId, @UserMessage String message);
}

package com.kaiju.demo;

import com.kaiju.service.StreamAssistant;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.PartialResponse;
import dev.langchain4j.model.chat.response.PartialResponseContext;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.TokenStream;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.kaiju.config.LlmConfig.getStreamChatModel;

public class StreamDemo {

    //使用以下返回类型时， AI 服务可以逐个令牌地传输响应流TokenStream：
    private static void streamChatDemo() {
        StreamingChatModel streamChatModel = getStreamChatModel();
        StreamAssistant streamAssistant = AiServices.create(StreamAssistant.class, streamChatModel);
        TokenStream tokenStream = streamAssistant.chat("Tell me a joke");

        CompletableFuture<ChatResponse> completableFuture = new CompletableFuture<>();

        tokenStream
                .onPartialResponse((partialResponse) -> {
                    System.out.println("partial response: " + partialResponse);
                })
                .onPartialThinking((partialThinking) -> {
                    System.out.println("partial thinking: " + partialThinking);
                })
                .onRetrieved((List<Content> contents) -> {
                    System.out.println("contents: " + contents);
                })
                .onIntermediateResponse((ChatResponse chatResponse) -> {
                    System.out.println("intermediate response: " + chatResponse);
                })
                .beforeToolExecution(beforeToolExecution -> {
                    System.out.println("before tool execution: " + beforeToolExecution);
                })
                .onToolExecuted(toolExecuted -> {
                    System.out.println("tool executed: " + toolExecuted);
                })
                .onCompleteResponse(chatResponse -> {
                    System.out.println("complete response: " + chatResponse);
                    completableFuture.complete(chatResponse);
                })
                .onError(throwable -> {
                    System.out.println("error");
                    completableFuture.completeExceptionally(throwable);
                })
                .start();
        completableFuture.join();

    }


    private static void process(PartialResponse partialResponse) {
        System.out.println("partial response: " + partialResponse.text());
    }

    private static boolean shouldCancel(PartialResponse partialResponse) {
        return partialResponse.text().equals("Because");
    }

    //如果您想取消流媒体播放，可以通过以下回调函数之一进行操作：
    private static void streamChatCancelDemo(){
        StreamingChatModel streamChatModel = getStreamChatModel();
        StreamAssistant streamAssistant = AiServices.create(StreamAssistant.class, streamChatModel);
        TokenStream tokenStream = streamAssistant.chat("Tell me a joke");

        CompletableFuture<ChatResponse> completableFuture = new CompletableFuture<>();
        tokenStream
                .onPartialResponseWithContext((PartialResponse partialResponse, PartialResponseContext context) -> {
                    process(partialResponse);
                    if (shouldCancel(partialResponse)) {
                        context.streamingHandle().cancel();
                    }
                })
                .onCompleteResponse(chatResponse -> {
                    System.out.println("complete response: " + chatResponse);
                    completableFuture.complete(chatResponse);
                })
                .onError((Throwable error) -> completableFuture.completeExceptionally(error))
                .start();
        completableFuture.join();
    }

    public static void main(String[] args) {
//        streamChatDemo();
        streamChatCancelDemo();
    }
}

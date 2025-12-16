package com.kaiju.demo;

import com.kaiju.service.AudienceEditor;
import com.kaiju.service.CreativeWriter;
import com.kaiju.service.NovelCreator;
import com.kaiju.service.StyleEditor;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;

import java.util.Map;

import static com.kaiju.config.LlmConfig.getChatModel;

//顺序工作流
public class UntypedAgentDemo {
    private static void novelCreatorDemo() {
        CreativeWriter creativeWriter = AgenticServices
                .agentBuilder(CreativeWriter.class)
                .chatModel(getChatModel())
                .outputKey("story")
                .build();

        AudienceEditor audienceEditor = AgenticServices
                .agentBuilder(AudienceEditor.class)
                .chatModel(getChatModel())
                .outputKey("story")
                .build();

        StyleEditor styleEditor = AgenticServices
                .agentBuilder(StyleEditor.class)
                .chatModel(getChatModel())
                .outputKey("story")
                .build();

        UntypedAgent novelCreator = AgenticServices
                .sequenceBuilder()
                .subAgents(creativeWriter, audienceEditor, styleEditor)
                .outputKey("story")
                .build();

        Map<String, Object> input = Map.of(
                "topic", "龙与地下城",
                "style", "冒险",
                "audience", "年轻的"
        );

        String story = (String) novelCreator.invoke(input);
        System.out.println(story);

        NovelCreator novelCreator1 = AgenticServices
                .sequenceBuilder(NovelCreator.class)
                .subAgents(creativeWriter, audienceEditor, styleEditor)
                .outputKey("story")
                .build();

        String story1 = novelCreator1.createNovel("美女", "年轻的", "冒险");
        System.out.println(story1);
    }

    public static void main(String[] args) {
        novelCreatorDemo();
    }
}

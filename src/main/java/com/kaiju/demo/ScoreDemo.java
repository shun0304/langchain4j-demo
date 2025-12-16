package com.kaiju.demo;

import com.kaiju.service.CreativeWriter;
import com.kaiju.service.StyleEditor;
import com.kaiju.service.StyleScorer;
import com.kaiju.service.StyledWriter;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;

import static com.kaiju.config.LlmConfig.getChatModel;

public class ScoreDemo {
    private static void scorerDemo(){
        StyleEditor styleEditor = AgenticServices
                .agentBuilder(StyleEditor.class)
                .chatModel(getChatModel())
                .outputKey("story")
                .build();

        StyleScorer styleScorer = AgenticServices
                .agentBuilder(StyleScorer.class)
                .chatModel(getChatModel())
                .outputKey("score")
                .build();

        //如果前 3 次迭代的得分至少为 0.8，则循环退出；否则，将降低质量预期，以至少 0.6 的得分终止循环，
        // 并且styleEditor即使在满足退出条件后，也会强制最后一次调用代理。
        UntypedAgent styleReviewLoop = AgenticServices
                .loopBuilder()
                .subAgents(styleScorer, styleEditor)
                .maxIterations(5)
                .testExitAtLoopEnd(true)
                .exitCondition( (agenticScope, loopCounter) -> {
                    double score = agenticScope.readState("score", 0.0);
                    return loopCounter <= 3 ? score >= 0.8 : score >= 0.6;
                })
                .build();

        CreativeWriter creativeWriter = AgenticServices
                .agentBuilder(CreativeWriter.class)
                .chatModel(getChatModel())
                .outputKey("story")
                .build();

        StyledWriter styledWriter = AgenticServices
                .sequenceBuilder(StyledWriter.class)
                .subAgents(creativeWriter, styleReviewLoop)
                .outputKey("story")
                .build();

        String story = styledWriter.writeStoryWithStyle("小美女", "性感");
        System.out.println(story);
    }

    public static void main(String[] args) {
        scorerDemo();
    }
}

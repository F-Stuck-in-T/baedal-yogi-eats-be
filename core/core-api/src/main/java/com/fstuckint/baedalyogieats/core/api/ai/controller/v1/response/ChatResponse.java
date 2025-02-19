package com.fstuckint.baedalyogieats.core.api.ai.controller.v1.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {

    private List<Candidate> candidates;

    private PromptFeedback promptFeedback;

    @Data
    public static class Candidate {

        private Content content;

        private String finishReason;

        private int index;

        private List<SafetyRating> safetyRatings;

    }

    @Data
    public static class Content {

        private List<Parts> parts;

        private String role;

    }

    @Data
    public static class Parts {

        private String text;

    }

    @Data
    public static class SafetyRating {

        private String category;

        private String probability;

    }

    @Data
    public static class PromptFeedback {

        private List<SafetyRating> safetyRatings;

    }

}

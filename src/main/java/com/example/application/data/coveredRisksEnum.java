package com.example.application.data;

public class coveredRisksEnum {
    public enum CoveredRisksEnum {

        political("Политический"),
        commercial("Коммерческий"),
        both("Политический/Коммерческий");

        private final String displayName;

        CoveredRisksEnum(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}

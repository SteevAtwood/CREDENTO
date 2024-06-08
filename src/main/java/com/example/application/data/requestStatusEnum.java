package com.example.application.data;

public class requestStatusEnum {
    public enum RequestStatusEnum {

        pending("В рассмотрении"),
        accepted("Одобрено"),
        declined("Отклонено");

        private final String displayName;

        RequestStatusEnum(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}

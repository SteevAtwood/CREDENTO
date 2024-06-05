package com.example.application.data;

public class statusEnum {
    public enum StatusEnum {
        prospect("Проспект"),
        signed_contract("Подписанный контракт");

        private final String displayName;

        StatusEnum(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}

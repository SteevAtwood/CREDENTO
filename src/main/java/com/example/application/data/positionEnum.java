package com.example.application.data;

public class positionEnum {
    public enum PositionEnum {

        mainUnderwriter("Главный андеррайтер"),
        underwriter("Андеррайтер"),
        supervisingUOPBemployee("Главный УОПБ сотрудник"),
        UOPBemployee("УОПБ сотрудник"),
        supervisingDAemployee("Главный ДА сотрудник"),
        DAemployee("ДА сотрудник");

        private final String displayName;

        PositionEnum(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

}

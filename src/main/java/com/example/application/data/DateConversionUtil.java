package com.example.application.data;

import java.sql.Date;
import java.time.LocalDate;

public class DateConversionUtil {
    public static LocalDate toLocalDate(Date date) {
        return (date == null) ? null : date.toLocalDate();
    }
}

package com.alvarozarza.basf.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static Integer getYearFromDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyyMMdd" );
        LocalDate localDate = LocalDate.parse(date , formatter );
        return localDate.getYear();
    }
}

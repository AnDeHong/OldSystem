package com.example.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class DateUtils {

    public static Date dateFormat(String date) throws ParseException {
        Instant instant = Instant.parse(date);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = localDateTime.format(formatter);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(formattedDate);
    }

    public static String dateFormat2(Date date) throws ParseException {
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return localDateTime.format(formatter);
    }

//    public static Date dateFormat(String date) {
//        Instant instant = Instant.parse(date);
//        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formattedDate = localDateTime.format(formatter);
//        return (Date) formatter.parse(formattedDate);
//    }

    public static Date dateFormat1(String datetr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = dateFormat.parse(datetr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}

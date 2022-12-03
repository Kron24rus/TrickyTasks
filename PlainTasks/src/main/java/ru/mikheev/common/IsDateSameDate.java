package ru.mikheev.common;

import java.util.Date;
import java.util.HashMap;

public class IsDateSameDate {

    public static void main(String[] args) {
        Date date = new Date();

        HashMap<Date, String> hashMap = new HashMap<>();

        hashMap.put(date, "StringValue");

        System.out.println(hashMap.get(date));
        date.setYear(1995); // date хранит время в миллисекундах и hasCode считается от этого значения
        System.out.println(hashMap.get(date));
    }
}

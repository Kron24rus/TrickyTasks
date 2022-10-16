package ru.mikheev.executors;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorsGivenTask {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String[] strings = getStringsFromServer();
        //мы здесь фильтруем на null т.к StringBuffer всё приведет к 'null' а Java 11+ так что можно пользовать Optional
        List<String> result = Arrays.stream(strings).filter(s -> !s.equals("null")).toList();
        result.forEach(System.out::println);
    }

    public static String[] getStringsFromServer() throws ExecutionException, InterruptedException {

        //Потокобезопасный сборщик строк, у него все методы помечены аннотацией synchronized
        StringBuffer buffer = new StringBuffer();

        //Создаётся пул потоков с 1 потоком а на вторую задачу не создаётся новый
        //создание пула потоков тяжёлая задача поэтому нужно переиспользовать уже созданный
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future<?> future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                buffer.append(getStringFromServer("string1")).append(",");
            }
        });
        //Мы блокируемся в этом месте до получения результата
        future.get();

        //Здесь нужно было бы переиспользовать ранее созданный пул потоков а не создавать новый
        Executors.newFixedThreadPool(1).execute(new Runnable() {
            @Override
            public void run() {
                buffer.append(getStringFromServer("string2"));
                buffer.append(",");
            }
        });


        //Не гарантируется что код в execute() отработает до return т.к execute отдаёт задачу потоку которая будет
        //выполнена КТТС а значит в большинстве случаев мы будем возвращать только результат который получили во
        //future. Так-же executorService повиснут после выполнения программы т.к не было вызова shutdown()
        return buffer.toString().split(",");
    }

    public static String getStringFromServer(String s) {
        try {
            return server(s);
        } catch (Exception e) {
            return null;
        }
    }

    public static String server(String s) {
        if (isErrorMustOccurs()) {
            throw new IllegalStateException();
        } else {
            return s;
        }
    }

    private static boolean isErrorMustOccurs() {
        return new Random().nextInt(0, 20) == 13;
    }
}

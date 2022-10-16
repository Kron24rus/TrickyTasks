package ru.mikheev.executors;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class ExecutorsImproved {

    public static void main(String[] args) {
        String[] strings = getStringsFromServer();
        //т.к мы вернули Optional больше нет необходимости фильтровать
        List<String> result = Arrays.stream(strings).toList();
        result.forEach(System.out::println);
    }

    public static String[] getStringsFromServer() {
        StringBuffer buffer = new StringBuffer();
        //Улучшение можно запустить асинхронно CompletableFuture и синхранизируемся только уже на моменте сборки результата
        //в итоге мы сходим на сервер параллельно за 2 данными в 2 потока и только потом заблокируемся
        CompletableFuture<Optional<String>> result1 = CompletableFuture.supplyAsync(() -> getStringFromServer("string1"));
        CompletableFuture<Optional<String>> result2 = CompletableFuture.supplyAsync(() -> getStringFromServer("string2"));
        result1.thenAcceptBoth(result2, (s1, s2) -> {
            s1.ifPresent(s -> buffer.append(s).append(","));
            s2.ifPresent(s -> buffer.append(s).append(","));
        });
        return buffer.toString().split(",");
    }

    public static Optional<String> getStringFromServer(String s) {
        try {
            return Optional.ofNullable(server(s));
        } catch (Exception e) {
            return Optional.empty();
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

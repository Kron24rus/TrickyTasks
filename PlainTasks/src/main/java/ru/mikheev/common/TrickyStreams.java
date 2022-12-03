package ru.mikheev.common;

import java.util.Arrays;

public class TrickyStreams {

    public static void main(final String[] args) {
        final int result = Arrays.stream(new int[] {4, 2, 3, 2, 1})
                .filter(TrickyStreams::f) // stateless intermediate operation - работает с 1 элементом и пускает в новый стрим дальше.
                .map(TrickyStreams::m)
                .sorted() // stateful intermediate operation. - ему нужен все элементы стрима чтобы продолжить работу.
                .filter(TrickyStreams::f)
                .peek(TrickyStreams::p)
                .reduce(0, (a, b) -> a + b); // terminal operation - завершает выполнение стрима.

        System.out.println(result);
    }

    private static boolean f(final int x) {
        System.out.println("f: " + x);
        return x > 1;
    }

    private static int m(final int x) {
        System.out.println("m: " + x);
        return x - 1;
    }

    private static void p(final int x) {
        System.out.println("p: " + x);
    }
}

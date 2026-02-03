package dev.training;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Tablo {

    static Map<Long, Long> mults = Map.of(
            1L, 3L,
            2L, 2L,
            4L, 1L,
            8L, 0L,

            3L, 4L,
            6L, 3L,
            7L, 2L,
            9L, 1L,

            0L, 0L,
            5L, 0L
    );
    static Map<Long, Long> corr = Map.of(
            1L, 8L,
            2L, 8L,
            4L, 8L,
            8L, 8L,

            3L, 18L,
            6L, 18L,
            7L, 18L,
            9L, 18L,

            0L, 0L,
            5L, 0L
    );
    static Map<Long, Long> zz = Map.of(
            0L, 8L,
            1L, 11L,
            2L, 12L,
            3L, 9L
    );
    static Map<Long, Long> fix24 = Map.of(
            0L, 0L,
            1L, 8L,
            2L, 14L,
            3L, 16L
    );


    static void test() {
        test_formula(1, 0, 1);
        test_formula(1, 1, 2);
        test_formula(1, 2, 4);
        test_formula(1, 3, 8);

        test_formula(2, 0, 2);
        test_formula(2, 1, 4);
        test_formula(14, 25, 128);

        test_formula(0, 0, 0);
        test_formula(15, 0, 15);
        test_formula(15, 1, 20);
        test_formula(15, 2, 20);
        test_formula(100, 111, 100);

        for (int j = 0; j< 50; j++) {
            for (int i = 0; i <= 20; i++) {
                test_formula(i, j, -1);
            }
        }


//        test_formula(1000000000L, 11, 1000000000L);
//        test_formula(1000000000L, 1, 1000000000L);
//        test_formula(1000000000L, 0, 1000000000L);
//
//        test_formula(1000000000L - 1, 0, 1000000000L - 1);
//        test_formula(100000001L, 1000000000L, 0);
//        test_formula(1000000000L - 1, 1000000000L, 5000000014L);

        System.out.print("Testing done");
    }

    private static void test_formula(long start, long steps, long i2) {
        BigInteger r = formula(start, steps);
        BigInteger expected = getnum(start, steps);
        String str = "formula(" + start + "," + steps + ") = " + r + ", expected " + expected;
        String msg = r.equals(expected) ? "OK" : "FAIL";
        System.out.println(str + " : " + msg);
    }

    static long corrS(long start) {
        return corr.get(start % 10);
    }

    static long zz0(long k) {
        return mults.get(k % 10);
    }

    static BigInteger formula(long start, long step) {

        if (start % 10 == 0) return BigInteger.valueOf(start);
        if (start % 5 == 0 && step == 0) return BigInteger.valueOf(start);
        if (start % 5 == 0) return BigInteger.valueOf(start + 5);

        if (step < 20) return getnum(start, step);

        long ssH = step - mults.get(start % 10);

        long res =
                ssH / 4 * 20
                        + corr.get(start % 10)
                        + fix24.getOrDefault(ssH % 4, 0L);
        BigInteger resBig = BigInteger.valueOf(ssH);
        resBig = resBig.divide(BigInteger.valueOf(4));
        resBig = resBig.multiply(BigInteger.valueOf(20));
        resBig = resBig.add(BigInteger.valueOf(corr.get(start % 10)));
        resBig = resBig.add(BigInteger.valueOf(fix24.getOrDefault(ssH % 4, 0L)));

        return resBig;
    }

    static BigInteger getnum(long start, long steps) {

        if (start % 10 == 0) return BigInteger.valueOf(start);
        if (start % 5 == 0 && steps == 0) return BigInteger.valueOf(start);
        if (start % 5 == 0) return BigInteger.valueOf(start + 5);

        BigInteger current = BigInteger.valueOf(start);
        for (long i = 0; i < steps; i++) {
            long lastDigit = current.mod(BigInteger.TEN).longValue();
            current = current.add(BigInteger.valueOf(lastDigit));
        }

        return current;
    }

    public static void main(String[] args) throws Exception {
        test();
//        main1(args);
        main0(args);
    }

    public static void main1(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] lineParts = reader.readLine().split(" ");
        long start = Long.parseLong(lineParts[0]);
        long steps = Long.parseLong(lineParts[1]);
        BigInteger result = formula(start, steps);
        writer.write(String.valueOf(result));
        writer.newLine();

        reader.close();
        writer.close();
    }

    public static void main0(String[] args) throws Exception {

//        prlongLine(0);
//        prlongLine(5);

        System.out.println("corr:" + corr);
        System.out.println("mults:" + mults);
        System.out.println("zz:" + zz);

        for (long start = 1; start <= 25; start++) {
            prlongLine(start);
        }

    }

    private static void prlongLine(long start) {
        // leading spaces
        String leadingSpaces = "   ".repeat((int) start);
        StringBuilder line = new StringBuilder();
        StringBuilder stepLine = new StringBuilder();
        StringBuilder line1 = new StringBuilder();
        StringBuilder minus = new StringBuilder();
        StringBuilder FF = new StringBuilder();
        StringBuilder help0 = new StringBuilder();
        StringBuilder oo1 = new StringBuilder();

        List<Map<String, BigInteger>> elements = new ArrayList<>();
        for (long step = 0; step <= 500; step++) {

            Map<String, BigInteger> e = new HashMap<>();
            e.put("01.step", BigInteger.valueOf(step));
//            e.put("02.fN0", getnum(start, step) - corrS(start));
            long ssH = step - mults.get(start % 10);
            e.put("11.sSH", BigInteger.valueOf(ssH));


//            e.put("11.zs", ssH / 4 * 20 + corr.get(start % 10)
//                    + fix24.getOrDefault(ssH % 4, 0L));

            var gnum = getnum(start, step);
            var form = formula(start, step);
            e.put("getnum", gnum);
            e.put("formlL", form);
            e.put("z_diff", gnum.subtract(form));

            elements.add(e);

            /*
            long r = getnum(start, step);
            stepLine.append(String.format("%3d", step));
            line1.append(String.format("%3d", r % 4));
            minus.append(String.format("%3d", r - start));
            line.append(String.format("%3d", r));
            FF.append(String.format("%3d", formula(start, step)));
            help0.append(String.format("%3d", step - mults.get(start % 10)));
            oo1.append(String.format("%3d", r - corrS(start)));
             */
        }

//        prlongMap(elements);
        prlongMapTranspose(elements, start);

        System.out.println();
    }


    static void prlongMapTranspose(List<Map<String, BigInteger>> elements, long start) {
        if (elements.isEmpty()) return;

        List<String> keys = new ArrayList<>(elements.get(0).keySet());

        // Print header
//        System.out.print("     ");
//        for (Map<String, BigInteger> element : elements) {
//            System.out.print(String.format("%15s", element.get("01.step")));
//        }
//        System.out.println();

        // Print each key row
        Collections.sort(keys);
        for (String key : keys) {
//            if (key.equals("01.step")) continue; // Skip step row as it's printed in header
            System.out.print(String.format("%10s ", key));
            for (Map<String, BigInteger> element : elements) {
                System.out.print(String.format("%5s", element.get(key)));
            }
            System.out.println();
        }
    }


}

package dev.training;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MyStreams {

    public static void main(String[] args) throws Exception {

        List<Person> data = List.of(
                new Person("Vadim", "NN", 44, List.of("Java", "C++", "Kotlin")),
                new Person("Sergey", "Bor", 23, List.of("Java", "kotlin")),
                new Person("Lena", "NN", 19, List.of("C++")),
                new Person("Katya", "MSK", 15, List.of("HelloWorld"))
        );

        Map<String, Person> dataMap =
                data.stream()
                        .map(
                                p -> Map.entry(p.name(), p)
                        )
                        .collect(
                                Collectors.toMap(
                                        e -> e.getKey(),
                                        e -> e.getValue()
                                )
                        );
        System.out.println(
                dataMap
        );

        var d2 = dataMap.entrySet().stream()
                .flatMap(
                        e -> {
                            return e.getValue().tags().stream().map(aa -> {
                                return aa.toLowerCase();
                            });
                        }
                )
                .collect(
                        //Collectors.toList()
                        Collectors.groupingBy(
                                elem -> elem,
                                Collectors.counting()
                        )

                );
        System.out.println(d2);

        Map<String, List<Person>> map; // по языкам программирования

        var d = data.stream()
                .flatMap(
                        p -> {
                            return p.tags.stream().map(tag -> {
                                return Map.entry(tag, p);
                            });
                        }
                )
                .collect(
                        Collectors.groupingBy(
                                e -> e.getKey().toLowerCase(),
                                Collectors.mapping(entry -> entry.getValue().name(),
                                        Collectors.toList()
                                )
                        )
                );
        ;
        System.out.println(d);

        var dd = data.stream()
                .sorted((p1, p2) -> p1.name().compareTo(p2.name()))
                .collect(
                        Collectors.mapping (p -> p.name(),
                        Collectors.mapping(
                                s -> s.toUpperCase(),
                                Collectors.toList()
                        )
                ));
        System.out.println(dd);


    }

    record Person(String name, String city, int age, List<String> tags) {
    }

}

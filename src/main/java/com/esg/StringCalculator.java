package com.esg;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringCalculator {
    private static final Pattern DELIMITER_PATTERN = Pattern.compile("\\[([^]]+)]");
    private static final String DEFAULT_DELIMITER = "[,\n]";

    public int add(String numbers) {
        String delimiter = DEFAULT_DELIMITER;
        if (numbers.startsWith("//")) {
            int end = numbers.indexOf("\n");
            String delimiterExpr = numbers.substring(2, end);
            if (delimiterExpr.startsWith("[")) {
                delimiter = DELIMITER_PATTERN.matcher(delimiterExpr).results()
                        .map(m -> m.group(1))
                        .map(Pattern::quote)
                        .collect(Collectors.joining("|"));
            } else {
                delimiter = Pattern.quote(delimiterExpr);
            }
            numbers = numbers.substring(end+1);
        }

        List<Integer> integers = Stream.of(numbers.split(delimiter))
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .filter(n -> n <= 1000)
                .toList();

        if (integers.stream().anyMatch(n -> n < 0)) {
            String negatives = integers.stream()
                    .filter(n -> n < 0)
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            throw new IllegalArgumentException("Negatives not allowed: " + negatives);
        }

        return integers.stream().mapToInt(Integer::intValue).sum();
    }
}

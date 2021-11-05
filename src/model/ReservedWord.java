package model;

import java.util.regex.Pattern;

public class ReservedWord {

    public static Pattern pattern = Pattern.compile("\\b(integer_collection|integer|while|verify|print|end|collection|read|char|string|float|boolean)\\b");

    public static Pattern getPattern() {
        return pattern;
    }
}

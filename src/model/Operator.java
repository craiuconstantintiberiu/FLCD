package model;

import java.util.regex.Pattern;

public class Operator {

    public static Pattern pattern = Pattern.compile("\\+|\\-|\\*|\\/|<|<=|=|>|>=|%|!=|==|;|\\[|\\]|\\.");

    public static Pattern getPattern() {
        return pattern;
    }
}

package model;

import java.util.List;
import java.util.regex.Pattern;

public class Miscellaneous {

    private static String digit = "[0-9]";
    private static String nonZeroDigit = "[1-9]";
    private static final Pattern digitPattern = Pattern.compile(digit);

    private static String letter = "[a-z]|[A-Z]";
    private static String character = ".";

    private static String stringConstant = "\"(([a-z]|[A-Z])|([0-9])|(\\(|\\)|\\{|\\}| ))*\""; //letter | digit |separator
    private static Pattern stringConstantPattern = Pattern.compile(stringConstant);

    private static String floats = "(\\b(0|([1-9][0-9]*))\\.[0-9]*)\\b";//0.number or 121312.number
    private static Pattern floatPattern = Pattern.compile(floats);

    private static String integer = "\\b((0|(\\+|\\-)[1-9]+)|[1-9][0-9]*)\\b";
    private static Pattern integerPattern = Pattern.compile(integer);


    public static Pattern getStringConstantPattern() {
        return stringConstantPattern;
    }

    public static Pattern getFloatPattern() {
        return floatPattern;
    }

    public static Pattern getIntegerPattern() {
        return integerPattern;
    }

    public static List<Pattern> getMiscellaneousPatterns(){
        return List.of(stringConstantPattern, floatPattern, integerPattern);
    }
}

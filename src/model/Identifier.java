package model;

import java.util.regex.Pattern;

public class Identifier {

    private static String identifier = "([a-z]|[A-Z])([a-z]|[A-Z]|[0-9]|_)*";
    private static Pattern identifierPattern = Pattern.compile(identifier);

    public static Pattern getIdentifierPattern() {
        return identifierPattern;
    }

    public static String getIdentifier() {
        return identifier;
    }
}

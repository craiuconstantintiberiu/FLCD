package model;

import java.util.regex.Pattern;

public class Separator {

    public static Pattern pattern = Pattern.compile("\\(|\\)|\\{|\\}");
    //did not add space as separtor

    public static Pattern getPattern() {
        return pattern;
    }
}

import model.*;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scanner {

    SymbolTable symbolTableIdentifiers = new SymbolTable(5000,400,400);
    SymbolTable symbolTableConstants = new SymbolTable(5000,400,400);
    ProgramInternalForm programInternalForm = new ProgramInternalForm();

    public void scanFileAndCreatePifAndST(String filename) throws IOException {


        List<Pattern> patterns = getAllPatterns();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String currentLine = reader.readLine();
        int numberOfLinesScanned = 1;

        while (currentLine != null) {
            System.out.println("Current line:"+currentLine);

            currentLine = currentLine.strip();
            int charactersLeft = currentLine.length();


            while (charactersLeft != 0) {

                boolean foundSomething = false;
                for (var pattern : patterns) {
                    if (!foundSomething) {
                        var correspondingMatcher = pattern.matcher(currentLine);
                        if (isPatternFound(correspondingMatcher)) {
                            if (isFoundAtStringStart(correspondingMatcher)) {
                                      foundSomething = true;
                                    /*System.out.println("Found pattern: " + correspondingMatcher.group() + "    -corresponds to:" + pattern.toString());*/
                                    currentLine = currentLine.substring(correspondingMatcher.end());
                                    charactersLeft = currentLine.length();
                                    actAccordinglyToType(pattern, getMatchedString(correspondingMatcher));

                            }
                        }
                    }
                }
           /*     System.out.println("Done. Stripping line.");*/
                currentLine = currentLine.strip();

                if(!foundSomething){
                    System.out.println("Error. Token does not match any patterns on line " + numberOfLinesScanned);
                    System.exit(0);
                }



            }
            numberOfLinesScanned++;
            currentLine = reader.readLine();
        }
        System.out.println("Lexically correct.");
        writeFile(programInternalForm,symbolTableIdentifiers,symbolTableConstants);
    }

    private void writeFile(ProgramInternalForm programInternalForm, SymbolTable symbolTableIdentifiers, SymbolTable symbolTableConstants) throws IOException {
        File pif = new File("PIF.out");
        File st = new File("ST.out");
        FileOutputStream fos = new FileOutputStream(pif);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        bw.write("PIF");
        bw.newLine();
        for (var x : programInternalForm.getElementsAndPositionsInSymbolTable()) {
            bw.write(x.getFirst()+"  " + x.getSecond()) ;
            bw.newLine();
        }
        bw.close();

        FileOutputStream fos2 = new FileOutputStream(st);

        BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(fos2));

        bw2.write("ST Identifiers");
        bw2.newLine();
        for (var x : symbolTableIdentifiers.getAllElements()) {
            bw2.write(x.getFirst()+"  " + x.getSecond()) ;
            bw2.newLine();
        }

        bw2.write("ST Constants");
        bw2.newLine();
        for (var x : symbolTableConstants.getAllElements()) {
            bw2.write(x.getFirst()+"  " + x.getSecond()) ;
            bw2.newLine();
        }
        bw2.close();
    }

    private List<Pattern> getAllPatterns() {
        List<Pattern> otherPatterns = new java.util.ArrayList<>(List.of(ReservedWord.getPattern(),
                Operator.getPattern(),
                Separator.getPattern(),
                Identifier.getIdentifierPattern()));
        List<Pattern> patterns = new LinkedList<>(Miscellaneous.getMiscellaneousPatterns());
        otherPatterns.addAll(patterns);
        return otherPatterns;
    }


    private List<Pattern> getPatternsThatDoNotNeedToBeAddedToSymbolTable(){
       return List.of(Operator.getPattern(), Separator.getPattern(), ReservedWord.getPattern());
    }

    private List<Pattern> getPatternsThatAreConstants(){
        return Miscellaneous.getMiscellaneousPatterns();
    }


    private void actAccordinglyToType(Pattern pattern, String foundString) {
        if(getPatternsThatDoNotNeedToBeAddedToSymbolTable().contains(pattern)){
            programInternalForm.addElement(foundString, -1);
            return;
        }
        if(getPatternsThatAreConstants().contains(pattern)){
            int positionToAddInPif = symbolTableConstants.add(foundString);
            programInternalForm.addElement("constant", positionToAddInPif);
        }
        if(Identifier.getIdentifierPattern().equals(pattern)){
            int positionToAddInPif = symbolTableIdentifiers.add(foundString);
            programInternalForm.addElement("identifier", positionToAddInPif);
        }

    }

    private boolean isPatternFound(Matcher matcher){
        return matcher.find();
    }

    private boolean isFoundAtStringStart(Matcher matcher) {
        return matcher.start() == 0;
    }

    private String getMatchedString(Matcher matcher) {
        return matcher.group();
    }
}

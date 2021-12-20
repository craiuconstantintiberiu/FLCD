import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ParserOutput {
    Parser parser;
    List<String> alpha;
    List<String> beta;
    List<String> phi;


    public ParserOutput(Parser parser) {
        this.parser = parser;
    }

    public boolean verifySequence(List<String> words){
        try {
            alpha = new ArrayList<>(List.of("0"));
            beta = new ArrayList<>(words);
            beta.add("$");
            phi = new ArrayList<>();
            while (true) {
                var index = Integer.parseInt(alpha.get(alpha.size() - 1));
                var action = parser.table.get(index).get("action");
                if (Objects.equals(action, "shift")) {
                    doShift();
                } else if (Objects.equals(action, "accept")) {
                    System.out.println("Sequence is accepted.");
                    return true;
                } else if (action!=null) {
                    doReduce();
                } else {
                    throw new RuntimeException();
                }
            }
        } catch (Exception e){
            System.out.println("Sequence is not accepted.");
            System.out.println("Currently: alpha:"+alpha+"\n beta "+beta+"\n phi" + phi);
            return false;
        }
    }

    private void doShift() {
        var headBeta = beta.get(0);
        var sM = alpha.get(alpha.size()-1);//sM last element of alpha
        //if goto(last element alpha, first element beta) is sj, then put head of beta and sj to end of alpha
        var sj = parser.table.get(Integer.parseInt(sM)).get(headBeta);
        alpha.add(headBeta);
        alpha.add(sj);
        beta.remove(0);
    }

    private void doReduce() {
        var sM = alpha.get(alpha.size()-1);//sM is last element of Alpha
        var act = Integer.parseInt(parser.table.get(Integer.parseInt(sM)).get("action"));//act is action corresponding to last element, it is reduce
        //action is mapped to an integer (e.g red 1 marking the production)
        var move = parser.productions.get(act);//this is A, from A->xM-p+1...xM; goto(sm-p, A) = sJ
        var start = move.first;
        var result = move.second;
        var indexSecondPart = result.size()-1;
        var indexAlpha = alpha.size()-1;
        while(indexSecondPart >=0){
            //go from xM to xm-P+1
            indexAlpha-=2;
            indexSecondPart--;
        }
        this.alpha = alpha.subList(0, indexAlpha+1);//remove elements from alpha
        var sJ=parser.table.get(Integer.parseInt(alpha.get(indexAlpha))).get(start);
        this.alpha.add(start);//add start of corresponding production, A
        this.alpha.add(sJ);//add sJ. sJ=goto(sm-p, A). //
        //add index of production corresponding to reduce
        phi.add(0, String.valueOf(act));
    }
}

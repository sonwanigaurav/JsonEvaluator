import java.util.ArrayList;

public class MainDriver {
    public static void main(String args[]) {

        try {
            String[] expressions = {"$color == 'red'", "$mattress.name == 'king' AND $cost == 100.0", "NOT EXISTS $color", "( $cost == 100.0 AND ( $mattress.big == false ) ) OR $size == 100"};

            PostfixEvaluatorJson postfixEvaluatorJson = new PostfixEvaluatorJson();


            //Reading JSONInput file from classpath
            postfixEvaluatorJson.readJSONInputFromFile();
            System.out.println("=================================================================================");
            for(String expression: expressions) {

                System.out.println(expression);

                //Converting Input Infix Expression into Postfix
                ArrayList<String> response = InfixToPostfix.infixToPostfix(expression);

                //evaluatePostfix called
                System.out.println(postfixEvaluatorJson.evaluatePostfixExpression(response));
                System.out.println("=================================================================================");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

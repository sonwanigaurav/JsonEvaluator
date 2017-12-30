import java.util.ArrayList;
import java.util.Stack;

public class InfixToPostfix {

    /**
     * Checking if the input string is belongs to set of operators
     * AND, OR, NOT, EXISTS, ==, (, )
     * @param str
     * @return
     */
    public static boolean isOperator(String str) {
        if (str.equals("==") || str.equals("AND") || str.equals("OR") ||
                str.equals("(") || str.equals(")") || str.equals("NOT")
                || str.equals("EXISTS"))
            return true;
        else return false;
    }

    /**
     * A utility function to return precedence of a given operator
     * Higher returned value means higher precedence
     * @param operator
     * @return
     */
    public static int operatorPrecedence(String operator) {
        switch (operator) {
            case "AND":
            case "OR":
                return 1;
            case "==":
                return 2;
            case "NOT":
                return 3;
            case "EXISTS":
                return 4;
        }
        return -1;
    }

    /**
     * The main method that converts given infix expression
     * to postfix expression.
     * @param exp
     * @return
     * @throws Exception
     */
    public static ArrayList<String> infixToPostfix(String exp) throws Exception {

        String[] parts = exp.split("\\s+");

        ArrayList<String> result = new ArrayList<String>();

        // initializing empty stack
        Stack<String> stack = new Stack();

        for (int i = 0; i < parts.length; ++i) {

            String str = parts[i];
            // If the string is an operand, add it to output.
            if (!isOperator(str))
                result.add(str);

                // If the string is an "(", push it to the stack.
            else if ("(".equalsIgnoreCase(str))
                stack.push(str);

                //  If the string is an ")", pop and output from the stack
                // until an "(" is encountered.
            else if (")".equalsIgnoreCase(str)) {
                while (!stack.isEmpty() && !"(".equalsIgnoreCase(stack.peek()))
                    result.add(stack.pop());

                if (!stack.isEmpty()) {
                    stack.pop();
                } else {
                    throw new Exception("Invalid Expression");
                }

            } else // an operator is encountered
            {
                while (!stack.isEmpty() && operatorPrecedence(str) <= operatorPrecedence(stack.peek()))
                    result.add(stack.pop());
                stack.push(str);
            }

        }
        // pop all the operators from the stack
        while (!stack.isEmpty()) {
            if (stack.peek().equalsIgnoreCase("(") || stack.peek().equalsIgnoreCase(")"))
                throw new Exception("Invalid Expression");
            result.add(stack.pop());
        }

        return result;
    }
}
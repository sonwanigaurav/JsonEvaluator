import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

public class PostfixEvaluatorJson {


    public static JSONObject jsonObject;

    /**
     * Reading the Json file, creating jsonObject
     * @throws Exception
     */
    public static void readJSONInputFromFile() throws Exception {
        try {
            Object obj = new JSONParser().parse(new FileReader("JSONExample.json"));
            jsonObject = (JSONObject) obj;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * passing the key to get value, if exist
     * @param expression
     * @return
     * @throws Exception
     */
    public static String valueOfKey(String expression) throws Exception {
        try {
            String prefix = "$";
            expression = expression.substring(expression.indexOf(prefix) + prefix.length());
            String key = expression.replaceAll("\\s", "");

            String[] nestedFeild = key.split("\\.");

            if (nestedFeild.length <= 1) {
                Object value1 = jsonObject.get(key);

                if (value1 == null)
                    return "false";
                else {
                    String value = String.valueOf(value1);
                    return value;
                }
            } else {
                Object nestedobj = jsonObject.get(nestedFeild[0]);
                if (nestedobj instanceof Map) {
                    Map mattress = ((Map) jsonObject.get(nestedFeild[0]));
                    Iterator<Map.Entry> itr1 = mattress.entrySet().iterator();
                    while (itr1.hasNext()) {
                        Map.Entry pair = itr1.next();
                        if (pair.getKey().equals(nestedFeild[1]))
                            return String.valueOf(pair.getValue());
                        else return "NotThere";
                    }
                }
                if (nestedobj instanceof JSONArray) {
                    JSONArray ja = (JSONArray) jsonObject.get(nestedFeild[0]);
                    Iterator itr2 = ja.iterator();

                    while (itr2.hasNext()) {
                        Iterator<Map.Entry> itr1;
                        itr1 = ((Map) itr2.next()).entrySet().iterator();
                        while (itr1.hasNext()) {
                            Map.Entry pair = itr1.next();
                            if (pair.getKey().equals(nestedFeild[1]))
                                return String.valueOf(pair.getValue());
                            else return "NotThere";
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }

    /**
     * Evaluating Postfix Expression
     * @param exp
     * @return
     * @throws Exception
     */
    public static boolean evaluatePostfixExpression(ArrayList<String> exp) throws Exception {

        Stack stack = new Stack();
        try {
            //System.out.println("stack initialized: " + stack);
            for (int i = 0; i < exp.size(); ++i) {
                // If the string is an operand, push it to the stack.
                if (!InfixToPrefix.isOperator(exp.get(i)))
                    stack.push(exp.get(i));

                    //  If the string is an binary operator, pop two
                    // elements from stack
                    // If the string is an unary operator, pop one
                    // element from stack

                else {
                    String valuegiven = (String) stack.pop();
                    String val1 = valuegiven.replaceAll("[\\s,']", "");

                    String val2 = "";
                    if (!("NOT".equalsIgnoreCase(exp.get(i)) || "EXISTS".equalsIgnoreCase(exp.get(i)))) {
                        val2 = (String) stack.pop();
                    }

                    if ("==".equals(exp.get(i))) {
                        val2 = valueOfKey(val2);
                    } else if ("EXISTS".equals(exp.get(i))) {
                        val1 = valueOfKey(val1);
                    }

                    switch (exp.get(i)) {
                        case "==": {
                            if (val2.equals(val1)) {
                                stack.push("true");
                                break;
                            } else stack.push("false");
                            break;
                        }
                        case "AND": {
                            if (Boolean.valueOf(val2) && Boolean.valueOf(val1)) {
                                stack.push("true");
                                break;
                            } else stack.push("false");
                            break;
                        }
                        case "OR": {
                            if (Boolean.valueOf(val2) || Boolean.valueOf(val1)) {
                                stack.push("true");
                                break;
                            } else stack.push("false");
                            break;
                        }
                        case "NOT": {
                            if ("false".equals(val1))
                                stack.push("true");
                            else
                                stack.push("false");
                            break;
                        }
                        case "EXISTS": {
                            if ("false".equals(val1))
                                stack.push("false");
                            else
                                stack.push("true");
                            break;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return Boolean.valueOf(stack.pop().toString());
    }
}

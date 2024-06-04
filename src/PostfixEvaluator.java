import java.util.Deque;
import java.util.LinkedList;

class PostfixEvaluator {

    private String expression;
    private String postfix;
    private final Deque<Character> operatorStack;

    public PostfixEvaluator(String expression) {
        this.expression = expression.trim().replaceAll("\\s+", "");
        this.operatorStack = new LinkedList<>();

        generateNotation();
    }

    public String getExpression() {

        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
        generateNotation();
    }

    private void generateNotation() {
        StringBuilder aux = new StringBuilder();
        for (char i : expression.toCharArray()) {
            if (Character.isDigit(i)) {
                aux.append(i);
            } else if (isOperator(i)) {
                while (!operatorStack.isEmpty() && hasHigherPrecedence(operatorStack.peek(), i)) {
                    aux.append(operatorStack.pop());
                }
                operatorStack.push(i);
            } else if (i == '(') {
                operatorStack.push(i);
            } else if (i == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    aux.append(operatorStack.pop());
                }
                operatorStack.pop();
            }
        }

        while (!operatorStack.isEmpty()) {
            aux.append(operatorStack.pop());
        }

        postfix = aux.toString();
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    private static int getPrecedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return 0;
        }
    }

    private static boolean hasHigherPrecedence(char op1, char op2) {
        int precedence1 = getPrecedence(op1);
        int precedence2 = getPrecedence(op2);

        if (precedence1 == precedence2) {
            return op1 != '^';
        }

        return precedence1 > precedence2;
    }

    public static int evaluateNotation(String postfix) {
        Deque<Integer> operandStack = new LinkedList<>();

        for (char i : postfix.toCharArray()) {
            if (Character.isDigit(i)) {
                operandStack.push(Character.getNumericValue(i));
            } else {

                int operand2 = operandStack.pop();
                int operand1 = operandStack.pop();
                int aux = performOperation(i, operand1, operand2);
                operandStack.push(aux);
            }
        }


        return operandStack.pop();
    }

    private static int performOperation(char operator, int operand1, int operand2) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return operand1 / operand2;
            case '^':
                return (int) Math.pow(operand1, operand2);
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    public String getPostfix() {
        return postfix;
    }
}
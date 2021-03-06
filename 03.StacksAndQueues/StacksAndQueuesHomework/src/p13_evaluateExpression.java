import java.util.ArrayDeque;
import java.util.Scanner;

public class p13_evaluateExpression {
    static ArrayDeque<String> operatorStack = new ArrayDeque<>();
    static ArrayDeque<String> outputQueue = new ArrayDeque<>();

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String[] tokens = in.nextLine().split(" ");
        for (int i = 0; i < tokens.length; i++) {
            String currentToken = tokens[i];

            if (isInteger(currentToken, 10)) {  // if it is a number to the queue
                outputQueue.addLast(currentToken);
            } else if (isOperator(currentToken)) {    // operator
                popFromStackAddToQueue(currentToken);
            } else {  // then bracket
                if (currentToken.equals("("))
                    operatorStack.addFirst(currentToken);
                else
                    toLeftBracket();
            }
        }

        while (operatorStack.size() > 0) {
            outputQueue.addLast(operatorStack.removeFirst());
        }

        ArrayDeque<Double> stack = new ArrayDeque<>();
        while (outputQueue.size() > 0) {
            String currentToken = outputQueue.removeFirst();

            if (isInteger(currentToken, 10)) {
                stack.addFirst(Double.parseDouble(currentToken));
            } else { // + - * /
                if (currentToken.equals("+")) {
                    double popped = stack.removeFirst();
                    double toAdd = stack.removeFirst();

                    stack.addFirst(toAdd + popped);
                } else if (currentToken.equals("-")) {
                    double popped = stack.removeFirst();
                    double toAdd = stack.removeFirst();

                    stack.addFirst(toAdd - popped);
                } else if (currentToken.equals("*")) {
                    double popped = stack.removeFirst();
                    double toAdd = stack.removeFirst();

                    stack.addFirst(toAdd * popped);
                } else if (currentToken.equals("/")) {
                    double popped = stack.removeFirst();
                    double toAdd = stack.removeFirst();

                    stack.addFirst(toAdd / popped);
                }
            }
        }

        System.out.printf("%.2f", stack.peekFirst());
    }

    private static void popFromStackAddToQueue(String currentToken) {
        while (true) {
            if (operatorStack.size() == 0) {
                operatorStack.addFirst(currentToken);

                return;
            }

            switch (currentToken) {
                case "+":
                case "-":
                    if (operatorStack.peekFirst().equals("(")) {
                        operatorStack.addFirst(currentToken);
                        return;
                    } else {
                        outputQueue.addLast(operatorStack.removeFirst());
                    }
                    break;
                case "*":
                case "/":
                    if (operatorStack.peekFirst().equals("*") || operatorStack.peekFirst().equals("/")) {
                        outputQueue.addLast(operatorStack.removeFirst());
                    }
                    else {
                        operatorStack.addFirst(currentToken);
                        return;
                    }

                    break;
            }
        }
    }

    private static void toLeftBracket() {
        while (!operatorStack.peekFirst().equals("(")) {
            outputQueue.addLast(operatorStack.removeFirst());
        }

        operatorStack.removeFirst();
    }

    private static boolean isOperator(String currentToken) {
        if (currentToken.equals("+") || currentToken.equals("-") || currentToken.equals("*") || currentToken.equals("/"))
            return true;

        return false;
    }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
}

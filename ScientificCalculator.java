import java.util.Scanner;

public class ScientificCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Select an option:");
            System.out.println("1. Basic Arithmetic Operations");
            System.out.println("2. Scientific Functions");
            System.out.println("3. Exit");

            int option = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character

            switch (option) {
                case 1:
                    basicArithmetic(scanner);
                    break;
                case 2:
                    scientificFunctions(scanner);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void basicArithmetic(Scanner scanner) {
        while (true) {
            System.out.println("Enter an expression or 'back' to return to the main menu:");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("back")) {
                return;
            }

            try {
                double result = evaluateExpression(input);
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println("Error: Invalid expression");
            }
        }
    }

    private static void scientificFunctions(Scanner scanner) {
        while (true) {
            System.out.println("Select a scientific function:");
            System.out.println("1. Square Root (sqrt)");
            System.out.println("2. Sine (sin)");
            System.out.println("3. Cosine (cos)");
            System.out.println("4. Tangent (tan)");
            System.out.println("5. Arcsine (asin)");
            System.out.println("6. Arccosine (acos)");
            System.out.println("7. Arctangent (atan)");
            System.out.println("8. Back to main menu");

            int option = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character

            switch (option) {
                case 1:
                    calculateSquareRoot(scanner);
                    break;
                case 2:
                    calculateSine(scanner);
                    break;
                case 3:
                    calculateCosine(scanner);
                    break;
                case 4:
                    calculateTangent(scanner);
                    break;
                case 5:
                    calculateArcSine(scanner);
                    break;
                case 6:
                    calculateArcCosine(scanner);
                    break;
                case 7:
                    calculateArcTangent(scanner);
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static double evaluateExpression(String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean isDigitChar(int c) {
                return c >= '0' && c <= '9';
            }

            boolean isSpaceChar(int c) {
                return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
            }

            void skipSpaces() {
                while (ch > 0 && isSpaceChar(ch)) nextChar();
            }

            double parseNumber() {
                skipSpaces();
                StringBuilder sb = new StringBuilder();
                while (ch > 0 && (isDigitChar(ch) || ch == '.')) {
                    sb.append((char) ch);
                    nextChar();
                }
                return Double.parseDouble(sb.toString());
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    skipSpaces();
                    if (ch == '+') {
                        nextChar();
                        x += parseTerm();
                    } else if (ch == '-') {
                        nextChar();
                        x -= parseTerm();
                    } else {
                        return x;
                    }
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    skipSpaces();
                    if (ch == '/') {
                        nextChar();
                        x /= parseFactor();
                    } else if (ch == '*') {
                        nextChar();
                        x *= parseFactor();
                    } else {
                        return x;
                    }
                }
            }

            double parseFactor() {
                skipSpaces();
                if (ch == '+') {
                    nextChar();
                    return parseFactor();
                }
                if (ch == '-') {
                    nextChar();
                    return -parseFactor();
                }
                double x;
                int startPos = this.pos;
                if (ch == '(') {
                    nextChar();
                    x = parseExpression();
                    if (ch != ')')
                        throw new RuntimeException("Unmatched brackets");
                    nextChar();
                } else if (isDigitChar(ch) || ch == '.') {
                    x = parseNumber();
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
                if (ch == '^') {
                    nextChar();
                    x = Math.pow(x, parseFactor());
                }
                return x;
            }
        }.parse();
    }

    private static void calculateSquareRoot(Scanner scanner) {
        System.out.println("Enter a number:");
        double num = scanner.nextDouble();
        double result = Math.sqrt(num);
        System.out.println("Square Root: " + result);
    }

    private static void calculateSine(Scanner scanner) {
        System.out.println("Enter an angle (in degrees):");
        double angle = scanner.nextDouble();
        double result = Math.sin(Math.toRadians(angle));
        System.out.println("Sine: " + result);
    }

    private static void calculateCosine(Scanner scanner) {
        System.out.println("Enter an angle (in degrees):");
        double angle = scanner.nextDouble();
        double result = Math.cos(Math.toRadians(angle));
        System.out.println("Cosine: " + result);
    }

    private static void calculateTangent(Scanner scanner) {
        System.out.println("Enter an angle (in degrees):");
        double angle = scanner.nextDouble();
        double result = Math.tan(Math.toRadians(angle));
        System.out.println("Tangent: " + result);
    }

    private static void calculateArcSine(Scanner scanner) {
        System.out.println("Enter a value:");
        double value = scanner.nextDouble();
        double result = Math.toDegrees(Math.asin(value));
        System.out.println("Arcsine: " + result);
    }

    private static void calculateArcCosine(Scanner scanner) {
        System.out.println("Enter a value:");
        double value = scanner.nextDouble();
        double result = Math.toDegrees(Math.acos(value));
        System.out.println("Arccosine: " + result);
    }

    private static void calculateArcTangent(Scanner scanner) {
        System.out.println("Enter a value:");
        double value = scanner.nextDouble();
        double result = Math.toDegrees(Math.atan(value));
        System.out.println("Arctangent: " + result);
    }
}

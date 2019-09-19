package io.javaclasses.shuntingyard;


import io.javaclasses.shuntingyard.exception.ImproperParenthesesException;
import io.javaclasses.shuntingyard.token.LeftParenthesesToken;
import io.javaclasses.shuntingyard.token.Operator;
import io.javaclasses.shuntingyard.token.RightParenthesesToken;
import io.javaclasses.shuntingyard.token.Token;
import io.javaclasses.shuntingyard.token.operands.Real;
import io.javaclasses.shuntingyard.token.operands.Variable;

import java.util.ArrayList;
import java.util.List;

class ExpressionTokenizer {
    // A short alias for the OperationMap's instance.
    private static final OperatorMap MAP = OperatorMap.INSTANCE;
    // Characters that are valid for operators
    private static final String OP_CHARS = "+*/^=";
    // Non-numeric characters that are needed to represent numbers
    private static final String NUM_CHARS = "-.";
    // Non-alphabetic characters that can be a part of variable names
    private static final String VAR_CHARS = "_";

    static List<Token> tokenize(final String input) throws
            UnrecognizedOperatorException,
            ImproperParenthesesException,
            UnrecognizedCharacterException {
        // Remove all whitespace
        String modifiedInput = input.replaceAll("\\s+", "");
        // Replace -variable or -literal with -1*(...)
        modifiedInput = modifiedInput.replaceAll("-", "+-1*");
        // If it was "(-x", it changed to "(+-1*x"
        // Remove redundant "+" following "("
        modifiedInput = modifiedInput.replaceAll("\\(\\+", "(");
        // Same for leading plus signs
        if (modifiedInput.startsWith("+")) {
            modifiedInput = modifiedInput.substring(1);
        }
        return tokenize(modifiedInput.toCharArray());
    }

    private static List<Token> tokenize(final char[] chars) throws
            UnrecognizedOperatorException,
            UnrecognizedCharacterException,
            ImproperParenthesesException {
        // The final tokens are appended to this list
        List<Token> tokenList = new ArrayList<>(chars.length);
        // Numeric literals are generated character by character
        StringBuilder numericBuffer = new StringBuilder(chars.length);
        // variables and functions
        StringBuilder lettersBuffer = new StringBuilder(chars.length);

        int parenthesesTally = 0;

        for (char ch : chars) {
            if (lettersBuffer.length() == 0
                    && Character.isDigit(ch)
                    || NUM_CHARS.indexOf(ch) > -1) {
                numericBuffer.append(ch);
            } else if (Character.isLetterOrDigit(ch) || VAR_CHARS.indexOf(ch) > -1) {
                // Encountering a letter might be because of a variable
                // or a function.
                if (numericBuffer.length() > 0) {
                    // A numeric literal precedes this. Process it.
                    emptyNumericBufferAsLiteral(numericBuffer, tokenList);
                    // 2x => 2 * x
                    tokenList.add(MAP.getFor("*"));
                }
                lettersBuffer.append(ch);
            } else if (OP_CHARS.indexOf(ch) > -1) {
                // We have found a one-letter, binary operand
                if (numericBuffer.length() > 0) {
                    emptyNumericBufferAsLiteral(numericBuffer, tokenList);
                } else if (lettersBuffer.length() > 0) {
                    emptyLettersBufferAsVariable(lettersBuffer, tokenList);
                }
                tokenList.add(MAP.getFor(ch));
            } else if (ch == '(') {
                parenthesesTally++;
                if (lettersBuffer.length() > 0) {
                    emptyLettersBufferAsFunction(lettersBuffer, tokenList);
                } else if (numericBuffer.length() > 0) {
                    // ... 3(... => ... 3 * (...
                    emptyNumericBufferAsLiteral(numericBuffer, tokenList);
                    tokenList.add(MAP.getFor("*"));
                } else if (tokenList.size() > 0 &&
                        tokenList.get(tokenList.size() - 1) // last element added
                                instanceof RightParenthesesToken) {
                    // "..)(.." => "...)*(..."
                    tokenList.add(MAP.getFor("*"));
                }
                tokenList.add(new LeftParenthesesToken());
            } else if (ch == ')') {
                // Validate first
                parenthesesTally--;
                if (parenthesesTally < 0) {
                    throw new ImproperParenthesesException(
                            "Closing parenthesis occurs before opening parenthesis."
                    );
                }
                // "()" appears
                if (tokenList.size() > 0
                        && tokenList.get(tokenList.size() - 1) // last element added
                        instanceof LeftParenthesesToken
                        && lettersBuffer.length() == 0
                        && numericBuffer.length() == 0) {
                    // i.e. "...()..." is NOT allowed
                    throw new ImproperParenthesesException("Empty parentheses pair.");
                }
                if (lettersBuffer.length() > 0) {
                    emptyLettersBufferAsVariable(lettersBuffer, tokenList);
                } else if (numericBuffer.length() > 0) {
                    emptyNumericBufferAsLiteral(numericBuffer, tokenList);
                }
                tokenList.add(new RightParenthesesToken());
            } else {
                // Unrecognised character
                throw new UnrecognizedCharacterException(ch);
            }
        }
        // The final check to see if the parentheses were balanced.
        if (parenthesesTally != 0) {
            throw new ImproperParenthesesException(
                    "Unbalanced parentheses. Closing parenthesis missing."
            );
        }
        // Empty the buffers
        if (numericBuffer.length() > 0) {
            emptyNumericBufferAsLiteral(numericBuffer, tokenList);
        }
        if (lettersBuffer.length() > 0) {
            emptyLettersBufferAsVariable(lettersBuffer, tokenList);
        }
        return tokenList;
    }

    private static void emptyLettersBufferAsFunction(StringBuilder lettersBuffer,
                                                     List<Token> tokenList)
            throws UnrecognizedOperatorException {
        final String bufferContents = lettersBuffer.toString();
        final Operator operator = MAP.getFor(bufferContents);
        if (operator == null) {
            throw new UnrecognizedOperatorException(bufferContents);
        }
        tokenList.add(operator);
        lettersBuffer.delete(0, lettersBuffer.length());
    }

    private static void emptyLettersBufferAsVariable(StringBuilder lettersBuffer,
                                                     List<Token> tokenList) {
        tokenList.add(new Variable(lettersBuffer.toString()));
        lettersBuffer.delete(0, lettersBuffer.length());
    }

    private static void emptyNumericBufferAsLiteral(StringBuilder numericBuffer,
                                                    List<Token> tokenList) {
        tokenList.add(new Real(numericBuffer.toString()));
        numericBuffer.delete(0, numericBuffer.length());
    }

    static class UnrecognizedCharacterException extends RuntimeException {

        UnrecognizedCharacterException(final char unknown) {
            super("Unrecognised character encountered while parsing: " + unknown);
        }
    }

    static class UnrecognizedOperatorException extends RuntimeException {

        UnrecognizedOperatorException(final String tokenString) {
            super("Unrecognised operation : " + tokenString);
        }
    }
}

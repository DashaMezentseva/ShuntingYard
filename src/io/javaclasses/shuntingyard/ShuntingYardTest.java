package io.javaclasses.shuntingyard;

import io.javaclasses.shuntingyard.converter.PostfixConverter;
import io.javaclasses.shuntingyard.token.Token;
import io.javaclasses.shuntingyard.visitor.ShuntingYardVisitor;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static org.junit.Assert.assertEquals;

public class ShuntingYardTest {

    @Test
    public void testHelloWorld() {

        String expression = "5 + (8.3 - 2) * 6";

        List<Token> tokens = new PostfixConverter().convert(expression);

        ByteArrayOutputStream actualOutput = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(actualOutput);

        for (Token token: tokens){
            System.out.println(token);
        }






        assertEquals("42.8", actualOutput.toString());

    }


}

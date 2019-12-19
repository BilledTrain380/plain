package ch.kinji.plain;

import ch.kinji.plain.lexer.LexicalAnalyzer;
import ch.kinji.plain.lexer.token.Token;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nmaerchy <billedtrain380@gmail.com>
 * @since 0.0.1
 */
public class Main {

    public static void main(String[] args) {

        try {

            var input = new ByteArrayInputStream("5 * 23.45245 * ((1219 - (result * 100)) / 100)".getBytes());


            var lexicalAnalyzer = new LexicalAnalyzer(input);
            lexicalAnalyzer.startAnalysis();

            List<Token> tokens = new ArrayList<>();

            Token token;
            while ((token = lexicalAnalyzer.next()) != null) {
                tokens.add(token);
            }

            lexicalAnalyzer.finishAnalysis();

            System.out.println(tokens);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

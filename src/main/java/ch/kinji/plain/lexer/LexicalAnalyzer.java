package ch.kinji.plain.lexer;

import ch.kinji.plain.lexer.token.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * @author nmaerchy <billedtrain380@gmail.com>
 * @since 0.0.1
 */
public class LexicalAnalyzer implements Iterator<Token> {
    private final InputStream inputStream;

    private BufferedReader reader;

    private final Pattern letterPattern = Pattern.compile("[a-zA-Z]");
    private final Pattern digitPattern = Pattern.compile("[\\d.]");
    private final Pattern operatorPattern = Pattern.compile("[+\\-*/=]");
    private final Pattern separatorPattern = Pattern.compile("[(){}]");

    public LexicalAnalyzer(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public boolean startAnalysis() throws IOException {
        reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.ready();
    }

    public void finishAnalysis() throws IOException {
        reader.close();
    }

    @Override
    public boolean hasNext() {
        try {
            if (reader != null) {
                return reader.ready();
            }

            return false;
        } catch (IOException e) {
            throw new IllegalStateException("Lexical Analyzer is not ready; nested exception", e);
        }
    }

    @Override
    public Token next() {
        try {
            var currentChar = reader.read();

            if (currentChar < 0) {
                return null;
            }

            if (Character.isWhitespace(currentChar)) {
                return next();
            } else if (isOperator(currentChar)) {
                return Token.operator((char) currentChar);
            } else if (isSeparator(currentChar)) {
                return Token.separator((char) currentChar);
            } else if (isDigit(currentChar)) {
                return Token.literal(scanDigit(currentChar));
            } else if (isLetter(currentChar)) {
                return Token.identifier(scanIdentifier(currentChar));
            } else {
                throw new IllegalCharacterException("Unexpected character found: char=" + (char) currentChar);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String scanIdentifier(int start) throws IOException {
        return scan(start, this::isLetter);
    }

    private String scanDigit(int start) throws IOException {
        return scan(start, this::isDigit);
    }

    private String scan(int start, Predicate<Integer> isAllowed) throws IOException {

        var stringBuilder = new StringBuilder(String.valueOf((char) start));

        boolean isStreamMarked = false;
        int codePoint = -1;
        while ((codePoint = reader.read()) >= 0 && isAllowed.test(codePoint)) {
            stringBuilder.append((char) codePoint);
            // The number of characters we want to go back
            reader.mark(1);
            isStreamMarked = true;
        }

        if (isStreamMarked) {
            reader.reset();
        }

        return stringBuilder.toString();
    }

    private boolean isLetter(int codePoint) {
        return letterPattern.matcher(String.valueOf((char) codePoint)).matches();
    }

    private boolean isOperator(int codePoint) {
        return operatorPattern.matcher(String.valueOf((char) codePoint)).matches();
    }

    private boolean isSeparator(int codePoint) {
        return separatorPattern.matcher(String.valueOf((char) codePoint)).matches();
    }

    private boolean isDigit(int codePoint) {
        return digitPattern.matcher(String.valueOf((char) codePoint)).matches();
    }
}

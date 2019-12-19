package ch.kinji.plain.lexer.token;

/**
 * @author nmaerchy <billedtrain380@gmail.com>
 * @since 0.0.1
 */
public class Token {
    private final TokenType type;
    private final String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public static Token identifier(String value) {
        return new Token(TokenType.IDENTIFIER, value);
    }

    public static Token keyword(String value) {
        return new Token(TokenType.KEYWORD, value);
    }

    public static Token separator(char value) {
        return new Token(TokenType.SEPARATOR, String.valueOf(value));
    }

    public static Token operator(char value) {
        return new Token(TokenType.OPERATOR, String.valueOf(value));
    }

    public static Token literal(String value) {
        return new Token(TokenType.LITERAL, value);
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}

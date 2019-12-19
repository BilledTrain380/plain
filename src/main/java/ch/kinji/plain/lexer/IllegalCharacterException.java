package ch.kinji.plain.lexer;

/**
 * @author nmaerchy <billedtrain380@gmail.com>
 * @since 0.0.1
 */
public class IllegalCharacterException extends RuntimeException {
    public IllegalCharacterException() {
    }

    public IllegalCharacterException(String message) {
        super(message);
    }

    public IllegalCharacterException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalCharacterException(Throwable cause) {
        super(cause);
    }

    public IllegalCharacterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

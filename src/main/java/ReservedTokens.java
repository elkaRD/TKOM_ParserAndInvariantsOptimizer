import java.util.HashMap;
import java.util.Map;

public class ReservedTokens
{
    private ReservedTokens instance = new ReservedTokens();
    private Map<String, TokenType> reservedTokens = new HashMap<>();

    public ReservedTokens getInstance()
    {
        return instance;
    }

    private ReservedTokens()
    {
        reservedTokens.put("void", TokenType.VOID);
        reservedTokens.put("(", TokenType.PARENTHESES_OPEN);
        reservedTokens.put(")", TokenType.PARENTHESES_CLOSE);
        reservedTokens.put("{", TokenType.CURLY_OPEN);
        reservedTokens.put("}", TokenType.CURLY_CLOSE);
        reservedTokens.put("[", TokenType.SQUARE_OPEN);
        reservedTokens.put("]", TokenType.SQUARE_CLOSE);
        reservedTokens.put(";", TokenType.SEMICOLON);
        reservedTokens.put("if", TokenType.IF);
        reservedTokens.put("else", TokenType.ELSE);
        reservedTokens.put("while", TokenType.WHILE);
        reservedTokens.put("for", TokenType.FOR);
        reservedTokens.put(",", TokenType.COMMA);
        reservedTokens.put("return", TokenType.RETURN);

                //mathematical operators
        reservedTokens.put("+", TokenType.ADD);
        reservedTokens.put("-", TokenType.SUB);
        reservedTokens.put("/", TokenType.DIV);
        reservedTokens.put("*", TokenType.MUL);

                //logical operators
        reservedTokens.put("&&", TokenType.AND);
        reservedTokens.put("||", TokenType.OR);
        reservedTokens.put(">", TokenType.GREATER);
        reservedTokens.put(">=", TokenType.GREATER_EQUAL);
        reservedTokens.put("<", TokenType.LESS);
        reservedTokens.put("<=", TokenType.LESS_EQUAL);
        reservedTokens.put("==", TokenType.EQUAL);
        reservedTokens.put("!=", TokenType.NOT_EQUAL);
        reservedTokens.put("!", TokenType.NEG);

                //variable types
        reservedTokens.put("int", TokenType.INT);
        reservedTokens.put("float", TokenType.FLOAT);
        reservedTokens.put("bool", TokenType.BOOL);

                //boolean values
        reservedTokens.put("true", TokenType.TRUE);
        reservedTokens.put("false", TokenType.FALSE);

                //loop control keywords
        reservedTokens.put("continue", TokenType.CONTINUE);
        reservedTokens.put("break", TokenType.BREAK);
    }

    public TokenType recognizeReservedToken(Token token)
    {
        if (!reservedTokens.containsKey(token.tokenStr))
            return TokenType.INVALID;

        return reservedTokens.get(token.tokenStr);
    }
}

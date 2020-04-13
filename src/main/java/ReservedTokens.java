import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class ReservedTokens
{
    private static ReservedTokens instance = new ReservedTokens();

    public static ReservedTokens getInstance()
    {
        return instance;
    }

    private Map<String, TokenType> tokenTypes = new HashMap<>();
    private Map<String, EnumSet<TokenAttr>> tokenAttrs = new HashMap<>();

    private ReservedTokens()
    {
        fillTokenTypes();
        fillTokenAttrs();
    }

    private void fillTokenTypes()
    {
        tokenTypes.put("void", TokenType.VOID);
        tokenTypes.put("(", TokenType.PARENTHESES_OPEN);
        tokenTypes.put(")", TokenType.PARENTHESES_CLOSE);
        tokenTypes.put("{", TokenType.CURLY_OPEN);
        tokenTypes.put("}", TokenType.CURLY_CLOSE);
        tokenTypes.put("[", TokenType.SQUARE_OPEN);
        tokenTypes.put("]", TokenType.SQUARE_CLOSE);
        tokenTypes.put(";", TokenType.SEMICOLON);
        tokenTypes.put("if", TokenType.IF);
        tokenTypes.put("else", TokenType.ELSE);
        tokenTypes.put("while", TokenType.WHILE);
        tokenTypes.put("for", TokenType.FOR);
        tokenTypes.put(",", TokenType.COMMA);
        tokenTypes.put("return", TokenType.RETURN);

        tokenTypes.put("=", TokenType.ASSIGN);

        //mathematical operators
        //  addOperator
        tokenTypes.put("+", TokenType.ADD);
        tokenTypes.put("-", TokenType.SUB);
        //  mulOperator
        tokenTypes.put("/", TokenType.DIV);
        tokenTypes.put("*", TokenType.MUL);

        //logical operators
        //  andOperator
        tokenTypes.put("&&", TokenType.AND);
        //  orOperator
        tokenTypes.put("||", TokenType.OR);
        //  relationalOperator
        tokenTypes.put(">", TokenType.GREATER);
        tokenTypes.put(">=", TokenType.GREATER_EQUAL);
        tokenTypes.put("<", TokenType.LESS);
        tokenTypes.put("<=", TokenType.LESS_EQUAL);
        //  equalOperator
        tokenTypes.put("==", TokenType.EQUAL);
        tokenTypes.put("!=", TokenType.NOT_EQUAL);
        //  negOperator - mask is not needed
        tokenTypes.put("!", TokenType.NEG);

        //variable types
        tokenTypes.put("int", TokenType.INT);
        tokenTypes.put("float", TokenType.FLOAT);
        tokenTypes.put("bool", TokenType.BOOL);

        //boolean values
        tokenTypes.put("true", TokenType.TRUE);
        tokenTypes.put("false", TokenType.FALSE);

        //loop control keywords
        tokenTypes.put("continue", TokenType.CONTINUE);
        tokenTypes.put("break", TokenType.BREAK);
    }

    private void fillTokenAttrs()
    {
        tokenAttrs.put("+", EnumSet.of(TokenAttr.SUM_OPERATOR));
        tokenAttrs.put("-", EnumSet.of(TokenAttr.SUM_OPERATOR));

        tokenAttrs.put("*", EnumSet.of(TokenAttr.MUL_OPERATOR));
        tokenAttrs.put("/", EnumSet.of(TokenAttr.MUL_OPERATOR));

        tokenAttrs.put("&&", EnumSet.of(TokenAttr.AND_OPERATOR));
        tokenAttrs.put("||", EnumSet.of(TokenAttr.OR_OPERATOR));

        tokenAttrs.put("<", EnumSet.of(TokenAttr.RELATIONAL_OPERATOR));
        tokenAttrs.put(">", EnumSet.of(TokenAttr.RELATIONAL_OPERATOR));
        tokenAttrs.put("<=", EnumSet.of(TokenAttr.RELATIONAL_OPERATOR));
        tokenAttrs.put(">=", EnumSet.of(TokenAttr.RELATIONAL_OPERATOR));

        tokenAttrs.put("<=", EnumSet.of(TokenAttr.EQUAL_OPERATOR));
        tokenAttrs.put(">=", EnumSet.of(TokenAttr.EQUAL_OPERATOR));

        tokenAttrs.put("int", EnumSet.of(TokenAttr.VAR_TYPE));
        tokenAttrs.put("float", EnumSet.of(TokenAttr.VAR_TYPE));
        tokenAttrs.put("bool", EnumSet.of(TokenAttr.VAR_TYPE));

        tokenAttrs.put("true", EnumSet.of(TokenAttr.BOOL_VAL));
        tokenAttrs.put("false", EnumSet.of(TokenAttr.BOOL_VAL));
    }

    public Token recognizeReservedToken(String tokenStr)
    {
        if (!tokenTypes.containsKey(tokenStr))
            return null;

        Token token = new Token (tokenTypes.get(tokenStr));

        if (tokenAttrs.containsKey(tokenStr))
            token.detailedType = tokenAttrs.get(tokenStr);

        return token;
    }
}

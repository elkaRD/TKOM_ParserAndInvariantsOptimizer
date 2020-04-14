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
        for (String token : tokenTypes.keySet())
            tokenAttrs.put(token, EnumSet.noneOf(TokenAttr.class));
        
        tokenAttrs.get("+").add(TokenAttr.SUM_OPERATOR);
        tokenAttrs.get("-").add(TokenAttr.SUM_OPERATOR);

        tokenAttrs.get("*").add(TokenAttr.MUL_OPERATOR);
        tokenAttrs.get("/").add(TokenAttr.MUL_OPERATOR);

        tokenAttrs.get("&&").add(TokenAttr.AND_OPERATOR);
        tokenAttrs.get("||").add(TokenAttr.OR_OPERATOR);

        tokenAttrs.get("<").add(TokenAttr.RELATIONAL_OPERATOR);
        tokenAttrs.get(">").add(TokenAttr.RELATIONAL_OPERATOR);
        tokenAttrs.get("<=").add(TokenAttr.RELATIONAL_OPERATOR);
        tokenAttrs.get(">=").add(TokenAttr.RELATIONAL_OPERATOR);

        tokenAttrs.get("<=").add(TokenAttr.EQUAL_OPERATOR);
        tokenAttrs.get(">=").add(TokenAttr.EQUAL_OPERATOR);

        tokenAttrs.get("int").add(TokenAttr.VAR_TYPE);
        tokenAttrs.get("float").add(TokenAttr.VAR_TYPE);
        tokenAttrs.get("bool").add(TokenAttr.VAR_TYPE);

        tokenAttrs.get("true").add(TokenAttr.BOOL_VAL);
        tokenAttrs.get("false").add(TokenAttr.BOOL_VAL);

        tokenAttrs.get("true").add(TokenAttr.VAR_VAL);
        tokenAttrs.get("false").add(TokenAttr.VAR_VAL);

        tokenAttrs.get("if").add(TokenAttr.STATEMENT);
        tokenAttrs.get("for").add(TokenAttr.STATEMENT);
        tokenAttrs.get("while").add(TokenAttr.STATEMENT);
        tokenAttrs.get("return").add(TokenAttr.STATEMENT);
        tokenAttrs.get("break").add(TokenAttr.STATEMENT);
        tokenAttrs.get("continue").add(TokenAttr.STATEMENT);
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

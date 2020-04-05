import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScannerTest
{
    @Test
    public void checkIdDetection()
    {
        Token token = getFirstToken("abcd");
        assertTrue(token.type == TokenType.ID);
        assertTrue(token instanceof TokenId);
        TokenId tokenId = (TokenId) token;
        assertTrue(tokenId.value.equals("abcd"));
    }

    @Test
    public void checkIntDetection()
    {
        Token token = getFirstToken("123");
        assertTrue(token.type == TokenType.NUM_INT);
        assertTrue(token instanceof TokenInt);
        TokenInt tokenInt = (TokenInt) token;
        assertTrue(tokenInt.value == 123);
    }

    @Test
    public void checkFloatDetection()
    {
        checkFloatDetection1();
        checkFloatDetection2();
        checkFloatDetection3();
        checkFloatDetection4();
    }

    private void checkFloatDetection1()
    {
        Token token = getFirstToken("123.");
        assertTrue(token.type == TokenType.NUM_FLOAT);
        assertTrue(token instanceof TokenFloat);
        TokenFloat tokenFloat = (TokenFloat) token;
        assertTrue(tokenFloat.value == 123.0);
    }

    private void checkFloatDetection2()
    {
        Token token = getFirstToken("123.45");
        assertTrue(token.type == TokenType.NUM_FLOAT);
        assertTrue(token instanceof TokenFloat);
        TokenFloat tokenFloat = (TokenFloat) token;
        assertTrue(tokenFloat.value == (float) 123.45);
    }

    private void checkFloatDetection3()
    {
        Token token = getFirstToken("0.");
        assertTrue(token.type == TokenType.NUM_FLOAT);
        assertTrue(token instanceof TokenFloat);
        TokenFloat tokenFloat = (TokenFloat) token;
        assertTrue(tokenFloat.value == 0);
    }

    private void checkFloatDetection4()
    {
        Token token = getFirstToken("0.12");
        assertTrue(token.type == TokenType.NUM_FLOAT);
        assertTrue(token instanceof TokenFloat);
        TokenFloat tokenFloat = (TokenFloat) token;
        assertTrue(tokenFloat.value == (float) 0.12);
    }

    @Test
    public void checkZeroDetection()
    {
        Token token = getFirstToken("0");
        assertTrue(token.type == TokenType.NUM_INT);
        assertTrue(token instanceof TokenInt);
        TokenInt tokenInt = (TokenInt) token;
        assertTrue(tokenInt.value == (float) 0);
    }

    @Test
    public void checkKeywordsDetection()
    {
        assertTrue(checkKeyword("void"));
        assertTrue(checkKeyword("&&"));
        assertTrue(checkKeyword("||"));
        assertTrue(checkKeyword("<="));
        assertTrue(checkKeyword("bool"));
        assertTrue(checkKeyword("for"));
        assertTrue(checkKeyword("while"));
        assertTrue(checkKeyword("float"));
        assertTrue(checkKeyword("else"));
        assertTrue(checkKeyword("continue"));
        assertTrue(checkKeyword("["));
        assertTrue(checkKeyword("]"));
        assertTrue(checkKeyword("if"));
        assertTrue(checkKeyword("=="));
        assertTrue(checkKeyword("!"));
        assertTrue(checkKeyword("void"));
        assertTrue(checkKeyword("break"));
        assertTrue(checkKeyword("("));
        assertTrue(checkKeyword("false"));
        assertTrue(checkKeyword(")"));
        assertTrue(checkKeyword("*"));
        assertTrue(checkKeyword("+"));
        assertTrue(checkKeyword(","));
        assertTrue(checkKeyword("-"));
        assertTrue(checkKeyword("int"));
        assertTrue(checkKeyword("/"));
        assertTrue(checkKeyword("true"));
        assertTrue(checkKeyword("{"));
        assertTrue(checkKeyword(";"));
        assertTrue(checkKeyword("<"));
        assertTrue(checkKeyword("!="));
        assertTrue(checkKeyword("}"));
        assertTrue(checkKeyword("="));
        assertTrue(checkKeyword("return"));
        assertTrue(checkKeyword(">"));
        assertTrue(checkKeyword(">="));
    }

    @Test
    public void checkTokensDetection()
    {
        String inputText = "void main ()" +
                "{" +
                "   float zmienna = 10.;" +
                "   for (int i = 0; i < 10; i = i + 1)" +
                "       if (zmienna * i <= 12 && true)" +
                "           continue;" +
                "       else" +
                "           break;" +
                "}";

        Vector<Token> tokens = getTokens(inputText);
        int it = 0;
        //void main ()
        assertTrue(tokens.get(it++).type == TokenType.VOID);
        assertTrue(tokens.get(it++).type == TokenType.ID);
        assertTrue(tokens.get(it++).type == TokenType.PARENTHESES_OPEN);
        assertTrue(tokens.get(it++).type == TokenType.PARENTHESES_CLOSE);
        //{
        assertTrue(tokens.get(it++).type == TokenType.CURLY_OPEN);
        //float zmienna = 10.;
        assertTrue(tokens.get(it++).type == TokenType.FLOAT);
        assertTrue(tokens.get(it++).type == TokenType.ID);
        assertTrue(tokens.get(it++).type == TokenType.ASSIGN);
        assertTrue(tokens.get(it++).type == TokenType.NUM_FLOAT);
        assertTrue(tokens.get(it++).type == TokenType.SEMICOLON);
        //for (int i = 0;
        assertTrue(tokens.get(it++).type == TokenType.FOR);
        assertTrue(tokens.get(it++).type == TokenType.PARENTHESES_OPEN);
        assertTrue(tokens.get(it++).type == TokenType.INT);
        assertTrue(tokens.get(it++).type == TokenType.ID);
        assertTrue(tokens.get(it++).type == TokenType.ASSIGN);
        assertTrue(tokens.get(it++).type == TokenType.NUM_INT);
        assertTrue(tokens.get(it++).type == TokenType.SEMICOLON);
        //i < 10;
        assertTrue(tokens.get(it++).type == TokenType.ID);
        assertTrue(tokens.get(it++).type == TokenType.LESS);
        assertTrue(tokens.get(it++).type == TokenType.NUM_INT);
        assertTrue(tokens.get(it++).type == TokenType.SEMICOLON);
        //i = i + 1)
        assertTrue(tokens.get(it++).type == TokenType.ID);
        assertTrue(tokens.get(it++).type == TokenType.ASSIGN);
        assertTrue(tokens.get(it++).type == TokenType.ID);
        assertTrue(tokens.get(it++).type == TokenType.ADD);
        assertTrue(tokens.get(it++).type == TokenType.NUM_INT);
        assertTrue(tokens.get(it++).type == TokenType.PARENTHESES_CLOSE);
        //if (zmienna * i
        assertTrue(tokens.get(it++).type == TokenType.IF);
        assertTrue(tokens.get(it++).type == TokenType.PARENTHESES_OPEN);
        assertTrue(tokens.get(it++).type == TokenType.ID);
        assertTrue(tokens.get(it++).type == TokenType.MUL);
        assertTrue(tokens.get(it++).type == TokenType.ID);
        //<= 12 && true)
        assertTrue(tokens.get(it++).type == TokenType.LESS_EQUAL);
        assertTrue(tokens.get(it++).type == TokenType.NUM_INT);
        assertTrue(tokens.get(it++).type == TokenType.AND);
        assertTrue(tokens.get(it++).type == TokenType.TRUE);
        assertTrue(tokens.get(it++).type == TokenType.PARENTHESES_CLOSE);
        //continue; else break; }
        assertTrue(tokens.get(it++).type == TokenType.CONTINUE);
        assertTrue(tokens.get(it++).type == TokenType.SEMICOLON);
        assertTrue(tokens.get(it++).type == TokenType.ELSE);
        assertTrue(tokens.get(it++).type == TokenType.BREAK);
        assertTrue(tokens.get(it++).type == TokenType.SEMICOLON);
        assertTrue(tokens.get(it++).type == TokenType.CURLY_CLOSE);
    }

    @Test
    public void checkErrorDetection()
    {

    }

    @Test
    public void checkWhiteSkipper()
    {

    }

    @Test
    public void checkSingleLineCommentSkipper()
    {

    }

    @Test
    public void checkMultiLineCommentSkipper()
    {

    }

    private boolean checkKeyword(String keyword)
    {
        InputManager input = new InputManager();
        input.readText(keyword);
        ParserMock parser = new ParserMock();

        Scanner scanner = new Scanner();
        scanner.parseTokens(input, parser);

        return parser.token.type == ReservedTokens.getInstance().recognizeReservedToken(keyword);
    }

    private Token getFirstToken(String inputText)
    {
        InputManager input = new InputManager();
        input.readText(inputText);
        ParserMock parser = new ParserMock();

        Scanner scanner = new Scanner();
        scanner.parseTokens(input, parser);

        return parser.token;
    }

    private Vector<Token> getTokens(String inputText)
    {
        InputManager input = new InputManager();
        input.readText(inputText);
        ParserMock2 parser = new ParserMock2();

        Scanner scanner = new Scanner();
        scanner.parseTokens(input, parser);

        return parser.tokens;
    }

    private class ParserMock implements IParser
    {
        public Token token = null;

        public void readToken(Token token)
        {
            this.token = token;
        }
    }

    private class ParserMock2 implements  IParser
    {
        public Vector<Token> tokens = new Vector<>();

        public void readToken(Token token)
        {
            this.tokens.add(token);
        }

    }
}
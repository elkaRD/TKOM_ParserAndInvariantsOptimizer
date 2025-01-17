///
/// EN: Project for the ‘Compilation Techniques’ course
///     Warsaw University of Technology
///     Parser and Optimizer of Own Programming Language
///
/// PL: Projekt TKOM (Techniki kompilacji)
///     PW WEiTI 20L
///     Parser i optymalizator wlasnego jezyka programowania
///
/// Copyright (C) Robert Dudzinski 2020
///
/// File: ScannerTest.java

import org.junit.jupiter.api.Test;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScannerTest
{
    @Test
    public void checkIdDetectionOnlyLetters()
    {
        Token token = getFirstToken("abcd");
        assertTrue(token.type == TokenType.ID);
        assertTrue(token instanceof TokenId);
        TokenId tokenId = (TokenId) token;
        assertTrue(tokenId.value.equals("abcd"));
    }

    @Test
    public void checkIdDetectionLettersAndDigits()
    {
        Token token = getFirstToken("abcd123");
        assertTrue(token.type == TokenType.ID);
        assertTrue(token instanceof TokenId);
        TokenId tokenId = (TokenId) token;
        assertTrue(tokenId.value.equals("abcd123"));
    }

    @Test
    public void checkIdDetectionLettersDigitsAndLetters()
    {
        Token token = getFirstToken("abcd321ght");
        assertTrue(token.type == TokenType.ID);
        assertTrue(token instanceof TokenId);
        TokenId tokenId = (TokenId) token;
        assertTrue(tokenId.value.equals("abcd321ght"));
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
    public void checkFloatDetectionNoRightSide()
    {
        Token token = getFirstToken("123.");
        assertTrue(token.type == TokenType.NUM_FLOAT);
        assertTrue(token instanceof TokenFloat);
        TokenFloat tokenFloat = (TokenFloat) token;
        assertTrue(tokenFloat.value == 123.0);
    }

    @Test
    public void checkFloatDetectionLeftRightSide()
    {
        Token token = getFirstToken("123.45");
        assertTrue(token.type == TokenType.NUM_FLOAT);
        assertTrue(token instanceof TokenFloat);
        TokenFloat tokenFloat = (TokenFloat) token;
        assertTrue(tokenFloat.value == (float) 123.45);
    }

    @Test
    public void checkFloatDetectionZeroPoint()
    {
        Token token = getFirstToken("0.");
        assertTrue(token.type == TokenType.NUM_FLOAT);
        assertTrue(token instanceof TokenFloat);
        TokenFloat tokenFloat = (TokenFloat) token;
        assertTrue(tokenFloat.value == 0);
    }

    @Test
    public void checkFloatDetectionLessThan1()
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
    public void checkTokensDetectionComplex()
    {
        String inputText = "void main ()                \n" +
                "{                                      \n" +
                "   float zmienna = 10.;                \n" +
                "   for (int i = 0; i < 10; i = i + 1)  \n" +
                "       if (zmienna * i <= 12 && true)  \n" +
                "           continue;                   \n" +
                "       else                            \n" +
                "           break;                      \n" +
                "}                                      ";

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

        assertTrue(it == tokens.size());
    }

    @Test
    public void checkTokensDetectionNoSeperators()
    {
        String inputText = "123.456.789abc";

        Vector<Token> tokens = getTokens(inputText);
        int it = 0;
        //void main ()
        assertTrue(tokens.get(it++).type == TokenType.NUM_FLOAT);
        assertTrue(tokens.get(it++).type == TokenType.NUM_FLOAT);
        assertTrue(tokens.get(it++).type == TokenType.ID);
    }

    @Test
    public void checkErrorDetectionForbiddenCharacter()
    {
        CharPos errorPos = getErrorPos("#$%$#");

        assertTrue(errorPos != null);
        assertTrue(errorPos.cursorPos == 0);
    }

    @Test
    public void checkErrorDetectionForbiddenCharInMiddle()
    {
        CharPos errorPos = getErrorPos("123#$%$#");

        assertTrue(errorPos != null);
        assertTrue(errorPos.cursorPos == 3);
    }

    @Test
    public void checkErrorDetectionSingleDot()
    {
        CharPos errorPos = getErrorPos("abcd.abc");

        assertTrue(errorPos != null);
        assertTrue(errorPos.cursorPos == 4);
    }

    @Test
    public void checkWhiteSkipper()
    {
        String inputText = "      \nabc";

        InputManager inputManager = new InputManager();
        inputManager.readText(inputText);

        Scanner scanner = new Scanner();
        scanner.skipWhiteChars(inputManager);

        assertTrue(inputManager.peekNext() == 'a');
        assertFalse(scanner.getErrorFlag());
    }

    @Test
    public void checkSingleLineCommentSkipper()
    {
        String inputText = "//abc\ndef";

        InputManager inputManager = new InputManager();
        inputManager.readText(inputText);

        Scanner scanner = new Scanner();
        scanner.skipSingleLineComment(inputManager);

        assertTrue(inputManager.peekNext() == 'd');
        assertFalse(scanner.getErrorFlag());
    }

    @Test
    public void checkMultiLineCommentSkipper()
    {
        String inputText = "/*abc\ndef*/ghi";

        InputManager inputManager = new InputManager();
        inputManager.readText(inputText);

        Scanner scanner = new Scanner();
        scanner.skipMultiLineComment(inputManager);

        assertTrue(inputManager.peekNext() == 'g');
        assertFalse(scanner.getErrorFlag());
    }

    @Test
    public void checkSingleCommentNestedInMulti()
    {
        String inputText = "/*abc\n//def*/ghi";

        InputManager inputManager = new InputManager();
        inputManager.readText(inputText);

        Scanner scanner = new Scanner();
        scanner.skipComments(inputManager);

        assertTrue(inputManager.peekNext() == 'g');
        assertFalse(scanner.getErrorFlag());
    }

    @Test
    public void checkMultiCommentNestedInSingle()
    {
        String inputText = "//abc/*def\nghi*/jkl";

        InputManager inputManager = new InputManager();
        inputManager.readText(inputText);

        Scanner scanner = new Scanner();
        scanner.skipComments(inputManager);

        assertTrue(inputManager.peekNext() == 'g');
        assertFalse(scanner.getErrorFlag());
    }

    private boolean checkKeyword(String keyword)
    {
        InputManager input = new InputManager();
        input.readText(keyword);
        ParserMock parser = new ParserMock();

        Scanner scanner = new Scanner();
        scanner.parseTokens(input, parser);

        assertFalse(scanner.getErrorFlag());

        return parser.token.type == ReservedTokens.getInstance().recognizeReservedToken(keyword).type;
    }

    private CharPos getErrorPos(String inputText)
    {
        InputManager input = new InputManager();
        input.readText(inputText);

        Scanner scanner = new Scanner();
        scanner.parseTokens(input, new ParserMock());

        return scanner.getErrorPos();
    }

    private Token getFirstToken(String inputText)
    {
        InputManager input = new InputManager();
        input.readText(inputText);
        ParserMock parser = new ParserMock();

        Scanner scanner = new Scanner();
        scanner.parseTokens(input, parser);

        assertFalse(scanner.getErrorFlag());

        return parser.token;
    }

    private Vector<Token> getTokens(String inputText)
    {
        InputManager input = new InputManager();
        input.readText(inputText);
        ParserMock2 parser = new ParserMock2();

        Scanner scanner = new Scanner();
        scanner.parseTokens(input, parser);

        assertFalse(scanner.getErrorFlag());

        return parser.tokens;
    }

    private class ParserMock implements IParser
    {
        public Token token = null;

        public void readToken(Token token)
        {
            this.token = token;
        }

        public Program parse(IInputManager inputManager)
        {
            return null;
        }

        public String getParsedProgram()
        {
            return null;
        }
    }

    private class ParserMock2 implements  IParser
    {
        public Vector<Token> tokens = new Vector<>();

        public void readToken(Token token)
        {
            this.tokens.add(token);
        }

        public Program parse(IInputManager inputManager)
        {
            return null;
        }

        public String getParsedProgram()
        {
            return null;
        }
    }
}

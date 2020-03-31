import com.sun.org.apache.xpath.internal.FoundIndex;
import sun.jvm.hotspot.ui.tree.FloatTreeNodeAdapter;

public class Scanner implements IScanner
{
    private enum State
    {
        INVALID,
        //INIT,

        BEGIN,
        ID,
        ZERO,
        INT,
        FLOAT,
        POINT,
        SPECIAL,

        PREDEFINED
    }

    public void parseTokens(IInputManager inputManager)
    {
        while (inputManager.isAvailableChar())
        {
            parseNextToken(inputManager);
        }
    }

    private Token parseNextToken(IInputManager inputManager)
    {
        String result = "";

        State curState = State.BEGIN;
        State prevState = State.BEGIN;
        Token curToken = new Token();
        String curTokenStr = "";

        while (inputManager.isAvailableChar())
        {
            //pre-processing
            while (skipComments(inputManager));

            if (!inputManager.isAvailableChar())
                break;

            if (curState == State.BEGIN)
                skipWhiteChars(inputManager);

            char nextChar = inputManager.peekNext();

            switch (curState) {
                case BEGIN:
                    if (isAlpha(nextChar)) {
                        curState = State.ID;
                    } else if (isNonZeroDigit(nextChar)) {
                        curState = State.ZERO;
                    } else if (isDigit(nextChar)) {
                        curState = State.INT;
                    } else if (nextChar == '.') {
                        curState = State.POINT;
                    } else if (isSpecial(nextChar)) {
                        curState = State.SPECIAL;
                    } else {
                        curState = State.INVALID;
                    }
                    break;

                case ID:
                    if (!isAlpha(nextChar))
                    {
                        curState = State.INVALID;
                    }
                    break;

                case ZERO:
                    curState = State.INVALID;
                    break;

                case INT:
                    if (!isDigit(nextChar))
                    {
                        curState = State.INVALID;
                    }
                    break;

                case FLOAT:
                    if (!isDigit(nextChar))
                    {
                        curState = State.INVALID;
                    }
                    break;

                case POINT:
                    if (isDigit(nextChar))
                    {
                        curState = State.FLOAT;
                    }
                    else
                    {
                        curState = State.INVALID;
                    }
                    break;

                case SPECIAL:
                    if (isSpecial(nextChar) && ReservedTokens.getInstance().recognizeReservedToken(curTokenStr+nextChar) != TokenType.INVALID)
                    {
                        curState = State.SPECIAL;
                    }
                    else
                    {
                        curState = State.INVALID;
                    }
                    break;
            }

            if (prevState == curState || prevState == State.BEGIN)
            {
                curTokenStr += inputManager.getNext();
            }
            else
            {
                if (isAcceptingState(prevState))
                {
                    TokenType reserved = ReservedTokens.getInstance().recognizeReservedToken(curTokenStr);

                    Token generatedToken = reserved == TokenType.INVALID ?
                            generateToken(curToken, prevState, curTokenStr) :
                            generateToken(curToken, reserved);

                    int i = 20;
                }
                else
                {
                    //TODO: raise error
                    int e = 30;
                }

                curTokenStr = "";
                curToken = new Token();
                curState = State.BEGIN;
                prevState = State.BEGIN;
            }

            prevState = curState;

            //result += inputManager.getNext();
        }

        System.out.println("Text after lexer: " + result);

        return null;
    }

    private boolean isAcceptingState(State state)
    {
        switch (state)
        {
            case ID:
            case ZERO:
            case INT:
            case FLOAT:
            case SPECIAL:
                return true;
        }
        return false;
    }

    private Token generateToken(Token curToken, TokenType predefinedType)
    {
        curToken.type = predefinedType;
        return curToken;
    }

    private Token generateToken(Token curToken, State state, String tokenStr)
    {
        switch (state)
        {
            case ID:
                curToken.type = TokenType.ID;
                curToken.valueId = tokenStr;
                break;
            case INT:
            case ZERO:
                curToken.type = TokenType.NUM_INT;
                curToken.valueInt = Integer.parseInt(tokenStr);
                break;
            case FLOAT:
                curToken.type = TokenType.NUM_FLOAT;
                curToken.valueFloat = Float.parseFloat(tokenStr);
                break;
        }

        return curToken;
    }

    private boolean skipWhiteChars(IInputManager inputManager)
    {
        boolean skipped = false;
        while (inputManager.isAvailableChar() && isWhite(inputManager.peekNext()))
        {
            inputManager.getNext();
            skipped = true;
        }

        return skipped;
    }

    private boolean skipComments(IInputManager inputManager)
    {
        boolean foundComment = false;
        while (inputManager.isAvailableChar(2))
        {
            boolean skipped = skipSingleLineComment(inputManager) || skipMultiLineComment(inputManager);
            foundComment = foundComment || skipped;
            if (!skipped)
                break;
        }

        return foundComment;
    }

    private boolean skipSingleLineComment(IInputManager inputManager)
    {
        if (inputManager.peekNext() == '/' && inputManager.peekNext(2) == '/')
        {
            inputManager.getNext();
            inputManager.getNext();

            while (inputManager.isAvailableChar() && !isNewLine(inputManager.peekNext()))
                inputManager.getNext();

            return true;
        }

        return false;
    }

    private boolean skipMultiLineComment(IInputManager inputManager)
    {
        if (inputManager.peekNext() == '/' && inputManager.peekNext(2) == '*')
        {
            inputManager.getNext();
            inputManager.getNext();

            while (inputManager.isAvailableChar())
            {
                char c = inputManager.getNext();
                if (c == '*' && inputManager.isAvailableChar() && inputManager.peekNext() == '/')
                {
                    inputManager.getNext();
                    break;
                }
            }

            return true;
        }

        return false;
    }

    private boolean isSpecial(char c)
    {
        switch (c)
        {
            case '[': case ']':
            case '(': case ')':
            case '{': case '}':
            case ';': case ',':
            case '+': case '-':
            case '*': case '/':
            case '&': case '|':
            case '<': case '>':
            case '=': case '!':
                return true;
        }

        return false;
    }

    private boolean isWhite(char c)
    {
        //TODO: test if character '\r' is also a white space
        return Character.isWhitespace(c);
    }

    private boolean isNewLine(char c)
    {
        return c == '\n';
    }

    private boolean isDigit(char c)
    {
        return Character.isDigit(c);
    }

    private boolean isNonZeroDigit(char c)
    {
        return isDigit(c) && c != '0';
    }

    private boolean isAlpha(char c)
    {
        return Character.isAlphabetic(c) || c == '_';
    }

    private boolean isAlphaNumeric(char c)
    {
        return isAlpha(c) || isDigit(c);
    }
}

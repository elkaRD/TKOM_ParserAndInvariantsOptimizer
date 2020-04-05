import com.sun.corba.se.impl.encoding.BufferManagerReadGrow;
import com.sun.org.apache.xpath.internal.FoundIndex;
import sun.jvm.hotspot.ui.tree.FloatTreeNodeAdapter;

import java.awt.*;

public class Scanner implements IScanner
{
    private enum State
    {
        INVALID,

        BEGIN,
        ID,
        ZERO,
        INT,
        FLOAT,
        POINT,
        SPECIAL
    }

    private boolean errorFlag = false;
    private CharPos errorPos = null;

    public boolean getErrorFlag()
    {
        return errorFlag;
    }

    public CharPos getErrorPos()
    {
        return errorPos;
    }

    public void parseTokens(IInputManager inputManager, IParser parser)
    {
        errorFlag = false;
        errorPos = null;

        while (inputManager.isAvailableChar())
        {
            skipWhiteCharsAndComments(inputManager, true, true);
            Token token = parseNextToken(inputManager);

            if (errorFlag)
            {
                ErrorHandler.getInstance().displayError("Aborting lexer work due to found error");
                break;
            }

            //TODO: temp solution; update when writing parser
            //Token could be null in case when we trying to get token from text containing only white characters
            if (parser != null && token != null)
                parser.readToken(token);
        }
    }

    private Token parseNextToken(IInputManager inputManager)
    {
        State curState = State.BEGIN;
        State prevState = State.BEGIN;
        String curTokenStr = "";
        CharPos tokenPos = inputManager.getCurrentPosition();

        while (skipWhiteCharsAndComments(inputManager, false, true))
        {
            char nextChar = inputManager.peekNext();

            switch (curState) {
                case BEGIN:
                    curState = handleBegin(nextChar);
                    break;

                case ID:
                    curState = handleId(nextChar);
                    break;

                case ZERO:
                    curState = handleZero(nextChar);
                    break;

                case INT:
                    curState = handleInt(nextChar);
                    break;

                case FLOAT:
                    curState = handleFloat(nextChar);
                    break;

                case POINT:
                    curState = handlePoint(nextChar);
                    break;

                case SPECIAL:
                    curState = handleSpecial(nextChar, curTokenStr);
                    break;
            }

            if (curState != State.INVALID)
            {
                curTokenStr += inputManager.getNext();
            }
            else
            {
                curState = State.INVALID;
                break;
            }

            prevState = curState;
        }

        if (curState == State.BEGIN) return null;

        Token createdToken = createToken(prevState, curTokenStr, tokenPos);

        if (createdToken == null)
        {
            ErrorHandler.getInstance().displayErrorLine(tokenPos, "Cannot recognize token");
            errorFlag = true;
            errorPos = tokenPos;
        }

        return createdToken;
    }
    
    private State handleBegin(char nextChar)
    {
        if (isAlpha(nextChar)) {
            return State.ID;
        } else if (isNonZeroDigit(nextChar)) {
            return State.INT;
        } else if (nextChar == '0') {
            return State.ZERO;
        } else if (nextChar == '.') {
            return State.POINT;
        } else if (isSpecial(nextChar)) {
            return State.SPECIAL;
        }

        return State.INVALID;
    }

    private State handleId(char nextChar)
    {
        if (!isAlpha(nextChar)) {
            return State.INVALID;
        }

        return State.ID;
    }

    private State handleZero(char nextChar)
    {
        if (nextChar == '.') {
            return State.FLOAT;
        }

        return State.INVALID;
    }

    private State handleInt(char nextChar)
    {
        if (nextChar == '.') {
            return State.FLOAT;
        } else if (!isDigit(nextChar)) {
            return State.INVALID;
        }

        return State.INT;
    }

    private State handleFloat(char nextChar)
    {
        if (!isDigit(nextChar)) {
            return State.INVALID;
        }

        return State.FLOAT;
    }

    private State handlePoint(char nextChar)
    {
        if (isDigit(nextChar)) {
            return State.FLOAT;
        }

        return State.INVALID;
    }

    private State handleSpecial(char nextChar, String curTokenStr)
    {
        if (isSpecial(nextChar) && ReservedTokens.getInstance().recognizeReservedToken(curTokenStr + nextChar) != TokenType.INVALID) {
            return State.SPECIAL;
        }

        return State.INVALID;
    }
    
    private boolean skipWhiteCharsAndComments(IInputManager inputManager, boolean skipWhite, boolean skipComments)
    {
        boolean skipped;
        do {
            skipped = false;
            if (skipWhite)
                skipped = skipWhiteChars(inputManager);
            if (skipComments)
                skipped = skipped || skipComments(inputManager);
        }
        while (skipped);

        return inputManager.isAvailableChar();
    }
    
    private Token createToken(State prevState, String curTokenStr, CharPos tokenPos)
    {
        if (!isAcceptingState(prevState))
            return null;

        TokenType reserved = ReservedTokens.getInstance().recognizeReservedToken(curTokenStr);

        Token generatedToken = reserved == TokenType.INVALID ?
                generateToken(prevState, curTokenStr) :
                generateToken(reserved);

        generatedToken.tokenPos = tokenPos;

        System.out.println(curTokenStr + "\t\t" + generatedToken.type);

        return generatedToken;
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

    private Token generateToken(TokenType predefinedType)
    {
        return new Token(predefinedType);
    }

    private Token generateToken(State state, String tokenStr)
    {
        switch (state)
        {
            case ID:
                return new TokenId(tokenStr);

            case INT:
            case ZERO:
                return new TokenInt(tokenStr);

            case FLOAT:
                return new TokenFloat(tokenStr);
        }

        return null;
    }

    public boolean skipWhiteChars(IInputManager inputManager)
    {
        boolean skipped = false;
        while (inputManager.isAvailableChar() && isWhite(inputManager.peekNext()))
        {
            inputManager.getNext();
            skipped = true;
        }

        return skipped;
    }

    public boolean skipComments(IInputManager inputManager)
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

    public boolean skipSingleLineComment(IInputManager inputManager)
    {
        if (inputManager.peekNext() == '/' && inputManager.peekNext(2) == '/')
        {
            inputManager.getNext();
            inputManager.getNext();

            while (inputManager.isAvailableChar() && !isNewLine(inputManager.peekNext()))
                inputManager.getNext();
            inputManager.getNext();

            return true;
        }

        return false;
    }

    public boolean skipMultiLineComment(IInputManager inputManager)
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

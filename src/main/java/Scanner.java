import com.sun.org.apache.xpath.internal.FoundIndex;

public class Scanner implements IScanner
{
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

        while (inputManager.isAvailableChar())
        {
            //pre-processing
            while (skipWhiteChars(inputManager) || skipComments(inputManager));

            if (!inputManager.isAvailableChar())
                break;

            result += inputManager.getNext();
        }

        System.out.println("Text after lexer: " + result);

        return null;
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

    private boolean isAlpha(char c)
    {
        return Character.isAlphabetic(c);
    }

    private boolean isAlphaNumeric(char c)
    {
        return isAlpha(c) || isDigit(c);
    }
}

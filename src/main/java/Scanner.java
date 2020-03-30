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
            skipWhiteChars(inputManager);
            skipComments(inputManager);

            if (!inputManager.isAvailableChar())
                break;

            result += inputManager.getNext();
        }

        System.out.println("Text after lexer: " + result);

        return null;
    }

    private void skipWhiteChars(IInputManager inputManager)
    {

    }

    private void skipComments(IInputManager inputManager)
    {
        if (!inputManager.isAvailableChar(2))
            return;

        if (inputManager.peekNext() == '/' && inputManager.peekNext(2) == '/')
        {
            inputManager.getNext();
            inputManager.getNext();

            while (inputManager.isAvailableChar() && !isNewLine(inputManager.peekNext()))
                inputManager.getNext();
        }
        else if (inputManager.peekNext() == '/' && inputManager.peekNext(2) == '*')
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
        }
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

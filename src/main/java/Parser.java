public class Parser implements IParser
{
    public void readToken(Token token)
    {

    }

    private IScanner scanner;
    private IInputManager input;

    public void parse(IInputManager inputManager)
    {
        scanner = new Scanner();
        input = inputManager;

        try
        {
            parseProgram();
        }
        catch (Exception e)
        {

        }
    }

    private void parseProgram() throws Exception
    {
        while (checkNextToken(TokenAttr.VAR_TYPE))
        {
            parseInitVar();
            checkToken(TokenType.SEMICOLON);
        }

        parseDefFunction();
    }

    private void parseDefFunction() throws Exception
    {
        checkToken(TokenType.VOID);
        checkToken(TokenType.ID);
        checkToken(TokenType.PARENTHESES_OPEN);
        checkToken(TokenType.PARENTHESES_CLOSE);
        parseBlock();
    }

    private void parseBlock() throws Exception
    {
        if (checkNextToken(TokenAttr.STATEMENT))
        {
            parseStatement();
        }
        else if (checkOptionalToken(TokenType.CURLY_OPEN))
        {
            while (true)
            {
                if (checkNextToken(TokenType.CURLY_OPEN))
                    parseBlock();
                else if (checkNextToken(TokenAttr.STATEMENT))
                    parseStatement();
                else if (checkOptionalToken(TokenType.CURLY_CLOSE))
                    break;
                else
                    raiseError(peekToken(), TokenType.CURLY_CLOSE);
            }
        }
        else
        {
            raiseError(peekToken(), TokenAttr.STATEMENT);
        }
    }

    private void parseStatement() throws Exception
    {
        if (checkNextToken(TokenType.IF))
            parseCondition();
        else if (checkNextToken(TokenType.FOR))
            parseForLoop();
        else if (checkNextToken(TokenType.WHILE))
            parseWhileLoop();
        else if (checkNextToken(TokenAttr.VAR_TYPE))
        {
            parseInitVar();
            checkToken(TokenType.SEMICOLON);
        }
        else if (checkNextToken(TokenType.ID))
        {
            parseAssignVar();
            checkToken(TokenType.SEMICOLON);
        }
        else if (checkToken(TokenType.RETURN))
            checkToken(TokenType.SEMICOLON);
        else if (checkToken(TokenType.CONTINUE))
            checkToken(TokenType.SEMICOLON);
        else if (checkToken(TokenType.BREAK))
            checkToken(TokenType.SEMICOLON);
    }

    private void parseCondition() throws Exception
    {

    }

    private void parseForLoop() throws Exception
    {

    }

    private void parseWhileLoop() throws Exception
    {

    }

    private void parseInitVar() throws Exception
    {
        checkToken(TokenAttr.VAR_TYPE);
        parseVar();

        if (checkOptionalToken(TokenType.ASSIGN))
        {
            parseVarValue();
        }
    }

    private void parseAssignVar() throws Exception
    {

    }

    private void parseVar() throws Exception
    {

    }

    private void parseVarValue() throws Exception
    {

    }

    private Token curToken = null;
    private Token nextToken = null;

    private Token peekToken()
    {
        if (curToken == null)
        {
            curToken = scanner.parseNextToken(input);
            nextToken = scanner.parseNextToken(input);
        }

        return curToken;
    }

    private Token peekNextToken()
    {
        if (curToken == null)
        {
            curToken = scanner.parseNextToken(input);
            nextToken = scanner.parseNextToken(input);
        }

        return nextToken;
    }

    private Token getToken()
    {
        Token result = peekToken();
        curToken = nextToken;
        nextToken = scanner.parseNextToken(input);
        return result;
    }

    private boolean checkNextToken(TokenAttr attr)
    {
        return peekNextToken().detailedType.contains(attr);
    }

    private boolean checkNextToken(TokenType type)
    {
        return peekNextToken().type != type;
    }

    private boolean checkOptionalToken(TokenAttr attr)
    {
        boolean result = peekToken().detailedType.contains(attr);

        if (result)
            getToken();

        return result;
    }

    private boolean checkOptionalToken(TokenType type)
    {
        boolean result = peekToken().type == type;

        if (result)
            getToken();

        return result;
    }

    private boolean checkToken(TokenAttr attr) throws Exception
    {
        Token token = getToken();

        if (!token.detailedType.contains(attr))
            raiseError(token, attr);

        return true;
    }

    private boolean checkToken(TokenType type) throws Exception
    {
        Token token = getToken();

        if (token.type != type)
            raiseError(token, type);

        return true;
    }

//    private boolean checkToken(Token token, TokenAttr attr, String errorMsg)
//    {
//        boolean result = token.detailedType.contains(attr);
//    }
//
//    private boolean checkToken(Token token, TokenType type, String errorMsg)
//    {
//        return token.type == type;
//    }

    private void raiseError(String msg)
    {
        ErrorHandler.getInstance().displayErrorLine(curToken.tokenPos, msg);
    }

    private void raiseError(Token token, String msg)
    {
        ErrorHandler.getInstance().displayErrorLine(token.tokenPos, msg);
    }

    private void raiseError(Token token, TokenType expectedType) throws Exception
    {
        ErrorHandler.getInstance().displayErrorLine(token.tokenPos, "Expected " + expectedType);
        throw new Exception("Parser TokenType error");
    }

    private void raiseError(Token token, TokenAttr expectedAttr) throws Exception
    {
        ErrorHandler.getInstance().displayErrorLine(token.tokenPos, "Expected " + expectedAttr);
        throw new Exception("Parser TokenAttr error");
    }
}

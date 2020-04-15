import com.sun.org.apache.xpath.internal.operations.And;
import com.sun.tools.classfile.Annotation;
import com.sun.tools.corba.se.idl.constExpr.Minus;

public class Parser implements IParser
{
    public void readToken(Token token)
    {

    }

    private IScanner scanner;
    private IInputManager input;

    public void parse(IInputManager inputManager) throws Exception
    {
        scanner = new Scanner();
        input = inputManager;

        parseProgram();
    }

    private void parseProgram() throws Exception
    {
        while (checkToken(TokenAttr.VAR_TYPE))
        {
            parseInitVar();
            getToken(TokenType.SEMICOLON);
        }

        parseDefFunction();
    }

    private void parseDefFunction() throws Exception
    {
        getToken(TokenType.VOID);
        getToken(TokenType.ID);
        getToken(TokenType.PARENTHESES_OPEN);
        getToken(TokenType.PARENTHESES_CLOSE);
        parseBlock();
    }

    private void parseBlock() throws Exception
    {
        if (getOptionalToken(TokenType.CURLY_OPEN))
        {
            while (true)
            {
                if (checkToken(TokenType.CURLY_OPEN))
                    parseBlock();
                else if (checkToken(TokenAttr.STATEMENT))
                    parseStatement();
                else if (getOptionalToken(TokenType.CURLY_CLOSE))
                    break;
                else
                    raiseError(peekToken(), TokenType.CURLY_CLOSE);
            }
        }
        else
        {
            parseStatement();
        }
    }

    private void parseStatement() throws Exception
    {
        if (checkToken(TokenType.IF))
            parseCondition();
        else if (checkToken(TokenType.FOR))
            parseForLoop();
        else if (checkToken(TokenType.WHILE))
            parseWhileLoop();
        else if (checkToken(TokenAttr.VAR_TYPE))
        {
            parseInitVar();
            getToken(TokenType.SEMICOLON);
        }
        else if (checkToken(TokenType.ID))
        {
            parseAssignVar();
            getToken(TokenType.SEMICOLON);
        }
        else if (getOptionalToken(TokenType.RETURN))
            getToken(TokenType.SEMICOLON);
        else if (getOptionalToken(TokenType.CONTINUE))
            getToken(TokenType.SEMICOLON);
        else if (getOptionalToken(TokenType.BREAK))
            getToken(TokenType.SEMICOLON);
    }

    private void parseCondition() throws Exception
    {
        getToken(TokenType.IF);
        getToken(TokenType.PARENTHESES_OPEN);
        parseLogicalStatement();
        getToken(TokenType.PARENTHESES_CLOSE);
        parseBlock();
        if (getOptionalToken(TokenType.ELSE))
        {
            parseBlock();
        }
    }

    private void parseLogicalStatement() throws Exception
    {
        parseAndCondition();

        while (getOptionalToken(TokenAttr.OR_OPERATOR))
        {
            parseAndCondition();
        }
    }

    private void parseAndCondition() throws Exception
    {
        parseEqualCondition();

        while (getOptionalToken(TokenAttr.AND_OPERATOR))
        {
            parseEqualCondition();
        }
    }

    private void parseEqualCondition() throws Exception
    {
        parseRelationalCondition();

        while (getOptionalToken(TokenAttr.EQUAL_OPERATOR))
        {
            parseRelationalCondition();
        }
    }

    private void parseRelationalCondition() throws Exception
    {
        parseLogicalParam();

        while (getOptionalToken(TokenAttr.RELATIONAL_OPERATOR))
        {
            parseLogicalParam();
        }
    }

    private void parseLogicalParam() throws Exception
    {
        getOptionalToken(TokenType.NEG);

        //TODO: check if these lines need to be commented, we can try to parse these values
//        if (getOptionalToken(TokenAttr.VAR_VAL))
//        {
//
//        }
//        else if (checkToken(TokenType.ID))
//        {
//            parseVar();
//        }
        /*else*/ if (checkToken(TokenType.PARENTHESES_OPEN))
        {
            getToken(TokenType.PARENTHESES_OPEN);
//            if (checkToken(TokenType.ID) && checkNextToken(TokenType.ASSIGN)) //TODO: there's going to be a problem with assigning values to array's elements
//            {
//                parseAssignVar();
//            }
//            else
//            {
//                parseExpression();
//            }
            parseLogicalStatement();
            getToken(TokenType.PARENTHESES_CLOSE);
        }
        else
        {
            parseExpression();
        }
    }

    private void parseExpression() throws Exception
    {
        parseMultiExpression();

        while (getOptionalToken(TokenAttr.SUM_OPERATOR))
        {
            parseMultiExpression();
        }
    }

    private void parseMultiExpression() throws Exception
    {
        parseMultiParam();

        while (getOptionalToken(TokenAttr.MUL_OPERATOR))
        {
            parseMultiParam();
        }
    }

    private void parseMultiParam() throws Exception
    {
        if (checkToken(TokenType.ID))
        {
            parseVar();
        }
        else if (checkToken(TokenAttr.VAR_VAL))
        {
            parseVarValue();
        }
        else
        {
            getToken(TokenType.PARENTHESES_OPEN);
//            if (checkToken(TokenType.ID) && checkNextToken(TokenType.ASSIGN))  //TODO: there's going to be a problem with assigning values to array's elements
//            {
//                parseAssignVar();
//            }
//            else
//            {
//                parseExpression();
//            }
            parseExpression();
            getToken(TokenType.PARENTHESES_CLOSE);
        }
    }

    private void parseForLoop() throws Exception
    {
        getToken(TokenType.FOR);
        getToken(TokenType.PARENTHESES_OPEN);
        if (!checkToken(TokenType.SEMICOLON))
        {
            if (checkToken(TokenAttr.VAR_TYPE))
                parseInitVar();
            else
                parseAssignVar();
        }
        getToken(TokenType.SEMICOLON);
        if (!checkToken(TokenType.SEMICOLON))
            parseLogicalStatement();
        getToken(TokenType.SEMICOLON);
        if (!checkToken(TokenType.PARENTHESES_CLOSE))
            parseAssignVar();
        getToken(TokenType.PARENTHESES_CLOSE);
        parseBlock();
    }

    private void parseWhileLoop() throws Exception
    {
        getToken(TokenType.WHILE);
        getToken(TokenType.PARENTHESES_OPEN);
        parseLogicalStatement();
        getToken(TokenType.PARENTHESES_CLOSE);
        parseBlock();
    }

    private void parseInitVar() throws Exception
    {
        getToken(TokenAttr.VAR_TYPE);
        parseVar();

        if (getOptionalToken(TokenType.ASSIGN))
        {
            parseExpression();
        }
    }

    private void parseAssignVar() throws Exception
    {
        parseVar();
        getToken(TokenType.ASSIGN);
        parseExpression();
    }

    private void parseVar() throws Exception
    {
        getToken(TokenType.ID);
        if (getOptionalToken(TokenType.SQUARE_OPEN))
        {
            getToken(TokenType.NUM_INT);
            getToken(TokenType.SQUARE_CLOSE);
        }
    }

    private void parseVarValue() throws Exception
    {
        if (getOptionalToken(TokenAttr.BOOL_VAL))
        {
        }
        else if (getOptionalToken(TokenType.SUB))
        {
            if (getOptionalToken(TokenAttr.NUM_VAL))
            {

            }
            else
            {
                raiseError(TokenAttr.NUM_VAL);
            }
        }
        else if (getOptionalToken(TokenAttr.NUM_VAL))
        {

        }
        else
        {
            raiseError(TokenAttr.NUM_VAL);
        }
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

    private boolean checkToken(TokenAttr attr)
    {
        return peekToken().detailedType.contains(attr);
    }

    private boolean checkToken(TokenType type)
    {
        return peekToken().type == type;
    }

    private boolean checkNextToken(TokenAttr attr)
    {
        return peekNextToken().detailedType.contains(attr);
    }

    private boolean checkNextToken(TokenType type)
    {
        return peekNextToken().type == type;
    }

    private boolean getOptionalToken(TokenAttr attr)
    {
        boolean result = peekToken().detailedType.contains(attr);

        if (result)
            getToken();

        return result;
    }

    private boolean getOptionalToken(TokenType type)
    {
        boolean result = peekToken().type == type;

        if (result)
            getToken();

        return result;
    }

    private boolean getToken(TokenAttr attr) throws Exception
    {
        Token token = getToken();

        if (!token.detailedType.contains(attr))
            raiseError(token, attr);

        return true;
    }

    private boolean getToken(TokenType type) throws Exception
    {
        Token token = getToken();

        if (token.type != type)
            raiseError(token, type);

        return true;
    }

//    private boolean getToken(Token token, TokenAttr attr, String errorMsg)
//    {
//        boolean result = token.detailedType.contains(attr);
//    }
//
//    private boolean getToken(Token token, TokenType type, String errorMsg)
//    {
//        return token.type == type;
//    }

    private void raiseError(TokenType expectedType) throws Exception
    {
        ErrorHandler.getInstance().displayErrorLine(curToken.tokenPos, "Expectged " + expectedType);
        throw new Exception("Parser TokenType error");
    }

    private void raiseError(TokenAttr expectedAttr) throws Exception
    {
        ErrorHandler.getInstance().displayErrorLine(curToken.tokenPos, "Expectged " + expectedAttr);
        throw new Exception("Parser TokenType error");
    }

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

import sun.util.resources.cldr.ig.LocaleNames_ig;

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

        Program program = parseProgram();
    }

    private Program parseProgram() throws Exception
    {
        Program program = new Program();

        while (checkToken(TokenAttr.VAR_TYPE))
        {
            program.addGlobalVar(parseInitVar());
            getToken(TokenType.SEMICOLON);
        }

        program.setMainFunction(parseDefFunction());

        return program;
    }

    private DefFunction parseDefFunction() throws Exception
    {
        DefFunction function = new DefFunction();

        getToken(TokenType.VOID);
        Token id = getToken(TokenType.ID);
        function.setMainFunction(((TokenId) id).value);

        getToken(TokenType.PARENTHESES_OPEN);
        getToken(TokenType.PARENTHESES_CLOSE);

        function.setMainBlock(parseBlock());

        return function;
    }

    private Block parseBlock() throws Exception
    {
        Block block = new Block();

        if (getOptionalToken(TokenType.CURLY_OPEN))
        {
            while (true)
            {
                if (checkToken(TokenType.CURLY_OPEN))
                    block.addStatement(parseBlock());
                else if (checkToken(TokenAttr.STATEMENT))
                    block.addStatement(parseStatement());
                else if (getOptionalToken(TokenType.CURLY_CLOSE))
                    break;
                else
                    raiseError(peekToken(), TokenType.CURLY_CLOSE);
            }
        }
        else
        {
            block.addStatement(parseStatement());
        }

        return block;
    }

    private Statement parseStatement() throws Exception
    {
        Statement statement = null;

        //TODO: fix all statements (those without returning values)

        if (checkToken(TokenType.IF))
            parseCondition();
        else if (checkToken(TokenType.FOR))
            statement = parseForLoop();
        else if (checkToken(TokenType.WHILE))
            parseWhileLoop();
        else if (checkToken(TokenAttr.VAR_TYPE))
        {
            statement = parseInitVar();
            getToken(TokenType.SEMICOLON);
        }
        else if (checkToken(TokenType.ID))
        {
            statement = parseAssignVar();
            getToken(TokenType.SEMICOLON);
        }
        else if (getOptionalToken(TokenType.RETURN))
            getToken(TokenType.SEMICOLON);
        else if (getOptionalToken(TokenType.CONTINUE))
            getToken(TokenType.SEMICOLON);
        else if (getOptionalToken(TokenType.BREAK))
            getToken(TokenType.SEMICOLON);

        return statement;
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

    private LogicalStatement parseLogicalStatement() throws Exception
    {
        LogicalStatement statement = new LogicalStatement();

        statement.addExpression(parseAndCondition());

        while (getOptionalToken(TokenAttr.OR_OPERATOR))
        {
            statement.addOperator(getLastOptionalToken());
            statement.addExpression(parseAndCondition());
        }

        return statement;
    }

    private LogicalStatement parseAndCondition() throws Exception
    {
        LogicalStatement statement = new LogicalStatement();

        statement.addExpression(parseEqualCondition());

        while (getOptionalToken(TokenAttr.AND_OPERATOR))
        {
            statement.addOperator(getLastOptionalToken());
            statement.addExpression(parseEqualCondition());
        }

        return statement;
    }

    private LogicalStatement parseEqualCondition() throws Exception
    {
        LogicalStatement statement = new LogicalStatement();

        statement.addExpression(parseRelationalCondition());

        while (getOptionalToken(TokenAttr.EQUAL_OPERATOR))
        {
            statement.addOperator(getLastOptionalToken());
            statement.addExpression(parseRelationalCondition());
        }

        return statement;
    }

    private LogicalStatement parseRelationalCondition() throws Exception
    {
        LogicalStatement statement = new LogicalStatement();

        statement.addExpression(parseLogicalParam());

        while (getOptionalToken(TokenAttr.RELATIONAL_OPERATOR))
        {
            statement.addOperator(getLastOptionalToken());
            statement.addExpression(parseLogicalParam());
        }

        return statement;
    }

    private LogicalParam parseLogicalParam() throws Exception
    {
        LogicalParam statement = new LogicalParam();

        getOptionalToken(TokenType.NEG);
        statement.setNegation();

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
            statement.addExpression(parseLogicalStatement());
            getToken(TokenType.PARENTHESES_CLOSE);
        }
        else
        {
            statement.addExpression(parseExpression());
        }

        return statement;
    }

    private Expression parseExpression() throws Exception
    {
        Expression expression = new Expression();

        expression.addExpression(parseMultiExpression());

        while (getOptionalToken(TokenAttr.SUM_OPERATOR))
        {
            expression.addOperator(getLastOptionalToken());
            expression.addExpression(parseMultiExpression());
        }

        return expression;
    }

    private Expression parseMultiExpression() throws Exception
    {
        Expression expression = new Expression();

        expression.addExpression(parseMultiParam());

        while (getOptionalToken(TokenAttr.MUL_OPERATOR))
        {
            expression.addOperator(getLastOptionalToken());
            expression.addExpression(parseMultiParam());
        }

        return expression;
    }

    private ExpressionParam parseMultiParam() throws Exception
    {
        ExpressionParam param = new ExpressionParam();

        if (checkToken(TokenType.ID))
        {
            param.addExpression(parseVar());
        }
        else if (checkToken(TokenAttr.VAR_VAL))
        {
            param.addExpression(parseVarValue());
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
            param.addExpression(parseExpression());
            getToken(TokenType.PARENTHESES_CLOSE);
        }

        return param;
    }

    private ForStatement parseForLoop() throws Exception
    {
        ForStatement statement = new ForStatement();

        getToken(TokenType.FOR);
        getToken(TokenType.PARENTHESES_OPEN);
        if (!checkToken(TokenType.SEMICOLON))
        {
            if (checkToken(TokenAttr.VAR_TYPE))
                statement.setFirstParam(parseInitVar());
            else
                statement.setFirstParam(parseAssignVar());
        }
        getToken(TokenType.SEMICOLON);
        if (!checkToken(TokenType.SEMICOLON))
            statement.setSecondParam(parseLogicalStatement());
        getToken(TokenType.SEMICOLON);
        if (!checkToken(TokenType.PARENTHESES_CLOSE))
            statement.setThirdParam(parseAssignVar());
        getToken(TokenType.PARENTHESES_CLOSE);
        statement.setStatement(parseBlock());

        return statement;
    }

    private void parseWhileLoop() throws Exception
    {
        getToken(TokenType.WHILE);
        getToken(TokenType.PARENTHESES_OPEN);
        parseLogicalStatement();
        getToken(TokenType.PARENTHESES_CLOSE);
        parseBlock();
    }

    private InitVar parseInitVar() throws Exception
    {
        InitVar statement = new InitVar();

        getToken(TokenAttr.VAR_TYPE);
        statement.setVar(parseVar());

        if (getOptionalToken(TokenType.ASSIGN))
        {
            statement.setVarValue(parseExpression());
        }

        return statement;
    }

    private AssignVar parseAssignVar() throws Exception
    {
        AssignVar statement = new AssignVar();

        statement.setVar(parseVar());
        getToken(TokenType.ASSIGN);
        statement.setVarValue(parseExpression());

        return statement;
    }

    private Var parseVar() throws Exception
    {
        Var var = new Var();

        //TODO: check is it correct
        TokenId token = (TokenId) getToken(TokenType.ID);
        var.setName(token.value);
        if (getOptionalToken(TokenType.SQUARE_OPEN))
        {
            //TODO begin: check
            //before:
            //getToken(TokenType.NUM_INT);
            //now:
            var.setIndex(parseExpression());
            //todo end
            getToken(TokenType.SQUARE_CLOSE);
        }

        return var;
    }

    private VarValue parseVarValue() throws Exception
    {
        VarValue varValue = new VarValue();

        if (getOptionalToken(TokenAttr.BOOL_VAL))
        {
            varValue.setValue(getLastOptionalToken());
        }
        else if (getOptionalToken(TokenType.SUB))
        {
            varValue.setNegative();
            if (getOptionalToken(TokenAttr.NUM_VAL))
            {
                varValue.setValue(getLastOptionalToken());
            }
            else
            {
                raiseError(TokenAttr.NUM_VAL);
            }
        }
        else if (getOptionalToken(TokenAttr.NUM_VAL))
        {
            varValue.setValue(getLastOptionalToken());
        }
        else
        {
            raiseError(TokenAttr.NUM_VAL);
        }

        return varValue;
    }

    private Token curToken = null;
    private Token nextToken = null;

    private Token lastOptionalToken = null;

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
        lastOptionalToken = null;

        if (result)
            lastOptionalToken = getToken();

        return result;
    }

    private boolean getOptionalToken(TokenType type)
    {
        boolean result = peekToken().type == type;
        lastOptionalToken = null;

        if (result)
            lastOptionalToken = getToken();

        return result;
    }

    private Token getLastOptionalToken()
    {
        return lastOptionalToken;
    }

    private Token getToken(TokenAttr attr) throws Exception
    {
        Token token = getToken();

        if (!token.detailedType.contains(attr))
            raiseError(token, attr);

        return token;
    }

    private Token getToken(TokenType type) throws Exception
    {
        Token token = getToken();

        if (token.type != type)
            raiseError(token, type);

        return token;
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

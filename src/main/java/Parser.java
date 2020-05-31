import sun.java2d.pipe.SpanShapeRenderer;
import sun.util.resources.cldr.ig.LocaleNames_ig;

import java.util.Optional;

public class Parser implements IParser
{
    public void readToken(Token token)
    {

    }

    private IScanner scanner;
    private IInputManager input;
    private Environment environment = new Environment();

    private Program program;

    public String getParsedProgram()
    {
        return "" + program;
    }

    public Program parse(IInputManager inputManager) throws Exception
    {
        scanner = new Scanner();
        input = inputManager;

        Program program = parseProgram();
        return program;
    }

    private Program parseProgram() throws Exception
    {
        program = new Program();

        while (checkToken(TokenAttr.VAR_TYPE))
        {
            program.addGlobalVar(parseInitVar());
            getToken(TokenType.SEMICOLON);
        }

        program.setMainFunction(parseDefFunction());

        if (curToken != null)
            raiseError(peekToken(), "Expected EOF");

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

        environment.beginBlock(block);

        if (getOptionalToken(TokenType.CURLY_OPEN))
        {
            block.requireBrackets();
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

        environment.endBlock();

        return block;
    }

    private Statement parseStatement() throws Exception
    {
        Statement statement = null;

        if (checkToken(TokenType.IF))
            statement = parseCondition();
        else if (checkToken(TokenType.FOR))
            statement = parseForLoop();
        else if (checkToken(TokenType.WHILE))
            statement = parseWhileLoop();
        else if (checkToken(TokenAttr.VAR_TYPE))
        {
            statement = parseInitVar();
            environment.onParseVarStatement(statement);
            getToken(TokenType.SEMICOLON);
        }
        else if (checkToken(TokenType.ID))
        {
            statement = parseAssignVar();
            environment.onParseVarStatement(statement);
            getToken(TokenType.SEMICOLON);
        }
        else
        {
            if (getOptionalToken(TokenType.RETURN))
                getToken(TokenType.SEMICOLON);
            else if (getOptionalToken(TokenType.CONTINUE))
                getToken(TokenType.SEMICOLON);
            else if (getOptionalToken(TokenType.BREAK))
                getToken(TokenType.SEMICOLON);

            statement = new SimpleStatement(getLastOptionalToken());
        }

        return statement;
    }

    private IfStatement parseCondition() throws Exception
    {
        IfStatement statement = new IfStatement();

        environment.addAtBegin();

        getToken(TokenType.IF);
        getToken(TokenType.PARENTHESES_OPEN);
        statement.setCondition(parseLogicalStatement());
        getToken(TokenType.PARENTHESES_CLOSE);
        statement.setBlock(parseBlock());
        if (getOptionalToken(TokenType.ELSE))
        {
            statement.setElseBlock(parseBlock());
        }

        return statement;
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

        if (getOptionalToken(TokenType.NEG))
            statement.setNegation();

        if (checkToken(TokenType.PARENTHESES_OPEN))
        {
            getToken(TokenType.PARENTHESES_OPEN);
            statement.needBrackets();
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

        if (getOptionalToken(TokenType.SUB))
            param.setNegative();

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
            param.bracketsRequired();
            param.addExpression(parseExpression());
            getToken(TokenType.PARENTHESES_CLOSE);
        }

        return param;
    }

    private ForStatement parseForLoop() throws Exception
    {
        ForStatement statement = new ForStatement();

        environment.addAtBegin();
        environment.moveNextVarToNextBlock();

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
        environment.addAtEnd();
        if (!checkToken(TokenType.PARENTHESES_CLOSE))
            statement.setThirdParam(parseAssignVar());
        getToken(TokenType.PARENTHESES_CLOSE);
        statement.setBlock(parseBlock());

        return statement;
    }

    private WhileStatement parseWhileLoop() throws Exception
    {
        WhileStatement statement = new WhileStatement();

        environment.addAtBegin();

        getToken(TokenType.WHILE);
        getToken(TokenType.PARENTHESES_OPEN);
        statement.setCondition(parseLogicalStatement());
        getToken(TokenType.PARENTHESES_CLOSE);
        statement.setBlock(parseBlock());

        return statement;
    }

    private InitVar parseInitVar() throws Exception
    {
        InitVar statement = new InitVar();
        statement.setPos(peekToken().tokenPos);

        statement.setType(getToken(TokenAttr.VAR_TYPE));

        environment.nextWrite();
        environment.enableSkippingUndeclared();
        statement.setVar(parseVar());
        environment.disableSkippingUndeclared();

        if (getOptionalToken(TokenType.ASSIGN))
        {
            statement.setVarValue(parseExpression());
        }

        environment.declareVar(statement);

        return statement;
    }

    private AssignVar parseAssignVar() throws Exception
    {
        AssignVar statement = new AssignVar();
        statement.setPos(peekToken().tokenPos);

        environment.nextWrite();

        statement.setVar(parseVar());
        getToken(TokenType.ASSIGN);
        statement.setVarValue(parseExpression());

        return statement;
    }

    private Var parseVar() throws Exception
    {
        Var var = new Var();
        var.setPos(peekToken().tokenPos);

        TokenId token = (TokenId) getToken(TokenType.ID);

        var.setName(token.value);
        if (getOptionalToken(TokenType.SQUARE_OPEN))
        {
            var.setIndex(parseExpression());
            getToken(TokenType.SQUARE_CLOSE);
        }

        environment.usedVariable(var);

        return var;
    }

    private VarValue parseVarValue() throws Exception
    {
        VarValue varValue = new VarValue();

        if (getOptionalToken(TokenAttr.BOOL_VAL))
        {
            varValue.setValue(getLastOptionalToken());
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

    private void raiseError(TokenAttr expectedAttr) throws Exception
    {
        ErrorHandler.getInstance().displayErrorLine(curToken.tokenPos, "Expectged " + expectedAttr);
        throw new Exception("Parser TokenType error");
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

    private void raiseError(Token token, String msg) throws Exception
    {
        ErrorHandler.getInstance().displayErrorLine(token.tokenPos, msg);
        throw new Exception("Parser error");
    }
}

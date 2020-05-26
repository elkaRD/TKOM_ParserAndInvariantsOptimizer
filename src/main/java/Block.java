import java.util.*;

public class Block extends Statement
{
    private List<Statement> statements = new ArrayList<>();
    private boolean bracketsRequired = false;
    private Block parentBlock = null;
    private boolean isLoop = false;
    private Statement owner = this;

    private int level;

    public void setIsLoop()
    {
        isLoop = true;
    }

//    public static int debugCounter = 0;
//    public static Statement debugStatement = null;
//    public static Block debugBlock = null;

    public void addStatement(Statement statement)
    {
        statements.add(statement);

//        debugCounter += 1;
//        if (debugCounter == 5)
//        {
//            System.out.println("Found statement no. 3: " + statement);
//            debugStatement = statement;
//            debugBlock = this;
//        }
    }

    public void setParentBlock(Block block)
    {
        parentBlock = block;
    }

    public Block getParentBlock()
    {
        return parentBlock;
    }

    public void setOwner(Statement owner)
    {
        this.owner = owner;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public void requireBrackets()
    {
        bracketsRequired = true;
    }

    @Override
    public String toString()
    {
        String result = "\n";
        if (bracketsRequired)
            result += offset() + "{\n";
        for (Statement statement : statements)
        {
            result += offset() + "\t" + statement;
            if (isSemicolonNeeded(statement))
                result += ";\n";
        }
        if (bracketsRequired)
            result += offset() + "}\n";



        return result;
    }

    public String offset()
    {
        String result = "";
        for (int i = 0; i < level; i++)
            result += '\t';
        return result;
    }

    private boolean isSemicolonNeeded(Statement statement)
    {
        if (statement instanceof ForStatement)
            return false;
        if (statement instanceof IfStatement)
            return false;
        if (statement instanceof WhileStatement)
            return false;
        if (statement instanceof Block)
            return false;

        return true;
    }

    public void moveStatementHigher(Statement statement)
    {
        statements.remove(statement);
        //parentBlock.gotStatementFromChildBlock(statement, this);

//        Statement toFind = this;
//
//        while (toFind != null && !(toFind instanceof LoopStatement))
//        {
//            toFind = toFind.
//        }

        int index = parentBlock.statements.indexOf(owner);
        parentBlock.statements.add(index, statement);
    }

//    public void gotStatementFromChildBlock(Statement statement, Block child)
//    {
//
//    }

    public Map<String, LocalVar> localVars = new HashMap<>();

    @Override
    public Set<String> getReadVars()
    {
        Set<String> result = new TreeSet<>();
        Set<String> declaredVars = new TreeSet<>();

        int counter = preStatements.size() + preExpressions.size();

        for (Statement st : preStatements)
        {
            declaredVars.addAll(st.getDeclaredVars());
            Set<String> usedVars = st.getReadVars();

            for (String var : usedVars)
            {
                if (!declaredVars.contains(var) && !result.contains(var))
                {
                    result.add(var);
                    localVars.put(var, new LocalVar());
                }
                if (!declaredVars.contains(var))
                {
                    localVars.get(var).addRead(counter);
                }
            }

            counter++;
        }

        for (Expression expr : preExpressions)
        {
            Set<String> usedVars = expr.getVars();

            for (String var : usedVars)
            {
                if (!declaredVars.contains(var) && !result.contains(var))
                {
                    result.add(var);
                    localVars.put(var, new LocalVar());
                }
                if (!declaredVars.contains(var))
                {
                    localVars.get(var).addRead(counter);
                }
            }

//            result.addAll(st.getReadVars());

            counter++;
        }

        for (Statement st : statements)
        {
            declaredVars.addAll(st.getDeclaredVars());
            Set<String> usedVars = st.getReadVars();

            for (String var : usedVars)
            {
                if (!declaredVars.contains(var) && !result.contains(var))
                {
                    result.add(var);
                    localVars.put(var, new LocalVar());
                }
                if (!declaredVars.contains(var))
                {
                    localVars.get(var).addRead(counter);
                }
            }

//            result.addAll(st.getReadVars());

            counter++;
        }

        for (Statement st : postStatements)
        {
            declaredVars.addAll(st.getDeclaredVars());
            Set<String> usedVars = st.getReadVars();

            for (String var : usedVars)
            {
                if (!declaredVars.contains(var) && !result.contains(var))
                {
                    result.add(var);
                    localVars.put(var, new LocalVar());
                }
                if (!declaredVars.contains(var))
                {
                    localVars.get(var).addRead(counter);
                }
            }

            counter++;
        }

        return result;
    }

    @Override
    public Set<String> getWrittenVars()
    {
        Set<String> result = new TreeSet<>();
        Set<String> declaredVars = new TreeSet<>();

        int counter = preStatements.size() + preExpressions.size();

        for (Statement st : preStatements)
        {
            declaredVars.addAll(st.getDeclaredVars());
            Set<String> usedVars = st.getWrittenVars();

            for (String var : usedVars)
            {
                if (!declaredVars.contains(var) && !result.contains(var))
                {
                    result.add(var);
                    localVars.put(var, new LocalVar());
                }
                if (!declaredVars.contains(var))
                {
                    localVars.get(var).addRead(counter);
                }
            }

            counter++;
        }

        for (Statement st : statements)
        {
            declaredVars.addAll(st.getDeclaredVars());
            Set<String> usedVars = st.getWrittenVars();

            for (String var : usedVars)
            {
                if (!declaredVars.contains(var) && !result.contains(var))
                {
                    result.add(var);
                    localVars.put(var, new LocalVar());
                }
                if (!declaredVars.contains(var))
                {
                    localVars.get(var).addWrite(counter);
                }
            }

//            result.addAll(st.getReadVars());

            counter++;
        }

        return result;
    }

    private List<Statement> preStatements = new ArrayList<>();
    private List<Statement> postStatements = new ArrayList<>();
    private List<Expression> preExpressions = new ArrayList<>();

    public void addPreStatement(Statement statement)
    {
        if (statement == null)
            return;

        preStatements.add(statement);
    }

    public void addPreExpression(Expression expr)
    {
        if (expr == null)
            return;

        preExpressions.add(expr);
    }

    public void addPostStatement(Statement statement)
    {
        if (statement == null)
            return;

        postStatements.add(statement);
    }

    @Override
    public void fillEnvironment(Environment environment, int linesOffset)
    {

    }

    public void optimize()
    {

    }
}

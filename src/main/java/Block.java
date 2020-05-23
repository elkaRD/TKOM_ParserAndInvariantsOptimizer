import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<String> getReadVars()
    {
        List<String> result = new ArrayList<>();

        for (Statement st : statements)
        {
            result.addAll(st.getReadVars());
        }

        return result;
    }

    @Override
    public List<String> getWrittenVars()
    {
        List<String> result = new ArrayList<>();

        for (Statement st : statements)
        {
            result.addAll(st.getWrittenVars());
        }

        return result;
    }
}

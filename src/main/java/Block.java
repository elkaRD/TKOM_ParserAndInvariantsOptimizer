import java.util.ArrayList;
import java.util.List;

public class Block extends Statement
{
    private List<Statement> statements = new ArrayList<>();
    private boolean bracketsRequired = false;

    public void addStatement(Statement statement)
    {
        statements.add(statement);
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
            result += "{\n";
        for (Statement statement : statements)
        {
            result += statement;
            if (isSemicolonNeeded(statement))
                result += ";\n";
        }
        if (bracketsRequired)
            result += "}\n";

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
}

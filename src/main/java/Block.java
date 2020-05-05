import java.util.ArrayList;
import java.util.List;

public class Block extends Statement
{
    private List<Statement> statements = new ArrayList<>();

    public void addStatement(Statement statement)
    {
        statements.add(statement);
    }

    @Override
    public String toString()
    {
        String result = "\n{\n";
        for (Statement statement : statements)
        {
            result += statement;
            if (isSemicolonNeeded(statement))
                result += ";\n";
        }
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

        return true;
    }
}

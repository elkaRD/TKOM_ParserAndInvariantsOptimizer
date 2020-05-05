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
            result += statement;
        result += "}\n";

        return result;
    }
}

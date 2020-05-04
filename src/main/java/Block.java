import java.util.ArrayList;
import java.util.List;

public class Block extends Statement
{
    private List<Statement> statements = new ArrayList<>();

    public void addStatement(Statement statement)
    {
        statements.add(statement);
    }
}

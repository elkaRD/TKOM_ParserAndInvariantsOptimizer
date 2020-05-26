import java.util.Set;
import java.util.TreeSet;

public class IfStatement extends Statement
{
    private LogicalStatement condition = null;
    private Block block = null;
    private Block elseBlock = null;

    public void setCondition(LogicalStatement condition)
    {
        this.condition = condition;
    }

    public void setBlock(Block block)
    {
        this.block = block;
        this.block.addPreExpression(condition);
    }

    public void setElseBlock(Block block)
    {
        this.elseBlock = block;
    }

    @Override
    public String toString()
    {
        String result =  "if (" + condition + ")" + block;

        if (elseBlock != null)
        {
            result += elseBlock.offset() + "else" + elseBlock;
        }

        return result;
    }

    @Override
    public Set<String> getReadVars()
    {
        Set<String> result = new TreeSet<>();
        result.addAll(block.getReadVars());
        return result;
    }

    @Override
    public Set<String> getWrittenVars()
    {
        Set<String> result = new TreeSet<>();
        result.addAll(block.getWrittenVars());
        return result;
    }

    @Override
    public Set<String> getDeclaredVars()
    {
        Set<String> result = new TreeSet<>();
        result.addAll(block.getDeclaredVars());
        return result;
    }
}

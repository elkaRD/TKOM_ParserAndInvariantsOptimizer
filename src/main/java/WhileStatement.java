import java.util.Set;
import java.util.TreeSet;

public class WhileStatement extends LoopStatement
{
    private LogicalStatement condition = null;
    private Block block = null;

    public void setCondition(LogicalStatement condition)
    {
        this.condition = condition;
    }

    public void setBlock(Block block)
    {
        this.block = block;
        this.block.setIsLoop();
        this.block.setOwner(this);
        this.block.addPreExpression(condition);
    }

    @Override
    public String toString()
    {
        return "while (" + condition + ")" + block;
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

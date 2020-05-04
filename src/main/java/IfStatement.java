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
    }

    public void setElseBlock(Block block)
    {
        this.elseBlock = block;
    }
}

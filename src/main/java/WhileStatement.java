public class WhileStatement extends Statement
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
    }

    @Override
    public String toString()
    {
        return "while (" + condition + ")" + block;
    }
}

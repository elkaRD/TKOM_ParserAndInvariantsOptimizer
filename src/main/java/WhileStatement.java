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
    }

    @Override
    public String toString()
    {
        return "while (" + condition + ")" + block;
    }
}

public class DefFunction
{
    private String mainFunction;
    private Block mainBlock;

    public void setMainFunction(String func)
    {
        mainFunction = func;
    }

    public void setMainBlock(Block block)
    {
        mainBlock = block;
    }

    @Override
    public String toString()
    {
        return "\nvoid " + mainFunction + "()" + mainBlock;
    }
}

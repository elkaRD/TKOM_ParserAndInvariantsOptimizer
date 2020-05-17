public class Environment
{
    private static Environment instance = new Environment();

    private Block rootBlock = new Block();
    private Block curBlock = rootBlock;
    private int curLevel = 0;

    private Environment()
    {

    }

    public static Environment getInstance()
    {
        return instance;
    }

    public void beginBlock(Block block)
    {
        block.setParentBlock(curBlock);
        block.setLevel(curLevel);
        curBlock = block;
        curLevel++;
    }

    public void endBlock()
    {
        curBlock = curBlock.getParentBlock();
        curLevel--;
    }

    public void addStatement(Statement statement)
    {

    }

    public void declareVar(Var var)
    {

    }
}

import com.sun.org.apache.xml.internal.security.Init;

public class ForStatement extends LoopStatement
{
    private Statement firstParam = null;
    private LogicalStatement secondParam = null;
    private Statement thirdParam = null;
    private Block block = null;

    public void setFirstParam(Statement statement) throws Exception
    {
        firstParam = statement;

//        if (firstParam instanceof InitVar)
//        {
//            InitVar initVar = (InitVar) firstParam;
//            Environment.getInstance().declareVar(initVar);
//        }
    }

    public void setSecondParam(LogicalStatement statement)
    {
        secondParam = statement;
    }

    public void setThirdParam(Statement statement)
    {
        thirdParam = statement;
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
        String result = "for (";
        if (firstParam != null)
            result += firstParam;
        result += "; ";
        if (secondParam != null)
            result += secondParam;
        result += "; ";
        if (thirdParam != null)
            result += thirdParam;
        result += ")" + block;

        return result;
    }
}

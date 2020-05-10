import sun.rmi.runtime.Log;

public class ForStatement extends Statement
{
    private Statement firstParam = null;
    private LogicalStatement secondParam = null;
    private Statement thirdParam = null;
    private Statement statement = null;

    public void setFirstParam(Statement statement)
    {
        firstParam = statement;
    }

    public void setSecondParam(LogicalStatement statement)
    {
        secondParam = statement;
    }

    public void setThirdParam(Statement statement)
    {
        thirdParam = statement;
    }

    public void setStatement(Statement statement)
    {
        this.statement = statement;
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
        result += ")" + statement;

        return result;
    }
}

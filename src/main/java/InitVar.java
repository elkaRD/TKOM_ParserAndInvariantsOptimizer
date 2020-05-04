public class InitVar extends Statement
{
    private Var var = null;
    private Expression varValue = null;

    public void setVar(Var var)
    {
        this.var = var;
    }

    public void setVarValue(Expression varValue)
    {
        this.varValue = varValue;
    }
}

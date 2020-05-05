public class InitVar extends Statement
{
    private Token varType = null;
    private Var var = null;
    private Expression varValue = null;

    public void setType(Token varType)
    {
        this.varType = varType;
    }

    public void setVar(Var var)
    {
        this.var = var;
    }

    public void setVarValue(Expression varValue)
    {
        this.varValue = varValue;
    }

    @Override
    public String toString()
    {
        String result = ReservedTokens.getInstance().getStr(varType.type) + " " + var;
        if (varValue != null)
            result += " = " + varValue;

        return result;
    }
}

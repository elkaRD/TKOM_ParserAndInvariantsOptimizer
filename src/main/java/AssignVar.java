import java.util.ArrayList;
import java.util.List;

public class AssignVar extends Statement
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

    @Override
    public String toString()
    {
        String result = "" + var;
        if (varValue != null)
            result += " = " + varValue;

        return result;
    }

    @Override
    public List<String> getReadVars()
    {
        return varValue.getReadVars();
    }

    @Override
    public List<String> getWrittenVars()
    {
        List<String> result = new ArrayList<>();
        result.add(var.getName());

        return result;
    }
}

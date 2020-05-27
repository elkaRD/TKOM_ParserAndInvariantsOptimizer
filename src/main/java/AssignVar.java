import java.util.Set;
import java.util.TreeSet;

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
    public Set<String> getReadVars()
    {
        Set<String> result = new TreeSet<>();
        if (var.getIndex() != null)
            result.addAll(var.getIndex().getVars());
        result.addAll(varValue.getVars());

        return result;
    }

    @Override
    public Set<String> getWrittenVars()
    {
        Set<String> result = new TreeSet<>();
        result.add(var.getName());

        return result;
    }
}

import java.util.ArrayList;
import java.util.List;

public class Program
{
    private List<InitVar> globalVars= new ArrayList<>();
    private DefFunction mainFunction;

    public void addGlobalVar(InitVar var)
    {
        globalVars.add(var);
    }

    public void setMainFunction(DefFunction function)
    {
        mainFunction = function;
    }
}
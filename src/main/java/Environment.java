import java.util.HashMap;
import java.util.Map;

public class Environment
{
    private static Environment instance = new Environment();

    private Block rootBlock = new Block();
    private Block curBlock = rootBlock;
    private int curLevel = 0;

    private Map<Block, Map<String, LocalVar>> localVars = new HashMap<>();

    private Environment()
    {
        localVars.put(rootBlock, new HashMap<>());
    }

    public static Environment getInstance()
    {
        return instance;
    }

    public void beginBlock(Block block)
    {
        localVars.put(block, new HashMap<>());

        Map<String, LocalVar> parentVars = localVars.get(curBlock);
        for (String var : parentVars.keySet())
        {
            localVars.get(block).put(var, parentVars.get(var).copyForInheritedScope());
        }

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

    public void assignValToVar(AssignVar assignVar)
    {

    }

    public void declareVar(InitVar initVar) throws Exception
    {
        Var var = initVar.getVar();

        Map<String, LocalVar> vars = localVars.get(curBlock);
        LocalVar newVar = new LocalVar();
        newVar.setVar(var);

        if (vars.containsKey(var.getName()))
        {
            if (vars.get(var.getName()).isDeclaredHere())
            {
                raiseError(initVar, "Redeclaration of variable " + var.getName());
            }
            newVar.setReplacedVar(vars.get(var.getName()));
        }
        vars.put(var.getName(), newVar);
    }

    private void raiseError(Statement statement, String msg) throws Exception
    {
        ErrorHandler.getInstance().displayErrorLine(statement.getPos(), msg);
        throw new Exception("Semantic error");
    }
}

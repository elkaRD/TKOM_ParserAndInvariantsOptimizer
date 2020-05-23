import java.util.HashMap;
import java.util.Map;

public class Environment
{
    private static Environment instance = new Environment();

    private Block rootBlock = new Block();
    private Block curBlock = rootBlock;
    private int curLevel = 0;

    private Map<Block, Map<String, LocalVar>> localVars = new HashMap<>();

    private InitVar varToMove = null;
    private boolean moveNext = false;

    public Environment()
    {
        localVars.put(rootBlock, new HashMap<>());
    }

//    public static Environment getInstance()
//    {
//        return instance;
//    }

    public void beginBlock(Block block) throws Exception
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

        if (varToMove != null)
        {
            declareVar(varToMove);
            varToMove = null;
        }
    }

    public void endBlock()
    {
        curBlock = curBlock.getParentBlock();
        curLevel--;
    }

    public void declareVar(InitVar initVar) throws Exception
    {
        if (moveNext)
        {
            varToMove = initVar;
            moveNext = false;
            return;
        }

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

    private void raiseError(Var var, String msg) throws Exception
    {
        ErrorHandler.getInstance().displayErrorLine(var.getPos(), msg);
        throw new Exception("Semantic error");
    }

    public void moveNextVarToNextBlock()
    {
        moveNext = true;
    }

    public void usedVariable(Var var) throws Exception
    {
        if (skipUndeclared)
            return;

        String varName = var.getName();

        if (varToMove != null && varToMove.getVar().getName().equals(varName))
        {
            if (varToMove.getVar().isArray() == var.isArray())
                return;

            if (varToMove.getVar().isArray())
                raiseError(var, "Missing index in table variable " + varName);
            else
                raiseError(var, "Got index in not-table variable " + varName);
        }

        Block blockIter = curBlock;
        Map<String, LocalVar> declaredVarsIter = localVars.get(curBlock);

        while (blockIter != null)
        {
            if (declaredVarsIter.containsKey(varName))
            {
                LocalVar localVar = declaredVarsIter.get(varName);
                if (localVar.isArray() == var.isArray())
                {
                    return;
                }
                else
                {
                    if (localVar.isArray())
                        raiseError(var, "Missing index in table variable " + varName);
                    else
                        raiseError(var, "Got index in not-table variable " + varName);
                }
            }

            blockIter = blockIter.getParentBlock();
        }

        raiseError(var, "Using of undeclared variable " + varName);
    }

    private boolean skipUndeclared = false;

    public void enableSkippingUndeclared()
    {
        skipUndeclared = true;
    }

    public void disableSkippingUndeclared()
    {
        skipUndeclared = false;
    }

    public static void reset()
    {
        instance = new Environment();
    }
}

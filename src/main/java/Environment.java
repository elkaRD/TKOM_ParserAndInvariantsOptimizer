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

    private Environment()
    {
        localVars.put(rootBlock, new HashMap<>());
    }

    public static Environment getInstance()
    {
        return instance;
    }

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

    public void gotStatement(Statement statement)
    {
        if (statement instanceof InitVar)
        {

        }
        else if (statement instanceof AssignVar)
        {

        }
        else
        {

        }
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

    public void moveNextVarToNextBlock()
    {
        moveNext = true;
    }

    public void usedVariable(Var var) throws Exception
    {
        if (skipUndeclared)
            return;

        checkVar(var.getName(), var.isArray());
    }

    private void checkVar(String varName, boolean isTable) throws Exception
    {
        if (varToMove != null && varToMove.getVar().getName().equals(varName))
            return;

        Block blockIter = curBlock;
        Map<String, LocalVar> declaredVarsIter = localVars.get(curBlock);

        while (blockIter != null)
        {
            if (declaredVarsIter.containsKey(varName))
            {
                LocalVar var = declaredVarsIter.get(varName);
                if (var.isArray() == isTable)
                {
                    return;
                }
                else
                {
                    //TODO: get and display position of var
                    throw new Exception("Semantic exception - table problem");
                }
            }

            blockIter = blockIter.getParentBlock();
        }

        throw new Exception("Semantic exception - use of an undeclared variable " + varName);
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

//    private InitVar toMove = null;
//
//    public void pushInitVar(InitVar initVar)
//    {
//        toMove = initVar;
//    }
//
//    public void popInitVar()
//    {
//
//    }
}

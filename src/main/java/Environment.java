import java.util.*;

public class Environment
{
    private static Environment instance = new Environment();

    private Block rootBlock = new Block();
    private Block curBlock = rootBlock;
    private int curLevel = 0;

    private Map<Block, Map<String, LocalVar>> localVars = new HashMap<>();
    private Map<Block, Integer> statementCounters = new HashMap<>();

    private List<Statement> delayedStatementsBegin = new ArrayList<>();
    private List<Statement> delayedStatementsEnd = new ArrayList<>();
    private Map<Block, List<Statement>> toAddAtEnd = new HashMap<>();

    private InitVar varToMove = null;
    private boolean moveNext = false;
    private boolean moveNextAtNextEnd = false;

    private class BufferedVarUse
    {
        public String varName;
        public boolean write = false;
    }

    public Environment()
    {
        localVars.put(rootBlock, new HashMap<>());
        statementCounters.put(rootBlock, 0);
    }

//    public static Environment getInstance()
//    {
//        return instance;
//    }

    public void beginBlock(Block block) throws Exception
    {
        localVars.put(block, new HashMap<>());
        statementCounters.put(block, 0);

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

        bufferBegin = false;
        bufferEnd = false;

        for (Statement st : delayedStatementsBegin)
            onParseVarStatement(st);

        toAddAtEnd.put(curBlock, new ArrayList<>());
        for (Statement st : delayedStatementsEnd)
            toAddAtEnd.get(curBlock).add(st);

        delayedStatementsBegin.clear();
        delayedStatementsEnd.clear();
    }

    public void endBlock()
    {
        bufferBegin = false;
        bufferEnd = false;

        for (Statement st : toAddAtEnd.get(curBlock))
            onParseVarStatement(st);

        toAddAtEnd.remove(curBlock);

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
        moveNextAtNextEnd = false;
    }

    public void moveNextVarToEndOfNextBlock()
    {
        moveNextAtNextEnd = true;
        moveNext = false;
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

    public void initVar(Block block, String varName, int line)
    {

    }

    public void readVar(Block block, String varName, int line)
    {

    }

    public void writeVar(Block block, String varName, int line)
    {

    }

    private void checkCurBlock()
    {

    }

    public void gotStatement(Statement statement)
    {
        return;

//        Set<String> read = statement.getReadVars();
//        Set<String> written = statement.getWrittenVars();
//
//        String rstr = "";
//        if (read != null)
//            for (String s : read)
//                rstr += s + ", ";
//
//        String wstr = "";
//        if (written != null)
//            for (String s : written)
//                wstr += s + ", ";
//
//        //System.out.println("Got statement " + statement + "     R: " + rstr + "    W: " + wstr);
    }

    public boolean isLocalVar(Block block, String var)
    {
        Map<String, LocalVar> blockVars = localVars.get(block);
        return blockVars.get(var).isDeclaredHere();
    }

    public void optimize()
    {
        rootBlock.fillEnvironment(this, 0);
    }

    private boolean nextWritten = false;
    private boolean bufferBegin = false;
    private boolean bufferEnd = false;

    public void nextWrite()
    {
        nextWritten = true;
    }

    public void addAtBegin()
    {
        bufferBegin = true;
        bufferEnd = false;
    }

    public void addAtEnd()
    {
        bufferBegin = false;
        bufferEnd = true;
    }

    public void onParseVarStatement(Statement statement) //initVar and assignVar
    {
        if (!(statement instanceof AssignVar) && !(statement instanceof InitVar))
            System.out.println("FATAL ERROR ENV");

        if (bufferBegin)
        {
            delayedStatementsBegin.add(statement);
        }
        else if (bufferEnd)
        {
            delayedStatementsEnd.add(statement);
        }
        else
        {
            Set<String> reads = statement.getReadVars();
            Set<String> writes = statement.getWrittenVars();

            for (String var : reads)
                onParseVar(var, false);

            for (String var : writes)
                onParseVar(var, true);

            statementCounters.put(curBlock, statementCounters.get(curBlock) + 1);
        }
    }


    public void onParseVar(String varName)
    {
//        BufferedVarUse varUse = new BufferedVarUse();
//        varUse.varName = varName;
//        varUse.write = nextWritten;
//        nextWritten = false;
//
//        if (bufferBegin)
//        {
//            delayedStatementsBegin.add(varUse);
//        }
//        else if (bufferEnd)
//        {
//            delayedStatementsEnd.add(varUse);
//        }
//        else
//        {
//            onParseVar(varUse.varName, varUse.write);
//        }
    }

    private void onParseVar(String varName, boolean write)
    {
        Block blockIter = curBlock;
        Map<String, LocalVar> declaredVarsIter = localVars.get(curBlock);

        while (blockIter != null)
        {
            if (declaredVarsIter.containsKey(varName))
            {
                LocalVar localVar = declaredVarsIter.get(varName);
                if (write)
                    localVar.addWrite(statementCounters.get(curBlock));
                else
                    localVar.addRead(statementCounters.get(curBlock));

                break;
            }

            blockIter = blockIter.getParentBlock();
        }


    }
}

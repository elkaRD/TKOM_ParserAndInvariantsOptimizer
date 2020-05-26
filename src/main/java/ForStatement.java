import com.sun.org.apache.xml.internal.security.Init;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ForStatement extends LoopStatement
{
    private Statement firstParam = null;
    private LogicalStatement secondParam = null;
    private Statement thirdParam = null;
    private Block block = null;

    public void setFirstParam(Statement statement) throws Exception
    {
        firstParam = statement;

//        if (firstParam instanceof InitVar)
//        {
//            InitVar initVar = (InitVar) firstParam;
//            Environment.getInstance().declareVar(initVar);
//        }
    }

    public void setSecondParam(LogicalStatement statement)
    {
        secondParam = statement;
    }

    public void setThirdParam(Statement statement)
    {
        thirdParam = statement;
    }

    public void setBlock(Block block)
    {
        this.block = block;
        this.block.setIsLoop();
        this.block.setOwner(this);

        this.block.addPreStatement(firstParam);
        this.block.addPreExpression(secondParam);
        this.block.addPostStatement(thirdParam);
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
        result += ")" + block;

        return result;
    }

    @Override
    public Set<String> getReadVars()
    {
        Set<String> result = new TreeSet<>();
        result.addAll(block.getReadVars());
        return result;
    }

    @Override
    public Set<String> getWrittenVars()
    {
        Set<String> result = new TreeSet<>();
        result.addAll(block.getWrittenVars());
        return result;
    }

    @Override
    public Set<String> getDeclaredVars()
    {
        Set<String> result = new TreeSet<>();
        result.addAll(block.getDeclaredVars());
        return result;
    }

    @Override
    public void fillEnvironment(Environment environment, int linesOffset)
    {
//        if (firstParam != null)
//        {
//            List<String> readVars = firstParam.getReadVars();
//            for (String var : readVars)
//            {
//                environment.readVar(block, var, 0);
//            }
//        }
//        if (secondParam != null)
//        {
//            result.addAll(secondParam.getVars());
//        }
//        if (thirdParam != null)
//        {
//            result.addAll(thirdParam.getReadVars());
//        }

    }

    @Override
    public boolean optimize()
    {
        return block.optimize();
    }
}

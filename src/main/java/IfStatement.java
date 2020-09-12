///
/// EN: Project for the ‘Compilation Techniques’ course
///     Warsaw University of Technology
///     Parser and Optimizer of Own Programming Language
///
/// PL: Projekt TKOM (Techniki kompilacji)
///     PW WEiTI 20L
///     Parser i optymalizator wlasnego jezyka programowania
///
/// Copyright (C) Robert Dudzinski 2020
///
/// File: IfStatement.java

import java.util.Set;
import java.util.TreeSet;

public class IfStatement extends Statement
{
    private LogicalStatement condition = null;
    private Block block = null;
    private Block elseBlock = null;

    public void setCondition(LogicalStatement condition)
    {
        this.condition = condition;
    }

    public void setBlock(Block block)
    {
        this.block = block;
        this.block.addPreExpression(condition);
        this.block.blockOptimizer();
    }

    public void setElseBlock(Block block)
    {
        this.elseBlock = block;
        this.elseBlock.blockOptimizer();
    }

    @Override
    public String toString()
    {
        String result =  "if (" + condition + ")" + block;

        if (elseBlock != null)
        {
            result += elseBlock.offset() + "else" + elseBlock;
        }

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
    public boolean optimize()
    {
        return block.optimize();
    }
}

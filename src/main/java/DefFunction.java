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
/// File: DefFunction.java

public class DefFunction
{
    private String mainFunction;
    private Block mainBlock;

    public void setMainFunction(String func)
    {
        mainFunction = func;
    }

    public void setMainBlock(Block block)
    {
        mainBlock = block;
        mainBlock.blockOptimizer();
    }

    @Override
    public String toString()
    {
        return "\nvoid " + mainFunction + "()" + mainBlock;
    }

    public Block getBlock()
    {
        return mainBlock;
    }

    public boolean optimize()
    {
        return mainBlock.optimize();
    }
}

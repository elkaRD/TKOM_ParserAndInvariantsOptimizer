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
/// File: Block.java

import java.util.*;

public class Block extends Statement
{
    private List<Statement> statements = new ArrayList<>();
    private boolean bracketsRequired = false;
    private Block parentBlock = null;
    private Statement owner = this;

    private int level;

    //Objects necessary to optimize block
    private boolean blockedOptimizer = false;
    private List<Statement> preStatements = new ArrayList<>();
    private List<Statement> postStatements = new ArrayList<>();
    private List<Expression> preExpressions = new ArrayList<>();
    public Map<String, LocalVar> localVars = new HashMap<>();


    public void addStatement(Statement statement)
    {
        statements.add(statement);
    }

    public void setParentBlock(Block block)
    {
        parentBlock = block;
    }

    public Block getParentBlock()
    {
        return parentBlock;
    }

    public void setOwner(Statement owner)
    {
        this.owner = owner;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public void requireBrackets()
    {
        bracketsRequired = true;
    }

    @Override
    public String toString()
    {
        if (statements.size() == 0)
            return "\n" + offset() + "{}\n";

        String result = "\n";
        if (bracketsRequired)
            result += offset() + "{\n";
        for (Statement statement : statements)
        {
            result += offset() + "\t" + statement;
            if (isSemicolonNeeded(statement))
                result += ";\n";
        }
        if (bracketsRequired)
            result += offset() + "}\n";

        return result;
    }

    public String offset()
    {
        String result = "";
        for (int i = 0; i < level; i++)
            result += '\t';
        return result;
    }

    private boolean isSemicolonNeeded(Statement statement)
    {
        if (statement instanceof ForStatement)
            return false;
        if (statement instanceof IfStatement)
            return false;
        if (statement instanceof WhileStatement)
            return false;
        if (statement instanceof Block)
            return false;

        return true;
    }

    public void moveStatementHigher(Statement statement)
    {
        statements.remove(statement);
        int index = parentBlock.statements.indexOf(owner);
        parentBlock.statements.add(index, statement);
    }

    private void handleReadVars(Set<String> usedVars, Set<String> declaredVars, Set<String> result, int counter)
    {
        for (String var : usedVars)
        {
            if (!declaredVars.contains(var) && !result.contains(var))
            {
                result.add(var);
            }
            if (!declaredVars.contains(var))
            {
                if (!localVars.containsKey(var))
                    localVars.put(var, new LocalVar());
                localVars.get(var).addRead(counter);
            }
        }
    }

    private void handleWrittenVars(Set<String> usedVars, Set<String> declaredVars, Set<String> result, int counter)
    {
        for (String var : usedVars)
        {
            if (!declaredVars.contains(var) && !result.contains(var))
            {
                result.add(var);
            }
            if (!declaredVars.contains(var))
            {
                if (!localVars.containsKey(var))
                    localVars.put(var, new LocalVar());
                localVars.get(var).addWrite(counter);
            }
        }
    }

    @Override
    public Set<String> getReadVars()
    {
        Set<String> result = new TreeSet<>();
        Set<String> declaredVars = new TreeSet<>();

        int counter = 0;

        for (Statement st : preStatements)
        {
            declaredVars.addAll(st.getDeclaredVars());
            Set<String> usedVars = st.getReadVars();

            handleReadVars(usedVars, declaredVars, result, counter);
            counter++;
        }

        for (Expression expr : preExpressions)
        {
            Set<String> usedVars = expr.getVars();

            handleReadVars(usedVars, declaredVars, result, counter);
            counter++;
        }

        for (Statement st : statements)
        {
            declaredVars.addAll(st.getDeclaredVars());
            Set<String> usedVars = st.getReadVars();

            handleReadVars(usedVars, declaredVars, result, counter);
            counter++;
        }

        for (Statement st : postStatements)
        {
            declaredVars.addAll(st.getDeclaredVars());
            Set<String> usedVars = st.getReadVars();

            handleReadVars(usedVars, declaredVars, result, counter);
            counter++;
        }

        return result;
    }

    @Override
    public Set<String> getWrittenVars()
    {
        Set<String> result = new TreeSet<>();
        Set<String> declaredVars = new TreeSet<>();

        int counter = 0;

        for (Statement st : preStatements)
        {
            declaredVars.addAll(st.getDeclaredVars());
            Set<String> usedVars = st.getWrittenVars();

            handleWrittenVars(usedVars, declaredVars, result, counter);
            counter++;
        }

        counter += preExpressions.size();

        for (Statement st : statements)
        {
            declaredVars.addAll(st.getDeclaredVars());
            Set<String> usedVars = st.getWrittenVars();

            handleWrittenVars(usedVars, declaredVars, result, counter);
            counter++;
        }

        for (Statement st : postStatements)
        {
            declaredVars.addAll(st.getDeclaredVars());
            Set<String> usedVars = st.getWrittenVars();

            handleWrittenVars(usedVars, declaredVars, result, counter);
            counter++;
        }

        return result;
    }

    public void addPreStatement(Statement statement)
    {
        if (statement == null)
            return;

        preStatements.add(statement);
    }

    public void addPreExpression(Expression expr)
    {
        if (expr == null)
            return;

        preExpressions.add(expr);
    }

    public void addPostStatement(Statement statement)
    {
        if (statement == null)
            return;

        postStatements.add(statement);
    }

    private boolean checkUsedVars(Set<String> read)
    {
        for (String readVar : read)
        {
            //variables used by invariant can't be declared in the current block
            if (!localVars.containsKey(readVar))
            {
                return false;
            }

            //used variables can't be modified in current block
            if (localVars.get(readVar).getWrites().size() != 0)
            {
                return false;
            }
        }
        return true;
    }

    private boolean checkIfUsedBefore(String var)
    {
        Set<Integer> readLines = localVars.get(var).getReads();
        for (Integer line : readLines)
        {
            if (line <= localVars.get(var).getWrites().iterator().next())
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean optimize()
    {
        boolean childOptimized = true;
        boolean childSomethingChanged = false;

        //launch optimizer for all children
        while (childOptimized == true)
        {
            childOptimized = false;
            for (Statement statement : statements)
            {
                if (statement.optimize())
                {
                    childSomethingChanged = true;
                    childOptimized = true;
                    break;
                }
            }
        }

        //if we are in the block that can't participate in optimization (like 'if' block) then skip optimization
        if (blockedOptimizer)
            return childSomethingChanged;

        //at this point all children are optimized and current block supports optimization, so we start optimizing
        boolean changedSomething = true;
        boolean result = false;

        //trying iterate as long as we find new invariants (removing one invariant can unlock other invariants)
        while (changedSomething == true)
        {
            changedSomething = false;

            //get local variables declared outside current block
            localVars.clear();
            getWrittenVars();
            getReadVars();

            //iterate over all statements to find out which are invariants
            for (Statement statement : statements)
            {
                //only AssignVar can be invariant
                if (!(statement instanceof AssignVar))
                    continue;

                //written (and var) is modified variable; read contains all variables on right side of = and all variables from index in case of table variables
                Set<String> written = statement.getWrittenVars();
                Set<String> read = statement.getReadVars();

                String var = written.iterator().next();

                //if localVars doesn't contain our var, it means that this var is declared in current block - we can't remove it
                if (!localVars.containsKey(var))
                    continue;

                //we can only remove variables that are being modified only once
                if (localVars.get(var).getWrites().size() != 1)
                    continue;

                if (!checkUsedVars(read))
                    continue;

                //potential invariant cannot be used before
                if (checkIfUsedBefore(var))
                    continue;

                //If we met all required conditions to optimize then we reached this fragment and can move statement to higher block
                moveStatementHigher(statement);
                changedSomething = true;
                result = true;
                break;
            }
        }

        return result;
    }

    public void blockOptimizer()
    {
        blockedOptimizer = true;
    }
}

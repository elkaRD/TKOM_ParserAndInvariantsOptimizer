import java.util.Set;
import java.util.TreeSet;

public class LocalVar
{
    private String var;

    private Set<Integer> reads = new TreeSet<>();
    private Set<Integer> writes = new TreeSet<>();

    private int numberOfReads = 0;
    private int numberOfWrites = 0;

    private int initPos = -1;
    private boolean declaredHere = true;
    private LocalVar replacedVar = null;

    private boolean array = false;

    private Expression firstWriteValue = null;

    public String getVar()
    {
        return var;
    }

    public void setVar(Var var)
    {
        this.var = var.getName();
        this.array = var.getIndex() != null;
    }

    public boolean isDeclaredHere()
    {
        return declaredHere;
    }

    public LocalVar getReplacedVar()
    {
        return replacedVar;
    }

    public void setReplacedVar(LocalVar replacedVar)
    {
        this.replacedVar = replacedVar;
    }

    public LocalVar copyForInheritedScope()
    {
        LocalVar newVar = new LocalVar();
        newVar.array = this.array;

        newVar.declaredHere = false;
        newVar.replacedVar = this;

        return newVar;
    }

    public boolean isArray()
    {
        return array;
    }

    public void addRead(int readLine)
    {
        reads.add(readLine);
        numberOfReads++;
    }

    public void addWrite(int writeLine)
    {
        writes.add(writeLine);
        numberOfWrites++;
    }

    public void setInitPos(int initPos)
    {
        this.initPos = initPos;
    }

    public int getInitPos()
    {
        return this.initPos;
    }

    public Set<Integer> getReads()
    {
        return reads;
    }

    public Set<Integer> getWrites()
    {
        return writes;
    }
}

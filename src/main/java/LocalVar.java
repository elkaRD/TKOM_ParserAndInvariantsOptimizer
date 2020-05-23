public class LocalVar
{
    private String var;
    private int initPos = -1;
    private int firstRead = -1;
    private int firstWrite = -1;
    private int lastRead = -1;
    private int lastWrite = -1;

    private int numberOfReads = 0;
    private int numberOfWrites = 0;

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

    public int getInitPos()
    {
        return initPos;
    }

    public void setInitPos(int initPos)
    {
        this.initPos = initPos;
    }

    public int getFirstRead()
    {
        return firstRead;
    }

    public void setFirstRead(int firstRead)
    {
        this.firstRead = firstRead;
    }

    public int getFirstWrite()
    {
        return firstWrite;
    }

    public void setFirstWrite(int firstWrite)
    {
        this.firstWrite = firstWrite;
    }

    public int getLastRead()
    {
        return lastRead;
    }

    public void setLastRead(int lastRead)
    {
        this.lastRead = lastRead;
    }

    public int getLastWrite()
    {
        return lastWrite;
    }

    public void setLastWrite(int lastWrite)
    {
        this.lastWrite = lastWrite;
    }

    public boolean isDeclaredHere()
    {
        return declaredHere;
    }

    public void setDeclaredHere(boolean declaredHere)
    {
        this.declaredHere = declaredHere;
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
        newVar.initPos = -1;

        newVar.firstRead = this.firstRead;
        newVar.firstWrite = this.firstWrite;
        newVar.lastRead = this.lastRead;
        newVar.lastWrite = this.lastWrite;
        newVar.array = this.array;

        newVar.declaredHere = false;

        return newVar;
    }

    public boolean isNotModified()
    {
        return firstWrite == -1;
    }

    public boolean hasConstWrite()
    {
        //TODO: implement
        return true;
    }

    public boolean isArray()
    {
        return array;
    }

    public void setArray(boolean array)
    {
        this.array = array;
    }
}

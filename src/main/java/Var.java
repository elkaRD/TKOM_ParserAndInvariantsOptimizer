public class Var extends Expression
{
    private String name;
    private Expression index = null;

    public void setName(String name)
    {
        this.name = name;
    }

    public void setIndex(Expression index)
    {
        this.index = index;
    }

    public String getName()
    {
        return this.name;
    }

    public Expression getIndex()
    {
        return this.index;
    }

    @Override
    public String toString()
    {
        String result = name;
        if (index != null)
            result += "[" + index + "]";

        return result;
    }
}

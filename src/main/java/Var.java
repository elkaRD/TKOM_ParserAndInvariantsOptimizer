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
}

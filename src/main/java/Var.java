import java.util.ArrayList;
import java.util.List;

public class Var extends Expression
{
    private String name;
    private Expression index = null;

    private CharPos pos = null;

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

    public boolean isArray()
    {
        return index != null;
    }

    public CharPos getPos()
    {
        return pos;
    }

    public void setPos(CharPos pos)
    {
        this.pos = pos;
    }

    @Override
    public String toString()
    {
        String result = name;
        if (index != null)
            result += "[" + index + "]";

        return result;
    }

    @Override
    public List<String> getVars()
    {
        List<String> result = new ArrayList<>();
        result.add(name);
        return result;
    }
}

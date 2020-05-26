import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Statement
{
    private CharPos pos;

    public CharPos getPos()
    {
        return pos;
    }

    public void setPos(CharPos pos)
    {
        this.pos = pos;
    }

    public Set<String> getReadVars()
    {
        return new TreeSet<>();
    }

    public Set<String> getWrittenVars()
    {
        return new TreeSet<>();
    }

    public Set<String> getDeclaredVars()
    {
        return new TreeSet<>();
    }

    public void fillEnvironment(Environment environment, int linesOffset)
    {

    }
}

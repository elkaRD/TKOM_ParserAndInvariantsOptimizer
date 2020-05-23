import java.util.ArrayList;
import java.util.List;

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

    public List<String> getReadVars()
    {
        return null;
    }

    public List<String> getWrittenVars()
    {
        return null;
    }
}

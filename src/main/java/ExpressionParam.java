import java.util.ArrayList;
import java.util.List;

public class ExpressionParam extends Expression
{
    private boolean negative = false;
    private boolean areBracketsRequired = false;

    public void setNegative()
    {
        negative = true;
    }

    public void bracketsRequired()
    {
        areBracketsRequired = true;
    }

    @Override
    public String toString()
    {
        String result = "";

        if (negative)
            result += "-";

        if (areBracketsRequired)
            return result + "(" + super.toString() + ")";

        return result + super.toString();
    }
}

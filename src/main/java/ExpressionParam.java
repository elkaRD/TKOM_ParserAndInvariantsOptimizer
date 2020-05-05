public class ExpressionParam extends Expression
{

    //TODO: toString() if not a var and const_val then put expression into brackets

    private boolean negative = false;
    private boolean areBracketsRequired = false;

    public void setNegative()
    {
        negative = true;
        //areBracketsRequired = true;
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

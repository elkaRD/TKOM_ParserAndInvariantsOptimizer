public class VarValue extends Expression
{
    private Token constValue = null;
    private boolean negative = false;

    public void setValue(Token constValue)
    {
        this.constValue = constValue;
    }

    public void setNegative()
    {
        negative = true;
    }

    @Override
    public String toString()
    {
        return "" + constValue;
    }
}

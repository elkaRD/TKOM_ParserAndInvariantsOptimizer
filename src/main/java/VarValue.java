public class VarValue extends Expression
{
    private Token constValue = null;

    public void setValue(Token constValue)
    {
        this.constValue = constValue;
    }

    @Override
    public String toString()
    {
        return "" + constValue;
    }
}

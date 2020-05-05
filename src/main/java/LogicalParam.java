public class LogicalParam extends LogicalStatement
{
    private boolean negation = false;

    public void setNegation()
    {
        negation = true;
    }

    @Override
    public String toString()
    {
        if (!negation)
            return super.toString();

        return "!(" + super.toString() + ")";
    }
}

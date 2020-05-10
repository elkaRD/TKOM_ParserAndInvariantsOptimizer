public class LogicalParam extends LogicalStatement
{
    private boolean negation = false;
    private boolean bracketsNeeded = false;

    public void setNegation()
    {
        negation = true;
    }

    public void needBrackets()
    {
        bracketsNeeded = true;
    }

    @Override
    public String toString()
    {
        String result = "";

        if (negation)
            result += "!";
        if (bracketsNeeded)
            result += "(";

        result += super.toString();

        if (bracketsNeeded)
            result += ")";

        return result;

//        if (!negation)
//        {
//            if (!bracketsNeeded)
//                return super.toString();
//
//        }
//
//        return "!(" + super.toString() + ")";
    }
}

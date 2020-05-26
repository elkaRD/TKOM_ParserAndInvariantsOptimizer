import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Expression
{
    protected List<Expression> expressions = new ArrayList<>();
    protected List<TokenType> operators = new ArrayList<>();

    public void addExpression(Expression expression)
    {
        expressions.add(expression);
    }

    public void addOperator(Token operator)
    {
        operators.add(operator.type);
    }

    @Override
    public String toString()
    {
        String result = "" + expressions.get(0);

        for (int i = 0; i < operators.size(); i++)
        {
            result += " " + ReservedTokens.getInstance().getStr(operators.get(i)) + " ";
            result += expressions.get(i+1);
        }

        return result;
    }

    public Set<String> getVars()
    {
        Set<String> result = new TreeSet<>();

        for (Expression expr : expressions)
        {
            result.addAll(expr.getVars());
        }

        return result;
    }
}

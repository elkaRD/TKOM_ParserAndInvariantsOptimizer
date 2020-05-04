import java.util.ArrayList;
import java.util.List;

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
}

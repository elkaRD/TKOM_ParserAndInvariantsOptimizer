public class Optimizer
{
    public String optimize(Program program)
    {
        program.optimize();
        return program.toString();
    }
}

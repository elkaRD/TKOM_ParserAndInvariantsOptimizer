///
/// EN: Project for the ‘Compilation Techniques’ course
///     Warsaw University of Technology
///     Parser and Optimizer of Own Programming Language
///
/// PL: Projekt TKOM (Techniki kompilacji)
///     PW WEiTI 20L
///     Parser i optymalizator wlasnego jezyka programowania
///
/// Copyright (C) Robert Dudzinski 2020
///
/// File: TKOM.java

public class TKOM
{
    public static void main(String[] args)
    {

        if (args.length != 2)
        {
            System.out.println("Wrong number of params. First argument should be '-t' or '-f'.");
            System.out.println("");
            System.out.println("After '-t' should be source code to optimize");
            System.out.println("After '-f' should be path to file with source code to optimize");
            System.out.println("");
            System.out.println("");
            System.out.println("Example:");
            System.out.println("java -jar kod.jar -t \"void main() { int x; for (;;) x = 1; }\"");
            return;
        }

        String inputMode = args[0];

        InputManager input = new InputManager();

        if (inputMode.equals("-t"))
        {
            input.readText(args[1]);
        }
        else if (inputMode.equals("-f"))
        {
            input.readFile(args[1]);
        }
        else
        {
            System.out.println("Unsupported mode");
            return;
        }

        try
        {
            Parser parser = new Parser();
            Optimizer optimizer = new Optimizer();
            Program program = parser.parse(input);
            String result = optimizer.optimize(program);

            System.out.println(result);
        }
        catch (Exception e)
        {
            System.out.println("Got exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

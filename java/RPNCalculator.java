import java.io.BufferedReader; 
import java.io.InputStreamReader; 
import java.util.Stack;


//Normally, this class would go in its own package to better organize it and reduce name conflicts.
public class RPNCalculator{

    public static String INVALID_INPUT_ERROR_MSG = "This was not a valid input";
    public static String INVALID_OPERATOR_LOCATION_MESSAGE = "Invalid operator.  There must be at least two numbers in the stack before an operator.";
    public static String HELP_STRING = "Commands are:\n c clear stack\n p print stack\n u  undo\nValid operators are:\n +\n -\n *\n /\n";
    Stack<String> stack = new Stack<String>();
    Stack<Stack> stackHistory = new Stack<Stack>();

    public static void main(String [] args) throws java.io.IOException
    {
        RPNCalculator calc = new RPNCalculator();
        calc.runCommandLine();
    }

    public void runCommandLine()
    {
        try
        {
            //Loop forever.
            while(true)
            {
                //Get Input
                String input =  getInput();
                if(input.equals("q") )
                {
                    System.out.println("goodbye");
                    return;
                }
                //Send to calc
                String result = addInput(input);
                //Print response
                System.out.println(result);
            }
        }
        catch(Exception ex)
        {
            System.out.println("Caught exception"+ex );
            ex.printStackTrace();
        }
    }

    public String addInput(String input) 
    {
        if(isValidInput(input) )
        {
            return calculate(input);
        }
        else
        {
            return INVALID_INPUT_ERROR_MSG;
        }
    }

    public String calculate(String input)
    {
        if(isValidNumber(input) )
                return addNumberToStack(input);
        if(isValidOperator(input) )
            return performOperation(input);
        if(isValidCommand(input) )
            return executeCommand(input);
        return "Validation failed.  Should never get this error.";
    }

    public String addNumberToStack(String input)
    {
        stackHistory.push((Stack) stack.clone() );
        stack.push(input);
        return input;
    }

    public boolean isValidOperator(String input)
    {
        if(input==null)
            return false;
        if ( input.equals("+") ||
             input.equals("-") ||
             input.equals("/") ||
             input.equals("*") 
           )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public String performOperation(String input)
    {
        if( stack.size() < 2)
            return INVALID_OPERATOR_LOCATION_MESSAGE;

        Double num1 = Double.parseDouble(stack.pop() );
        Double num2 = Double.parseDouble(stack.pop() );
        Double answer = null;
        //Java case statements must be primitives
        int op = input.charAt(0);
        switch(op){
            case 42: // times
                answer = num1 * num2;
                break;
            case 43: // plus
                answer = num1 + num2;
                break;
            case 47: // divide
                answer = num2 / num1;
                break;
            case 45: // minus
                answer = num2 - num1;
                break;
        }
        String answer_string = formatNumber(answer);
        stackHistory.push((Stack) stack.clone() );
        stack.push(answer_string);
        return answer_string;
    }

    //Coerce 4.0 to display as 4
    public String formatNumber(Double answer)
    {
        int integer = answer.intValue();
        if(answer == integer)
            return ""+integer;
        else
            return answer.toString();

    }

    // I don't like having duplicate lists of commands here and in execute command.
    // There are ways to re-work the order of checking for valid operators and numbers
    // so executeCommand comes last, then any inputs you don't handle are 'invalid'.
    public boolean isValidCommand(String input)
    {
        if ( input.equals("q") ||
             input.equals("p") ||
             input.equals("u") ||
             input.equals("h") ||
             input.equals("c")
           )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public String executeCommand(String input)
    {
        if(!isValidCommand(input) )
            return INVALID_INPUT_ERROR_MSG;
        //Don't do anything.
        if(input.equals("h") )
            return HELP_STRING;
        if(input.equals("q") )
            return "";
        if(input.equals("c") )
            return clearStack();
        if(input.equals("p") )
            return printStack();
        if(input.equals("u") )
            return undoStack();

        return "isValidCommand Failed.  Should never get this error.";
    }

    public String undoStack(){
        stack = (Stack) stackHistory.pop();
        return "undo successful";
    }

    public String clearStack()
    {
        stackHistory.push((Stack) stack.clone() );
        stack = new Stack<String>();
        return "";
    }

    public String printStack()
    {
        String str="";
        for(int i=0; i < stack.size(); i++)
        {
            str = str + stack.get(i) + " ";
        }
        return str.trim();
    }

    public boolean isValidInput(String input)
    {
        if (input == null)
            return false;
        //If it's a command, operator, or number, return true, otherwise return false
        return isValidNumber(input) || isValidOperator(input) || isValidCommand(input);
    }

    public boolean isValidNumber(String input)
    {
        if(input == null)
            return false;
        try
        {
            Double d = Double.parseDouble(input);
            return true;
        }
        catch(NumberFormatException ex)
        {
            return false;
        }
    }

    public String getInput() throws java.io.IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); 
        System.out.print(">"); 
        String line = reader.readLine(); 
        return line;
    }
   

}

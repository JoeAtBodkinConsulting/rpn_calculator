import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Before;

public class RPNCalculatorTest {
  RPNCalculator calc;
  public static void main(String args[]) {
    org.junit.runner.JUnitCore.main("RPNCalculatorTest");
  }

    @Before
    public void setUp() throws Exception {
      calc = new RPNCalculator();
    }


    @Test
    public void testAddNumber(){
      assertEquals("add number", "3", calc.addNumberToStack("3") );
      assertEquals("add number", "-345678903", calc.addNumberToStack("-345678903") );
    }

    @Test
    public void testPerformUndo(){
      calc.calculate("12");
      calc.calculate("12");
      calc.calculate("c");
      assertEquals("print stack", "", calc.calculate("p") );
      calc.calculate("u");
      assertEquals("undo", "12 12", calc.calculate("p") );
    }

    @Test
    public void testPerformClearStack(){
      calc.calculate("12");
      calc.calculate("12");
      calc.calculate("c");
      assertEquals("print stack", "", calc.calculate("p") );
    }

    @Test
    public void testPerformPrintStack(){
      assertEquals("print stack", "", calc.calculate("p") );
      calc.calculate("5");
      calc.calculate("5");
      calc.calculate("5");
      assertEquals("print stack", "5 5 5", calc.calculate("p") );
      calc.calculate("+");
      assertEquals("print stack 2", "5 10", calc.calculate("p") );
    }

    @Test
    public void testPerformExampleCalculation(){
      calc.calculate("5");
      calc.calculate("1");
      calc.calculate("2");
      calc.calculate("+");
      calc.calculate("4");
      calc.calculate("*");
      calc.calculate("+");
      calc.calculate("3");
      assertEquals("example calculation", "14", calc.calculate("-") );
    }
    @Test
    public void testPerformCalculation(){
      calc.addNumberToStack("2");
      calc.addNumberToStack("2");
      String answer = calc.performOperation("-");
      assertEquals("add number", "0", answer );
      calc.addNumberToStack("2");
      calc.addNumberToStack("2");
      answer = calc.performOperation("+");
      assertEquals("add number", "4", answer );
      calc.addNumberToStack("2");
      calc.addNumberToStack("3");
      answer = calc.performOperation("*");
      assertEquals("add number", "6", answer );
      calc.addNumberToStack("10");
      calc.addNumberToStack("2");
      answer = calc.performOperation("/");
      assertEquals("add number", "5", answer );
    }

    @Test
    public void testValidOperator(){
      assertEquals("plus", true, calc.isValidOperator("+") );
      assertEquals("minus", true, calc.isValidOperator("-") );
      assertEquals("divide", true, calc.isValidOperator("/") );
      assertEquals("times", true, calc.isValidOperator("*") );
      assertEquals("float input", false, calc.isValidOperator("42") );
      assertEquals("character input", false, calc.isValidOperator("l") );
      assertEquals("empty input", false, calc.isValidOperator("") );
      assertEquals("null input", false, calc.isValidOperator(null) );
    }

    @Test
    public void testValidNumber(){
      assertEquals("simple number", true, calc.isValidNumber("3") );
      assertEquals("float input", true, calc.isValidNumber(".1001") );
      assertEquals("float input", true, calc.isValidNumber(".1000000000000000001") );
      assertEquals("exponential input", true, calc.isValidNumber("10E4") );
      assertEquals("character input", false, calc.isValidNumber("l") );
      assertEquals("test empty input", false, calc.isValidNumber("") );
      assertEquals("test null input", false, calc.isValidNumber(null) );
    }

    @Test
    public void testInput(){
      assertEquals("test easy input", true, calc.isValidInput("3") );
      assertEquals("test float input", true, calc.isValidInput(".1001") );
      assertEquals("test character input", false, calc.isValidInput("l") );
      assertEquals("test equation input", false, calc.isValidInput("7 + 4") );
      assertEquals("test empty input", false, calc.isValidInput("") );
      assertEquals("test null input", false, calc.isValidInput(null) );
    }

}

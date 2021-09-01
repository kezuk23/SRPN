import java.util.Stack;
import java.lang.Math;
import java.math.BigInteger;
import java.util.Random;
/**
 * Program class for an SRPN calculator. 
 */

public class SRPN {
  
  //user input integer store for operators 
  //results will be within integer limits (/ %)
  private int num1;
  private int num2;
  private int result;

  // user input long store for operators
  //results can be larger numbers than int limits i.e. + - *
  private long long1; 
  private long long2;
  private long longResult;

  //user input double store for largest operator
  //out of long limits i.e ^
  private double numDouble1;
  private double numDouble2;
  private double resultDouble;

  // a stack to take multiple inputs
  // the stack holds integers so some calculations 
  // need to typecast before being pushed to stack
  private Stack<Integer> calculator = new Stack<>(); 

  //keep track of the number of # entered by user
  private int hashCount = 0;

  public void longToIntPush(long i){ 
   //method for pushing long numbers onto an int stack
   //used in + - * calculations
    if (i > 2147483647){
      num1 = 2147483647;
      } else if (i < -2147483648){
        num1 = -2147483648;
      } else { 
        //typcast long to int
        num1 = (int)i;
      }
      calculator.push(num1);
  }

  //operators + - * / % ^
  //to optimise memory useage 
  //and for larger numbers out of int range
  //use long variable for + - * 
  //and double variable for ^

  public void additionMethod(){ //addition (+) method//
    num1 = calculator.pop();
    num2 = calculator.pop();
    //typecast int into long to accommodate larger nums 
    //e.g. int max + int max
    long1 = (long)num1;
    long2 = (long)num2;
    //calculation
    longResult = long1+long2;
    //back to int and push
    longToIntPush(longResult);
  }
  public void subtractionMethod(){ //subtraction (-) method//
    num1 = calculator.pop();
    num2 = calculator.pop();
    //typecast int into long
    long1 = (long)num1;
    long2 = (long)num2;
    //calculation
    longResult = long2-long1;
    //back to int and push
    longToIntPush(longResult);
  }
  public void multiplicationMethod(){ //multiplication (*) method//
    num1 = calculator.pop();
    num2 = calculator.pop();
    //typecast int into long
    long1 = (long)num1;
    long2 = (long)num2;
    //calculation
    longResult = long1*long2;
    //back to int and push
    longToIntPush(longResult);
  }
  public void divisionMethod(){ //integer division (/) method//
    num1 = calculator.pop();
    num2 = calculator.pop();
    //calculation and push result
    result = num2/num1;
    calculator.push(result);
  }
  public void remainderDivisionMethod(){ //remainder division (%) method//
    num1 = calculator.pop();
    num2 = calculator.pop();
    //calculation and push result
    result = num2%num1;
    calculator.push(result);
  }
  public void powerMethod(){ //positive exponents (^) method//
    num1 = calculator.pop();
    num2 = calculator.pop();
    //convert int to double for larger numbers 
    //and pow function: Math.pow(double, double) 
    numDouble1 = num1;
    numDouble2 = num2;
    resultDouble = Math.pow(numDouble2, numDouble1);
    //typecast double back to int for stack
    result = (int)resultDouble;
    calculator.push(result);
  }

  //main function for user input
  //will accept any input from user or throws NumberFormatException
  //includes "#", "d", and "r", special keys
  //number of user inputs limited to 24

  public void processCommand(String s){
  
    //before we process the regular inputs
    //need to consider special cases
    //split the user input to accommodate multiple 
    //number entries on single input line

    String str = s;
    String[] strArray = str.split(" |\\."); //will split String at " " or "."
    
    for (int x=0; x<strArray.length; x++){
      s = strArray[x]; //split String will now pass to the rest of the method

      //'#' character blocks code from being processed
      //hashCount will track the number of #'s from input
      //to accept and acknowledge the initial #
      if (s.equals("#")){
      hashCount = hashCount + 1;
      }
      //when hashCount is an odd number, 
      //i.e. one hash hasn't been undone by another hash, ignore input.
      //when hashCount is even, start reading again
      if (hashCount%2==1){
      //comment to show nothing will happen
      } else

      //main section for processing user input
      //below is the code for = + - * / % d r 
      //numbers and all other inputs
    
      //try catch for exceptions
      try {

        if (s.equals("#")){ //stop user input being processed within single input
          hashCount = hashCount + 1;
        }
      
        if (s.charAt(s.length()-1) == '='){ //if an "=" is entered
          //print top of stack
          //exception 1: empty stack
          boolean empty = calculator.empty();
          if (empty == true){
            System.out.println("Stack empty.");

          } else if (empty == false){
            //if stack has something in it, print it
            System.out.println(calculator.peek());
          }
        }
        else if (s.equals("d")){ //if a "d" is entered print stack
          //exception 1: empty stack 
          if (calculator.empty()==true){
            num1 = -2147483648;
            calculator.push(num1);
            System.out.println(num1);
          }
        else {
          //create an array to hold stack items
          //size of the stack = size of array
          int size = calculator.size();
          Integer[] list = new Integer[size];
          //add items to array from stack
          for (int j=0; j<=size-1; j++){
            list[j] = calculator.pop();
          }
          //print array in reverse
          for (int i=size-1; i>=0; i--){
            System.out.println(list[i]);
            //return items to stack
            calculator.push(list[i]);
          }
        }
        }
        else if (s.equals("r")){ //if a "r" is entered generate random number and push
          Random random1 = new Random();
          num1 = random1.nextInt();
          //SRPN only uses positive random numbers, so loop negatives out
          while (num1<0){
            num1 = random1.nextInt();
          }
          //push generated positive number
          calculator.push(num1);
        }
    
        //operators + - * / % ^
        //all operators will generate "Stack Underflow."
        //if num1 or num2 is vacant
        //each operator has its own associated method
        //at the top to help readability and maintenance
    
        else if (s.equals("+")){ //addition
          if (calculator.size() == 0 || calculator.size() == 1){
            System.out.println("Stack underflow.");
          } else {
            additionMethod();
            }
        } 
        else if (s.equals("-")){ //subtraction
          if (calculator.size() == 0 || calculator.size() == 1){
            System.out.println("Stack underflow.");
          } else {
            subtractionMethod();
            } 
        } 
        else if (s.equals("*")){ //multiplication
          if (calculator.size() == 0 || calculator.size() == 1){
            System.out.println("Stack underflow.");
          } else {
            multiplicationMethod(); 
            }
        }
        else if (s.equals("/")){ //integer division 
          if (calculator.size() == 0 || calculator.size() == 1){
            System.out.println("Stack underflow.");
          } else if (calculator.peek() == 0){ //avoid logical error
            System.out.println("Divide by 0.");
          } else {
            divisionMethod();
          }
        }
        else if (s.equals("%")){ //remainder division
          if (calculator.size() == 0 || calculator.size() == 1){
            System.out.println("Stack underflow.");
          } else {
            remainderDivisionMethod();
          }
        } 
        else if (s.equals("^")){ //power
          if (calculator.size() == 0 || calculator.size() == 1){
            System.out.println("Stack underflow.");
          } else {
              if (calculator.peek()<0){ //for power less than zero
                System.out.println("Negative power.");
              } else { //for power zero and above
                powerMethod();
              }
            }
        }

        //bring hashCount back to even number if # entered
        //and code entered will then be used in calculations
        else if (s.equals("#")) { //restarts user input processing as normal
        hashCount = hashCount + 1;
        }

        else { //numeric inputs
          //** program can calculate values from 
          //-2147483648 to +2147483647 but will accept 
          //any number and larger numbers will saturate to min or max**

          //create BigInteger for initial user input
          BigInteger userInputBigInt = new BigInteger(s);
          //max and min values program can calculate
          BigInteger maxInt = new BigInteger("2147483647");
          BigInteger minInt = new BigInteger("-2147483648");
          //compare user input with max/min integer values 
          int compareValueMax = userInputBigInt.compareTo(maxInt);
          int compareValueMin = userInputBigInt.compareTo(minInt);
          if (compareValueMax == 1) {//if userInputBigInt is larger than maxInt, push positive saturation number to stack
            calculator.push(2147483647);
            } else if (compareValueMin == -1) {//if userInputBigInt is less than minInt, push negative saturation number to stack
                calculator.push(-2147483648);
                } else { //number within int range, convert userInputBigInt to int and push to stack
                  num1 = userInputBigInt.intValue();
                  calculator.push(num1); 
                }
        }

        //max stack size for SRPN is 24
        if (calculator.size() == 24){
          System.out.println("Stack overflow.");
          calculator.pop();
          }
      } // closing the try 
      
      catch (NumberFormatException e){
        //enter a character not accepted
        System.out.println("Unrecognised operator or operand \"" + s + "\".");
      }
   
      catch (IllegalStateException e){
        System.out.println("- error -"); 
        //for anything really unsual that might stop the calculator
        //from functioning
      }
      
      catch (StringIndexOutOfBoundsException e){
        //press enter nothing happens 
      }

    } //close the split string for loop
  } //close processCommand
} //close SRPN class
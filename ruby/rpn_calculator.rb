
module BodkinConsulting
  class RpnCalculator
    CLEAR_STACK_MSG="clearing the stack and starting over"
    attr_accessor :stack #This probably needs to be private

    def initialize( allowed_ops=["+", "-", "/", "*"])
      @valid_operators=allowed_ops
      @stack_operator_min=2
      clear_state # in case we want to add more functionality later
    end

    def clear_state
      @stack=[] #Ruby uses Array as a stack, but it's not all that safe.  Might be better to wrap it like this http://macdevelopertips.com/ruby/using-a-stack-with-ruby.html
    end

    def calculate(stack)
      puts " * * stack before calculate = #{@stack.inspect} " if $DEBUG
      op = stack.pop
      num2=stack.pop.to_f
      num1=stack.pop.to_f
      puts " * * * * *  #{num1} #{op} #{num2} " if $DEBUG
      answer=(num1.send(op, num2) ).to_s #sending the operator and the second argument allows us to use any valid arguments, even if not in the valid list.
      # make "8.0" look like "8", as per examples
      answer = answer.to_f.floor.to_s if (answer =~ /\.0$/)
      stack.push answer
      puts " * * * * *  stack after calculate = #{@stack.inspect} " if $DEBUG
      return answer
      
    end

    def add_input(input)
        if valid?(input)
          @stack.push input
          if is_an_operator? input
            return calculate(@stack) #Passing the stack makes it easier to test 
          else
            return input
          end
        else
          return "Input [[#{input}]] not valid.  Valid inputs are number, operators #{@valid_operators.inspect} without the quotes, c to clear all values, or q to quit.  No changes made."
        end
    end
    def is_an_operator? input
      #It would be more flexible to check if the input is a callable function on the first number than to work off a constrained list
      return @valid_operators.include? input
    end

    #Note:  I got this from the internet while checking for string functions.  http://railsforum.com/viewtopic.php?id=19081
    def is_a_number?(s)
      s.to_s.match(/\A[+-]?\d+?(\.\d+)?\Z/) == nil ? false : true
    end

    def is_valid_operator_location?
      if @stack.size >=2
        return true
      else
        return false
      end
    end

    def valid?(input)
      #TODO Check for zero
      if is_a_number? input
        return true
      end
      if is_an_operator? input
        if is_valid_operator_location?
          return true
        else
          return false
        end
      end
      return false
    end 
    def process_from_command_line
      while true
        putc ">"
        input = gets.chomp.strip # no cr, no leading/trailing spaces
        if input == "c" #If clear all values
          clear_state
          puts CLEAR_STACK_MSG
          next
        elsif input == "q" #If quit, return
          puts "goodbye"
          return
        end
        puts add_input(input)
      end
    end
  end
end



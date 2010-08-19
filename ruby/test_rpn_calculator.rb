require 'test/unit'
require 'rpn_calculator'

class RpnCalculator_Test < Test::Unit::TestCase


  def setup
    @calc = BodkinConsulting::RpnCalculator.new()
  end

  def teardown
  end

  def test_true
    assert true, "True is true"
  end

  def test_valid_input
    assert @calc.valid?("0"), "0"
    assert @calc.valid?("1"), "1"
    assert @calc.valid?("100000000000000"), "100000000000000"
    assert !@calc.valid?("Bugblatter"), "Non-numeric string should not be valid"
    assert !@calc.valid?(""), "Empty String should not be valid"
    assert !@calc.valid?(" "), "Space should not be valid"
    assert !@calc.valid?("q"), "q"
    assert !@calc.valid?("3+3"), "3+3"
  end

  def test_is_a_number
    assert @calc.is_a_number?(1), "1"
    assert @calc.is_a_number?(0), "0"
    assert @calc.is_a_number?(1000000000000), "1000000000000"
    assert @calc.is_a_number?(-1), "-1"
    assert @calc.is_a_number?(-1000000000000), "-1000000000000"
    assert @calc.is_a_number?(-0), "-0"
    assert !@calc.is_a_number?("Non-numeric string should not be a number"), "Non-numeric string should not be a number"
    assert !@calc.is_a_number?("77 Non-numeric string with leading integer should not be a number"), "Non-numeric string with leading integer should not be a number"
  end

  def test_is_operator
    assert @calc.is_an_operator?("+"), "+"
    assert @calc.is_an_operator?("-"), "-"
    assert @calc.is_an_operator?("*"), "*"
    assert @calc.is_an_operator?("/"), "/"
    assert !@calc.is_an_operator?("?"), "?"
    assert !@calc.is_an_operator?("%"), "%"
    assert !@calc.is_an_operator?(""), ""
    assert !@calc.is_an_operator?(nil), nil
  end

  def test_add_input_failed
    assert @calc.add_input("0")=="0", "0"
    assert @calc.add_input("+")!="+", "+"
  end

  def test_add_input
    assert @calc.add_input("2")=="2", "2"
    assert @calc.add_input("2")=="2", "2"
    assert_equal @calc.add_input("+"), "4", "Adding 2 to 2"
  end

  def test_add_input_b
    assert_equal @calc.add_input("2"), "2", "2"
    assert_equal @calc.add_input("3"), "3", "3"
    assert_equal @calc.add_input("5"), "5", "5"
    assert_equal @calc.add_input("+"), "8", "Adding 3 to 5"
    assert_equal @calc.add_input("+"), "10", "Adding 8 to 10"
  end

  def test_multiply_input
    assert_equal @calc.add_input("3"), "3", "3"
    assert_equal @calc.add_input("5"), "5", "5"
    assert_equal @calc.add_input("*"), "15", "3 * 5"
  end

  def test_subtract_input
    assert_equal @calc.add_input("3"), "3", "3"
    assert_equal @calc.add_input("5"), "5", "5"
    assert_equal @calc.add_input("-"), "-2", "3-5"
  end

  def test_subtract_negatives
    assert_equal @calc.add_input("3"), "3", "3"
    assert_equal @calc.add_input("5"), "5", "5"
    assert_equal @calc.add_input("11"), "11", "11"
    assert_equal @calc.add_input("-"), "-6", "5-11"
    assert_equal @calc.add_input("-"), "9", "3 - -6"
  end

  def test_division
    assert_equal @calc.add_input("256"), "256", "256"
    assert_equal @calc.add_input("2"), "2", "2"
    assert_equal @calc.add_input("/"), "128", "128"
  end

  def test_division_floats
    assert_equal @calc.add_input("255"), "255", "255"
    assert_equal @calc.add_input("2"), "2", "2"
    assert_equal @calc.add_input("/"), "127.5", "127.5"
    assert_equal @calc.add_input("3"), "3", "3"
    assert_equal @calc.add_input("/"), "42.5", "42.5"
    assert_equal @calc.add_input("3"), "3", "3"
    assert_equal @calc.add_input("/"), "14.1666666666667", "14.1666666666667"
  end

  def test_advanced_math
    stack=["10", "3", "%"]
    assert_equal @calc.calculate(stack), "1", "Calculating modulo"
  end

end

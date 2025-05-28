# frozen_string_literal: true

require 'minitest/autorun'
require_relative 'bingo_board'

class BingoBoardBDDTest < Minitest::Test
  def test_a_newly_created_board_is_not_initialized
    given_a_bingo_board_of_size(1, 1)
    then_the_board_is_not_initialized
  end

  def test_when_all_fields_are_set_the_board_is_initialized
    any_value = '42'

    given_a_bingo_board_of_size(1, 1)
    when_cell_is_defined(0, 0, any_value)
    then_the_board_is_initialized
  end

  def test_when_all_fields_on_rectangular_board_are_set_it_is_initialized
    one = 'one, two, three'
    two = 'Bingo cells can contain any text'

    given_a_bingo_board_of_size(1, 2)
    when_cell_is_defined(0, 0, one)
    when_cell_is_defined(0, 1, two)
    then_the_board_is_initialized
  end

  def test_a_defined_cell_cant_be_redefined_even_if_its_the_same_value
    any_value = '42'

    given_a_bingo_board_of_size(1, 1)
    when_cell_is_defined(0, 0, any_value)
    
    assert_raises(BingoBoard::IllegalStateException) do
      when_cell_is_defined(0, 0, any_value)
    end
  end

  def test_duplicate_cells_are_not_allowed
    any_value = '42'

    given_a_bingo_board_of_size(2, 2)
    when_cell_is_defined(0, 1, any_value)
    
    assert_raises(BingoBoard::IllegalStateException) do
      when_cell_is_defined(1, 0, any_value)
    end
  end

  def test_a_cell_can_be_marked_on_initialized_board
    any_value = '42'

    given_a_bingo_board_of_size(1, 1)
    when_cell_is_defined(0, 0, any_value)
    when_cell_is_marked(0, 0)
    then_the_cell_is_marked(0, 0)
  end

  def test_a_non_initialized_board_cannot_be_marked
    given_a_bingo_board_of_size(1, 1)
    
    assert_raises(BingoBoard::IllegalStateException) do
      when_cell_is_marked(0, 0)
    end
  end

  private

  def given_a_bingo_board_of_size(width, height)
    @board = BingoBoard.new(width, height)
  end

  def when_cell_is_defined(x, y, value)
    @board.define_cell(x, y, value)
  end

  def when_cell_is_marked(x, y)
    @board.mark_cell(x, y)
  end

  def then_the_board_is_not_initialized
    refute board_initialized_state
  end

  def then_the_board_is_initialized
    assert board_initialized_state
  end

  def board_initialized_state
    @board.initialized?
  end

  def then_the_cell_is_marked(x, y)
    assert @board.marked?(x, y)
  end
end
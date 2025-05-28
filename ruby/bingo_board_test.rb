# frozen_string_literal: true

require "minitest/autorun"
require_relative "bingo_board"

class BingoBoardTest < Minitest::Test
  def test_newly_created_board_is_not_initialized
    board = BingoBoard.new(1, 1)
    refute board.initialized?
  end

  def test_when_all_fields_are_set_the_board_is_initialized
    board = BingoBoard.new(1, 1)
    board.define_cell(0, 0, "42")
    assert board.initialized?
  end

  def test_when_all_fields_on_rectangular_board_are_set_it_is_initialized
    board = BingoBoard.new(1, 2)
    board.define_cell(0, 0, "0, 0")
    board.define_cell(0, 1, "0, 1")
    assert board.initialized?
  end

  def test_a_defined_cell_can_not_be_redefined_even_if_its_the_same_value
    board = BingoBoard.new(1, 1)
    board.define_cell(0, 0, "42")

    assert_raises(BingoBoard::IllegalStateException) do
      board.define_cell(0, 0, "64")
    end
  end

  def test_duplicate_cells_are_not_allowed
    duplicate_value = "38"

    board = BingoBoard.new(2, 2)
    board.define_cell(0, 1, duplicate_value)

    assert_raises(BingoBoard::IllegalStateException) do
      board.define_cell(1, 0, duplicate_value)
    end
  end

  def test_a_non_initialized_board_cannot_be_marked
    board = BingoBoard.new(1, 1)

    assert_raises(BingoBoard::IllegalStateException) do
      board.mark_cell(0, 0)
    end
  end

  def test_when_cell_gets_marked_it_is_marked
    board = BingoBoard.new(1, 1)
    board.define_cell(0, 0, "42")
    board.mark_cell(0, 0)

    assert board.marked?(0, 0)
  end
end

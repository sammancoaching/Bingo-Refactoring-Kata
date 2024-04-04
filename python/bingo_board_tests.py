import unittest

from bingo_board import BingoBoard, IllegalStateException


class BingoBoardTestCase(unittest.TestCase):

    def test_newly_created_board_is_not_initialised(self) -> None:
        board = BingoBoard(1, 1)
        assert board.is_initialised() == False

    def test_when_all_fields_are_set_the_board_is_initialised(self) -> None:
        board = BingoBoard(1, 1)
        board.define_cell(0, 0, "42")
        assert board.is_initialised()

    def test_when_all_fields_on_rectangular_board_are_set_it_is_initialised(self) -> None:
        board = BingoBoard(1, 2)
        board.define_cell(0, 0, "0, 0")
        board.define_cell(0, 1, "0, 1")
        assert board.is_initialised()

    def test_a_defined_cell_can_not_be_redefined_even_if_its_the_same_value(self) -> None:
        board = BingoBoard(1, 1)
        board.define_cell(0, 0, "42")

        with self.assertRaises(IllegalStateException):
            board.define_cell(0, 0, "64")

    def test_duplicate_cells_are_not_allowed(self) -> None:
        duplicate_value = "38"

        board = BingoBoard(2, 2)
        board.define_cell(0, 1, duplicate_value)

        with self.assertRaises(IllegalStateException):
            board.define_cell(1, 0, duplicate_value)

    def test_a_non_initialised_board_cannot_be_marked(self) -> None:
        board = BingoBoard(1, 1)
        with self.assertRaises(IllegalStateException):
            board.mark_cell(0, 0)

    def test_when_all_cell_gets_marked_it_is_marked(self) -> None:
        board = BingoBoard(1, 1)
        board.define_cell(0, 0, "42")
        board.mark_cell(0, 0)

        assert board.is_marked(0, 0)
import unittest

from bingo_board import BingoBoard, IllegalStateException


class BingoBoardBDDTestCase(unittest.TestCase):

    def test_a_newly_created_board_is_not_initialised(self):
        self.given_a_bingo_board_of_size(1, 1)
        self.then_the_board_is_not_initialised()

    def test_when_all_fields_are_set_the_board_is_initialised(self):
        any_value = "42"

        self.given_a_bingo_board_of_size(1, 1)
        self.when_cell_is_defined(0, 0, any_value)
        self.then_the_board_is_initialised()

    def test_when_all_fields_on_rectangular_board_are_set_it_is_initialised(self):
        one = "one, two, three";
        two = "Bingo cells can contain any text";

        self.given_a_bingo_board_of_size(1, 2);
        self.when_cell_is_defined(0, 0, one);
        self.when_cell_is_defined(0, 1, two);
        self.then_the_board_is_initialised();

    def test_a_defined_cell_cant_be_redefined_even_if_its_the_same_value(self):
        any_value = "42"

        self.given_a_bingo_board_of_size(1, 1)
        self.when_cell_is_defined(0, 0, any_value)
        with self.assertRaises(IllegalStateException):
            self.when_cell_is_defined(0, 0, any_value)

    def test_duplicate_cells_are_not_allowed(self):
        any_value = "42"

        self.given_a_bingo_board_of_size(2, 2)
        self.when_cell_is_defined(0, 1, any_value)
        with self.assertRaises(IllegalStateException):
            self.when_cell_is_defined(1, 0, any_value)

    def test_a_non_initialised_board_cannot_be_marked(self):
        any_value = "42"

        self.given_a_bingo_board_of_size(1, 1)
        self.when_cell_is_defined(0, 0, any_value)
        self.when_cell_is_marked(0, 0)
        self.then_the_cell_is_marked(0, 0)

    def given_a_bingo_board_of_size(self, width: int, height: int):
        self.board = BingoBoard(width, height)

    def when_cell_is_defined(self, x: int, y: int, value: str):
        self.board.define_cell(x, y, value)

    def when_cell_is_marked(self, x: int, y: int):
        self.board.mark_cell(x, y)

    def then_the_board_is_not_initialised(self):
        assert self.board_initialised_state() == False

    def then_the_board_is_initialised(self):
        assert self.board_initialised_state() == True

    def board_initialised_state(self):
        return self.board.is_initialised()

    def then_the_cell_is_marked(self, x: int, y: int):
        assert self.board.is_marked(x, y) == True


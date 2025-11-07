#!/usr/bin/env bash

# Source the bingo_board.sh file
# Use dirname of BASH_SOURCE to get the directory containing this script
source "$(dirname "${BASH_SOURCE[0]}")/bingo_board.sh"

function test_newly_created_board_is_not_initialised() {
    bingo_board_new 1 1

    assert_false bingo_board_is_initialised
}

function test_when_all_fields_are_set_the_board_is_initialised() {
    bingo_board_new 1 1
    bingo_board_define_cell 0 0 "42"

    assert_true bingo_board_is_initialised
}

function test_when_all_fields_on_rectangular_board_are_set_it_is_initialised() {
    bingo_board_new 1 2
    bingo_board_define_cell 0 0 "0, 0"
    bingo_board_define_cell 0 1 "0, 1"

    assert_true bingo_board_is_initialised
}

function test_a_defined_cell_cannot_be_redefined_even_if_its_the_same_value() {
    bingo_board_new 1 1
    bingo_board_define_cell 0 0 "42"

    assert_false bingo_board_define_cell 0 0 "64"
}

function test_duplicate_cells_are_not_allowed() {
    local duplicate_value="38"

    bingo_board_new 2 2
    bingo_board_define_cell 0 1 "$duplicate_value"

    assert_false bingo_board_define_cell 1 0 "$duplicate_value"
}

function test_a_non_initialised_board_cannot_be_marked() {
    bingo_board_new 1 1

    assert_false bingo_board_mark_cell 0 0
}

function test_when_a_cell_gets_marked_it_is_marked() {
    bingo_board_new 1 1
    bingo_board_define_cell 0 0 "42"
    bingo_board_mark_cell 0 0

    assert_true bingo_board_is_marked 0 0
}

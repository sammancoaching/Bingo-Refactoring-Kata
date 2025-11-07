#!/usr/bin/env bash

# Source the bingo_board.sh file
# Use dirname of BASH_SOURCE to get the directory containing this script
source "$(dirname "${BASH_SOURCE[0]}")/bingo_board.sh"

function test_bdd_a_newly_created_board_is_not_initialised() {
    given_a_bingo_board_of_size 1 1
    then_the_board_is_not_initialised
}

function test_bdd_when_all_fields_are_set_the_board_is_initialised() {
    local any_value="42"

    given_a_bingo_board_of_size 1 1
    when_cell_is_defined 0 0 "$any_value"
    then_the_board_is_initialised
}

function test_bdd_when_all_fields_on_rectangular_board_are_set_it_is_initialised() {
    local one="one, two, three"
    local two="Bingo cells can contain any text"

    given_a_bingo_board_of_size 1 2
    when_cell_is_defined 0 0 "$one"
    when_cell_is_defined 0 1 "$two"
    then_the_board_is_initialised
}

function test_bdd_a_defined_cell_cant_be_redefined_even_if_its_the_same_value() {
    local any_value="42"

    given_a_bingo_board_of_size 1 1
    when_cell_is_defined 0 0 "$any_value"
    assert_false when_cell_is_defined 0 0 "$any_value"
}

function test_bdd_duplicate_cells_are_not_allowed() {
    local any_value="42"

    given_a_bingo_board_of_size 2 2
    when_cell_is_defined 0 1 "$any_value"
    assert_false when_cell_is_defined 1 0 "$any_value"
}

function test_bdd_a_non_initialised_board_cannot_be_marked() {
    local any_value="42"

    given_a_bingo_board_of_size 1 1
    when_cell_is_defined 0 0 "$any_value"
    when_cell_is_marked 0 0
    then_the_cell_is_marked 0 0
}

# BDD helper functions

function given_a_bingo_board_of_size() {
    local width=$1
    local height=$2

    bingo_board_new "$width" "$height"
}

function when_cell_is_defined() {
    local x=$1
    local y=$2
    local value=$3

    bingo_board_define_cell "$x" "$y" "$value"
}

function when_cell_is_marked() {
    local x=$1
    local y=$2

    bingo_board_mark_cell "$x" "$y"
}

function then_the_board_is_not_initialised() {
    assert_false board_initialised_state
}

function then_the_board_is_initialised() {
    assert_true board_initialised_state
}

function board_initialised_state() {
    bingo_board_is_initialised
}

function then_the_cell_is_marked() {
    local x=$1
    local y=$2

    assert_true bingo_board_is_marked "$x" "$y"
}

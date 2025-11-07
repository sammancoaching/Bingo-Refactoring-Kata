#!/usr/bin/env bash

# BingoBoard implementation in bash compatible with Bash 3.2+
# Uses indexed arrays instead of associative arrays

declare -a BINGO_CELLS
declare -a BINGO_MARKED
BINGO_WIDTH=0
BINGO_HEIGHT=0

# Helper function to calculate array index from x,y coordinates
_bingo_board_get_index() {
    local x=$1
    local y=$2
    echo $((x * BINGO_HEIGHT + y))
}

# Creates a new bingo board of the given dimensions
# Arguments:
#   $1: width
#   $2: height
bingo_board_new() {
    local width=$1
    local height=$2
    local total_cells=$((width * height))

    BINGO_WIDTH=$width
    BINGO_HEIGHT=$height
    BINGO_CELLS=()
    BINGO_MARKED=()

    # Pre-initialize all array elements to support sparse arrays
    local i=0
    while [[ $i -lt $total_cells ]]; do
        BINGO_CELLS[$i]=""
        BINGO_MARKED[$i]=""
        i=$((i + 1))
    done
}

# Defines a cell with a value
# Arguments:
#   $1: x coordinate
#   $2: y coordinate
#   $3: value
# Returns:
#   0 on success
#   1 on error (prints error message to stderr)
bingo_board_define_cell() {
    local x=$1
    local y=$2
    local value=$3
    local index=$(_bingo_board_get_index "$x" "$y")

    # Check if cell is already defined
    if [[ -n "${BINGO_CELLS[$index]}" ]]; then
        echo "cell already defined" >&2
        return 1
    fi

    # Check for duplicate values
    local i=0
    while [[ $i -lt ${#BINGO_CELLS[@]} ]]; do
        if [[ "${BINGO_CELLS[$i]}" == "$value" ]]; then
            local cell_x=$((i / BINGO_HEIGHT))
            local cell_y=$((i % BINGO_HEIGHT))
            echo "$value already present at $cell_x, $cell_y" >&2
            return 1
        fi
        i=$((i + 1))
    done

    BINGO_CELLS[$index]="$value"
    return 0
}

# Marks a cell
# Arguments:
#   $1: x coordinate
#   $2: y coordinate
# Returns:
#   0 on success
#   1 on error (prints error message to stderr)
bingo_board_mark_cell() {
    local x=$1
    local y=$2
    local index=$(_bingo_board_get_index "$x" "$y")

    # Check if board is initialised
    if ! bingo_board_is_initialised; then
        echo "board is not initialised" >&2
        return 1
    fi

    BINGO_MARKED[$index]=1
    return 0
}

# Checks if the board is fully initialised (all cells defined)
# Returns:
#   0 if initialised
#   1 if not initialised
bingo_board_is_initialised() {
    local expected_cells=$((BINGO_WIDTH * BINGO_HEIGHT))

    # Count non-empty cells
    local actual_cells=0
    local i=0
    while [[ $i -lt $expected_cells ]]; do
        if [[ -n "${BINGO_CELLS[$i]}" ]]; then
            actual_cells=$((actual_cells + 1))
        fi
        i=$((i + 1))
    done

    if [[ $actual_cells -eq $expected_cells ]]; then
        return 0
    else
        return 1
    fi
}

# Checks if a cell is marked
# Arguments:
#   $1: x coordinate
#   $2: y coordinate
# Returns:
#   0 if marked
#   1 if not marked
bingo_board_is_marked() {
    local x=$1
    local y=$2
    local index=$(_bingo_board_get_index "$x" "$y")

    if [[ "${BINGO_MARKED[$index]}" == "1" ]]; then
        return 0
    else
        return 1
    fi
}

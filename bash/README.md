# Bingo Board - Bash Implementation

This is a Bash implementation of the Bingo Board refactoring kata using [bashunit](https://bashunit.typeddevs.com/) as the testing framework.

## Requirements

- Bash 3.2 or newer
- [bashunit](https://github.com/TypedDevs/bashunit) testing framework

## Installation

### Install bashunit

```bash
curl -s https://bashunit.typeddevs.com/install.sh | bash
```

This will create a `lib` directory with the bashunit executable.

## Running the Tests

Once bashunit is installed, you can run the tests:

### If installed via install script:
```bash
./lib/bashunit *_test.sh
```

## Implementation Details

The Bash implementation uses indexed arrays (compatible with Bash 3.2+) to store the board state:
- `BINGO_CELLS`: Array storing cell values
- `BINGO_MARKED`: Array storing marked cell status
- Coordinates (x, y) are converted to array indices using: `index = x * height + y`

## Test Files

- `bingo_board_test.sh`: Standard unit tests
- `bingo_board_bdd_test.sh`: BDD-style tests with given/when/then structure

As you refactor, lean on the tests. Note which of them is easier to understand and update as you refactor.

## API

### Functions

- `bingo_board_new <width> <height>` - Creates a new bingo board
- `bingo_board_define_cell <x> <y> <value>` - Defines a cell with a value
- `bingo_board_mark_cell <x> <y>` - Marks a cell
- `bingo_board_is_initialised` - Checks if all cells are defined
- `bingo_board_is_marked <x> <y>` - Checks if a cell is marked

All functions return 0 on success and 1 on failure, following Bash conventions.

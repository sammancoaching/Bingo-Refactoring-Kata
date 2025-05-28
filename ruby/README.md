# Ruby version of Bingo-Refactoring-Kata

This is the Ruby implementation of the Bingo-Refactoring-Kata.

## Prerequisites

- Ruby 2.7 or newer
- Bundler gem

## Installation

Run the following command to install dependencies:

```bash
bundle install
```

## Running Tests

To run the standard tests:

```bash
ruby bingo_board_test.rb
```

To run the BDD-style tests:

```bash
ruby bingo_board_bdd_test.rb
```

## The Kata

The goal of this kata is to refactor the code to remove code smells:

- Primitive Obsession
- Data Clumps
- Missing Polymorphism

Use Parallel Change to improve this code by introducing a Cell and Coordinate class.

1. First introduce the Cell class: focus on parallel change of the internal datastructure.
2. Second introduce the Coordinate class: focus on parallel change of the public API of the BingoBoard class.

Use the tests to ensure your refactoring doesn't break existing functionality.
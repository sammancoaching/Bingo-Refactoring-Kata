package bingo_board

import (
	"errors"
	"fmt"
)

type BingoBoard struct {
	Cells  [][]*string
	Marked [][]bool
}

func New(width, height int) BingoBoard {
	cells := make([][]*string, width)
	for row := range width {
		cells[row] = make([]*string, height)
	}

	marked := make([][]bool, width)
	for row := range width {
		marked[row] = make([]bool, height)
	}

	return BingoBoard{
		Cells:  cells,
		Marked: marked,
	}
}

func (b *BingoBoard) DefineCell(x, y int, value string) error {
	if b.Cells[x][y] != nil {
		return errors.New("cell already defined")
	}

	for c := range b.Cells {
		for r := range b.Cells[c] {
			if b.Cells[c][r] != nil && value == *b.Cells[c][r] {
				return fmt.Errorf("%s already present at %v, %v", value, c, r)
			}
		}
	}

	b.Cells[x][y] = &value
	return nil
}

func (b *BingoBoard) MarkCell(x, y int) error {
	if !b.IsInitialised() {
		return errors.New("board is not initialised")
	}

	b.Marked[x][y] = true
	return nil
}

func (b *BingoBoard) IsInitialised() bool {
	for _, row := range b.Cells {
		for _, column := range row {
			if column == nil {
				return false
			}
		}
	}
	return true
}

func (b *BingoBoard) IsMarked(x, y int) bool {
	return b.Marked[x][y]
}

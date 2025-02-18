package bingo_board_test

import (
	"testing"

	bingo_board "github.com/sammancoaching/Bingo-Refactoring-Kata"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
)

func TestNewlyCreatedBoardIsNotInitialised(t *testing.T) {
	bingoBoard := bingo_board.New(1, 1)

	assert.False(t, bingoBoard.IsInitialised())
}

func TestWhenAllFieldsAreSetTheBoardIsInitialised(t *testing.T) {
	bingoBoard := bingo_board.New(1, 1)
	require.NoError(t, bingoBoard.DefineCell(0, 0, "42"))

	assert.True(t, bingoBoard.IsInitialised())
}

func TestWhenAllFieldsOnRectangularBoardAreSetItIsInitialised(t *testing.T) {
	require := require.New(t)

	bingoBoard := bingo_board.New(1, 2)
	require.NoError(bingoBoard.DefineCell(0, 0, "0, 0"))
	require.NoError(bingoBoard.DefineCell(0, 1, "0, 1"))

	assert.True(t, bingoBoard.IsInitialised())
}

func TestADefinedCellCanNotBeRedefinedEvenIfItsTheSameValue(t *testing.T) {
	bingoBoard := bingo_board.New(1, 1)
	require.NoError(t, bingoBoard.DefineCell(0, 0, "42"))

	assert.Error(t, bingoBoard.DefineCell(0, 0, "64"))
}

func TestDuplicateCellsAreNotAllowed(t *testing.T) {
	duplicateValue := "38"

	bingoBoard := bingo_board.New(2, 2)
	require.NoError(t, bingoBoard.DefineCell(0, 1, duplicateValue))

	require.Error(t, bingoBoard.DefineCell(1, 0, duplicateValue))
}

func TestANonInitialisedBoardCannotBeMarked(t *testing.T) {
	bingoBoard := bingo_board.New(1, 1)
	assert.Error(t, bingoBoard.MarkCell(0, 0))
}

func TestWhenAllCellGetsMarkedItIsMarked(t *testing.T) {
	require := require.New(t)

	bingoBoard := bingo_board.New(1, 1)
	require.NoError(bingoBoard.DefineCell(0, 0, "42"))
	require.NoError(bingoBoard.MarkCell(0, 0))

	assert.True(t, bingoBoard.IsMarked(0, 0))
}

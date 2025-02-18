package bingo_board_test

import (
	"testing"

	bingo_board "github.com/sammancoaching/Bingo-Refactoring-Kata"
	"github.com/stretchr/testify/assert"
	"github.com/stretchr/testify/require"
)

func TestBDDANewlyCreatedBoardIsNotInitialised(t *testing.T) {
	testCase := bingoBoardBDDTestCase{}
	testCase.givenABingoBoardOfSize(1, 1)
	testCase.thenTheBoardIsNotInitialised(t)
}

func TestBDDWhenAllFieldsAreSetTheBoardIsInitialised(t *testing.T) {
	anyValue := "42"

	testCase := bingoBoardBDDTestCase{}
	testCase.givenABingoBoardOfSize(1, 1)
	require.NoError(t, testCase.whenCellIsDefined(0, 0, anyValue))
	testCase.thenTheBoardIsInitialised(t)
}

func TestBDDWhenAllFieldsOnRectangularBoardAreSetItIsInitialised(t *testing.T) {
	require := require.New(t)
	one := "one, two, three"
	two := "Bingo cells can contain any text"

	testCase := bingoBoardBDDTestCase{}
	testCase.givenABingoBoardOfSize(1, 2)
	require.NoError(testCase.whenCellIsDefined(0, 0, one))
	require.NoError(testCase.whenCellIsDefined(0, 1, two))
	testCase.thenTheBoardIsInitialised(t)
}

func TestBDDADefinedCellCantBeRedefinedEvenIfItsTheSameValue(t *testing.T) {
	anyValue := "42"

	testCase := bingoBoardBDDTestCase{}
	testCase.givenABingoBoardOfSize(1, 1)
	require.NoError(t, testCase.whenCellIsDefined(0, 0, anyValue))
	assert.Error(t, testCase.whenCellIsDefined(0, 0, anyValue))
}

func TestBDDDuplicateCellsAreNotAllowed(t *testing.T) {
	anyValue := "42"

	testCase := bingoBoardBDDTestCase{}
	testCase.givenABingoBoardOfSize(2, 2)
	require.NoError(t, testCase.whenCellIsDefined(0, 1, anyValue))
	assert.Error(t, testCase.whenCellIsDefined(1, 0, anyValue))
}

func TestBDDANonInitialisedBoardCannotBeMarked(t *testing.T) {
	require := require.New(t)
	anyValue := "42"

	testCase := bingoBoardBDDTestCase{}
	testCase.givenABingoBoardOfSize(1, 1)
	require.NoError(testCase.whenCellIsDefined(0, 0, anyValue))
	require.NoError(testCase.whenCellIsMarked(0, 0))
	testCase.thenTheCellIsMarked(t, 0, 0)
}

type bingoBoardBDDTestCase struct {
	board bingo_board.BingoBoard
}

func (c *bingoBoardBDDTestCase) givenABingoBoardOfSize(width, height int) {
	c.board = bingo_board.New(width, height)
}

func (c *bingoBoardBDDTestCase) whenCellIsDefined(x, y int, value string) error {
	return c.board.DefineCell(x, y, value)
}

func (c *bingoBoardBDDTestCase) whenCellIsMarked(x, y int) error {
	return c.board.MarkCell(x, y)
}

func (c *bingoBoardBDDTestCase) thenTheBoardIsNotInitialised(t *testing.T) {
	assert.False(t, c.boardInitialisedState())
}

func (c *bingoBoardBDDTestCase) thenTheBoardIsInitialised(t *testing.T) {
	assert.True(t, c.boardInitialisedState())
}

func (c *bingoBoardBDDTestCase) boardInitialisedState() bool {
	return c.board.IsInitialised()
}

func (c *bingoBoardBDDTestCase) thenTheCellIsMarked(t *testing.T, x, y int) {
	assert.True(t, c.board.IsMarked(x, y))
}

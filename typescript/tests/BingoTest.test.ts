import { BingoBoard } from "../src/BingoBoard";

/**
 * This class is identical to BingoTestBDD but all BDD-style methods are
 * inlined (and thus this test does depend at multiple points on the
 * implementation details of BingoBoard)
 */
describe("BingoTest", () => {
  let board: BingoBoard;

  test("anNewlyCreatedBoardIsNotInitialized", () => {
    board = new BingoBoard(1, 1);
    expect(board.isInitialized()).toBe(false);
  });

  test("whenAllFieldsAreSetTheBoardIsInitialized", () => {
    const anyValue = "42";
    board = new BingoBoard(1, 1);
    board.defineCell(0, 0, anyValue);
    expect(board.isInitialized()).toBe(true);
  });

  test("whenAllFieldsOnRectangularBoardAreSetItIsInitialized", () => {
    const one = "one, two, three";
    const two = "Bingo cells can contain any text";
    board = new BingoBoard(1, 2);
    board.defineCell(0, 0, one);
    board.defineCell(0, 1, two);
    expect(board.isInitialized()).toBe(true);
  });

  test("aDefinedCellCantBeRedefinedEvenIfItsTheSameValue", () => {
    const anyValue = "42";
    board = new BingoBoard(1, 1);
    board.defineCell(0, 0, anyValue);
    expect(() => board.defineCell(0, 0, anyValue)).toThrow("already defined");
  });

  test("duplicateCellsAreNotAllowed", () => {
    const anyValue = "42";
    board = new BingoBoard(2, 2);
    board.defineCell(0, 1, anyValue);
    expect(() => board.defineCell(1, 0, anyValue)).toThrow(
      `${anyValue} already present at 0,1`
    );
  });

  test("aNonInitializedBoardCannotBeMarked", () => {
    board = new BingoBoard(1, 1);
    expect(() => board.markCell(0, 0)).toThrow("not initialized");
  });

  test("whenAllCellGetsMarkedItIsMarked", () => {
    const anyValue = "42";
    board = new BingoBoard(1, 1);
    board.defineCell(0, 0, anyValue);
    board.markCell(0, 0);
    expect(board.isMarked(0, 0)).toBe(true);
  });
});

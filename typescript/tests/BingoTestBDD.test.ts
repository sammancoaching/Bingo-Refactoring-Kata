import { BingoBoard } from "../src/BingoBoard";

describe("BingoTestBDD", () => {
  let board: BingoBoard;

  test("aNewlyCreatedBoardIsNotInitialized", () => {
    givenBingoBoardOfSize(1, 1);
    thenBoardIsNotInitialized();
  });

  test("whenAllFieldsAreSetTheBoardIsInitialized", () => {
    const anyValue = "42";
    givenBingoBoardOfSize(1, 1);
    whenCellIsDefined(0, 0, anyValue);
    thenBoardIsInitialized();
  });

  test("whenAllFieldsOnRectangularBoardAreSetItIsInitialized", () => {
    const one = "one, two, three";
    const two = "Bingo cells can contain any text";
    givenBingoBoardOfSize(1, 2);
    whenCellIsDefined(0, 0, one);
    whenCellIsDefined(0, 1, two);
    thenBoardIsInitialized();
  });

  test("aDefinedCellCantBeRedefinedEvenIfItsTheSameValue", () => {
    const anyValue = "42";
    givenBingoBoardOfSize(1, 1);
    whenCellIsDefined(0, 0, anyValue);
    expect(() => whenCellIsDefined(0, 0, anyValue)).toThrow("already defined");
  });

  test("duplicateCellsAreNotAllowed", () => {
    const anyValue = "42";
    givenBingoBoardOfSize(2, 2);
    whenCellIsDefined(0, 1, anyValue);
    expect(() => whenCellIsDefined(1, 0, anyValue)).toThrow(
      `${anyValue} already present at 0,1`
    );
  });

  test("aNonInitializedBoardCannotBeMarked", () => {
    givenBingoBoardOfSize(1, 1);
    expect(() => whenCellIsMarked(0, 0)).toThrow("not initialized");
  });

  test("whenAllCellGetsMarkedItIsMarked", () => {
    const anyValue = "42";
    givenBingoBoardOfSize(1, 1);
    whenCellIsDefined(0, 0, anyValue);
    whenCellIsMarked(0, 0);
    thenCellIsMarked(0, 0);
  });

  function givenBingoBoardOfSize(width: number, height: number): void {
    board = new BingoBoard(width, height);
  }

  function whenCellIsDefined(x: number, y: number, value: string): void {
    board.defineCell(x, y, value);
  }

  function whenCellIsMarked(x: number, y: number): void {
    board.markCell(x, y);
  }

  function thenBoardIsNotInitialized(): void {
    expect(boardInitializeState()).toBe(false);
  }

  function thenBoardIsInitialized(): void {
    expect(boardInitializeState()).toBe(true);
  }

  function boardInitializeState(): boolean {
    return board.isInitialized();
  }

  function thenCellIsMarked(x: number, y: number): void {
    expect(board.isMarked(x, y)).toBe(true);
  }
});

package bingo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * This class is identical to {@link BingoTest} but all BDD-style methods are
 * inlined (and thus this test does depend at multiple points on the
 * implementation details of {@link BingoBoard})
 */
class BingoTest2 {

	BingoBoard board;

	@Test
	void anNewlyCreatedBoardIsNotInitialized() {
		board = new BingoBoard(1, 1);
		assertThat(board.isInitialzed()).isFalse();
	}

	@Test
	void whenAllFieldsAreSetTheBoarIsInitialized() {
		String anyValue = "42";
		board = new BingoBoard(1, 1);
		board.defineCell(0, 0, anyValue);
		assertThat(board.isInitialzed()).isTrue();
	}

	@Test
	void aDefinedCellCantBeRedefinedEvenIfItsTheSameValue() {
		String anyValue = "42";
		board = new BingoBoard(1, 1);
		board.defineCell(0, 0, anyValue);
		assertThat(assertThrows(RuntimeException.class, () -> board.defineCell(0, 0, anyValue)))
				.hasMessageContaining("already defined");
	}

	@Test
	void duplicateCellsAreNotAllowed() {
		String anyValue = "42";
		board = new BingoBoard(2, 2);
		board.defineCell(0, 1, anyValue);
		assertThat(assertThrows(RuntimeException.class, () -> board.defineCell(1, 0, anyValue)))
				.hasMessage(anyValue + " already present at 0,1");
	}

	@Test
	void aNonInitializedBoardCannotBeMarked() {
		board = new BingoBoard(1, 1);
		assertThat(assertThrows(RuntimeException.class, () -> board.markCell(0, 0)))
				.hasMessageContaining("not initialized");
	}

	@Test
	void whenAllCellGetsMarkedItIsMarked() {
		String anyValue = "42";
		board = new BingoBoard(1, 1);
		board.defineCell(0, 0, anyValue);
		board.markCell(0, 0);
		assertThat(board.isMarked(0, 0)).isTrue();
	}

}

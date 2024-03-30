package bingo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
		assertThat(board.isInitialized()).isFalse();
	}

	@Test
	void whenAllFieldsAreSetTheBoarIsInitialized() {
		String anyValue = "42";
		board = new BingoBoard(1, 1);
		board.defineCell(0, 0, anyValue);
		assertThat(board.isInitialized()).isTrue();
	}

	@Test
	void whenAllFieldsOnRectangularBoardAreSetItIsInitialized() {
		String one = "0, 0";
		String two = "0, 1";
		board = new BingoBoard(1, 2);
		board.defineCell(0, 0, one);
		board.defineCell(0, 1, two);
		assertThat(board.isInitialized()).isTrue();
	}

	@Test
	void aDefinedCellCantBeRedefinedEvenIfItsTheSameValue() {
		String anyValue = "42";
		board = new BingoBoard(1, 1);
		board.defineCell(0, 0, anyValue);
		assertThatThrownBy(() -> board.defineCell(0, 0, anyValue)) //
				.isInstanceOf(RuntimeException.class) //
				.hasMessageContaining("already defined");
	}

	@Test
	void duplicateCellsAreNotAllowed() {
		String anyValue = "42";
		board = new BingoBoard(2, 2);
		board.defineCell(0, 1, anyValue);
		assertThatThrownBy(() -> board.defineCell(1, 0, anyValue)) //
				.isInstanceOf(RuntimeException.class) //
				.hasMessageContaining(anyValue + " already present at 0,1");
	}

	@Test
	void aNonInitializedBoardCannotBeMarked() {
		board = new BingoBoard(1, 1);
		assertThatThrownBy(() -> board.markCell(0, 0)) //
				.isInstanceOf(RuntimeException.class) //
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

package bingo

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

/**
 * This class is identical to [BingoTest] but all BDD-style methods are
 * inlined (and thus this test does depend at multiple points on the
 * implementation details of [BingoBoard])
 */
internal class BingoTest {
    @Test
    fun `an newly created board is not initialized`() {
        val board = BingoBoard(1, 1)
        assertThat(board.isInitialized).isFalse()
    }

    @Test
    fun `when all fields are set the boar is initialized`() {
        val anyValue = "42"
        val board = BingoBoard(1, 1)
        board.defineCell(0, 0, anyValue)
        assertThat(board.isInitialized).isTrue()
    }

    @Test
    fun `when all fields on rectangular board are set it is initialized`() {
        val one = "one, two, three"
        val two = "Bingo cells can contain any text"
        val board = BingoBoard(1, 2)
        board.defineCell(0, 0, one)
        board.defineCell(0, 1, two)
        assertThat(board.isInitialized).isTrue()
    }

    @Test
    fun `a defined cell cant be redefined even if its the same value`() {
        val anyValue = "42"
        val board = BingoBoard(1, 1)
        board.defineCell(0, 0, anyValue)
        assertThatThrownBy { board.defineCell(0, 0, anyValue) } //
            .isInstanceOf(RuntimeException::class.java) //
            .hasMessageContaining("already defined")
    }

    @Test
    fun `duplicate cells are not allowed`() {
        val anyValue = "42"
        val board = BingoBoard(2, 2)
        board.defineCell(0, 1, anyValue)
        assertThatThrownBy { board.defineCell(1, 0, anyValue) } //
            .isInstanceOf(RuntimeException::class.java) //
            .hasMessageContaining("$anyValue already present at 0,1")
    }

    @Test
    fun `a non initialized board cannot be marked`() {
        val board = BingoBoard(1, 1)
        assertThatThrownBy { board.markCell(0, 0) } //
            .isInstanceOf(RuntimeException::class.java) //
            .hasMessageContaining("not initialized")
    }

    @Test
    fun `when all cell gets marked it is marked`() {
        val anyValue = "42"
        val board = BingoBoard(1, 1)
        board.defineCell(0, 0, anyValue)
        board.markCell(0, 0)
        assertThat(board.isMarked(0, 0)).isTrue()
    }
}

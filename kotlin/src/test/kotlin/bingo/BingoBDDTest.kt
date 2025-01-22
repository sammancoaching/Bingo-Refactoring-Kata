package bingo

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

internal class BingoBDDTest {

    @Test
    fun `a newly created board is not initialized`() {
        withBingoBoardOfSize(1, 1) {
            thenBoardIsNotInitialized()
        }
    }

    @Test
    fun `when all fields are set the board is initialized`() {
        val anyValue = "42"
        withBingoBoardOfSize(1, 1) {
            whenCellIsDefined(0, 0, anyValue)
            thenBoardIsInitialized()
        }
    }

    @Test
    fun `when all fields on rectangular board are set it is initialized`() {
        val one = "one, two, three"
        val two = "Bingo cells can contain any text"
        withBingoBoardOfSize(1, 2) {
            whenCellIsDefined(0, 0, one)
            whenCellIsDefined(0, 1, two)
            thenBoardIsInitialized()
        }
    }

    @Test
    fun `a defined cell cant be redefined even if its the same value`() {
        val anyValue = "42"
        withBingoBoardOfSize(1, 1) {
            whenCellIsDefined(0, 0, anyValue)
            assertThatThrownBy { whenCellIsDefined(0, 0, anyValue) }
                .isInstanceOf(RuntimeException::class.java)
                .hasMessageContaining("already defined")
        }
    }

    @Test
    fun `duplicate cells are not allowed`() {
        val anyValue = "42"
        withBingoBoardOfSize(2, 2) {
            whenCellIsDefined(0, 1, anyValue)
            assertThatThrownBy { whenCellIsDefined(1, 0, anyValue) }
                .isInstanceOf(RuntimeException::class.java)
                .hasMessageContaining("$anyValue already present at 0,1")
        }
    }

    @Test
    fun `a non initialized board cannot be marked`() {
        withBingoBoardOfSize(1, 1) {
            assertThatThrownBy { whenCellIsMarked(0, 0) }
                .isInstanceOf(RuntimeException::class.java)
                .hasMessageContaining("not initialized")
        }
    }

    @Test
    fun `when all cell gets marked it is marked`() {
        val anyValue = "42"
        withBingoBoardOfSize(1, 1) {
            whenCellIsDefined(0, 0, anyValue)
            whenCellIsMarked(0, 0)
            thenCellIsMarked(0, 0)
        }
    }
}

private fun withBingoBoardOfSize(width: Int, height: Int, block: BingoBoard.() -> Unit) {
    val board = BingoBoard(width, height)
    block(board)
}

private fun BingoBoard.whenCellIsDefined(x: Int, y: Int, value: String) {
    defineCell(x, y, value)
}

private fun BingoBoard.whenCellIsMarked(x: Int, y: Int) {
    markCell(x, y)
}

private fun BingoBoard.thenBoardIsNotInitialized() {
    assertThat(boardInitializeState()).isFalse()
}

private fun BingoBoard.thenBoardIsInitialized() {
    assertThat(boardInitializeState()).isTrue()
}

private fun BingoBoard.boardInitializeState(): Boolean = isInitialized

private fun BingoBoard.thenCellIsMarked(x: Int, y: Int) {
    assertThat(isMarked(x, y)).isTrue()
}


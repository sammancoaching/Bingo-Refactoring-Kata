namespace Bingo.Test;

/**
 * This class is identical to {@link BingoTest} but all BDD-style methods are
 * inlined (and thus this test does depend at multiple points on the
 * implementation details of {@link BingoBoard})
 */
public class BingoTest
{

    BingoBoard board;

    [Fact]
    void AnNewlyCreatedBoardIsNotInitialized()
    {
        board = new BingoBoard(1, 1);
        board.IsInitialized().Should().BeFalse();
    }

    [Fact]
    void WhenAllFieldsAreSetTheBoarIsInitialized()
    {
        var anyValue = "42";
        board = new BingoBoard(1, 1);
        board.DefineCell(0, 0, anyValue);
        board.IsInitialized().Should().BeTrue();
    }

    [Fact]
    void WhenAllFieldsOnRectangularBoardAreSetItIsInitialized()
    {
        var one = "one, two, three";
        var two = "Bingo cells can contain any text";
        board = new BingoBoard(1, 2);
        board.DefineCell(0, 0, one);
        board.DefineCell(0, 1, two);
        board.IsInitialized().Should().BeTrue();
    }

    [Fact]
    void ADefinedCellCantBeRedefinedEvenIfItsTheSameValue()
    {
        var anyValue = "42";
        board = new BingoBoard(1, 1);
        board.DefineCell(0, 0, anyValue);
        Action secondCall = () => board.DefineCell(0, 0, anyValue);
        secondCall.Should()
            .Throw<InvalidOperationException>()
            .Where(e => e.Message.Contains("already defined"));
    }

    [Fact]
    void DuplicateCellsAreNotAllowed()
    {
        var anyValue = "42";
        board = new BingoBoard(2, 2);
        board.DefineCell(0, 1, anyValue);
        Action secondCall = () => board.DefineCell(1, 0, anyValue);
        secondCall.Should()
            .Throw<InvalidOperationException>()
            .WithMessage(anyValue + " already present at 0,1");
    }

    [Fact]
    void ANonInitializedBoardCannotBeMarked()
    {
        board = new BingoBoard(1, 1);
        Action markCell = () => board.MarkCell(0, 0);
        markCell.Should()
            .Throw<InvalidOperationException>()
            .Where(e => e.Message.EndsWith("not initialized"));
    }

    [Fact]
    void WhenAllCellGetsMarkedItIsMarked()
    {
        var anyValue = "42";
        board = new BingoBoard(1, 1);
        board.DefineCell(0, 0, anyValue);
        board.MarkCell(0, 0);
        board.IsMarked(0, 0).Should().BeTrue();
    }

}

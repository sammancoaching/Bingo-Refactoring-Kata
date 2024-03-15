namespace Bingo.Test;

public class BingoTestBdd
{

    BingoBoard board;

    [Fact]
    void AnNewlyCreatedBoardIsNotInitialized()
    {
        GivenBingoBoardOfSize(1, 1);
        ThenBoardIsNotInitialzed();
    }

    [Fact]
    void WhenAllFieldsAreSetTheBoarIsInitialized()
    {
        var anyValue = "42";
        board = new BingoBoard(1, 1);
        board.DefineCell(0, 0, anyValue);
        board.IsInitialzed().Should().BeTrue();
    }

    [Fact]
    void WhenAllFieldsOnRectangularBoardAreSetItIsInitialized()
    {
        GivenBingoBoardOfSize(1, 2);
        WhenCellIsDefined(0, 0, "1");
        WhenCellIsDefined(0, 1, "2");
        ThenBoardIsInitialzed();
    }

    [Fact]
    void ADefinedCellCantBeRedefinedEvenIfItsTheSameValue()
    {
        var anyValue = "42";
        GivenBingoBoardOfSize(1, 1);
        WhenCellIsDefined(0, 0, anyValue);
        Action secondCall = () => WhenCellIsDefined(0, 0, anyValue);
        secondCall.Should()
            .Throw<InvalidOperationException>()
            .Where(e => e.Message.Contains("already defined"));
    }

    [Fact]
    void DuplicateCellsAreNotAllowed()
    {
        var anyValue = "42";
        GivenBingoBoardOfSize(2, 2);
        WhenCellIsDefined(0, 1, anyValue);
        Action secondCall = () => WhenCellIsDefined(1, 0, anyValue);
        secondCall.Should()
            .Throw<InvalidOperationException>()
            .WithMessage(anyValue + " already present at 0,1");
    }

    [Fact]
    void ANonInitializedBoardCannotBeMarked()
    {
        GivenBingoBoardOfSize(1, 1);
        Action markCell = () => WhenCellIsMarked(0, 0);
        markCell.Should()
            .Throw<InvalidOperationException>()
            .Where(e => e.Message.EndsWith("not initialized"));
    }

    [Fact]
    void WhenAllCellGetsMarkedItIsMarked()
    {
        var anyValue = "42";
        GivenBingoBoardOfSize(1, 1);
        WhenCellIsDefined(0, 0, anyValue);
        WhenCellIsMarked(0, 0);
        ThenCellIsMarked(0, 0);
    }

    private void GivenBingoBoardOfSize(int width, int height) {
        board = new BingoBoard(width, height);
    }

    private void WhenCellIsDefined(int x, int y, String value) {
        board.DefineCell(x, y, value);
    }

    private void WhenCellIsMarked(int x, int y) {
        board.MarkCell(x, y);
    }

    private void ThenBoardIsNotInitialzed()
    {
        BoardInitializeState().Should().BeFalse();
    }

    private void ThenBoardIsInitialzed() {
        BoardInitializeState().Should().BeTrue();
    }

    private bool BoardInitializeState() {
        return board.IsInitialzed();
    }

    private void ThenCellIsMarked(int x, int y) {
        board.IsMarked(x, y).Should().BeTrue();
    }
}

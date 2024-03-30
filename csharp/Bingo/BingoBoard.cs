namespace Bingo;

public class BingoBoard
{
    private string[,] cells;
    private bool[,] marked;

    public BingoBoard(int width, int height)
    {
        this.cells = new string[width, height];
        this.marked = new bool[width, height];
    }

    public void DefineCell(int x, int y, String value)
    {
        if (cells[x, y] == null)
        {
            for (int c = 0; c < cells.GetLength(0); c++)
            {
                for (int r = 0; r < cells.GetLength(1); r++)
                {
                    if (value.Equals(cells[c, r]))
                        throw new InvalidOperationException(value + " already present at " + c + "," + r);
                }
            }
            cells[x, y] = value;
        }
        else
        {
            throw new InvalidOperationException("cell already defined");
        }
    }

    public void MarkCell(int x, int y)
    {
        if (!IsInitialized())
        {
            throw new InvalidOperationException("board not initialized");
        }
        marked[x, y] = true;
    }

    public bool IsMarked(int x, int y)
    {
        return marked[x, y];
    }

    public bool IsInitialized()
    {
        foreach (string col in cells)
        {
            if (col == null)
                return false;
        }
        return true;
    }
}
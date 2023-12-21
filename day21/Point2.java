import java.util.Objects;

public class Point2 extends Point{
    public int realRow;
    public int realCol;

    public Point2(int row, int col, int realRow, int realCol) {
        super(row, col);
        this.realRow = realRow;
        this.realCol = realCol;
    }

    public Point2(int row, int col) {
        super(row, col);
        this.realRow = row;
        this.realCol = col;
    }

    @Override
    public boolean equals(Object obj) {
        Point2 other = (Point2) obj;
        return this.realRow == other.realRow && this.realCol == other.realCol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.realRow, this.realCol);
    }
}
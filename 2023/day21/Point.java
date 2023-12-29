import java.util.Objects;

public class Point {
    public int row;
    public int col;

    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object obj) {
        Point other = (Point) obj;
        return this.row == other.row && this.col == other.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.row, this.col);
    }
}

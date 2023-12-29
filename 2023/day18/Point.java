import java.util.Objects;

public class Point{
    public long x, y;

    public Point(long x, long y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj instanceof Point) {
            Point other = (Point) obj;
            return this.x == other.x && this.y == other.y;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

public class Connection implements Comparable<Connection>{
    public int sourceX;
    public int sourceY;
    public int x;
    public int y;
    public int distance;
    public int pathId;

    public Connection(int x, int y, int distance) {
        this.sourceX = x;
        this.sourceY = y;
        this.x = x;
        this.y = y;
        this.distance = distance;
    }

    public Connection(int sourceX, int sourceY, int x, int y, int distance) {
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.x = x;
        this.y = y;
        this.distance = distance;
    }

    public Connection setId(int pathId) {
        this.pathId = pathId;
        return this;
    }

    @Override
    public int compareTo(Connection o) {
        return this.distance - o.distance;
    }
}

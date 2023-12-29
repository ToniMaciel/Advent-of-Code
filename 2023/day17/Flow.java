public class Flow {
    public int x;
    public int y;
    public int dir;
    public int sameDirection = 0;
    public int cost = 0;

    public Flow(int x, int y, int dir, int sameDirection, int cost) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.sameDirection = sameDirection;
        this.cost = cost;
    }
    
}

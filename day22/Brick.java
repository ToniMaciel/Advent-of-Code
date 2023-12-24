import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Brick implements Comparable<Brick>{
    int id;
    int[] init;
    int[] end;
    List<Brick> supporters = new ArrayList<>();
    List<Brick> supported = new ArrayList<>();

    public Brick(int id, int[] init, int[] end) {
        this.id = id;
        this.init = init;
        this.end = end;
    }

    @Override
    public int compareTo(Brick o) {
        if (this.init[2] == o.init[2]) {
            return this.end[2] - o.end[2];	
        } 

        return this.init[2] - o.init[2];
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(init) + Arrays.hashCode(end) + id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Brick)) {
            return false;
        }

        Brick brick = (Brick) obj;

        return brick.id == this.id;
    }
}

import java.util.HashSet;
import java.util.Set;

public class Path {
    public Set<String> used = new HashSet<>();
    public String lastUsed = "";

    public Path() {
    }

    public Path(String lastUsed) {
        this.used.add(lastUsed);
        this.lastUsed = lastUsed;
    }

    public Path(Path path) {
        this.used = new HashSet<>();
        this.used.addAll(path.used);
        this.lastUsed = path.lastUsed;
    }
}

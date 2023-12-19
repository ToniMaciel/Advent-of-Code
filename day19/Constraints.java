import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constraints {
    public Map<Character, List<Integer>> map;

    public Constraints() {
        map = new HashMap<>();
        "xmas".chars().forEach(c -> map.put((char) c, new ArrayList<>(List.of(1, 4000))));
    }

    public Constraints(Map<Character, List<Integer>> map) {
        this.map = new HashMap<>(map);
        this.map.replaceAll((k, v) -> new ArrayList<>(v));
    }

    public Long getCombinations() {
        Long combinations = 1L;
        for (List<Integer> values : map.values()) {
            combinations *= Math.max(values.get(1) - values.get(0) + 1, 0L);
        }
        return combinations;
    }
}

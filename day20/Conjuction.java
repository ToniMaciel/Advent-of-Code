import java.util.HashMap;
import java.util.Map;

public class Conjuction extends Module {
    Map<Module, Boolean> memory = new HashMap<>();

    
    public Conjuction(String name) {
        super(name);
    }

    @Override
    Boolean newPulse(Boolean value, Module from) {
        this.memory.put(from, value);

        this.outputs.stream().forEach(o -> {
            Boolean newValue = true;
            for (Module input : this.inputs) {
                newValue &= this.memory.getOrDefault(input, false);
            }

            this.state = !newValue;
        });

        return this.state;
    }
    
}

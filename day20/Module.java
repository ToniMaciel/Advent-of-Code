import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    String name;
    List<Module> inputs = new ArrayList<Module>();
    List<Module> outputs = new ArrayList<Module>();
    Boolean state = false;

    public Module(String name) {
        this.name = name;
    }

    abstract Boolean newPulse(Boolean value, Module from);
}

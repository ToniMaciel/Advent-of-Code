public class Broadcaster extends Module {

    public Broadcaster(String name) {
        super(name);
    }

    @Override
    Boolean newPulse(Boolean value, Module from) {
        return false;
    }
    
}

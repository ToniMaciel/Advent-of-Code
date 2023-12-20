public class Pulse {
    public Module source;
    public Module target;
    public Boolean value;

    public Pulse(Module source, Module target, Boolean value) {
        this.source = source;
        this.target = target;
        this.value = value;
    }
}

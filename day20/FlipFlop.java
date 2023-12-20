public class FlipFlop extends Module {
    public FlipFlop(String name) {
        super(name);
    }

    @Override
    Boolean newPulse(Boolean value, Module from) {
        if (!value) {
            this.state = !this.state;
            return this.state;
        }

        return null;
    }
}

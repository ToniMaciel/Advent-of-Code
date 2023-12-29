public class Instruction {
    public char move;
    public long amount;

    public Instruction(String instruction) {
        instruction = instruction.replace("(", "").replace(")", "").replace("#", "");
        amount = Long.parseLong(instruction.substring(0, instruction.length() - 1), 16);
        
        switch (instruction.charAt(instruction.length() - 1)) {
            case '0':
                move = 'R';
                break;
            case '1':
                move = 'D';
                break;
            case '2':
                move = 'L';
                break;
            case '3':
                move = 'U';
                break;
        }
    }
}

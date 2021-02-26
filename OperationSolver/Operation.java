public enum Operation {
    ADDITION ("+"),
    SUBTRACTION ("-"),
    MULTIPLICATION ("*"),
    DIVISION ("/");
    
    private final String string;

    private Operation(String string) {
        this.string = string;
    }

    public String toString() {
        return string;
    }
    
    public double apply(double a, double b) {
        switch (this) {
            case ADDITION:
                return a + b;
            case SUBTRACTION:
                return a - b;
            case MULTIPLICATION:
                return a * b;
            case DIVISION:
                return a / b;
            default:
                return 0;
        }
    }

    public static Operation random() {
        int randInt = Main.randInt(0, 3);
        switch (randInt) {
            case 0:
                return ADDITION;
            case 1:
                return SUBTRACTION;
            case 2:
                return MULTIPLICATION;
            case 3:
                return DIVISION;
            default:
                return ADDITION;
        }
    }

    public static Operation[] list() {
        Operation[] list = { Operation.ADDITION, Operation.SUBTRACTION, Operation.MULTIPLICATION, Operation.DIVISION };
        return list;
    }

}
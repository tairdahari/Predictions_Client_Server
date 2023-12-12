package prediction.Termination;

public abstract class AbstractTermination implements ITermination {
    private final int count;

    public AbstractTermination(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }
}
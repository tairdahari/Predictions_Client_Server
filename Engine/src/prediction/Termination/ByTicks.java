package prediction.Termination;

public class ByTicks extends AbstractTermination {
    public ByTicks(int countByTicks) {
        super(countByTicks);
    }

    @Override
    public eTerminationType getTerminationName() {
        return eTerminationType.BY_TICKS;
    }
}
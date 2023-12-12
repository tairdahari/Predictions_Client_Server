package prediction.Termination;

public class BySeconds extends AbstractTermination {

    public BySeconds(int countBySeconds) {
        super(countBySeconds);
    }

    @Override
    public eTerminationType getTerminationName() {
        return eTerminationType.BY_SECONDS;
    }
}
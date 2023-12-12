package prediction.Termination;

public class ByUser extends AbstractTermination{

    public ByUser(Integer count) {
        super(count);
    }

    @Override
    public eTerminationType getTerminationName() {
        return eTerminationType.BY_USER;
    }
}

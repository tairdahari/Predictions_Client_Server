package prediction.rule;

import xmlJavaFXReader.schema.generated.PRDActivation;

public class ActivationImpl implements Activation {
    private  Integer ticks = 1;
    private double probability = 1.0 ;

    public ActivationImpl(PRDActivation prdActivation) {
        if (prdActivation != null) {
            if (prdActivation.getTicks() != null) {
                this.ticks = prdActivation.getTicks();
            }
            if (prdActivation.getProbability() != null) {
                this.probability = prdActivation.getProbability();
            }
        }
    }

    @Override
    public boolean isActive(int tickNumber) {
        return tickNumber % ticks == 0 && Math.random() < probability;
    }

    @Override
    public Integer getTicks() {
        return ticks;
    }

    @Override
    public double getProbability() {
        return probability;
    }
}
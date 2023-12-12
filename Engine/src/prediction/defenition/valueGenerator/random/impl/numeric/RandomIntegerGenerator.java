package prediction.defenition.valueGenerator.random.impl.numeric;

public class RandomIntegerGenerator extends AbstractNumericRandomGenerator<Integer> {

    public RandomIntegerGenerator(Integer from, Integer to) {
        super(from, to);
    }

    @Override
    public Integer generateValue() {
        if (from >= to) {
            throw new IllegalArgumentException("Invalid range: 'from' must be less than 'to'");
        }

        return from + random.nextInt(to);
    }
}

package prediction.defenition.valueGenerator.random.impl;

import prediction.defenition.valueGenerator.random.api.AbstractRandomValueGenerator;

public class RandomStringGenerator extends AbstractRandomValueGenerator<String> {

    private static final String VALID_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!?,_-.() ";

    @Override
    public String generateValue() {
        int length = random.nextInt(50) + 1;

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char randomChar = getRandomCharacter();
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }

    private char getRandomCharacter() {
        int index = random.nextInt(VALID_CHARACTERS.length());
        return VALID_CHARACTERS.charAt(index);
    }
}

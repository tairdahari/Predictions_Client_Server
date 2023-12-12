package utils;

public class DTOEntityExecution {
    private final String entityName;
    private final String quantity;
    private final String finalQuantity;

    public DTOEntityExecution(String entityInstanceName, String initialQuantity, String finalQuantity) {
        this.entityName = entityInstanceName;
        this.quantity = initialQuantity;
        this.finalQuantity = finalQuantity;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getFinalQuantity() {
        return finalQuantity;}
}

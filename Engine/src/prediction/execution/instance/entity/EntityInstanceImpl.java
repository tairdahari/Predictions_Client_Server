package prediction.execution.instance.entity;

import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.grid.Point;
import prediction.defenition.property.api.IPropertyDefinition;
import prediction.execution.context.GridManager;
import prediction.execution.instance.property.IPropertyInstance;
import prediction.execution.instance.property.PropertyInstanceImpl;

import java.util.LinkedHashMap;
import java.util.Map;

public class EntityInstanceImpl implements IEntityInstance{
    private final IEntityDefinition entityDefinition;
    private int id;
    private boolean isAlive;
    private final Map<String, IPropertyInstance> properties;
    private Point position;

    public EntityInstanceImpl(IEntityDefinition entityDefinition, int id, GridManager gridManager,Point position,  String mode) {
        this.entityDefinition = entityDefinition;
        this.id = id;
        properties = new LinkedHashMap<>();
        if( mode.equals("scratch")) {
            this.position = null;
        }else {
            this.position = position;
        }
       // this.position = gridManager.getAvailableCell();
        //gridManager.getSimulationGrid().setEntityAt(position.getX(), position.getY(), this);
        this.isAlive = true;

        for (Map.Entry<String, IPropertyDefinition> propertyDefinition : entityDefinition.getEntityProperties().entrySet()) {
            Object value = propertyDefinition.getValue().generateValue();
            IPropertyInstance newPropertyInstance = new PropertyInstanceImpl(propertyDefinition.getValue(), value);
            properties.put(newPropertyInstance.getPropertyDefinition().getName(),newPropertyInstance);
        }
    }
    public EntityInstanceImpl(IEntityDefinition entityDefinition, int id, GridManager gridManager) {
        this.entityDefinition = entityDefinition;
        this.id = id;
        properties = new LinkedHashMap<>();
        this.position = gridManager.getAvailableCell();
        gridManager.getSimulationGrid().setEntityAt(position.getX(), position.getY(), this);
        this.isAlive = true;

        for (Map.Entry<String, IPropertyDefinition> propertyDefinition : entityDefinition.getEntityProperties().entrySet()) {
            Object value = propertyDefinition.getValue().generateValue();
            IPropertyInstance newPropertyInstance = new PropertyInstanceImpl(propertyDefinition.getValue(), value);
            properties.put(newPropertyInstance.getPropertyDefinition().getName(),newPropertyInstance);
        }
    }
    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int newId) {
        this.id = newId;
    }

    @Override
    public IPropertyInstance getPropertyByName(String name) {
        if (!properties.containsKey(name)) {
            throw new IllegalArgumentException("for entity of type " + entityDefinition.getEntityName() + " has no property named " + name);
        }

        return properties.get(name);
    }

    @Override
    public void addPropertyInstance(IPropertyInstance propertyInstance) {
        properties.put(propertyInstance.getPropertyDefinition().getName(), propertyInstance);
    }
    @Override
    public boolean isAlive() {
        return isAlive;
    }
    @Override
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public IEntityDefinition getEntityDefinition() {
        return this.entityDefinition;
    }

    @Override
    public Map<String, IPropertyInstance> getPropertiesDefinition() {
        return this.properties;
    }
}
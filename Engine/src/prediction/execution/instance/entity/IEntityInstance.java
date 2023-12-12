package prediction.execution.instance.entity;

import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.grid.Point;
import prediction.execution.instance.property.IPropertyInstance;

import java.io.Serializable;
import java.util.Map;

public interface IEntityInstance extends Serializable {
    int getId();
    void setId(int newId);
    IPropertyInstance getPropertyByName(String name);
    void addPropertyInstance(IPropertyInstance propertyInstance);
    boolean isAlive();
    void setAlive(boolean alive);
    IEntityDefinition getEntityDefinition();
    Map<String, IPropertyInstance> getPropertiesDefinition();

    Point getPosition();

    void setPosition(Point position);
}



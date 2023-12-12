package prediction.execution.instance.entity.manager;

import prediction.defenition.entity.IEntityDefinition;
import prediction.execution.context.GridManager;
import prediction.execution.instance.entity.EntityInstanceImpl;
import prediction.execution.instance.entity.IEntityInstance;
import prediction.execution.instance.property.IPropertyInstance;
import prediction.execution.instance.property.PropertyInstanceImpl;
import utils.DTOPropertyHistogram;

import java.util.*;

public class EntityInstanceManagerImpl implements IEntityInstanceManager {
    private int count;
    private List<IEntityInstance> instances;
    public EntityInstanceManagerImpl() {
        count = 0;
        instances = new ArrayList<>();
    }

    @Override
    public IEntityInstance create(IEntityDefinition entityDefinition, GridManager gridManager) {
        IEntityInstance newEntityInstance = new EntityInstanceImpl(entityDefinition, count, gridManager);
        instances.add(newEntityInstance);
        this.count++;
        return newEntityInstance;
    }

    @Override
    public List<IEntityInstance> getInstances() {
        return instances;
    }
    @Override
    public int getEntityQuantityByName(String entityInstanceByName) {
        int count = 0;

        for (IEntityInstance entityInstance :instances){
            if(entityInstance.getEntityDefinition().getEntityName().equals(entityInstanceByName))
                count++;
        }
        return count;
    }

    @Override
    public IEntityInstance getEntityInstanceByName(String entityName) {
        for(IEntityInstance entityInstance : instances ) {
            if(entityInstance.getEntityDefinition().getEntityName().equals(entityName))
                return entityInstance;
        }
        throw new RuntimeException();
    }
    @Override
    public void setCount(int newCount) {
        this.count = newCount;
    }
    @Override
    public void setInstances(List<IEntityInstance> instancesForChange ) {
        List<IEntityInstance> newInstancesList = new ArrayList<>();

        for (IEntityInstance entityInstance : instancesForChange) {
            if(entityInstance.isAlive()) {
                newInstancesList.add(entityInstance);
            }
        }
        //this.count = newInstancesList.size();
        this.instances = newInstancesList;
    }

    @Override
    public void killEntity(int id) {
        for( IEntityInstance entityInstance : this.instances) {
            if(entityInstance.getId() == id) {
                entityInstance.setAlive(false);
                break;
            }
        }
    }

    @Override
    public Integer getCount() {
        return this.count;
    }

    @Override
    public DTOPropertyHistogram propertyHistogram(String property) {
        Map<String, Integer> histogram = new LinkedHashMap<>();

        if(instances.size() == 0)
            throw new IllegalArgumentException("There is no instances for display");

        for(IEntityInstance entityInstance : instances) {
            if(entityInstance.getPropertiesDefinition().containsKey(property) == true) {
                if (histogram.get(entityInstance.getPropertyByName(property).getValue().toString()) == null) {
                    histogram.put(entityInstance.getPropertyByName(property).getValue().toString(), 1);
                } else {
                    Integer temp = histogram.get(entityInstance.getPropertyByName(property).getValue().toString());
                    if(entityInstance.getPropertyByName(property).getValue() instanceof PropertyInstanceImpl) {
                        String value = ((PropertyInstanceImpl) entityInstance.getPropertyByName(property).getValue()).getValue().toString();
                        histogram.put(value, temp + 1);
                    }
                    histogram.put(entityInstance.getPropertyByName(property).getValue().toString(), temp + 1);
                }
            }
        }
        return new DTOPropertyHistogram(histogram);
    }

    @Override
    public void setInstancesFromNewList(List<IEntityInstance> instancesForUpdate, Integer gridSize) {
        for(IEntityInstance entityInstance : instancesForUpdate)
            if(instances.size() < gridSize) {
                instances.add(entityInstance);
            }
    }

    @Override
    public float propertyConsistency(String propertyName, int currTick) {
        float consistency = 0;

        if (instances.size() == 0)
            throw new IllegalArgumentException("No instances found");

        for (IEntityInstance entityInstance : instances) {
            IPropertyInstance propertyInstance = entityInstance.getPropertyByName(propertyName);

            if (propertyInstance == null) {
                throw new IllegalArgumentException("Property not found: " + propertyName);
            }

            Object currentValue = propertyInstance.getValue();
            int ticksSinceLastChange = (int) propertyInstance.getTicksSinceLastChange(currentValue, currTick);

            consistency += ticksSinceLastChange;
        }

        if (instances.size() > 0) {
            consistency /= instances.size();
        }

        return consistency;
    }
}
package prediction.execution.instance.entity.manager;

import prediction.defenition.entity.IEntityDefinition;
import prediction.execution.context.GridManager;
import prediction.execution.instance.entity.IEntityInstance;
import utils.DTOPropertyHistogram;

import java.io.Serializable;
import java.util.List;

public interface IEntityInstanceManager extends Serializable {
    IEntityInstance create(IEntityDefinition entityDefinition, GridManager gridManager);
    List<IEntityInstance> getInstances();

    int getEntityQuantityByName(String entityInstanceByName);

    IEntityInstance getEntityInstanceByName(String entityName);

    void setCount(int newCount);

    void setInstances(List<IEntityInstance> instances);
    void killEntity(int id);
    Integer getCount();
    DTOPropertyHistogram propertyHistogram(String property);

    void setInstancesFromNewList(List<IEntityInstance> instancesForUpdate, Integer gridSize);

    float propertyConsistency(String propertyName, int currTick);
}

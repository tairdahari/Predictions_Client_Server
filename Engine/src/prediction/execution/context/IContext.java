package prediction.execution.context;

import prediction.World;
import prediction.execution.instance.entity.IEntityInstance;
import prediction.execution.instance.entity.manager.IEntityInstanceManager;
import prediction.execution.instance.enviroment.api.IActiveEnvironment;
import prediction.execution.instance.property.IPropertyInstance;
import prediction.execution.runner.eSimulationState;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IContext extends Serializable {
    IEntityInstance getPrimaryEntityInstance();
    World getWorld();
    void setPrimaryEntityInstance(IEntityInstance entityInstance);
    void setSecondaryEntityInstance(IEntityInstance entityInstance);
    IEntityInstance getSecondaryEntityInstance();
    void removeEntity(IEntityInstance entityInstance);
    IActiveEnvironment getActiveEnvironment();
    IEntityInstanceManager getEntityInstanceManager();
    Object ckNewValueInRange(Object newValue, IPropertyInstance propertyInstance);
    Object checkIntegerValueInRange(Integer newValue, IPropertyInstance propertyInstance);
    Object checkFloatValueInRange(Float newValue, IPropertyInstance propertyInstance);
    String getSecondaryEntityName();
    void setSecondaryEntityName(String name);
     Integer getCurrTick();
     void setCurrTick(int currTick);
    List<IEntityInstance> getInstancesForUpdate();

    GridManager getGridManager();

    Integer getCurrTime();
    void setCurrTime(Integer time);

    Integer getSimulationId();

    eSimulationState getInProgress();

    void setInProgress(eSimulationState inProgress);

     void setEntityQuantities(Integer tick, Integer quantity);

    Map<Integer, Integer> getEntityQuantities();
}
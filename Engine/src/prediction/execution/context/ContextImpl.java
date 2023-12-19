package prediction.execution.context;

import prediction.Termination.clientTerminationManager.TerminationByClientManager;
import prediction.World;
import prediction.defenition.property.Range;
import prediction.defenition.property.impl.FloatPropertyDefinition;
import prediction.defenition.property.impl.IntegerPropertyDefinition;
import prediction.execution.instance.entity.IEntityInstance;
import prediction.execution.instance.entity.manager.IEntityInstanceManager;
import prediction.execution.instance.enviroment.api.IActiveEnvironment;
import prediction.execution.instance.property.IPropertyInstance;
import prediction.execution.runner.eSimulationState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContextImpl implements IContext {
    private IEntityInstance primaryEntityInstance;
    private IEntityInstance secondaryEntityInstance;
    private final IEntityInstanceManager entityInstanceManager;
    private final IActiveEnvironment activeEnvironment;
    private String secondaryEntityName;
    private Integer currTick;
    private Integer currTime;
    private Integer simulationId;
    private  List<IEntityInstance> instancesForUpdate;
    private GridManager gridManager;
    private World world;
    private eSimulationState inProgress = eSimulationState.RUNNING;
    private final Map<Integer, Integer> entityQuantities = new HashMap<>();
    private final TerminationByClientManager terminationManager;


    public ContextImpl(int idForSimulation, World world, IEntityInstance primaryEntityInstance, IEntityInstanceManager entityInstanceManager, IActiveEnvironment activeEnvironment, GridManager gridManager, TerminationByClientManager terminationManager) {
        this.primaryEntityInstance = primaryEntityInstance;
        this.entityInstanceManager = entityInstanceManager;
        this.activeEnvironment = activeEnvironment;
        this.gridManager = gridManager;
        this.secondaryEntityName = null;
        this.currTick = 0;
        this.currTime = 0;
        this.instancesForUpdate = new ArrayList<>();
        this.world = world;
        this.simulationId = idForSimulation;
        this.terminationManager = terminationManager;
    }

    @Override
    public IEntityInstance getPrimaryEntityInstance() {
        return primaryEntityInstance;
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public void setPrimaryEntityInstance(IEntityInstance entityInstance) {
        this.primaryEntityInstance = entityInstance;
    }
    @Override
    public TerminationByClientManager getTerminationManager() {
        return terminationManager;
    }
    @Override
    public void setSecondaryEntityInstance(IEntityInstance entityInstance) {
        this.secondaryEntityInstance = entityInstance;
    }

    @Override
    public IEntityInstance getSecondaryEntityInstance() {
        return this.secondaryEntityInstance;
    }

    @Override
    public void removeEntity(IEntityInstance entityInstance) {
        entityInstanceManager.killEntity(entityInstance.getId());
    }
    @Override
    public IActiveEnvironment getActiveEnvironment() {
        return this.activeEnvironment;
    }

    @Override
    public IEntityInstanceManager getEntityInstanceManager() {
        return entityInstanceManager;
    }

    @Override
    public Object ckNewValueInRange(Object newValue, IPropertyInstance propertyInstance) {
        if(propertyInstance.getPropertyDefinition() instanceof IntegerPropertyDefinition) {
            return checkIntegerValueInRange((Integer)newValue, propertyInstance);
        } else {
            return checkFloatValueInRange((Float)newValue, propertyInstance);
        }
    }

    @Override
    public Object checkIntegerValueInRange(Integer newValue, IPropertyInstance propertyInstance) {
        Range range = ((IntegerPropertyDefinition) propertyInstance.getPropertyDefinition()).getRange();

        if ((newValue >= range.getFrom()) && (newValue <= range.getTo())) {
            return newValue;}
        else {
            return propertyInstance.getValue();
        }
    }

    @Override
    public Object checkFloatValueInRange(Float newValue, IPropertyInstance propertyInstance) {
        Range range = ((FloatPropertyDefinition<Float>) propertyInstance.getPropertyDefinition()).getRange();

        if ((newValue >= range.getFrom()) && (newValue <= range.getTo())) {
            return newValue;}
        else {
            return propertyInstance.getValue();
        }
    }

    @Override
    public String getSecondaryEntityName() {
        return secondaryEntityName;
    }

    @Override
    public void setSecondaryEntityName(String name) {
        this.secondaryEntityName = name;
    }
    @Override
    public Integer getCurrTick() {
        return currTick;
    }
    @Override
    public void setCurrTick(int currTick) {
        this.currTick = currTick;
    }
    @Override
    public List<IEntityInstance> getInstancesForUpdate() {
        return instancesForUpdate;
    }
    @Override
    public GridManager getGridManager() {
        return gridManager;
    }

    @Override
    public Integer getCurrTime() {
        return currTime;
    }

    @Override
    public void setCurrTime(Integer time) {
        currTime = time;
    }

    @Override
    public Integer getSimulationId() {
        return simulationId;
    }
    @Override
    public eSimulationState getInProgress() {
        return inProgress;
    }
    @Override
    public void setInProgress(eSimulationState inProgress) {
        this.inProgress = inProgress;
    }

    @Override
    public void setEntityQuantities(Integer tick, Integer quantity)
    {

        entityQuantities.put(tick, quantity);
    }

    @Override
    public Map<Integer, Integer> getEntityQuantities()
    {
        return this.entityQuantities;
    }
}
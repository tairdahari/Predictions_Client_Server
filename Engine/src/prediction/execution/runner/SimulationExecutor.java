package prediction.execution.runner;

import prediction.Termination.BySeconds;
import prediction.Termination.ByTicks;
import prediction.Termination.clientTerminationManager.TerminationByClientManager;
import prediction.action.api.IAction;
import prediction.action.impl.condition.AbstractConditionAction;
import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.grid.Point;
import prediction.execution.context.GridManager;
import prediction.execution.context.IContext;
import prediction.execution.instance.entity.IEntityInstance;
import prediction.execution.simulationExecutionDetails.ISimulationDetails;
import prediction.execution.simulationExecutionDetails.SimulationDetails;
import prediction.execution.simulations.SimulationExecutionManager;
import prediction.rule.IRule;
import prediction.worldManager.IWorldManager;
import prediction.worldManager.WorldManager;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SimulationExecutor implements Runnable {
    private IContext context;
    private SimulationExecutionManager simulationExecutionManager;
    private boolean stopRequested = false;
    private eSimulationState simulationState = eSimulationState.RUNNING;
//    private final Object pauseLock = new Object();
    private boolean paused = false;
    private String simulationError;
    private long pauseTime = 0;
    private List<Integer> populationList;
    private List<Object> userEnvVarChoices;
    private IWorldManager engineManager;
    private TerminationByClientManager terminationByClientManager;


    public eSimulationState getSimulationState() {
        return simulationState;
    }

    public void setSimulationState(eSimulationState simulationState) {
        this.simulationState = simulationState;
    }

    public SimulationExecutor(IContext context, SimulationExecutionManager simulationExecutionManager, WorldManager worldManager, TerminationByClientManager terminationManager) {
        this.context = context;
        this.simulationExecutionManager =simulationExecutionManager;
        this.engineManager = worldManager;
        this.terminationByClientManager = terminationManager;
    }

    @Override
    public void run() {
        int currentTick = 0;
        Thread thread= Thread.currentThread();
        LocalDateTime currentDateTime = LocalDateTime.now();

        ISimulationDetails simulationDetails = new SimulationDetails(currentDateTime, context.getSimulationId(), this.context);
        simulationDetails.setStartSimulationDateTime(currentDateTime);

        thread.setName(simulationDetails.getId().toString());
        simulationExecutionManager.getAllSimulations().add(simulationDetails);

        while (!Thread.currentThread().isInterrupted() && !stopRequested && (simulationState.equals(eSimulationState.RUNNING) || simulationState.equals(eSimulationState.PAUSED)) && checkTerminationByTicks(currentTick) && checkTerminationByTime(context.getCurrTime())){

            if(simulationState.equals(eSimulationState.RUNNING))
            {
                context.setInProgress(eSimulationState.RUNNING);
            }
            else if(simulationState.equals(eSimulationState.PAUSED))
            {
                context.setInProgress(eSimulationState.PAUSED);
            }
            performSimulationStep(currentTick, currentDateTime, context);
            context.setEntityQuantities(currentTick,context.getEntityInstanceManager().getInstances().size());

            currentTick++;
        }
        context.setInProgress(eSimulationState.STOPPED);
        engineManager.setCountEndedSimulations(engineManager.getCountEndedSimulations() + 1);
        LocalDateTime endSimulationDateTime = LocalDateTime.now();
        simulationDetails.setEndSimulationDateTime(endSimulationDateTime);
    }


    public void stop() {
        stopRequested = true;
        simulationState = eSimulationState.STOPPED;
    }
    public void pause() {
        paused = true;
        simulationState = eSimulationState.PAUSED;
    }

//    public void resume() {
//        synchronized (pauseLock) {
//            paused = false;
//            pauseLock.notifyAll();
//        }
//        simulationState = eSimulationState.RUNNING;
//
//    }
    public boolean isPaused() {
        return paused;
    }
//    public void performSimulationStep(int currentTick,LocalDateTime time, IContext context) {
//        synchronized (this) {
//            if (simulationState == eSimulationState.PAUSED) {
//                long startTime = System.currentTimeMillis();
//                try {
//                    this.wait();
//                } catch (InterruptedException e) {
//                    String exceptionMessage = e.getMessage(); // Get the exception message
//                    setSimulationError(exceptionMessage);
//                }
//                pauseTime+=System.currentTimeMillis() - startTime;
//            }
//        }
//        for (IRule rule :context.getWorld().getRuleManager().getRules().values()) {
//
//            if (stopRequested) {
//                break;
//            }
//            if (rule.getActivation().isActive(currentTick)) {
//                for (IAction action : rule.getActionsToPerform()) {
//                    for (IEntityInstance entityInstance  : context.getEntityInstanceManager().getInstances()) {
//                        if(isValidEntityForExecute(entityInstance, action.getPrimaryEntity())) {
//                            context.getGridManager().moveEntityRandomly(entityInstance);
//                            if(isSecondaryEntityExist(action))
//                                secondaryEntitiesExecute(action);
//                            else {
//                                this.context.setPrimaryEntityInstance(entityInstance);
//                                context.setSecondaryEntityInstance(null);
//                                action.execute(context);
//                            }
//                        }
//                    }
//                    context.getEntityInstanceManager().setInstances(context.getEntityInstanceManager().getInstances());
//                    context.getEntityInstanceManager().setInstancesFromNewList(context.getInstancesForUpdate(), context.getWorld().getWorldGrid().getGridSize());
//                }
//            }
//        }
//        context.setCurrTick(currentTick);
//        LocalDateTime currentTime = LocalDateTime.now();
//        long elapsedSeconds = java.time.Duration.between(time, currentTime).getSeconds() - pauseTime / 1000;
//        context.setCurrTime((int) elapsedSeconds);
//
//    }
    public void performSimulationStep(int currentTick, LocalDateTime time, IContext context) {
        synchronized (this) {
            if (simulationState == eSimulationState.PAUSED) {
                long startTime = System.currentTimeMillis();
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    String exceptionMessage = e.getMessage(); // Get the exception message
                    setSimulationError(exceptionMessage); // Store the exception message
                }
                pauseTime += System.currentTimeMillis() - startTime;
            }
        }
        try {
            for (IRule rule: context.getWorld().getRuleManager().getRules().values()) {
                if (stopRequested) {
                    break;
                }
                if (rule.getActivation().isActive(currentTick)) {
                    for (IAction action: rule.getActionsToPerform()) {
                        for (IEntityInstance entityInstance: context.getEntityInstanceManager().getInstances()) {
                            if (isValidEntityForExecute(entityInstance, action.getPrimaryEntity())) {
                                context.getGridManager().moveEntityRandomly(entityInstance);
                                if (isSecondaryEntityExist(action))
                                    secondaryEntitiesExecute(action);
                                else {
                                    this.context.setPrimaryEntityInstance(entityInstance);
                                    context.setSecondaryEntityInstance(null);
                                    action.execute(context);
                                }
                            }
                        }

                    }
                }
            }
            context.getGridManager().clearBoard(context.getEntityInstanceManager().getInstances());
            context.getEntityInstanceManager().setInstances(context.getEntityInstanceManager().getInstances());
            context.getEntityInstanceManager().setInstancesFromNewList(context.getInstancesForUpdate(), context.getWorld().getWorldGrid().getGridSize());
            setEntitiesInGrid(context.getEntityInstanceManager().getInstances() , context.getGridManager());
            context.getInstancesForUpdate().clear();
        } catch (Exception e) {
            String exceptionMessage = e.getMessage(); // Get the exception message
            setSimulationError(exceptionMessage); // Store the exception message
            simulationState = eSimulationState.STOPPED;
        }
        context.setCurrTick(currentTick);
        LocalDateTime currentTime = LocalDateTime.now();
        long elapsedSeconds = java.time.Duration.between(time, currentTime).getSeconds() - pauseTime / 1000;
        context.setCurrTime((int) elapsedSeconds);
    }

    private void setEntitiesInGrid(List<IEntityInstance> instances, GridManager gridManager) {
        for(IEntityInstance entityInstance : instances) {
            if( entityInstance.getPosition() != null) {
                gridManager.getSimulationGrid().setEntityAt(entityInstance.getPosition().getX() , entityInstance.getPosition().getY(), entityInstance);
            }
        }
        for(IEntityInstance entityInstance : instances) {
            if(entityInstance.getPosition() == null) {
                Point point = gridManager.getAvailableCell();
                entityInstance.setPosition(point);
                gridManager.getSimulationGrid().setEntityAt(entityInstance.getPosition().getX() , entityInstance.getPosition().getY(), entityInstance);
            }
        }
    }

    private boolean isSecondaryEntityExist(IAction action) {
        if(action.getSecondaryEntityDefinition() != null) {
            context.setSecondaryEntityName(action.getSecondaryEntityDefinition().getSecondaryEntityDefinition().getEntityName());
            return true;
        }
        context.setSecondaryEntityName(null);
        return false;
    }

    private boolean isValidEntityForExecute(IEntityInstance entityInstance, IEntityDefinition contextEntity) {
        return entityInstance.getEntityDefinition().getEntityName().equals(contextEntity.getEntityName());
    }
    private void secondaryEntitiesExecute(IAction action) {
        String secondaryEntityName = action.getSecondaryEntityDefinition().getSecondaryEntityDefinition().getEntityName();
        Map<Integer, IEntityInstance> secondaryEntitiesForExecute = new LinkedHashMap<>();
        List<IEntityInstance> instances = context.getEntityInstanceManager().getInstances();

        for(IEntityInstance entityInstance : instances) {
            if( entityInstance.getEntityDefinition().getEntityName().equals(secondaryEntityName)) {
                context.setSecondaryEntityInstance(entityInstance);
                if(ckCondition(action.getSecondaryEntityDefinition().getConditions().get(0), context)) {
                    secondaryEntitiesForExecute.put(entityInstance.getId(), entityInstance);
                }
            }
        }
        executeActionForSecondaryEntity(action, secondaryEntitiesForExecute);
    }
    private void executeActionForSecondaryEntity(IAction action, Map<Integer, IEntityInstance> secondaryEntitiesForExecute) {
        int count = action.getSecondaryEntityDefinition().getCount();
        int secondaryEntitiesForExecuteSize = secondaryEntitiesForExecute.size();
        int mapItr = 0;

        for (Map.Entry<Integer, IEntityInstance> secondaryInstance: secondaryEntitiesForExecute.entrySet()) {
            if(mapItr == count && count !=0)
                break;
            context.setSecondaryEntityInstance(secondaryInstance.getValue());
            action.execute(context);
            mapItr++;
        }
    }
    private boolean ckCondition(IAction action, IContext context) {
        if(action instanceof AbstractConditionAction) {
            if(((AbstractConditionAction) action).checkCondition(context)) {
                return true;
            }
        }
        return false;
    }
    public boolean checkTerminationByTicks(int currentTick) {
        for (Object condition : context.getTerminationManager().getSimulationTerminationConditions()) {
            if (condition instanceof ByTicks) {
                ByTicks termination = (ByTicks) condition;
                if(currentTick >= termination.getCount()) {
                    return false;
                }

            }
        }
      return true;
    }
    public boolean checkTerminationByTime(int currTime) {
        long startTime = System.currentTimeMillis();
        long maxRunTimeMillis;

        for (Object condition: context.getTerminationManager().getSimulationTerminationConditions()) {
            if (condition instanceof BySeconds) {
                BySeconds termination = (BySeconds) condition;
                Integer seconds = termination.getCount();

                if(currTime >= seconds)
                    return false;

//                maxRunTimeMillis = seconds * 1000L;
//                if (System.currentTimeMillis() - startTime - pauseTime >= maxRunTimeMillis) {
//                    return false;
//                }
            }
        }

        return true;
    }

    public List<Integer> getPopulationList() {
        return populationList;
    }

    public void setPopulationList(List<Integer> populationList) {
        this.populationList = populationList;
    }

    public List<Object> getUserEnvVarChoices() {
        return userEnvVarChoices;
    }

    public void setUserEnvVarChoices(List<Object> userEnvVarChoices) {
        this.userEnvVarChoices = userEnvVarChoices;
    }

    public String getSimulationError() {
        return simulationError;
    }

    public void setSimulationError(String simulationError) {
        this.simulationError = simulationError;
    }
}

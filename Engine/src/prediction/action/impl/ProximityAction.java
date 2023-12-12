package prediction.action.impl;

import prediction.action.api.AbstractAction;
import prediction.action.api.IAction;
import prediction.action.api.eActionType;
import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.entity.ISecondaryEntity;
import prediction.execution.context.IContext;
import prediction.execution.context.SimulationGrid;
import prediction.execution.instance.entity.IEntityInstance;
import prediction.expression.ExpressionHandlerImpl;
import prediction.function.api.AbstractFunction;

import java.util.ArrayList;
import java.util.List;

public class ProximityAction extends AbstractAction {
    private ExpressionHandlerImpl expressionHandler;
    private IEntityDefinition sourceEntity;
    private IEntityDefinition targetEntity;
    private List<IAction> actions;
    private Integer depth;

    public ProximityAction(IEntityDefinition entityDefinition, ISecondaryEntity secondaryEntity, IEntityDefinition targetEntity, String ofExpression, List<IAction> actions) {
        super(eActionType.PROXIMITY, entityDefinition, secondaryEntity);
        this.sourceEntity = entityDefinition;
        this.targetEntity = targetEntity;
        this.actions = actions;
        this.expressionHandler = new ExpressionHandlerImpl(entityDefinition, ofExpression, "NUMERIC");

    }

    @Override
    public void execute(IContext context) {
        if( expressionHandler.getValueExpression(context) instanceof AbstractFunction)
            functionExpressionHandler(context);

        depth = Math.round((Float) expressionHandler.getValueExpression(context));
        List<IEntityInstance> nearestEntities = calculateNearestEntitiesInProximityCircles(context, depth);

        IEntityInstance selectedTargetEntity = null;
        if (!nearestEntities.isEmpty()) {
            selectedTargetEntity = nearestEntities.get(0);


            context.setSecondaryEntityInstance(selectedTargetEntity);
            for (IAction action: actions) {
                action.execute(context);
            }
        }
    }

    private List<IEntityInstance> calculateNearestEntitiesInProximityCircles(IContext context, int depth) {
        List<IEntityInstance> nearestEntities = new ArrayList<>();
        IEntityInstance sourceEntity = context.getPrimaryEntityInstance();
        SimulationGrid simulationGrid = context.getGridManager().getSimulationGrid();
        int sourceRow = sourceEntity.getPosition().getX();
        int sourceCol = sourceEntity.getPosition().getY();

        // Iterate through all entities in the grid
        for (int row = 0; row < simulationGrid.getRows(); row++) {
            for (int col = 0; col < simulationGrid.getCols(); col++) {
                IEntityInstance entity = simulationGrid.getEntityAt(row, col);

                if (entity != null && entity.getEntityDefinition().getEntityName().equals(targetEntity.getEntityName()) ) {
                    // Calculate the distance between sourceEntity and entity
                    int distance = calculateDistance(sourceRow, sourceCol, row, col, simulationGrid.getRows(), simulationGrid.getCols());

                    // Check if the entity is within the specified proximity circles
                    if (distance <= depth) {
                        nearestEntities.add(entity);
                    }
                }
            }
        }

        return nearestEntities;
    }

    private int calculateDistance(int sourceRow, int sourceCol, int targetRow, int targetCol, int numRows, int numCols) {
        int horizontalDistance = Math.min(Math.abs(targetCol - sourceCol), numCols - Math.abs(targetCol - sourceCol));
        int verticalDistance = Math.min(Math.abs(targetRow - sourceRow), numRows - Math.abs(targetRow - sourceRow));
        return horizontalDistance + verticalDistance;
    }

    private void functionExpressionHandler(IContext context) {
        Object evaluatedObject;

        evaluatedObject =((AbstractFunction) expressionHandler.getValueExpression(context)).execute(((AbstractFunction) expressionHandler.getValueExpression(context)).getArgumentName(), context);
        this.expressionHandler = new ExpressionHandlerImpl(super.getPrimaryEntity(), evaluatedObject.toString(), "NUMERIC");
    }

    public IEntityDefinition getSourceEntity() {
        return sourceEntity;
    }

    public IEntityDefinition getTargetEntity() {
        return targetEntity;
    }

    public ExpressionHandlerImpl getExpressionHandler() {
        return expressionHandler;
    }

    public List<IAction> getActions() {
        return actions;
    }
}

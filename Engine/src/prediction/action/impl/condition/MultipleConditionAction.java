package prediction.action.impl.condition;

import prediction.action.api.IAction;
import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.entity.ISecondaryEntity;
import prediction.defenition.entity.manager.EntityDefinitionManager;
import prediction.execution.context.IContext;
import xmlJavaFXReader.schema.generated.PRDCondition;

import java.util.ArrayList;
import java.util.List;

public class MultipleConditionAction extends AbstractConditionAction{
    private final List<AbstractConditionAction> conditions;
    private final String rootLogicalOperator;


    public MultipleConditionAction(IEntityDefinition entityDefinition, PRDCondition prdCondition,
                                   List<IAction> thenSection, List<IAction> elseSection, ISecondaryEntity secondaryEntity, EntityDefinitionManager entityDefinitionManager) {
        super(entityDefinition, thenSection, elseSection, secondaryEntity);
        conditions= new ArrayList<>();
        rootLogicalOperator = prdCondition.getLogical();
        for (PRDCondition subCondition : prdCondition.getPRDCondition()) {
            if(subCondition.getSingularity().equals("multiple"))
                conditions.add(new MultipleConditionAction(entityDefinition, subCondition, thenSection, elseSection, secondaryEntity, entityDefinitionManager));
            else
                conditions.add(new SingleConditionAction(entityDefinitionManager.getEntity(subCondition.getEntity()), subCondition.getProperty(), subCondition.getOperator(), subCondition.getValue(),thenSection, elseSection, secondaryEntity));
        }
    }
    public MultipleConditionAction(IEntityDefinition entityDefinition, PRDCondition prdCondition) {
        super(entityDefinition);
        conditions= new ArrayList<>();
        rootLogicalOperator = prdCondition.getLogical();
        for (PRDCondition subCondition : prdCondition.getPRDCondition()) {
            if(subCondition.getSingularity().equals("multiple"))
                conditions.add(new MultipleConditionAction(entityDefinition, subCondition));
            else
                conditions.add(new SingleConditionAction(entityDefinition, subCondition.getProperty(), subCondition.getOperator(), subCondition.getValue()));
        }
    }
    public Integer getConditionsSize() {
        return conditions.size();
    }
    @Override
    public boolean checkCondition(IContext context) {
        boolean result;

        if ("and".equalsIgnoreCase(rootLogicalOperator)) {
            result = true; // Initialize as true for "and" operator
        } else if ("or".equalsIgnoreCase(rootLogicalOperator)) {
            result = false; // Initialize as false for "or" operator
        } else {
            throw new IllegalArgumentException("Unsupported logical operator: " + rootLogicalOperator);
        }

        for (AbstractConditionAction condition : conditions) {
            boolean subConditionResult = condition.checkCondition(context);

            if ("and".equalsIgnoreCase(rootLogicalOperator)) {
                result = result && subConditionResult;
            } else if ("or".equalsIgnoreCase(rootLogicalOperator)) {
                result = result || subConditionResult;
            } else {
                throw new IllegalArgumentException("Unsupported logical operator: " + rootLogicalOperator);
            }

            if (!result && "and".equalsIgnoreCase(rootLogicalOperator)) {
                return false;
            } else if (result && "or".equalsIgnoreCase(rootLogicalOperator)) {
                return true;
            }
        }

        return result;
    }
    public String getRootLogicalOperator() {
        return rootLogicalOperator;
    }
}
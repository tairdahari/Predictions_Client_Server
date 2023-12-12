package prediction.rule;

import prediction.action.api.IAction;
import prediction.action.api.eActionType;
import prediction.action.impl.*;
import prediction.action.impl.condition.MultipleConditionAction;
import prediction.action.impl.condition.SingleConditionAction;
import prediction.action.impl.DecreaseAction;
import prediction.action.impl.DivideAction;
import prediction.action.impl.IncreaseAction;
import prediction.defenition.entity.IEntityDefinition;
import prediction.defenition.entity.ISecondaryEntity;
import prediction.defenition.entity.SecondaryEntityImpl;
import prediction.defenition.entity.manager.EntityDefinitionManager;
import xmlJavaFXReader.schema.generated.*;

import java.util.ArrayList;
import java.util.List;

public class RuleImpl implements IRule {
    private final String name;
    private final Activation activation;
    private final List<IAction> actions;
    private final EntityDefinitionManager entityDefinitionManager;

    public RuleImpl(PRDRule prdRule, EntityDefinitionManager entityManager) {
        this.name = prdRule.getName();
        this.activation = new ActivationImpl(prdRule.getPRDActivation());
        this.actions = new ArrayList<>();
        this.entityDefinitionManager = entityManager;

        for (PRDAction oneAction : prdRule.getPRDActions().getPRDAction()) {
            IAction action = createActionDefinition(oneAction, entityManager);
            actions.add(action);
        }
    }

    public IAction createActionDefinition(PRDAction oneAction, EntityDefinitionManager entityManager) {
        ISecondaryEntity secondaryEntity = createSecondaryEntityFromPRDSecondaryEntity(oneAction.getPRDSecondaryEntity(), entityManager );

        if(oneAction.getType().equals("calculation"))
            ckPropertyValid(oneAction.getResultProp(), entityManager);
        else if (oneAction.getProperty() != null)
            ckPropertyValid(oneAction.getProperty(), entityManager);

        switch (oneAction.getType()) {
            case "increase":
                return new IncreaseAction(entityManager.getEntity(oneAction.getEntity()),secondaryEntity, oneAction.getProperty(), oneAction.getBy());
            case "decrease":
                return new DecreaseAction(entityManager.getEntity(oneAction.getEntity()),secondaryEntity, oneAction.getProperty(),oneAction.getBy());
            case "calculation":
                return createCalcAction(oneAction, entityManager, secondaryEntity);
            case "condition":
                return createConditionAction(oneAction, entityManager, secondaryEntity);
            case "kill":
                return new KillAction(entityManager.getEntity(oneAction.getEntity()), secondaryEntity);
            case "set":
                return new SetAction(entityManager.getEntity(oneAction.getEntity()), secondaryEntity, oneAction.getProperty(),oneAction.getValue());
            case "proximity":
                return createProximityAction(oneAction, entityManager, secondaryEntity);
            case "replace":
                return new ReplaceAction(entityManager.getEntity(oneAction.getKill()),secondaryEntity, oneAction.getCreate(), oneAction.getMode());
            default:
                throw new IllegalArgumentException("Invalid property type: " + oneAction.getType());
        }
    }


    @Override
    public IAction createProximityAction(PRDAction oneAction, EntityDefinitionManager entityManager, ISecondaryEntity secondaryEntity) {
        List<IAction> actions = new ArrayList<>();

        for (PRDAction action: oneAction.getPRDActions().getPRDAction()) {
            actions.add(createActionDefinition(action, entityManager));
        }
        return new ProximityAction(entityManager.getEntity(oneAction.getPRDBetween().getSourceEntity()),secondaryEntity, entityManager.getEntity(oneAction.getPRDBetween().getTargetEntity()), oneAction.getPRDEnvDepth().getOf(), actions);
    }
    @Override
    public ISecondaryEntity createSecondaryEntityFromPRDSecondaryEntity(PRDAction.PRDSecondaryEntity prdSecondaryEntity, EntityDefinitionManager entityManager) {
        List<IAction> conditions = new ArrayList<>();
        if(prdSecondaryEntity != null ) {
            for (PRDCondition oneCondition: prdSecondaryEntity.getPRDSelection().getPRDCondition().getPRDCondition()) {
                conditions.add(createConditionActionForSecondaryEntity(oneCondition, entityManager));
            }
            conditions.add(createConditionActionForSecondaryEntity(prdSecondaryEntity.getPRDSelection().getPRDCondition(), entityManager));
            return new SecondaryEntityImpl(entityManager.getEntity(prdSecondaryEntity.getEntity()), prdSecondaryEntity.getPRDSelection().getCount(), conditions);
        }
        return null;
    }
    private void ckPropertyValid(String property, EntityDefinitionManager entityManager) {
        for (IEntityDefinition entity : entityManager.getEntities().values()) {
            if (entity.getEntityProperties().containsKey(property)) {
                return;
            }
        }
        throw new IllegalArgumentException("Invalid property name: " + property + ". No property with this name exists.");
    }
    public IAction createConditionActionForSecondaryEntity(PRDCondition oneCondition, EntityDefinitionManager entityManager) {

        if ("single".equals(oneCondition.getSingularity())) {
            return new SingleConditionAction(
                    entityManager.getEntity(oneCondition.getEntity()),
                    oneCondition.getProperty(),
                    oneCondition.getOperator(),
                    oneCondition.getValue()
            );
        } else if ("multiple".equals(oneCondition.getSingularity())) {
            return new MultipleConditionAction(
                    entityManager.getEntity(oneCondition.getEntity()),
                    oneCondition
            );
        } else {
            throw new IllegalArgumentException("Invalid singularity for PRDCondition: " + oneCondition.getSingularity());
        }
    }
@Override
    public IAction createConditionAction(PRDAction oneAction, EntityDefinitionManager entityManager, ISecondaryEntity secondaryEntity) {
        PRDCondition prdCondition = oneAction.getPRDCondition();
        List<IAction> thenSection = new ArrayList<>();
        List<IAction> elseSection = new ArrayList<>();

        PRDThen prdThen = oneAction.getPRDThen();
        for (PRDAction thenAction : prdThen.getPRDAction()) {
            thenSection.add(createActionDefinition(thenAction, entityManager));
        }

        PRDElse prdElse = oneAction.getPRDElse();
        if (prdElse != null) {
            for (PRDAction elseAction : prdElse.getPRDAction()) {
                elseSection.add(createActionDefinition(elseAction, entityManager));
            }
        }

        if ("single".equals(prdCondition.getSingularity())) {
            return new SingleConditionAction(
                    entityManager.getEntity(prdCondition.getEntity()),
                    prdCondition.getProperty(),
                    prdCondition.getOperator(),
                    prdCondition.getValue(),
                    thenSection,
                    elseSection,
                    secondaryEntity
            );
        } else if ("multiple".equals(prdCondition.getSingularity())) {
            return new MultipleConditionAction(
                    entityManager.getEntity(oneAction.getEntity()),
                    prdCondition,
                    thenSection,
                    elseSection,
                    secondaryEntity,
                    entityDefinitionManager
            );
        } else {
            throw new IllegalArgumentException("Invalid singularity for PRDCondition: " + prdCondition.getSingularity());
        }
    }


    @Override
    public IAction createCalcAction(PRDAction oneAction, EntityDefinitionManager entityManager, ISecondaryEntity secondaryEntity) {
        if(oneAction.getPRDMultiply() != null) {
            return new MultiplyAction(eActionType.CALCULATION, entityManager.getEntity(oneAction.getEntity()) , secondaryEntity,oneAction.getResultProp(),
                    oneAction.getPRDMultiply().getArg1()
            ,oneAction.getPRDMultiply().getArg2());
        } else if (oneAction.getPRDDivide() !=null) {
            return new DivideAction(eActionType.CALCULATION, entityManager.getEntity(oneAction.getEntity()), secondaryEntity ,oneAction.getResultProp(),
                    oneAction.getPRDDivide().getArg1()
                    ,oneAction.getPRDDivide().getArg2());
        }
        else {
            throw new IllegalArgumentException("Invalid PRDAction: Neither PRDMultiply nor PRDDivide is specified.");
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Activation getActivation() {
        return activation;
    }

    @Override
    public List<IAction> getActionsToPerform() { return actions; }
}
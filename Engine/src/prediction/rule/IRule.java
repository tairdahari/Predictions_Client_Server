package prediction.rule;

import prediction.action.api.IAction;
import prediction.defenition.entity.ISecondaryEntity;
import prediction.defenition.entity.manager.EntityDefinitionManager;
import xmlJavaFXReader.schema.generated.PRDAction;

import java.io.Serializable;
import java.util.List;

public interface IRule extends Serializable {
    String getName();
    Activation getActivation();
    List<IAction> getActionsToPerform();
    IAction createActionDefinition(PRDAction oneAction, EntityDefinitionManager entityManager) ;
    IAction createCalcAction(PRDAction oneAction, EntityDefinitionManager entityManager, ISecondaryEntity secondaryEntity);
    IAction createProximityAction(PRDAction oneAction, EntityDefinitionManager entityManager, ISecondaryEntity secondaryEntity);
    IAction createConditionAction(PRDAction oneAction, EntityDefinitionManager entityManager, ISecondaryEntity secondaryEntity);
    ISecondaryEntity createSecondaryEntityFromPRDSecondaryEntity(PRDAction.PRDSecondaryEntity prdSecondaryEntity, EntityDefinitionManager entityManager);
}
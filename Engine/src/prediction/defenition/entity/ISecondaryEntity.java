package prediction.defenition.entity;

import prediction.action.api.IAction;

import java.util.List;

public interface ISecondaryEntity {
    IEntityDefinition getSecondaryEntityDefinition();
     Integer getCount();
    boolean isPositive(Integer countToCk);
     List<IAction> getConditions();
    void setCount(String count);
}
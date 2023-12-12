package prediction.defenition.entity;

import com.sun.xml.internal.ws.protocol.soap.MessageCreationException;
import prediction.action.api.IAction;
import sun.plugin2.message.Message;

import java.util.ArrayList;
import java.util.List;

public class SecondaryEntityImpl implements ISecondaryEntity {
    private IEntityDefinition secondaryEntityDefinition;
    private Integer count;
    private List<IAction> conditions;

    public SecondaryEntityImpl(IEntityDefinition entity, String count, List<IAction> conditions) {
        this.conditions = new ArrayList<>();
        this.secondaryEntityDefinition = entity;
        setCount(count);
        this.conditions = conditions;
    }

    public IEntityDefinition getSecondaryEntityDefinition() {
        return secondaryEntityDefinition;
    }
    @Override
    public Integer getCount() {
        return count;
    }
    @Override
    public void setCount(String count) {
        try {
            Integer countToCk= Integer.parseInt(count);
            if(isPositive(countToCk))
                this.count = countToCk;
            else
                throw new IllegalArgumentException("Count must be positive");
        } catch (MessageCreationException e) {
            if(count.equals("ALL"))
                this.count = 0;
        }
    }

    @Override
    public boolean isPositive(Integer countToCk) {
        if(countToCk < 1)
            return false;
        return true;
    }
    @Override

    public List<IAction> getConditions() {
        return conditions;
    }
}

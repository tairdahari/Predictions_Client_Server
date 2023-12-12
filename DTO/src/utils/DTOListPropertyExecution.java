package utils;

import java.util.ArrayList;
import java.util.List;

public class DTOListPropertyExecution {
    private final List<DTOPropertyExecution> dtoPropertyExecutionList;

    public DTOListPropertyExecution()
    {
        dtoPropertyExecutionList=new ArrayList<>();
    }

    public List<DTOPropertyExecution> getDtoPropertyExecutionList() {
        return dtoPropertyExecutionList;
    }
}

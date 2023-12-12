package utils;

import prediction.defenition.grid.GridDefinition;

public class DTOGridDefinition {
    private String rows;
    private String cols;
    private Integer size;

    public DTOGridDefinition(GridDefinition grid) {
        this.rows = grid.getRows().toString();
        this.cols = grid.getCols().toString();
        this.size = grid.getCols() * grid.getRows();
    }

    public String getRows() {
        return rows;
    }

    public String getCols() {
        return cols;
    }

    public Integer getSize() {
        return size;
    }
}

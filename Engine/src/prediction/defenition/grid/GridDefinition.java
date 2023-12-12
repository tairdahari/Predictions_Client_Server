package prediction.defenition.grid;

import xmlJavaFXReader.schema.generated.PRDWorld;

public class GridDefinition {
    private Integer rows;
    private Integer cols;
    private Integer gridSize;

    public GridDefinition(PRDWorld.PRDGrid grid) {
        try {
            ckValidRowsAndCols(grid.getRows(), grid.getColumns());
            this.rows = grid.getRows();
            this.cols = grid.getColumns();
            this.gridSize = rows * cols;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void ckValidRowsAndCols(int rows, int columns) {
        if (rows < 10 || rows > 100 ) {
            throw new IllegalArgumentException("Rows must be between 10 and 100.");
        }
        if ( columns < 10 || columns > 100 ) {
            throw new IllegalArgumentException("columns must be between 10 and 100.");
        }
    }

    public Integer getRows() {
        return rows;
    }

    public Integer getCols() {
        return cols;
    }

    public Integer getGridSize() {
        return gridSize;
    }
}

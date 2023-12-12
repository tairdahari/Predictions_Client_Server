package prediction.execution.context;

import javafx.scene.effect.Light;
import prediction.defenition.grid.Point;
import prediction.execution.instance.entity.IEntityInstance;

import java.util.HashMap;
import java.util.Map;

public class SimulationGrid {
    private Integer rows;
    private Integer cols;
    private Integer gridSize;
    private IEntityInstance[][] instancesBoard;
    public SimulationGrid(Integer rows, Integer cols) {
        this.rows = rows;
        this.cols = cols;
        this.gridSize = rows * cols;
        this.instancesBoard = new IEntityInstance[rows][cols];
    }
    public IEntityInstance getEntityAt(int row, int col) {
        return instancesBoard[row][col];
    }

    public void setEntityAt(int row, int col, IEntityInstance entity) {
        instancesBoard[row][col] = entity;
    }
    public void moveEntity(IEntityInstance entity, int newRow, int newCol) {
        // Remove the entity from its current position
        int oldRow = entity.getPosition().getX();
        int oldCol = entity.getPosition().getY();
        instancesBoard[oldRow][oldCol] = null;

        // Set the entity at its new position
        instancesBoard[newRow][newCol] = entity;
        entity.setPosition(new Point(newRow, newCol));
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getCols() {
        return cols;
    }

    public void setCols(Integer cols) {
        this.cols = cols;
    }

    public Integer getGridSize() {
        return gridSize;
    }
}
package prediction.execution.context;

import prediction.defenition.grid.Point;
import prediction.execution.instance.entity.IEntityInstance;

import java.util.*;

public class GridManager {
    private SimulationGrid simulationGrid;

    public GridManager(SimulationGrid grid) {
        this.simulationGrid = grid;
    }
    public SimulationGrid getSimulationGrid() {
        return simulationGrid;
    }
    public Point getAvailableCell() {
        int numRows = simulationGrid.getRows();
        int numCols = simulationGrid.getCols();
        Random random = new Random();

        // List to store available cells
        List<Point> availableCells = new ArrayList<>();

        // Iterate through all cells in the grid
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (simulationGrid.getEntityAt(row, col) == null) {
                    // If the cell is unoccupied, add its coordinates to the list
                    availableCells.add(new Point(row, col));
                }
            }
        }

        // If there are available cells, choose one randomly
        if (!availableCells.isEmpty()) {
            int randomIndex = random.nextInt(availableCells.size());
            return availableCells.get(randomIndex);
        }

        throw new IllegalArgumentException("There is no available cell for this instance.");
    }

    public void moveEntityRandomly(IEntityInstance entity) {
        int currentRow = entity.getPosition().getX();
        int currentCol = entity.getPosition().getY();

        // List of possible directions (up, down, left, right)
        List<Point> directions = Arrays.asList(new Point(-1, 0), new Point(1, 0), new Point(0, -1), new Point(0, 1));

        // Shuffle the directions to choose a random one
        Collections.shuffle(directions);

        for (Point direction : directions) {
            int newRow = (currentRow + direction.getX() + simulationGrid.getRows()) % simulationGrid.getRows(); // Wrap around vertically
            int newCol = (currentCol + direction.getY() + simulationGrid.getCols()) % simulationGrid.getCols(); // Wrap around horizontally

            if (simulationGrid.getEntityAt(newRow, newCol) == null) {
                // Move the entity to the new position
                simulationGrid.moveEntity(entity, newRow, newCol);
                break;
            }
        }
    }

    public void clearBoard(List<IEntityInstance> instances) {
        for(IEntityInstance entityInstance : instances) {
            getSimulationGrid().setEntityAt(entityInstance.getPosition().getX(), entityInstance.getPosition().getY(), null);
//            if( !entityInstance.isAlive()) {
//                getSimulationGrid().setEntityAt(entityInstance.getPosition().getX(), entityInstance.getPosition().getY(), null);
        }
    }
}
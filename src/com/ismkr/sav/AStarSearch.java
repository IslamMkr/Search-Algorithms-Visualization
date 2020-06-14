package com.ismkr.sav;

import java.awt.*;
import java.util.ArrayList;

public class AStarSearch {

    private final ArrayList<Cell> openSet = new ArrayList<>(); // List of not evaluated cells
    private final ArrayList<Cell> closedSet = new ArrayList<>(); // List of evaluated cells
    private final ArrayList<Cell> path = new ArrayList<>(); // List of path cells
    private final Cell start; // Starting point, Cell
    private final Cell goal; // Ending point, Cell
    private final Pane pane;

    public AStarSearch(Cell start, Cell goal, Pane pane) {
        this.start = start;
        this.goal = goal;
        this.pane = pane;
        this.openSet.add(start);

        startAlgorithm();
    }

    /**
     * The A* Algorithm loop
     * Everything starts from here
     */
    private void startAlgorithm() {
        while (openSet.size() > 0) {
            int currentPos= getCurrent(); // The position of the cell with the lowest distance to the goal
            Cell current = openSet.get(currentPos);
            if(goal.equals(current)) { // The end of the loop where we arrive to the goal
                goal.setColor(Color.orange);
                goal.repaint();
                drawPath(current);
                break;
            }
            openSet.remove(currentPos);
            if(!current.equals(start) && !current.equals(goal)) current.setColor(Color.white);
            closedSet.add(current);
            neighborsCellsCheck(current.getNeighbors(), current);
            try {
                Thread.sleep(100);
                pane.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Draw the final path between starting point to the goal
     * @param current @Cell
     */
    private void drawPath(Cell current) {
        Cell temp = current;
        path.add(temp);
        temp.setPath(true);
        while (temp.getPrevious() != null) {
            path.add(temp.getPrevious());
            temp = temp.getPrevious();
            temp.setPath(true);
        }

        for (Cell cell : path)
            if (!cell.equals(goal) && !cell.equals(start)) cell.setColor(Color.BLUE);
    }

    /**
     * Calculates all necessary values of the neighbors cells of the current evaluated cell
     * @param neighbors @ArrayList<Cell>
     * @param current @Cell
     */
    private void neighborsCellsCheck(ArrayList<Cell> neighbors, Cell current){
        for(Cell neighbor : neighbors) {
            if (!inClosedSet(neighbor) && !neighbor.isWall()) { // Checking only the none evaluated
                double tempG = current.getCostG() + getDistance(neighbor, current);
                if (inOpenSet(neighbor)) {
                    if (tempG < neighbor.getCostG()) {
                        neighbor.setCostG(tempG);
                        calculateF(neighbor, current);
                    }
                } else {
                    neighbor.setCostG(tempG);
                    calculateF(neighbor, current);
                    if(!current.equals(goal)) neighbor.setColor(Color.MAGENTA);
                    openSet.add(neighbor);
                }
            }
        }
    }

    /**
     * Calculates the exacte distance between tow cells
     * @param neighbor @Cell
     * @param current @Cell
     * @return @Double
     */
    private double getDistance(Cell neighbor, Cell current) {
        if (neighbor.getCoordinates().x == current.getCoordinates().x ||
                neighbor.getCoordinates().y == current.getCoordinates().y) return 1;
        else return Math.sqrt(2);
    }

    private void calculateF(Cell neighbor, Cell current) {
        neighbor.setHeuristicH(Heuristic.manhattan(neighbor, goal));
        neighbor.setTotalCostF(neighbor.getCostG() + neighbor.getHeuristicH()); // F = G + H
        neighbor.setPrevious(current);
    }

    /**
     * Checks if cell is already evaluated (exists in @closedSet list or not)
     * @param cell @Cell
     * @return : @Boolean
     */
    private boolean inClosedSet(Cell cell) {
        for (Cell evaluated : closedSet)
            if(cell.equals(evaluated))
                return true;
        return false;
    }

    /**
     * Checks if cell is ready to be evaluated (exists in @openSet list or not)
     * @param cell @Cell
     * @return : @Boolean
     */
    private boolean inOpenSet(Cell cell) {
        for (Cell notEvaluated : openSet)
            if(cell.equals(notEvaluated))
                return true;
        return false;
    }

    /**
     * Returns the position of the cell that has lowest @F value
     * from the cells that are ready to be evaluated (from @openSet list)
     * @return : @Integer
     */
    private int getCurrent() {
        int posLowestDist = 0;
        for (int i=1; i < openSet.size(); i++)
            if(openSet.get(i).getTotalCostF() < openSet.get(posLowestDist).getTotalCostF())
                posLowestDist = i;
        return posLowestDist;
    }

}

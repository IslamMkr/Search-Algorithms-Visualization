package com.ismkr.sav;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Cell extends JPanel {

    public static final int CELL_WIDTH = 20;

    private final Point coordinates;
    private final Point startPoint;
    private Color color = Color.lightGray;
    private Cell previous = null;
    private boolean isWall = false;
    private boolean isPath = false;
    private boolean isStartOrGoal = false;

    private double totalCostF;
    private double costG;
    private double heuristicH;

    public Cell(Point point) {
        this.startPoint = point;
        this.coordinates = new Point(startPoint.x / CELL_WIDTH, startPoint.y / CELL_WIDTH);
    }

    public Point getStartPoint() { return startPoint; }
    public Point getCoordinates() { return coordinates; }
    public double getTotalCostF() { return totalCostF; }
    public double getCostG() { return costG; }
    public double getHeuristicH() { return heuristicH; }
    public Cell getPrevious() { return previous; }
    public boolean isWall() { return isWall; }
    //public boolean isPath() { return isPath; }


    public void setStartOrGoal(boolean startOrGoal) { isStartOrGoal = startOrGoal; }
    public void setPath(boolean path) { isPath = path; }
    public void setPrevious(Cell previous) { this.previous = previous; }
    public void setTotalCostF(double totalCostF) { this.totalCostF = totalCostF; }
    public void setCostG(double costG) { this.costG = costG; }
    public void setHeuristicH(double heuristicH) { this.heuristicH = heuristicH; }
    public void setColor(Color color) { this.color = color; }
    public void setIsWall(boolean isWall) { this.isWall = isWall; }

    /**
     * It draws a rectangle of @CELL_WIDTH of width with @color
     * the rectangle represents the cell
     * @param g : @Graphics
     */
    public void draw(Graphics g) {
        g.setColor(color);
        if (isStartOrGoal) {
            g.fillRect(startPoint.x, startPoint.y, CELL_WIDTH, CELL_WIDTH);
        }
        if (!isPath && !isStartOrGoal) {
            g.fillRect(startPoint.x, startPoint.y, CELL_WIDTH, CELL_WIDTH);
        } else {
            if(previous != null) {
                g.setColor(Color.BLUE);
                g.drawLine(startPoint.x + CELL_WIDTH/2, startPoint.y + CELL_WIDTH/2,
                        previous.getStartPoint().x + CELL_WIDTH/2, previous.getStartPoint().y + CELL_WIDTH/2);
            }
        }

        //g.setColor(Color.black);
        //g.drawLine(startPoint.x, startPoint.y, startPoint.x + CELL_WIDTH, startPoint.y);
        //g.drawLine(startPoint.x, startPoint.y, startPoint.x, startPoint.y + CELL_WIDTH);
        //g.drawLine(startPoint.x + CELL_WIDTH, startPoint.y + CELL_WIDTH, startPoint.x + CELL_WIDTH, startPoint.y);
        //g.drawLine(startPoint.x + CELL_WIDTH, startPoint.y + CELL_WIDTH, startPoint.x, startPoint.y + CELL_WIDTH);
    }

    /**
     * Compares two @Cell object by position
     * Two cells are equal only if they have the same coordinates
     * @param cell @Cell : cell to compare with
     * @return @Boolean
     */
    public boolean equals(Cell cell) {
        return this.coordinates.equals(cell.getCoordinates());
    }

    /**
     * Determinants the cell's neighboring cells
     * @return @ArrayList<Cell>
     */
    public ArrayList<Cell> getNeighbors() {
        ArrayList<Cell> neighbors = new ArrayList<>();
        if (coordinates.x-1 >= 0) neighbors.add(Pane.cells[coordinates.x-1][coordinates.y]);
        if (coordinates.y-1 >= 0) neighbors.add(Pane.cells[coordinates.x][coordinates.y-1]);
        if (coordinates.x+1 < Pane.verticalCells) neighbors.add(Pane.cells[coordinates.x+1][coordinates.y]);
        if (coordinates.y+1 < Pane.horizontalCells) neighbors.add(Pane.cells[coordinates.x][coordinates.y+1]);
        if (coordinates.x-1 >= 0 && coordinates.y-1 >= 0) neighbors.add(Pane.cells[coordinates.x-1][coordinates.y-1]);
        if (coordinates.x+1 < Pane.verticalCells && coordinates.y+1 < Pane.horizontalCells) neighbors.add(Pane.cells[coordinates.x+1][coordinates.y+1]);
        if (coordinates.x+1 < Pane.verticalCells && coordinates.y-1 >= 0) neighbors.add(Pane.cells[coordinates.x+1][coordinates.y-1]);
        if (coordinates.x-1 >= 0 && coordinates.y+1 < Pane.horizontalCells) neighbors.add(Pane.cells[coordinates.x-1][coordinates.y+1]);
        return neighbors;
    }

    public void reset() {
        color = Color.lightGray;
        previous = null;
        isWall = false;
        isPath = false;
        isStartOrGoal = false;

        totalCostF = 0;
        costG = 0;
        heuristicH = 0;
    }
}

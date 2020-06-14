package com.ismkr.sav;

import javax.swing.*;
import java.awt.*;

public class Pane extends JPanel {

    public static int verticalCells; // Number of cells in one vertical line
    public static int horizontalCells; // Number of cells in one horizontal line

    private Point startCellCoordinates = null; // Coordinates of the starting cell
    private Point goalCellCoordinates = null; // Coordinates of the goal cell

    public static Cell[][] cells; // Array of all cells

    private boolean startNGoalSpecified = false;

    private Thread algorithmThread = null;

    public Pane(int verticalCells, int horizontalCells) {
        Pane.verticalCells = verticalCells;
        Pane.horizontalCells = horizontalCells;
        cells = new Cell[verticalCells][horizontalCells];

        for (int i=0; i < verticalCells; i++)
            for (int j=0; j < horizontalCells; j++)
                cells[i][j] = new Cell(new Point(i * Cell.CELL_WIDTH, j * Cell.CELL_WIDTH));
    }

    public boolean isStartNGoalSpecified() { return startNGoalSpecified; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Cell[] cell : cells)
            for (int i=0; i < horizontalCells; i++)
                cell[i].draw(g);
    }

    public void mouseDragging(Point point) {
        if(startCellCoordinates != null && goalCellCoordinates != null) {
            if(!startCellCoordinates.equals(point) && !goalCellCoordinates.equals(point)) {
                cells[point.x][point.y].setIsWall(true);
                cells[point.x][point.y].setColor(Color.black);
                this.repaint();
            }
        }
    }

    public void mouseClicked(Point point) {
        if(startCellCoordinates == null) {
            startCellCoordinates = point;
            cells[point.x][point.y].setColor(Color.green);
            cells[point.x][point.y].setIsWall(false);
            cells[point.x][point.y].setStartOrGoal(true);
            if (goalCellCoordinates != null) startNGoalSpecified = true;
        } else if(startCellCoordinates.equals(point)) {
            cells[startCellCoordinates.x][startCellCoordinates.y].setColor(Color.lightGray);
            cells[startCellCoordinates.x][startCellCoordinates.y].setStartOrGoal(false);
            startNGoalSpecified = false;
            startCellCoordinates = null;
        } else if(goalCellCoordinates == null) {
            goalCellCoordinates = point;
            cells[point.x][point.y].setColor(Color.orange);
            cells[point.x][point.y].setIsWall(false);
            cells[point.x][point.y].setStartOrGoal(true);
            startNGoalSpecified = true;
        }  else if(goalCellCoordinates.equals(point)) {
            cells[goalCellCoordinates.x][goalCellCoordinates.y].setColor(Color.lightGray);
            cells[goalCellCoordinates.x][goalCellCoordinates.y].setStartOrGoal(false);
            startNGoalSpecified = false;
            goalCellCoordinates = null;
        } else if (cells[point.x][point.y].isWall()) {
            cells[point.x][point.y].setIsWall(false);
            cells[point.x][point.y].setColor(Color.lightGray);
        } else {
            if(!startCellCoordinates.equals(point) && !goalCellCoordinates.equals(point)) {
                cells[point.x][point.y].setIsWall(true);
                cells[point.x][point.y].setColor(Color.black);
            }
        }
        this.repaint();
    }

    public void startAlgorithm() {
        Cell start = cells[startCellCoordinates.x][startCellCoordinates.y];
        Cell goal = cells[goalCellCoordinates.x][goalCellCoordinates.y];
        Pane pane = this;
        algorithmThread = new Thread(() -> new AStarSearch(start, goal, pane));
        algorithmThread.start();
    }

    public void reset() {
        startCellCoordinates = null;
        goalCellCoordinates = null;
        startNGoalSpecified = false;
        algorithmThread.stop();
        algorithmThread = null;

        for (int i=0; i < verticalCells; i++)
            for (int j=0; j < horizontalCells; j++)
                cells[i][j].reset();
        this.repaint();
    }
}

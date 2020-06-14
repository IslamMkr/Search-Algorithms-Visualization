package com.ismkr.sav;

public class Heuristic {

    private Heuristic() {}

    /**
     * Calculates the manhattan distance between two cells
     * @param start @Cell : the initial cell
     * @param goal @Cell : the finish cell
     * @return @Double : the distance
     */
    public static double manhattan(Cell start, Cell goal) {
        return Math.abs(goal.getCoordinates().x - start.getCoordinates().x) +
                Math.abs(goal.getCoordinates().y - start.getCoordinates().y);
    }

    /**
     * Calculates the euclidean distance between two cells
     * @param start @Cell : the initial cell
     * @param goal @Cell : the finish cell
     * @return @Double : the distance
     */
    public static double euclidean(Cell start, Cell goal) {
        return Math.sqrt(
                Math.pow(goal.getCoordinates().x - start.getCoordinates().x, 2) +
                        Math.pow(goal.getCoordinates().y - start.getCoordinates().y, 2)
        );
    }

}

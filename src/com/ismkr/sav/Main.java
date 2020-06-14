package com.ismkr.sav;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {

    private static final int WIDTH = 817;
    private static final int HEIGHT = 600;
    private final Pane pane;

    private boolean algorithmStarted = false;

    public Main() {
        setTitle("Search Algorithms Visualization");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        int verticalCells = (WIDTH - 17) / Cell.CELL_WIDTH;
        int horizontalCells = HEIGHT / Cell.CELL_WIDTH;

        pane = new Pane(verticalCells, horizontalCells);
        pane.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!algorithmStarted) {
                    int x = e.getX(), y = e.getY();
                    int cellX = x / Cell.CELL_WIDTH;
                    int cellY = y / Cell.CELL_WIDTH;

                    pane.mouseClicked(new Point(cellX, cellY));
                }
            }
            @Override public void mousePressed(MouseEvent e) { }
            @Override public void mouseReleased(MouseEvent e) { }
            @Override public void mouseEntered(MouseEvent e) { }
            @Override public void mouseExited(MouseEvent e) { }
        });
        pane.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!algorithmStarted) {
                    int x = e.getX(), y = e.getY();
                    int cellX = x / Cell.CELL_WIDTH;
                    int cellY = y / Cell.CELL_WIDTH;

                    pane.mouseDragging(new Point(cellX, cellY));
                }
            }
            @Override public void mouseMoved(MouseEvent e) { }
        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_SPACE) {
                    if (pane.isStartNGoalSpecified()) {
                        algorithmStarted = true;
                        pane.startAlgorithm();
                    }
                } else if(e.getKeyChar() == KeyEvent.VK_R) {
                    algorithmStarted = false;
                    pane.reset();
                }
            }
            @Override
            public void keyPressed(KeyEvent e) { }
            @Override
            public void keyReleased(KeyEvent e) { }
        });

        setContentPane(pane);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }

}

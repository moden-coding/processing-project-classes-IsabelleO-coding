import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Snake {
    private PApplet canvas;
    PImage Snake;
    int width;
    private int x;
    private int y;
    private int Swidth;
    private int Sheight;
    private int speed;
    private int Sdirection;
    ArrayList<Snake> snakes;

    public Snake(int Xcorr, int Ycorr, PApplet c, ArrayList<Snake> s, int direction) { // sets up snake variables and everything it needs to access
        x = Xcorr;
        y = Ycorr;
        canvas = c;
        Swidth = 75;
        Sheight = 30;
        speed = 3;
        Snake = canvas.loadImage("snake_CS1.png");
        Snake.resize(Swidth, Sheight);
        width = canvas.width;
        Sdirection = direction;
        snakes = s;
    }

    public void display() { // diplays snake image
        canvas.image(Snake, x, y);
    }

    public void move() { // moves snake alternating from left to right or right to left
        x += speed * Sdirection;
        if (x > width || x < -100) {
            disappear();
        }
    }

    public void disappear() { // removing snake from array list
        snakes.remove(this);
    }

    public int getWidth() { // returns snake width for indiana jones class to use to check for overlapping
        return Swidth;
    }

    public int getHeight() { // returns snake height for indiana jones class to use to check for overlapping
        return Sheight;
    }

    public Integer getXPosition() { // returns x corrdinate of snake for indiana jones class to use to check for overlapping
        return x;
    }

    public Integer getYPosition() { // returns y corrdinate of snake for indiana jones class to use to check forcoverlapping
        return y;
    }
}

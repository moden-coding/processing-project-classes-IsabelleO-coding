import processing.core.PApplet;

public class Boulder {
    int width;
    private PApplet canvas;
    private int x;
    private int y;
    private int color;
    private int size;
    private int speed;

    public Boulder(int Xcorr, int Ycorr, PApplet c) { // sets up all the variables for the boulder
        canvas = c;
        x = Xcorr;
        y = Ycorr;
        color = canvas.color(139, 115, 85);
        size = 100;
        speed = 20;
        width = canvas.width;
    }

    public void display() { // displays boulder circle
        canvas.noStroke();
        canvas.fill(color);
        canvas.circle(x, y, size);
    }

    public void move() { // moves boulder accros screen
        x += speed;
        respawn();
    }

    public void respawn() { // makes boulder respawn at left of screen after passing through
        if (x - (size / 2) == width + 10) {
            x = -200;
        }
    }

    public Integer getXPosition() { // returns x position of boulder for indiana jones class to use for overlapping check
        return x;
    }

    public Integer getYPosition() { // returns y position of boulder for indiana jones class to use for overlapping check
        return y;
    }

    public Integer getSize() { // returns size of boulder for indiana jones class to use for overlapping check
        return size;
    }

}

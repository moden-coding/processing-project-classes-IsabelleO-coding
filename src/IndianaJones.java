import processing.core.PApplet;
import processing.core.PImage;

public class IndianaJones {
    private PApplet canvas;
    private PImage indianaJones;
    private PApplet parent;
    private int x;
    private int y;
    private int Jwidth;
    private int Jheight;

    public IndianaJones(int Xcorr, int Ycorr, int w, int h, PApplet c, PApplet p) { // sets up all the variables and image for indianajones
        canvas = c;
        x = Xcorr;
        y = Ycorr;
        Jwidth = w;
        Jheight = h;
        indianaJones = canvas.loadImage("indianaJonesCS1.png");
        indianaJones.resize(Jwidth, Jheight);
        parent = p;
    }

    public void display() { // displays image
        canvas.image(indianaJones, x, y);
    }

    public void move(int kC) { // makes indiana jones move with arrow keys
        int code = kC; // Chat GPT told me this so that the keys work in a custom class
        if (canvas.height - 25 >= y + Jheight && y > 0) {
            if (code == parent.UP) {
                y -= 75;
            }
        }
        if (0 <= y && y + Jheight < canvas.height - 25) {
            if (code == parent.DOWN) {
                y += 75;
            }
        }
        if (canvas.width >= x + Jwidth && 0 < x) {
            if (code == parent.LEFT) {
                x -= 50;
            }
        }
        if (0 <= x && canvas.width > x + Jwidth) {
            if (code == parent.RIGHT) {
                x += 50;
            }

        }
    }

    public boolean touchingBoulder(Boulder boulder) { // checking if jones is touching a boulder
        int Bx = boulder.getXPosition();
        int By = boulder.getYPosition();
        int Bradius = boulder.getSize() / 2;
        int bottomOfB = By + Bradius;
        int topOfB = By - Bradius;
        int leftOfB = Bx - Bradius;
        int rightOfB = Bx + Bradius;
        int bottomOfJ = y + Jheight;
        int topOfJ = y;
        int leftOfJ = x;
        int rightOfJ = x + Jwidth;
        if (bottomOfJ > topOfB && topOfJ < bottomOfB && rightOfJ > leftOfB && leftOfJ < rightOfB) {
            return true;
        } else {
            return false;
        }
    }

    public boolean touchingSpike(Spike spike) { // checking if jones is touching a spike
        int Scolor = spike.getColor();
        int Sx = spike.getXPosition();
        int Sy = spike.getYPosition();
        int Sradius = spike.getSize() / 2;
        int bottomOfS = Sy + Sradius;
        int topOfS = Sy - Sradius;
        int leftOfS = Sx - Sradius;
        int rightOfS = Sx + Sradius;
        int bottomOfJ = y + Jheight;
        int topOfJ = y;
        int leftOfJ = x;
        int rightOfJ = x + Jwidth;
        if (bottomOfJ > topOfS && topOfJ < bottomOfS && rightOfJ > leftOfS && leftOfJ < rightOfS
                && Scolor == canvas.color(255, 0, 0)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean touchingSnake(Snake snake) { // checking if jones is touching a snake
        int Snx = snake.getXPosition();
        int Sny = snake.getYPosition();
        int Swidth = snake.getWidth();
        int Sheight = snake.getHeight();
        int topOfSn = Sny;
        int bottomOfSn = Sny + Sheight;
        int leftOfSn = Snx;
        int rightOfSn = Snx + Swidth;
        int bottomOfJ = y + Jheight;
        int topOfJ = y;
        int leftOfJ = x;
        int rightOfJ = x + Jwidth;
        if (bottomOfJ > topOfSn && topOfJ < bottomOfSn && rightOfJ > leftOfSn && leftOfJ < rightOfSn) {
            return true;
        } else {
            return false;
        }
    }

    public Integer getXPosition() { // returning x corrdinate of jones for app to use
        return x;
    }

    public Integer getYPosition() { // returning y corrdinate of jones for app to use
        return y;
    }

    public Integer getWidth() { // returning width of jones for app to use
        return Jwidth;
    }

    public Integer getHeight() { // returning height of jones for app to use
        return Jheight;
    }
}

import processing.core.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.*;

public class App extends PApplet {
    PImage indianaJones;
    int width = 900;
    int height = 850;
    int jonesWidth = 50;
    int jonesHeight = 75;
    int jonesX = width / 2;
    int jonesY = height - 100;
    boolean spawn = true;
    ArrayList<Spike> spikes;
    ArrayList<Snake> snakes;
    // int boulderX = -200;
    // int boulderY = height - 200;
    Boulder boulder;
    IndianaJones jones;
    Spike spike;
    Snake snake;
    PImage cave;
    int caveHeight = 75;
    int caveWidth = 300;
    int caveX = width / 2 - caveWidth / 2;
    int caveY = 0;
    int stage;
    int ButtonColor = color(101, 67, 33);
    double timer;
    double highscore;
    double startTime;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    public void setup() { // creates objects in my classes, makes cave image, and sets up timer
        boulder = new Boulder(-200, height - 180, this);
        jones = new IndianaJones(width / 2, height - 100, 50, 75, this, this);
        spikes = new ArrayList<>();
        snakes = new ArrayList<>();
        boulder.display();
        cave = loadImage("caveExit-CS1.jpg");
        cave.resize(caveWidth, caveHeight);
        stage = 0;
        startTime = millis();
        setupHighScore();
    }

    public Boolean OverlappingCave() { // checks if jones and cave are overlapping
        int Jx = jones.getXPosition();
        int Jy = jones.getYPosition();
        int Jwidth = jones.getWidth();
        int Jheight = jones.getHeight();
        int topOfJ = Jy;
        int bottomOfJ = Jy + Jheight;
        int leftOfJ = Jx;
        int rightOfJ = Jx + Jwidth;
        int topOfC = caveY;
        int bottomOfC = caveY + caveHeight;
        int leftOfC = caveX;
        int rightOfC = caveX + caveWidth;
        if (bottomOfJ > topOfC && topOfJ < bottomOfC && rightOfJ > leftOfC && leftOfJ < rightOfC) {
            return true;
        } else {
            return false;
        }
    }

    public void settings() { // creates size of canvas
        size(width, height);
    }

    public void draw() {
        background(112, 101, 85);
        if (stage == 0) { // opening screen with instructions
            fill(0);
            textSize(80);
            textAlign(CENTER, CENTER);
            text("Welcome to my game!", width / 2, 100);
            textSize(40);
            text("Indiana Jones has just obtained the golden treasure", width / 2, 200);
            text("but now he has to make it back out the cave and past", width / 2, 250);
            text("all these traps.", width / 2, 300);
            textSize(30);
            text("Move Indiana Jones using the arrow keys and avoid the obstacles.", width / 2, 375);
            text("You die whenever you touch all obstacles except the spikes at the top.", width / 2, 450);
            text("When the spikes are orange they are safe but the minute they turn red", width / 2, 490);
            text("you die.", width / 2, 540);
            text("click the dark brown botton to play", width / 2, 630);
            fill(ButtonColor);
            circle(width / 2, 760, 115);
        }

        if (stage == 1) { // game screen, setting up timer, displaying/moving the objects in my classes and determine what happens when touching those objects
            fill(0);
            textSize(50);
            timer = millis() - startTime;
            timer = ((int) timer / 100) / 10.0;
            text("" + timer, width - 100, 50);
            image(cave, caveX, caveY);
            jones.display();
            boulder.display();
            boulder.move();

            if (jones.touchingBoulder(boulder)) {
                stage = 2;
            }

            snakeSetup();

            spikeSetup();

            if (OverlappingCave()) {
                stage = 3;
            }
        }

        if (stage == 2) { // fail screen
            fill(0);
            textSize(60);
            textAlign(CENTER, CENTER);
            text("You died :(", width / 2, 200);
            textSize(50);
            text("click button below to restart", width / 2, 325);
            fill(ButtonColor);
            circle(width / 2, 500, 115);
        }

        if (stage == 3) { // winning screen with highscore
            setupHighScore();
            fill(0);
            textSize(60);
            textAlign(CENTER, CENTER);
            text("You escaped!", width / 2, 200);
            textSize(40);
            text("Escape time: " + timer, width / 2, 300);
            text("Fastest escape: " + highscore, width / 2, 350);
            text("click button below to play again", width / 2, 425);
            fill(ButtonColor);
            circle(width / 2, 600, 115);
        }
    }

    public void snakeSetup() { // determining where snakes spawn if it is going certain direction and calling methods from snake class
        if (random(1) < .05) {
            int spawnPlace = 1;
            int direction = 1;
            if (random(1) < 0.5) {
                direction = -1;
            }
            if (random(1) < 0.5) {
                spawnPlace = 0;
            }
            int Snx = 0;
            int Sny = 0;
            if (direction == -1) {
                Snx = width;
                Sny = 450;
            }
            if (direction == 1) {
                Snx = -75;
                if (spawnPlace == 1) {
                    Sny = 530;
                } else if (spawnPlace == 0) {
                    Sny = 370;
                }
            }
            Snake s = new Snake(Snx, Sny, this, snakes, direction);
            snakes.add(s);
        }

        for (int i = 0; i < snakes.size(); i++) {
            Snake s = snakes.get(i);
            s.display();
            s.move();
            if (jones.touchingSnake(s)) {
                stage = 2;
            }
        }
    }

    public void spikeSetup() { // spawing spikes at random spots (within a range) and calling class methods
        if (random(1) < .1) {
            int Spx = (int) random(width);
            int Spy = (int) random(100, 325);
            Spike s = new Spike(Spx, Spy, this, spikes);
            spikes.add(s);
        }

        for (int i = 0; i < spikes.size(); i++) {
            Spike s = spikes.get(i);
            s.appear();
            if (jones.touchingSpike(s)) {
                stage = 2;
            }
        }
    }

    public void keyPressed() { // moves jones in main class by using code from IndianaJones class
        jones.move(keyCode);
    }

    public void mousePressed() { // says to start/restart game when press a button of certian color
        if (stage == 0 || stage == 2 || stage == 3) {
            if (get(mouseX, mouseY) == ButtonColor) {
                setup();
                stage = 1;
            }
        }
    }

    public void setupHighScore() { // figuring out when would need to set highscore to timer
        if (stage == 3) { // so that the highscore only changes when you actually win the game
            getHighScore();
            if (highscore == 0 || highscore > timer) {
                highscore = timer;
                savingHighScore();
            }
        }
    }

    public void getHighScore() { // getting the value of highscore from our file
        try (Scanner scanner = new Scanner(Paths.get("highscore.txt"))) {
            String row = scanner.nextLine();
            highscore = Double.valueOf(row);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void savingHighScore() { // putting the value for highscore into the highscore file
        try (PrintWriter writer = new PrintWriter("highscore.txt")) {
            writer.println(highscore);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }
}

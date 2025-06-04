import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Spike {
    private PApplet canvas;
    private int x;
    private int y;
    private int color;
    private int size;
    private Timer timerClass = new Timer();
    private ArrayList<Spike> spikes;

    public Spike(int Xcorr, int Ycorr, PApplet c, ArrayList<Spike> s) { // sets up all variables for spike and what it needs to access
        spikes = s;
        x = Xcorr;
        y = Ycorr;
        canvas = c;
        color = canvas.color(200, 130, 90);
        size = 30;

        TimerTask taskColor = new TimerTask() { // chat gpt showed me how to use these Timer classes and TimerTask classes to make it so after a certain amount of time the circle turns red and then after some more time disappers
            @Override
            public void run() {
                color = canvas.color(255, 0, 0);
            }
        };
        timerClass.schedule(taskColor, 2000);

        TimerTask taskDisappear = new TimerTask() {
            @Override
            public void run() {
                disappear();
            }
        };
        timerClass.schedule(taskDisappear, 4000);
    }

    public void appear() { // makes spike circle appear
        canvas.noStroke();
        canvas.fill(color);
        canvas.circle(x, y, size);
    }

    public void disappear() { // removes spikes from array list
        spikes.remove(this);
    }

    public Integer getXPosition() { // returns x corrdinate of spike for indiana jones class to use to check for overlapping
        return x;
    }

    public Integer getYPosition() { // returns y corrdinate of spike for indiana jones class to use to check for overlapping
        return y;
    }

    public Integer getSize() { // returns size of spike for indiana jones class to use to check for overlapping
        return size;
    }

    public Integer getColor() { // returns color of spike for indiana jones class to use to check for overlapping
        return color;
    }
}

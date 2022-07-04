package levels;

import objects.Object;

import java.util.ArrayList;

public interface Level {
    void init();
    void update();
    void draw();
    void collide();
}

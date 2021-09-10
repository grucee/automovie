package com.grucee.gobject;

import java.awt.*;

/**
 * 几何图形-点，默认半径为5
 */
public class AmPoint extends AmCircle{
    public AmPoint() {
        super(10);
        this.setFill(Color.YELLOW);
    }

    public AmPoint(int radius) {
        super(radius);
        this.setFill(Color.YELLOW);
    }
}

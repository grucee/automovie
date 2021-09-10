package com.grucee.gobject;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * 几何图形-圆
 */
public class AmCircle extends AmShape{
    public AmCircle(int radius) {
        super(new Ellipse2D.Double(-radius, -radius, 2*radius, 2*radius));
    }
}

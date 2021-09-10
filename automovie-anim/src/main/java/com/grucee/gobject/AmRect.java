package com.grucee.gobject;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/**
 * 几何形状-矩形
 */
public class AmRect extends AmShape{
    /**
     * 直角矩形
     * @param w
     * @param h
     */
    public AmRect(float w, float h) {
        super(new Rectangle2D.Double(-w/2.0, -h/2.0, w, h));
    }

    /**
     * 圆角矩形
     * @param w
     * @param h
     */
    public AmRect(float w, float h, float rx, float ry) {
        super(new RoundRectangle2D.Double(-w/2.0, -h/2.0, w, h, rx*2, ry*2));
    }
}

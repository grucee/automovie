package com.grucee.gobject;

import org.apache.batik.gvt.GraphicsNode;

/**
 * graphics object
 */
public interface Gobject {

    /**
     * A GraphicsNode encapsulates graphical attributes and
     * can perform atomic operations of a complex rendering.
     * @return
     */
    GraphicsNode getGraphicsNode();

    /**
     * translation transformation
     * @param tx
     * @param ty
     */
    void translate(double tx, double ty);

    /**
     * scaling transformation
     * @param sx
     * @param sy
     */
    void scale(double sx, double sy);

    /**
     * rotation transformation
     * @param dx
     */
    void rotate(double dx);
}

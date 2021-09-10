package com.grucee.gobject;

import org.apache.batik.gvt.GraphicsNode;

import java.awt.geom.AffineTransform;

/**
 * abstract VGobject
 */
public abstract class AbsGobject implements Gobject {

    @Override
    public void translate(double tx, double ty) {
        getTransform().translate(tx, ty);
    }

    @Override
    public void scale(double sx, double sy) {
        getTransform().scale(sx, sy);
    }

    @Override
    public void rotate(double dx) {
        getTransform().rotate(dx);
    }

    protected AffineTransform getTransform() {
        AffineTransform trans = getGraphicsNode().getTransform();
        if (trans == null) {
            trans = new AffineTransform();
            getGraphicsNode().setTransform(trans);
        }
        return trans;
    }
}

package com.grucee.gobject;

import org.apache.batik.gvt.FillShapePainter;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.gvt.ShapeNode;

import java.awt.*;

/**
 * 几何形状
 */
public class AmShape extends AbsGobject{
    /**
     * graphics node
     */
    private ShapeNode shapeNode = new ShapeNode();

    public AmShape(Shape shape) {
        //null check
        assert shape != null : "Shape cannt be null";

        //设置形状
        this.shapeNode.setShape(shape);

        //设置默认样式
        this.setDefaultStyle(shape);
    }

    private void setDefaultStyle(Shape shape) {
        FillShapePainter fp = new FillShapePainter(shape);
        fp.setPaint(Color.LIGHT_GRAY);
        this.shapeNode.setShapePainter(fp);
    }

    @Override
    public GraphicsNode getGraphicsNode() {
        return shapeNode;
    }
}

package com.grucee.gobject;

import org.apache.batik.gvt.*;

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

    /**
     * 设置填充
     * @param fillPaint
     */
    public void setFill(Paint fillPaint) {
        //step-1:创建FillShapePainter
        FillShapePainter newSP = new FillShapePainter(this.shapeNode.getShape());
        newSP.setPaint(fillPaint);

        //step-2:设置到ShapeNode上
        ShapePainter oldSP = this.shapeNode.getShapePainter();
        if (oldSP == null || oldSP instanceof FillShapePainter) {
            //没设置或只设置了填充，则直接覆盖
            this.shapeNode.setShapePainter(newSP);
        } else if (oldSP instanceof StrokeShapePainter) {
            //设置了边框，则将边框和填充组合起来
            CompositeShapePainter csp = new CompositeShapePainter(this.shapeNode.getShape());
            csp.addShapePainter(oldSP);
            csp.addShapePainter(newSP);
            this.shapeNode.setShapePainter(csp);
        } else if (oldSP instanceof CompositeShapePainter) {
            //直接添加到末尾
            CompositeShapePainter csp = (CompositeShapePainter)oldSP;
            csp.addShapePainter(newSP);
        } else {
            throw new RuntimeException("unknown ShapePainter:" + oldSP.getClass());
        }
    }


    /**
     * 设置边框
     * @param stokePaint
     */
    public void setStroke(float width, Paint stokePaint) {
        Stroke stroke = new BasicStroke(width);
        this.setStroke(stroke, stokePaint);
    }

    private void setStroke(Stroke stroke, Paint stokePaint) {
        //step-1:创建StrokeShapePainter
        StrokeShapePainter newSP = new StrokeShapePainter(this.shapeNode.getShape());
        newSP.setStroke(stroke);
        newSP.setPaint(stokePaint);

        //step-2:设置到ShapeNode上
        ShapePainter oldSP = this.shapeNode.getShapePainter();
        if (oldSP == null || oldSP instanceof StrokeShapePainter) {
            //没设置或只设置了填充，则直接覆盖
            this.shapeNode.setShapePainter(newSP);
        } else if (oldSP instanceof FillShapePainter) {
            //设置了边框，则将边框和填充组合起来
            CompositeShapePainter csp = new CompositeShapePainter(this.shapeNode.getShape());
            csp.addShapePainter(oldSP);
            csp.addShapePainter(newSP);
            this.shapeNode.setShapePainter(csp);
        } else if (oldSP instanceof CompositeShapePainter) {
            //直接添加到末尾
            CompositeShapePainter csp = (CompositeShapePainter)oldSP;
            csp.addShapePainter(newSP);
        } else {
            throw new RuntimeException("unknown ShapePainter:" + oldSP.getClass());
        }
    }
}

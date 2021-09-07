package com.grucee.gobject;

import com.grucee.coordinate.CoordinateSystem;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.gvt.GraphicsNode;

/**
 * 最外层场景：本质上是一个组合节点
 */
public class AmScene extends AbsGobject{
    /**
     * 场景容器
     */
    private CompositeGraphicsNode container = new CompositeGraphicsNode();

    /**
     * 场景宽度：最终渲染出来的视频宽度
     */
    private int width;

    /**
     * 场景高度：最终渲染出来的视频高度
     */
    private int height;

    /**
     * 场景对应的用户空间坐标系
     */
    private CoordinateSystem cs;

    public AmScene(int width, int height) {
        this.width = width;
        this.height = height;
        //初始化用户空间坐标系
        this.cs = new CoordinateSystem(width, height);
        Coordinater.setCoordinateSystem(this.cs);
    }

    /**
     * 添加对象到场景
     * @param gobject
     */
    public void add(Gobject gobject) {
        this.container.add(gobject.getGraphicsNode());
    }

    @Override
    public GraphicsNode getGraphicsNode() {
        return this.container;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * 全局坐标,方便在任何地方获取当前用户空间坐标系，放在此处标识坐标系和场景是关联的
     */
    public static class Coordinater {
        private static CoordinateSystem cs;
        private Coordinater(){}

        static void setCoordinateSystem(CoordinateSystem cs) {
            Coordinater.cs = cs;
        }

        public static CoordinateSystem getCoordinateSystem() {
            return Coordinater.cs;
        }
    }
}

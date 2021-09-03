package com.grucee.gobject;

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

    public AmScene(int width, int height) {
        this.width = width;
        this.height = height;
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
}

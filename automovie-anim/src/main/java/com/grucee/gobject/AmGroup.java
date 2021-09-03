package com.grucee.gobject;

import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.gvt.GraphicsNode;

/**
 * 组对象，可一起变换
 */
public class AmGroup extends AbsGobject{
    /**
     * 组容器
     */
    private CompositeGraphicsNode container = new CompositeGraphicsNode();

    @Override
    public GraphicsNode getGraphicsNode() {
        return null;
    }
}

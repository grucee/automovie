package com.grucee.gobject;

import com.grucee.coordinate.CoordinateSystem;
import com.grucee.enums.RenderPriority;
import com.grucee.output.AmMovieOutput;
import com.grucee.render.AmRender;
import org.apache.batik.gvt.CompositeGraphicsNode;
import org.apache.batik.gvt.GraphicsNode;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 背景，默认纯白背景
     */
    private Paint background = Color.WHITE;

    /**
     * 帧速率，默认25
     */
    private int fps = 25;

    /**
     * 场景渲染器
     */
    private AmRender render = new AmRender();

    /**
     * 视频输出器
     */
    private AmMovieOutput outputer;

    public AmScene(int width, int height) {
        this(width, height, RenderPriority.QUALITY_PREFER);
    }

    public AmScene(int width, int height, RenderPriority rp) {
        this.width = width;
        this.height = height;
        //设置渲染参数
        this.container.setRenderingHints(renderingHints(rp));
        //初始化用户空间坐标系
        this.cs = new CoordinateSystem(width, height);
    }

    /**
     * 启动视频记录
     * @param outputMoviePath
     * @throws IOException
     */
    public void startRecord(String outputMoviePath) throws IOException {
        this.outputer = new AmMovieOutput(this.width, this.height, outputMoviePath);
        this.outputer.start();
    }

    /**
     * 停止视频记录
     */
    public void stopRecord() throws IOException, InterruptedException {
        this.outputer.stop();
    }

    /**
     * 添加对象到场景
     * @param gobject
     */
    public void add(Gobject gobject) {
        GraphicsNode graphicsNode = gobject.getGraphicsNode();
        graphicsNode.setTransform(new AffineTransform(this.cs.getViewTransform()));
        this.container.add(graphicsNode);
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

    public Paint getBackground() {
        return background;
    }

    public void setBackground(Paint background) {
        this.background = background;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public RenderingHints getRenderingHints() {
        return this.container.getRenderingHints();
    }

    private Map<RenderingHints.Key, Object> renderingHints(RenderPriority rp) {
        Map<RenderingHints.Key, Object> hints = new HashMap<>();
        switch (rp) {
            case QUALITY_PREFER:
                //颜色渲染
                hints.put(RenderingHints.KEY_COLOR_RENDERING,
                        RenderingHints.VALUE_COLOR_RENDER_QUALITY);
                hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION,
                        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
                //形状渲染
                hints.put(RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY);
                hints.put(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                hints.put(RenderingHints.KEY_STROKE_CONTROL,
                        RenderingHints.VALUE_STROKE_PURE);
                //字体渲染
                hints.put(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                break;
            case SPEED_PREFER:
                //颜色渲染
                hints.put(RenderingHints.KEY_COLOR_RENDERING,
                        RenderingHints.VALUE_COLOR_RENDER_SPEED);
                hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION,
                        RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
                //形状渲染
                hints.put(RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_SPEED);
                hints.put(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_OFF);
                break;
            default:
                //形状渲染
                hints.put(RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_DEFAULT);
                hints.put(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_OFF);
                break;
        }
        return hints;
    }

}

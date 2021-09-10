package com.grucee.render;

import com.grucee.coordinate.CoordinateSystem;
import com.grucee.gobject.AmScene;
import org.apache.batik.gvt.renderer.ConcreteImageRendererFactory;
import org.apache.batik.gvt.renderer.ImageRenderer;
import org.apache.batik.gvt.renderer.ImageRendererFactory;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * 场景渲染器
 */
public class AmRender {
    private ImageRendererFactory rendererFactory = new ConcreteImageRendererFactory();

    /**
     * 渲染场景为栅格化图像
     * @param scene
     * @return
     */
    public BufferedImage render(AmScene scene) {
        int width = scene.getWidth();
        int height = scene.getHeight();

        /**
         * step-1: 初始化渲染器
         */
        ImageRenderer renderer = rendererFactory.createStaticImageRenderer();
        //待渲染的场景
        renderer.setTree(scene.getGraphicsNode());
        //从用户空间到设备空间的变换
        renderer.setTransform(new AffineTransform());
        //待研究
        renderer.setDoubleBuffered(true);
        //设置长宽高
        renderer.updateOffScreen(width, height);
        //清空之前的内容
        renderer.clearOffScreen();

        /**
         * step-2:开始渲染
         */
        renderer.repaint(new Rectangle(0, 0, width, height));

        /**
         * step-3:获取渲染后的栅格图像
         */
        BufferedImage sceneImage = renderer.getOffScreen();

        /**
         * step-4:释放资源
         */
        renderer.dispose();

        /**
         * step-5:设置背景颜色
         */
        BufferedImage outImage = new BufferedImage(width, height, sceneImage.getType());
        Graphics2D g2d = outImage.createGraphics();
        //设置渲染参数
        g2d.setRenderingHints(scene.getRenderingHints());
        g2d.setPaint(scene.getBackground());
        g2d.fillRect(0, 0, width, height);
        //拷贝图像
        g2d.drawImage(sceneImage, null, 0, 0);
        //释放资源
        g2d.dispose();

        return outImage;
    }
}

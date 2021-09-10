package com.grucee.coordinate;

import java.awt.geom.AffineTransform;

/**
 * 原点中心坐标系统(用户空间坐标系)
 * 1.基本思路是基于设备空间的宽度和高度，构造一个方便使用的用户空间坐标系；
 * 2.该用户空间坐标系原点对应设备空间的正中心
 * 3.该用户空间坐标系将设备空间的宽度等分为100份，即在该用户空间坐标系中，x轴取值范围永远是[-50,50]
 * 4.该用户空间坐标系将设备空间的高度按照设备空间宽高比设置为相应的份数(设为Y)，即在该用户空间坐标系中，y轴取值范围永远是[-y/2,y/2]
 */
public class CoordinateSystem {
    /**
     * 当前坐标系对应的设备空间坐标系宽度
     */
    private int devWidth;
    /**
     * 当前坐标系对应的设备空间坐标系高度
     */
    private int devHeight;

    /**
     * 当前坐标系到设备空间坐标系对应的转换矩阵
     */
    private AffineTransform viewTransform = new AffineTransform();

    public CoordinateSystem(int width, int height) {
        this.devWidth = width;
        this.devHeight = height;

        //确定本坐标系到设备空间坐标系的转换矩阵
        this.viewTransform.translate(width / 2.0, height / 2.0);
    }

    public AffineTransform getViewTransform() {
        return this.viewTransform;
    }
}

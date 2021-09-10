package com.grucee.main;

import com.grucee.gobject.AmPoint;
import com.grucee.gobject.AmRect;
import com.grucee.gobject.AmSVG;
import com.grucee.gobject.AmScene;
import com.grucee.output.AmMovieOutput;
import com.grucee.render.AmRender;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 */
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        //step-0:参数调整区域
        int sceneWidth = 1800;
        int sceneHeight = 1200;
        String inputSvgPath = "file:/F:/MG/svgs/colorful/01.svg";
        String outputMoviePath = "F:/MG/svgs/colorful/out.mp4";
        String outputImagePath = "F:/MG/svgs/colorful/out.png";

        //step-1:构造场景
        AmScene scene = new AmScene(sceneWidth, sceneHeight);

        //step-2:构造元素
        AmRect rect = new AmRect(200, 200);
        rect.setFill(Color.YELLOW);
        rect.setStroke(10.0f, Color.BLUE);

        AmPoint point = new AmPoint();
        point.setFill(Color.RED);

        AmSVG svg = new AmSVG(inputSvgPath);

        //step-3: 向场景中添加元素
        scene.add(rect);
        scene.add(point);
        scene.add(svg);

        //step-4:渲染场景
        AmRender render = new AmRender();

        //step-5:输出到文件
        AmMovieOutput output = new AmMovieOutput(sceneWidth, sceneHeight, outputMoviePath);
        output.start();
        for (int i = 0; i < 100; i++) {
            rect.translate(1, 1);
            svg.translate(2, 2);
            BufferedImage sceneImage = render.render(scene);
            if (i == 0) {
                output.writeFrame(sceneImage, outputImagePath);
            } else {
                output.writeFrame(sceneImage);
            }
        }
        output.stop();
    }
}

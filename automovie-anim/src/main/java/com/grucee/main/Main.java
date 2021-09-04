package com.grucee.main;

import com.grucee.gobject.AmRect;
import com.grucee.gobject.AmSVG;
import com.grucee.gobject.AmScene;
import com.grucee.output.AmMovieOutput;
import com.grucee.render.AmRender;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 */
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        //step-0:参数调整区域
        int sceneWidth = 900;
        int sceneHeight = 600;
        String inputSvgPath = "file:/F:/MG/svgs/colorful/02.svg";
        String outputMoviePath = "F:/MG/svgs/colorful/01.mp4";

        //step-1:构造场景
        AmScene scene = new AmScene(sceneWidth, sceneHeight);

        //step-2:构造元素
        AmRect rect = new AmRect(100, 100);
        rect.scale(1.1,1.1);
        rect.translate(5, 5);

        AmSVG svg = new AmSVG(inputSvgPath);
        svg.scale(0.5, 0.5);
        svg.translate(100, 100);

        //step-3: 向场景中添加元素
        scene.add(rect);
        scene.add(svg);

        //step-4:渲染场景
        AmRender render = new AmRender();

        //step-5:输出到文件
        AmMovieOutput output = new AmMovieOutput(sceneWidth, sceneHeight, outputMoviePath);
        output.start();
        for (int i = 0; i < 100; i++) {
            rect.translate(1, 1);
            //rect.rotate(Math.PI / 90);
            svg.translate(2, 2);
            //svg.rotate(Math.PI / 90);
            BufferedImage sceneImage = render.render(scene);
            output.writeFrame(sceneImage);
        }
        output.stop();
    }
}

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
        //step-1:构造场景
        int sceneWidth = 900;
        int sceneHeight = 600;
        AmScene scene = new AmScene(sceneWidth, sceneHeight);

        //step-2:构造元素
        AmRect rect = new AmRect(100, 100);
        rect.translate(5, 5);

        AmSVG svg = new AmSVG("file:/F:/MG/svgs/colorful/02.svg");
        svg.translate(100, 100);

        //step-3: 向场景中添加元素
        scene.add(rect);
        scene.add(svg);

        //step-4:渲染场景
        AmRender render = new AmRender();

        //step-5:输出到文件
        AmMovieOutput output = new AmMovieOutput(sceneWidth, sceneHeight,
                "F:/MG/svgs/colorful/01.mp4");
        output.start();
        for (int i = 0; i < 100; i++) {
            rect.translate(1, 1);

            BufferedImage sceneImage = render.render(scene);
            output.writeFrame(sceneImage);
        }
        output.stop();
    }
}

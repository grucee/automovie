package com.grucee.output;

import com.grucee.util.ConvertUtils;
import com.grucee.util.LogUtils;
import org.apache.commons.io.IOUtils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 输出渲染后的图像到视频
 */
public class AmMovieOutput {
    private int width;
    private int height;
    /**
     * 待输出文件路径
     */
    private String outputPath;

    /**
     * ffmpeg子进程
     */
    private Process process;

    public AmMovieOutput(int width, int height, String outputPath) {
        this.width = width;
        this.height = height;
        this.outputPath = outputPath;
    }

    /**
     * 启动开始生成视频
     */
    public void start() throws IOException {
        //step-1:启动ffmpeg
        String cmd = "ffmpeg -y -f rawvideo -s " + this.width + "x" + this.height +
                " -pix_fmt abgr -r 15 -i - -an -vcodec libx264 -pix_fmt yuv420p " + this.outputPath;
        LogUtils.log("开始执行命令:\n" + cmd);
        this.process = Runtime.getRuntime().exec(cmd);

        //step-2:实时输出stdin
        final InputStream in = this.process.getInputStream();
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> list = IOUtils.readLines(in);
                    LogUtils.log(list);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        });

        //step-3:实时输出stderr
        final InputStream err = this.process.getErrorStream();
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> list = IOUtils.readLines(err);
                    LogUtils.log(list);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        });
    }

    /**
     * 写入一帧
     * @param frame
     */
    public void writeFrame(BufferedImage frame) throws IOException {
        //step-1: 获取像素数据
        byte[] data;
        int type = frame.getType();
        DataBuffer dataBuffer = frame.getData().getDataBuffer();
        switch (type) {
            case BufferedImage.TYPE_4BYTE_ABGR:
            case BufferedImage.TYPE_4BYTE_ABGR_PRE:
                data = ((DataBufferByte)dataBuffer).getData();
                break;
            case BufferedImage.TYPE_INT_ARGB:
            case BufferedImage.TYPE_INT_ARGB_PRE:
                data = ConvertUtils.itoabgr(((DataBufferInt)dataBuffer).getData());
                break;
            default:
                throw new RuntimeException("unsupported BufferedImage type:" + type);
        }

        //step-2: 该帧数据写入到ffmpeg
        OutputStream outputStream = this.process.getOutputStream();
        BufferedOutputStream bo = new BufferedOutputStream(outputStream);
        bo.write(data);
        bo.flush();
    }

    public void stop() throws IOException, InterruptedException {
        //step-1: 关闭流以告知ffmpeg帧写入完毕
        OutputStream outputStream = this.process.getOutputStream();
        outputStream.flush();
        outputStream.close();

        //step-2: 等待ffmpeg子进程执行完毕
        int rtnValue = this.process.waitFor();
        LogUtils.log("视频生成完毕,rtnValue:" + rtnValue + ",path:" + this.outputPath);

        //step-3: 销毁Process对象
        this.process.destroy();
    }
}

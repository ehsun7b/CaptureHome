package com.ehsunbehravesh.capturehome;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ehsun Behravesh
 */
public class VideoCapture extends Observable implements Runnable {

    private final Webcam webcam;
    private final File file;
    private final long frames;
    private final Dimension size;
    private final int fps;
    private boolean recording;

    public VideoCapture(Webcam webcam, File file, long frames, Dimension size, int fps) {
        this.webcam = webcam;
        this.file = file;
        this.frames = frames;
        this.size = size;
        this.fps = fps;
    }

    @Override
    public void run() {
        recording = true;
        long startTime = System.currentTimeMillis();

        IMediaWriter writer = ToolFactory.makeWriter(file.getName());
        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, size.width, size.height);

        
        for (int i = 0; i < frames && recording; i++) {            
            System.out.println("Capture frame " + i);

            BufferedImage image = ConverterFactory.convertToType(webcam.getImage(), BufferedImage.TYPE_3BYTE_BGR);
            IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);

            IVideoPicture frame = converter.toPicture(image, (System.currentTimeMillis() - startTime) * 1000);
            frame.setKeyFrame(i == 0);
            frame.setQuality(0);

            writer.encodeVideo(0, frame);

            try {                
                Thread.sleep(1000 / fps);
            } catch (InterruptedException ex) {
                Logger.getLogger(VideoCapture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        setChanged();
        notifyObservers();
    }

    public void stop() {
        recording = false;
    }
    
    /*
    public static void main(String[] args) {
        Webcam webcam = Webcam.getDefault();
        //Dimension size = WebcamResolution.QVGA.getSize();
        Dimension size = new Dimension(640, 480);
        VideoCapture vc = new VideoCapture(webcam, new File("test.ts"), 300, size, 10);
                
        webcam.setViewSize(size);
        webcam.open();
        
        new Thread(vc).start();
    }*/
}

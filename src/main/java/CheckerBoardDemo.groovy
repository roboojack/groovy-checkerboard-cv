import groovy.transform.CompileStatic
import org.bytedeco.javacpp.indexer.FloatIndexer
import org.bytedeco.javacpp.indexer.LongIndexer
import org.bytedeco.javacv.CanvasFrame
import org.bytedeco.javacv.Frame
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_calib3d.*;


@CompileStatic
class CheckerBoardDemo {

    static void main(String[] args) {

        // Read an image.
        FrameGrabber grabber = FrameGrabber.createDefault(0);
        grabber.start();
        OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();

        Size patternSize2 = new Size(9, 6)
        Mat corners1 = new Mat()

        boolean found1 = false;

        println("${found1} ${corners1}")

        Mat grabbedImage

        CanvasFrame frame = new CanvasFrame("Place Checkerboard here!", CanvasFrame.getDefaultGamma() / grabber.getGamma() as double);

        while (frame.isVisible()) {
            grabbedImage = converter.convert(grabber.grab());

            found1 = findChessboardCorners(grabbedImage, patternSize2, corners1);

            println("Found the checkerboard? ${found1}")

            if (found1) {
                FloatIndexer indexer = corners1.createIndexer() as FloatIndexer // not very friendly syntax unfortunately
                for (int i = 0; i < corners1.size().height(); i++) {
                    try {
                        int x = (int) Math.floor(indexer.get(1, i, 0))
                        int y = (int) Math.floor(indexer.get(1, i, 1))

                        Point pt = new Point(x, y)
                        circle(grabbedImage, pt, 5, new Scalar(Math.random() * 255, Math.random() * 255), 2, 0, 0);
                    } catch (Exception e) {

                    }
                }
            }

            Frame rotatedFrame = converter.convert(grabbedImage);
            frame.showImage(rotatedFrame);
        }
        frame.dispose();
        grabber.stop();
    }

}
package controller;

import ij.plugin.DICOM;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 * Hello world!
 *
 */
public class App 
{

    private static final String processingFolder
            = "images/";
    private static final String sourceFolder
            = "F:\\Projects\\MedicalImageFusion\\Dicom files\\";
    public static void main( String[] args )
    {
        //opening DICOM file
        DICOM dcm = new DICOM();
        dcm.open(sourceFolder + "dicom.dcm");

        //extract image data from DICOM file
        BufferedImage bf = dcm.getBufferedImage();
        PrintWriter writer = null;
        String currentImageFolder
                = String.valueOf(System.currentTimeMillis()) + "/";
        File outputfile = new File(processingFolder + currentImageFolder + "image.jpg");
        outputfile.mkdirs();
        try {
            ImageIO.write(bf, "jpg", outputfile);
            writer = new PrintWriter(processingFolder + currentImageFolder + "imageInfo.txt", "UTF-8");
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Attempting to print dicom properties");
        writer.println(dcm.getProperties());
        writer.close();
        
        // opencv test
        // first Run -> Set project configuration -> Customize -> Run ->
        // VM Options -> -Djava.library.path="path-to-opencv-dll"
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
        System.out.println("mat = " + mat.dump());
    }
}

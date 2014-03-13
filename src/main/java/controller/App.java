/**
 *
 */
package controller;

import ij.plugin.DICOM;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Arthur Henning
 */
public class App 
{

    private static final String processingFolder
            = "images/";
    private static final String sourceFolder
            = "F:\\Projects\\MedicalImageFusion\\Dicom files\\";

    /**
     *
     * @param args
     */
    public static void main( String[] args )
    {
        //opening DICOM file
        DICOM dcm = new DICOM();
        dcm.open("F:\\Dicom\\1.dcm");

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
    }
}

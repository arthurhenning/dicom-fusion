/*
 * Copyright 2014 Arthur Henning.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package model;

import ij.plugin.DICOM;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Arthur Henning
 */
public class DicomIO {

    private DICOM dicom;
    private String imagePath;
    private String outputFolder;

    public DicomIO(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    public boolean open(String path) {
        dicom.open(path);
        return dicom.getWidth() != 0;
    }

    public BufferedImage getImage() {
        return dicom.getBufferedImage();
    }

    public boolean saveImage() {
        imagePath = outputFolder + dicom.getShortTitle() + ".jpg";
        File outputfile = new File(imagePath);
        outputfile.mkdirs();
        try {
            ImageIO.write(dicom.getBufferedImage(), "jpg", outputfile);
        } catch (IOException ex) {
            Logger.getLogger(DicomIO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean saveProperties() {
        try {
            PrintWriter writer = new PrintWriter(outputFolder + dicom.getShortTitle() + ".txt", "UTF-8");
            writer.println(dicom.getProperties());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DicomIO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DicomIO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public DICOM getDicom() {
        return dicom;
    }

    public void setDicom(DICOM dicom) {
        this.dicom = dicom;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }
}

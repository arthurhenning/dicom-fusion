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
package controller;

import exception.DicomFusionException;
import ij.ImagePlus;
import image_processing.FusionFacade;
import io.DicomIO;
import io.ExcelResultsWriter;
import io.ImageResultsWriter;
import io.ImageSaver;
import io.ResultsWriterFacade;
import io.TextResultsWriter;
import quality_metrics.QualityMetricsFacade;
import view.MessageDialog;

/**
 * The Main controller of the application.
 *
 * @author Arthur Henning
 */
public class MainController {

    private static DicomIO dicomIO;
    private static ImagePlus image1;
    private static ImagePlus image2;
    private static ImagePlus resultImage;
    private static FusionFacade fusionFacade;
    private static QualityMetricsFacade qualityMetricsFacade;
    private static ResultsWriterFacade resultsWriterFacade;

    public static void init() {
        image1 = null;
        image2 = null;
        resultImage = null;
    }

    public static boolean loadImage(String path, int imageNr) {

        // lazy loading
        if (dicomIO == null) {
            dicomIO = new DicomIO();
        }

        boolean ok = false;

        if (dicomIO.open(path)) {
            if (imageNr == 1) {
                if (image1 != null) {
                    image1.close();
                }
                image1 = dicomIO.getImage();
                image1.show();
                ok = true;
            } else if (imageNr == 2) {
                if (image2 != null) {
                    image2.close();
                }
                image2 = dicomIO.getImage();
                image2.show();
                ok = true;
            }
        }

        return ok;
    }

    public static void fuse(int algorithmId, int postProcId, int level, double sigma) {

        // lazy loading
        if (fusionFacade == null) {
            fusionFacade = new FusionFacade();
        }

        // start timer
        long startMillis = System.currentTimeMillis();

        image1.show();
        image2.show();
        resultImage = fusionFacade.fuse(image1, image2, algorithmId, postProcId, level, sigma);

        // stop timer
        long endMillis = System.currentTimeMillis();

        // calculate duration
        long duration = endMillis - startMillis;

        System.out.println("duration: " + duration);
        resultImage.setTitle(resultImage.getTitle() + "_" + duration + "ms");

        resultImage.show();
    }

    public static void runQualityMetrics(boolean textResults, boolean excelResults, boolean imageResults) {

        if (!textResults && !excelResults && !imageResults) {
            MessageDialog.showMessage("You must choose at least one results type!", "Warning");
            return;
        }

        // lazy loading
        if (qualityMetricsFacade == null) {
            qualityMetricsFacade = new QualityMetricsFacade();

            try {
                // add default values
                qualityMetricsFacade.addDefaultValues();
            } catch (DicomFusionException ex) {
                MessageDialog.showMessage("Error loading images: " + ex.getMessage(), "Error");
            }
        }

        // start timer
        long startMillis = System.currentTimeMillis();

        // calculate the actual quality metrics values
        qualityMetricsFacade.calculateQualityMetrics();

        // stop timer
        long endMillis = System.currentTimeMillis();

        // calculate duration
        long duration = endMillis - startMillis;

        System.out.println("duration: " + duration);

        // lazy loading
        if (resultsWriterFacade == null) {
            resultsWriterFacade = new ResultsWriterFacade();
        }

        if (textResults) {
            resultsWriterFacade.addResultsWriter(new TextResultsWriter());
        }
        if (excelResults) {
            resultsWriterFacade.addResultsWriter(new ExcelResultsWriter());
        }
        if (imageResults) {
            resultsWriterFacade.addResultsWriter(new ImageResultsWriter());
        }

        try {

            // write results
            resultsWriterFacade.writeResults(qualityMetricsFacade.getResults());
        } catch (DicomFusionException ex) {
            MessageDialog.showMessage(ex.getMessage(), "error");
        } finally {
            resultsWriterFacade.clearResultsWriters();
        }
        long endOperation = System.currentTimeMillis();
        MessageDialog.showMessage("Quality metrics calculated in " + duration + "ms."
                + " \nOperation finished in " + (endOperation - startMillis) + " ms.", "Info");
    }

    public static void saveLastImage() {

        String path = "fused_images";
        try {
            ImageSaver.save(resultImage, path);
        } catch (DicomFusionException ex) {
            MessageDialog.showMessage(ex.getMessage(), "Error");
        }
        MessageDialog.showMessage("Image successfully saved in \"" + path
                + "\"\nas \"" + resultImage.getTitle() + ".jpg\".", "Info");
    }
}

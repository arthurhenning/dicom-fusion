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

import ij.ImagePlus;
import image_processing.FusionFacade;
import model.DicomIO;

/**
 *
 * @author Arthur Henning
 */
public class MainController {

    private static DicomIO dicomIO;
    private static ImagePlus image1;
    private static ImagePlus image2;
    private static ImagePlus resultImage;
    private static FusionFacade fusionFacade;

    public static void init() {
        dicomIO = new DicomIO();
        image1 = null;
        image2 = null;
        resultImage = null;
        fusionFacade = new FusionFacade();
    }

    public static boolean loadImage(String path, int imageNr) {

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

        resultImage = fusionFacade.fuse(image1, image2, algorithmId, postProcId, level, sigma);

        resultImage.show();
    }
}

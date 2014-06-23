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

package io;

import exception.DicomFusionException;
import ij.ImagePlus;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Arthur Henning
 */
public class ImageSaver {

    public static void save(ImagePlus image, String path) throws DicomFusionException {

        String imagePath = path + "//" + image.getTitle() + ".jpg";
        File outputfile = new File(imagePath);
        outputfile.mkdirs();
        try {
            ImageIO.write(image.getBufferedImage(), "jpg", outputfile);
        } catch (IOException ex) {
            throw new DicomFusionException(ex.getMessage());
        }
    }
}

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
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Arthur Henning
 */
public class ImageLoader {

    public ImagePlus load(String filename) throws DicomFusionException {
        ClassLoader cl = getClass().getClassLoader();
        URL url = cl.getResource(filename);

        Image image = null;
        try {
            image = ImageIO.read(url);
        } catch (IOException ex) {
            throw new DicomFusionException(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new DicomFusionException(ex.getMessage());
        }
        return new ImagePlus(filename, image);

    }
}

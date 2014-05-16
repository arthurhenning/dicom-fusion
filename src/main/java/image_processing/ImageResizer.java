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

package image_processing;

import ij.ImagePlus;
import ij.process.ImageProcessor;

/**
 *
 * @author Arthur Henning
 */
public class ImageResizer {

    /**
     * If dimensions of the input images are not equal, resizes them both to the
     * average of their dimensions.
     *
     * @param image1
     * @param image2
     */
    public void resize(ImagePlus image1, ImagePlus image2) {

        if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
            int newWidth = (image1.getWidth() + image2.getWidth()) / 2;
            int newHeight = (image2.getHeight() + image2.getHeight()) / 2;

            ImageProcessor processor1 = image1.getProcessor();
            ImageProcessor processor2 = image2.getProcessor();

            processor1.resize(newWidth, newHeight);
            processor2.resize(newWidth, newHeight);
        }
    }
}

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
package fusion_method;

import ij.ImagePlus;
import ij.process.ImageProcessor;

/**
 *
 * @author Arthur Henning
 */
public class SimpleMinimumFusion implements FusionMethod {

    public ImagePlus fuse(ImagePlus image1, ImagePlus image2) {
        ImagePlus image1Duplicate = image1.duplicate();
        ImageProcessor processor1 = image1Duplicate.getProcessor();

        ImageProcessor processor2 = image2.getProcessor();

        float[][] pixels1 = processor1.getFloatArray();
        float[][] pixels2 = processor2.getFloatArray();
        float[][] result = new float[image1.getWidth()][image1.getHeight()];

        for (int i = 0; i < image1.getHeight(); i++) {
            for (int j = 0; j < image1.getWidth(); j++) {
                result[j][i] = pixels1[j][i] < pixels2[j][i]
                        ? pixels1[j][i] : pixels2[j][i];
            }
        }

        processor1.setFloatArray(result);

        image1Duplicate.setTitle("Minimum: " + image1.getShortTitle() + " + " + image2.getShortTitle());

        return image1Duplicate;
    }
}

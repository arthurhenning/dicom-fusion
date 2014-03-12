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

import ij.ImagePlus;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

/**
 *
 * @author Arthur Henning
 */
public class ImageProcessing {

    public ImagePlus wavelet1D(ImagePlus in) {
        ImageProcessor p = in.getProcessor();
        FloatProcessor processor = p.convertToFloatProcessor();
        System.out.println(in.toString());
        for (int i = 0; i < in.getHeight(); i++) {
            for (int j = 0; j < in.getWidth(); j++) {
                System.out.print(processor.getf(i, j) + " ");
                processor.setf(i, j, 10.221f);
            }
            System.out.print("\n");
        }

        for (int i = 0; i < in.getHeight(); i++) {
            for (int j = 0; j < in.getWidth(); j++) {
                System.out.print(processor.getf(i, j) + " ");
            }
            System.out.print("\n");
        }
        return null;
    }
}

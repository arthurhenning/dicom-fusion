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
import ij.WindowManager;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import java.awt.Color;

/**
 *
 * @author Arthur Henning
 */
public class DicomProcessor {

    /**
     * http://unix4lyfe.org/haar/
     *
     * @param in
     * @param row
     * @param width
     */
    public void haar1D(ImagePlus in, int row, int width) {
        ImageProcessor processor = in.getProcessor();

        int halfW = width / 2;

        for (int i = 0; i < width; i += 2) {
            int a = processor.get(i, row);
            int b = processor.get(i + 1, row);

            int average = (int) ((a + b) / Math.sqrt(2.0));
            int coeff = (int) ((a - b) / Math.sqrt(2.0));

            processor.set(i / 2, row, average);
            processor.set(i / 2 + halfW, row, coeff);
        }
        if (width > 2) {
            haar1D(in, row, width / 2);
        }
    }

    /**
     *
     * @param in
     */
    public void haar2D(ImagePlus in, int level) {
        for (int l = 0; l < level; l++) {
            for (int i = 0; i < in.getHeight(); i++) {
                haar1D(in, i, in.getWidth());
                in.show();
            }
        }
        in.show();
    }

    public void inverseHaar1D(ImagePlus in, int row, int width) {
        ImageProcessor processor = in.getProcessor();

        int halfW = width / 2;

        for (int i = 0; i < width; i += 2) {
            int a = processor.get(i / 2, row);
            int b = processor.get(i / 2 + halfW, row);

            processor.set(i / 2, row, a + b);
            processor.set(i / 2 + halfW, row, a - b);
        }
        if (width > 2) {
            haar1D(in, row, width / 2);
        }
    }

    public void inverseHaar2D(ImagePlus in, int level) {
        for (int l = 0; l < level; l++) {
            for (int i = 0; i < in.getHeight(); i++) {
                inverseHaar1D(in, i, in.getWidth());
                in.show();
            }
        }
        in.show();
    }

    public ImagePlus wavelet1D(ImagePlus in) {

        return null;
    }

    public ImagePlus wavelet1DProcessor(ImagePlus in) {
        ImagePlus buffer = new ImagePlus("2", in.getImage());
        ImageProcessor processor = buffer.getProcessor();

//        processor.filter(ImageProcessor.FIND_EDGES);
//        processor.erode();
        in.show("initial");
        processor.drawString("Test", 100, 50, Color.RED);
        buffer.show();

        return null;
    }

    public ImagePlus test(ImagePlus in) {
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

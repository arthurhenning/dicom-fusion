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
import ij.plugin.ImageCalculator;
import ij.process.ImageProcessor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Arthur Henning
 */
public class LaplacianPyramid {

    private List<ImagePlus> laplacianPyramid;
    private List<ImagePlus> gaussianPyramid;

    public LaplacianPyramid() {
        laplacianPyramid = new ArrayList<ImagePlus>();
        gaussianPyramid = new ArrayList<ImagePlus>();
    }

    public void calcGaussianPyramid(ImagePlus in, int level) {
        gaussianPyramid.add(new ImagePlus("Gaussian 0", in.getImage()));
        //in.show();
        for (int i = 1; i < level; i++) {
            ImagePlus previous = gaussianPyramid.get(i - 1).duplicate();
            ImageProcessor iP = previous.getProcessor();
            int width = previous.getWidth();

            iP.blurGaussian(2);
            ImageProcessor iPResized = iP.resize(width / 2);
            ImagePlus next = new ImagePlus("Gaussian " + i, iPResized.getBufferedImage());
            //next.show();
            gaussianPyramid.add(next);
        }
    }

    public void calcLaplacianPyramid(ImagePlus in, int level) {

        // calculate Gaussian pyramid
        calcGaussianPyramid(in, level);

        // add the same last image
        ImagePlus last = gaussianPyramid.get(level - 1).duplicate();
        last.setTitle("Laplacian " + (level - 1));
        laplacianPyramid.add(last);

        for (int i = level - 1; i > 0; i--) {
            ImagePlus gaussian = gaussianPyramid.get(i).duplicate();
            ImageProcessor processor = gaussian.getProcessor();

            // size *= 2
            ImageProcessor processorResized = processor.resize(gaussian.getWidth() * 2);
            gaussian = new ImagePlus("GaussianResized " + i, processorResized);

            // low pass filter
            processorResized.blurGaussian(2);

            //difference between current image and previous
            ImagePlus current = gaussianPyramid.get(i - 1);
            ImageCalculator calculator = new ImageCalculator();
            ImagePlus difference = calculator.run("Subtract create", current, gaussian);
            difference.setTitle("Laplacian " + (i - 1));

            // store laplacian image
            laplacianPyramid.add(difference);
        }

        // reverse list for correct order
        Collections.reverse(laplacianPyramid);
    }
}

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
        in.show();
        for (int i = 1; i < level; i++) {
            ImagePlus previous = gaussianPyramid.get(i - 1).duplicate();
            ImageProcessor iP = previous.getProcessor();
            int width = previous.getWidth();

            iP.blurGaussian(2);
            ImageProcessor iPResized = iP.resize(width / 2);
            ImagePlus next = new ImagePlus("Gaussian " + i, iPResized.getBufferedImage());
            next.show();
            gaussianPyramid.add(next);
        }
    }

    public void calcLaplacianPyr(ImagePlus in, int level) {
        calcGaussianPyramid(in, level);
        laplacianPyramid.add(level - 1, gaussianPyramid.get(level - 1).duplicate());
        for (int i = level - 1; i > 0; i--) {
            ImagePlus current = gaussianPyramid.get(i - 1).duplicate();
            ImageProcessor iP = current.getProcessor();
            ImageCalculator calculator = new ImageCalculator();
            ImagePlus difference = calculator.run("Difference", current, gaussianPyramid.get(i));
            // to do
        }
    }
}

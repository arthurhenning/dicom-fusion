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

import algorithm.LaplacianPyramid;
import ij.ImagePlus;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arthur Henning
 */
public class LaplacianPyrFusion implements FusionMethod {

    private LaplacianPyramid pyramidCalculator;
    private final int level;
    private final double sigma;
    private final FusionMethod simpleFusion;

    public LaplacianPyrFusion() {
        this.level = 3;
        this.sigma = 3.0;
        this.simpleFusion = new SimpleMaximumFusion();
    }

    public LaplacianPyrFusion(int level, double sigma, FusionMethod simpleFusion) {
        this.level = level;
        this.sigma = sigma;
        this.simpleFusion = simpleFusion;
    }

    public ImagePlus fuse(ImagePlus image1, ImagePlus image2) {

        pyramidCalculator = new LaplacianPyramid(level, sigma);
        pyramidCalculator.calcLaplacianPyramid(image1);
        List<ImagePlus> pyramid1 = pyramidCalculator.getLaplacianPyramid();

        pyramidCalculator = new LaplacianPyramid(level, sigma);
        pyramidCalculator.calcLaplacianPyramid(image2);
        List<ImagePlus> pyramid2 = pyramidCalculator.getLaplacianPyramid();

        List<ImagePlus> result = new ArrayList<ImagePlus>(pyramid1.size());

        for (int i = 0; i < pyramid1.size(); i++) {
            result.add(simpleFusion.fuse(pyramid1.get(i), pyramid2.get(i)));
        }

        pyramidCalculator.setLaplacianPyramid(result);

        ImagePlus resultImage = pyramidCalculator.reconstrLaplacianPyramid();

        resultImage.setTitle("Laplacian_" + level + "_" + sigma + ": " + image1.getShortTitle() + " + " + image2.getShortTitle());

        return resultImage;
    }

}

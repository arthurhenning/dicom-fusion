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

    private LaplacianPyramid pyramidCalc1;
    private LaplacianPyramid pyramidCalc2;

    public LaplacianPyrFusion(int level) {
        pyramidCalc1 = new LaplacianPyramid(level);
        pyramidCalc2 = new LaplacianPyramid(level);
    }

    public ImagePlus fuse(ImagePlus image1, ImagePlus image2) {
        pyramidCalc1.calcLaplacianPyramid(image1);
        pyramidCalc2.calcLaplacianPyramid(image2);

        List<ImagePlus> pyramid1 = pyramidCalc1.getLaplacianPyramid();
        List<ImagePlus> pyramid2 = pyramidCalc2.getLaplacianPyramid();
        List<ImagePlus> result = new ArrayList<ImagePlus>(pyramid1.size());

        SimpleMaximumFusion maximumFusion = new SimpleMaximumFusion();

        for (int i = 0; i < pyramid1.size(); i++) {
            result.add(maximumFusion.fuse(pyramid1.get(i), pyramid2.get(i)));
        }

        pyramidCalc1.setLaplacianPyramid(result);

        return pyramidCalc1.reconstrLaplacianPyramid();
    }

}

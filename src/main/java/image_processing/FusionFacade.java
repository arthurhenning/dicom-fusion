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

import fusion_method.FusionMethod;
import fusion_method.HaarWaveletFusion;
import fusion_method.LaplacianPyrFusion;
import fusion_method.SimpleAverageFusion;
import fusion_method.SimpleMaximumFusion;
import fusion_method.SimpleMinimumFusion;
import ij.ImagePlus;

/**
 *
 * @author Arthur Henning
 */
public class FusionFacade {

    public ImagePlus fuse(ImagePlus image1, ImagePlus image2, int algorithmId, int postProcId, int level, double sigma) {

        StackConverter stackConverter = new StackConverter();
        ImageResizer resizer = new ImageResizer();
        FusionMethod fusionMethod = null;

        // choose selected algorithm by its id
        switch (algorithmId) {
            case 0:
                fusionMethod = new SimpleMinimumFusion();
                break;
            case 1:
                fusionMethod = new SimpleAverageFusion();
                break;
            case 2:
                fusionMethod = new SimpleMaximumFusion();
                break;
            case 3:
                fusionMethod = new LaplacianPyrFusion(level, sigma, new SimpleMaximumFusion());
                break;
            case 4:
                fusionMethod = new HaarWaveletFusion(level, new SimpleMaximumFusion());
                break;
            default:
                fusionMethod = new HaarWaveletFusion(level, new SimpleMaximumFusion());
        }

        // stack z projection to single image
        image1 = stackConverter.convertToProjection(image1);
        image2 = stackConverter.convertToProjection(image2);

        // check dimensions
        // warning message if dimensions differ? throw exception?
        resizer.resize(image1, image2);

        // actual fusion
        ImagePlus resultImage = fusionMethod.fuse(image1, image2);

        ImagePlus resultPostProc = resultImage;

        // postprocessing by id
        if (postProcId < 5) {
            PostProcessor processor = new PostProcessor();
            resultPostProc = processor.process(resultImage, postProcId);
        }

        return resultPostProc;
    }
}

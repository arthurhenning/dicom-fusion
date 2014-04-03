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

import algorithm.HaarDWT;
import ij.ImagePlus;
import ij.process.ImageProcessor;

/**
 *
 * @author Arthur Henning
 */
public class HaarWaveletFusion implements FusionMethod {

    private final HaarDWT haarDwt;

    public HaarWaveletFusion(int level) {
        haarDwt = new HaarDWT(level);
    }

    public ImagePlus fuse(ImagePlus image1, ImagePlus image2) {
        ImagePlus haarImage1 = image1.duplicate();
        haarDwt.haar2D(haarImage1);

        ImagePlus haarImage2 = image2.duplicate();
        haarDwt.haar2D(haarImage2);

        SimpleMaximumFusion maximumFusion = new SimpleMaximumFusion();
        ImagePlus result = maximumFusion.fuse(haarImage1, haarImage2);

        haarDwt.inverseHaar2D(result);

        return result;
    }

}

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

/**
 *
 * @author Arthur Henning
 */
public class HaarWaveletFusion implements FusionMethod {

    private final HaarDWT haarDwt;
    private final FusionMethod simpleFusion;

    public HaarWaveletFusion(int level, FusionMethod simpleFusion) {
        haarDwt = new HaarDWT(level);
        this.simpleFusion = simpleFusion;
    }

    public ImagePlus fuse(ImagePlus image1, ImagePlus image2) {
        ImagePlus haarImage1 = image1.duplicate();
        haarDwt.haar2D(haarImage1);
        // debugging
        //haarImage1.show();

        ImagePlus haarImage2 = image2.duplicate();
        haarDwt.haar2D(haarImage2);
        // debugging
        ///haarImage2.show();

        // Fusion method used to fuse the two haar images
        ImagePlus result = simpleFusion.fuse(haarImage1, haarImage2);
        // debugging
        //result.show();

        haarDwt.inverseHaar2D(result);
        // debugging
        //result.show();

        result.setTitle("Haar_" + HaarDWT.getLevel() + ": " + image1.getShortTitle() + " + " + image2.getShortTitle());

        return result;
    }

}

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

package quality_metrics;

import ij.ImagePlus;
import ij.process.ImageProcessor;

/**
 *
 * @author Arthur Henning
 */
public class PeakSNR {

    private static double calculatePeak(ImagePlus in) {
        ImageProcessor proc = in.getProcessor();
        return proc.maxValue();
    }

    public static double calculate(ImagePlus perfectImage, ImagePlus assessedImage) {
        double mse = MeanSquaredError.calculate(perfectImage, assessedImage);
        double peak = calculatePeak(assessedImage);
        double psnr = 10 * Math.log10(Math.pow(peak, 2) / mse);
        return psnr;
    }

    public static double calculate(ImagePlus perfectImage, ImagePlus assessedImage, double mse) {
        double peak = calculatePeak(assessedImage);
        double psnr = 10 * Math.log10(Math.pow(peak, 2) / mse);
        return psnr;
    }
}

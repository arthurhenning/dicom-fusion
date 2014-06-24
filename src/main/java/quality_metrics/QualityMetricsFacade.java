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

import exception.DicomFusionException;
import fusion_method.FusionMethod;
import fusion_method.HaarWaveletFusion;
import fusion_method.LaplacianPyrFusion;
import fusion_method.SimpleAverageFusion;
import fusion_method.SimpleMaximumFusion;
import fusion_method.SimpleMinimumFusion;
import ij.ImagePlus;
import image_processing.PostProcessor;
import io.ImageLoader;
import java.util.ArrayList;

/**
 *
 * @author Arthur Henning
 */
public class QualityMetricsFacade {

    private ArrayList<FusionMethod> fusionMethods;
    private PostProcessor postProcessor;
    private ArrayList<QualityMetricsInput> inputImages;
    private ArrayList<ArrayList<QualityMetricsOutput>> results;

    public QualityMetricsFacade() {
        fusionMethods = new ArrayList<FusionMethod>();
        inputImages = new ArrayList<QualityMetricsInput>();
        results = new ArrayList<ArrayList<QualityMetricsOutput>>();
    }

    public void addDefaultValues() throws DicomFusionException {

        ImageLoader imageLoader = new ImageLoader();

        ImagePlus perfectImage = imageLoader.load("mri.jpg");
        ImagePlus image1 = imageLoader.load("mri_soft2.jpg");
        ImagePlus image2 = imageLoader.load("mri_hard2.jpg");
        this.addInputImages(new QualityMetricsInput(perfectImage, image1, image2));

        ImagePlus image1a = imageLoader.load("mri_soft.jpg");
        ImagePlus image2a = imageLoader.load("mri_hard.jpg");

        this.addInputImages(new QualityMetricsInput(perfectImage, image1a, image2a));

        ImagePlus image1b = imageLoader.load("mri_left_blurred.jpg");
        ImagePlus image2b = imageLoader.load("mri_right_blurred.jpg");

        this.addInputImages(new QualityMetricsInput(perfectImage, image1b, image2b));

        this.addFusionMethod(new SimpleAverageFusion());
        this.addFusionMethod(new SimpleMinimumFusion());
        this.addFusionMethod(new SimpleMaximumFusion());
        this.addFusionMethod(new HaarWaveletFusion(2, new SimpleMaximumFusion()));
        this.addFusionMethod(new LaplacianPyrFusion());
    }

    public void addInputImages(QualityMetricsInput input) {
        inputImages.add(input);
    }

    public void addFusionMethod(FusionMethod method) {
        fusionMethods.add(method);
    }

    public void calculateQualityMetrics() {

        int index = 0;
        for (QualityMetricsInput input : inputImages) {
            results.add(new ArrayList<QualityMetricsOutput>());
            for (FusionMethod method : fusionMethods) {
                ImagePlus resultImage = method.fuse(input.getImage1(), input.getImage2());

                postProcessor = new PostProcessor();

                // Postprocessors
                for (int postProcId = 0; postProcId <= 5; postProcId++) {
                    ImagePlus resultImagePostProc = postProcessor.process(resultImage, postProcId);

                    double mse = MeanSquaredError.calculate(input.getPerfectImage(), resultImagePostProc);
                    double psnr = PeakSNR.calculate(input.getPerfectImage(), resultImagePostProc, mse);

                    // add to arrayList of outputs
                    QualityMetricsOutput output = new QualityMetricsOutput(resultImagePostProc, mse, psnr);
                    results.get(index).add(output);
                }
            }
            index++;
        }
    }

    public ArrayList<ArrayList<QualityMetricsOutput>> getResults() {
        return results;
    }

}

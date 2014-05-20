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

import fusion_method.FusionMethod;
import ij.ImagePlus;
import image_processing.PostProcessor;
import java.util.ArrayList;

/**
 *
 * @author Arthur Henning
 */
public class QualityMetricsFacade {

    private ArrayList<FusionMethod> fusionMethods;
    private PostProcessor postProcessor;
    private ArrayList<QualityMetricsInput> inputImages;
    // TODO: arrayList<arrayList<>>
    private ArrayList<ArrayList<QualityMetricsOutput>> results;

    public QualityMetricsFacade() {
        fusionMethods = new ArrayList<FusionMethod>();
        inputImages = new ArrayList<QualityMetricsInput>();
        results = new ArrayList<ArrayList<QualityMetricsOutput>>();
    }

    public void addInputImages(QualityMetricsInput input) {
        inputImages.add(input);
    }

    public void addFusionMethod(FusionMethod method) {
        fusionMethods.add(method);
    }

    public void calculateQualityMetric() {

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
            // TODO: save this set of results or create an average of all the sets
        }
    }

    public ArrayList<ArrayList<QualityMetricsOutput>> getResults() {
        return results;
    }

}

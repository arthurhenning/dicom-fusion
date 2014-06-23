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

import ij.ImagePlus;
import ij.process.ImageProcessor;

/**
 *
 * @author Arthur Henning
 */
public class PostProcessor {

    public final static int DILATE = 0;
    public final static int ERODE = 1;
    public final static int SMOOTH = 2;
    public final static int DILATE_ERODE = 3;
    public final static int DILATE_SMOOTH = 4;

    public ImagePlus process(ImagePlus inReal, int option) {

        ImagePlus in = inReal.duplicate();

        ImageProcessor processor = in.getProcessor();

        switch (option) {
            case PostProcessor.DILATE:
                processor.dilate();
                in.setTitle(inReal.getTitle() + "_dilated");
                break;
            case PostProcessor.ERODE:
                processor.erode();
                in.setTitle(inReal.getTitle() + "_eroded");
                break;
            case PostProcessor.SMOOTH:
                processor.smooth();
                in.setTitle(inReal.getTitle() + "_smoothed");
                break;
            case PostProcessor.DILATE_ERODE:
                processor.dilate();
                processor.erode();
                in.setTitle(inReal.getTitle() + "_dilated_eroded");
                break;
            case PostProcessor.DILATE_SMOOTH:
                processor.dilate();
                processor.smooth();
                in.setTitle(inReal.getTitle() + "_dilated_smoothed");
                break;
            default:
                in.setTitle(inReal.getTitle());
        }

        return in;
    }

    /**
     * PostProcessing without specifying option parameter: dilate
     *
     * @param in
     * @return
     */
    public ImagePlus process(ImagePlus in) {
        ImageProcessor processor = in.getProcessor();
        processor.dilate();
        in.setTitle(in.getTitle() + "_dilated");
        return in;
    }
}

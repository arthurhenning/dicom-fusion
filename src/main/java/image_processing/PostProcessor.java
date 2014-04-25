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

    public ImagePlus process(ImagePlus in, int option) {
        ImageProcessor processor = in.getProcessor();

        switch (option) {
            case 0:
                processor.dilate();
                break;
            case 1:
                processor.erode();
                break;
            case 2:
                processor.smooth();
                break;
            case 3:
                processor.dilate();
                processor.erode();
                break;
            case 4:
                processor.dilate();
                processor.smooth();
                break;
            default:
                processor.dilate();
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
        return in;
    }
}

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

package model;

import algorithm.HaarDWT;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import java.awt.image.BufferedImage;
import junit.framework.TestCase;

/**
 *
 * @author Arthur Henning
 */
public class ImageProcessingTest extends TestCase {

    public ImageProcessingTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of wavelet1D method, of class ImageProcessing.
     */
    public void testWavelet1D() {
        System.out.println("wavelet1D");
//        ImagePlus in = new ImagePlus("inage", new BufferedImage(9, 9, BufferedImage.TYPE_USHORT_GRAY));
        DicomIO io = new DicomIO("testOutput");
        boolean ok = io.open("F:\\Dicom\\dicom.dcm");
        ImagePlus in = io.getImage();
        HaarDWT instance = new HaarDWT(3);
        ImagePlus expResult = null;
        ImagePlus original = new ImagePlus("original", in.getImage());
        original.show();
        instance.haar2D(in);
        in.show();
        instance.inverseHaar2D(in);
        in.show();
        assert (true);
    }

}

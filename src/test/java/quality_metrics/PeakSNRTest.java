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
import junit.framework.TestCase;

/**
 *
 * @author Arthur Henning
 */
public class PeakSNRTest extends TestCase {

    public PeakSNRTest(String testName) {
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
     * Test of calculate method, of class PeakSNR.
     */
    public void testCalculate() {
        System.out.println("calculate");
        ImagePlus perfectImage = new ImagePlus("F:\\UTCN\\test_images\\mri.jpg");
        ImagePlus assessedImage = new ImagePlus("F:\\UTCN\\test_results\\soft_hard\\haar\\haar_level4_maximum_smooth_dilate.jpg");
        double expResult = 0.0;
        double result = PeakSNR.calculate(perfectImage, assessedImage);
        System.out.println("PSNR: " + result);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}

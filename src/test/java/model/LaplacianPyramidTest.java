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

import ij.ImagePlus;
import junit.framework.TestCase;

/**
 *
 * @author Arthur Henning
 */
public class LaplacianPyramidTest extends TestCase {

    public LaplacianPyramidTest(String testName) {
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
     * Test of calculatePyramid method, of class LaplacianPyramid.
     */
    public void testCalculatePyramid() {
        System.out.println("calculatePyramid");
        DicomIO io = new DicomIO("testOutput");
        boolean ok = io.open("F:\\Dicom\\dicom.dcm");
        ImagePlus in = io.getImage();
        LaplacianPyramid pyr = new LaplacianPyramid();
        pyr.calcLaplacianPyramid(in, 4);
    }

}

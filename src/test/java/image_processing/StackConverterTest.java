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
import junit.framework.TestCase;

/**
 *
 * @author Arthur Henning
 */
public class StackConverterTest extends TestCase {

    public StackConverterTest(String testName) {
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
     * Test of convertToProjection method, of class StackConverter.
     */
    public void testConvertToProjection() {
        System.out.println("convertToProjection");
        ImagePlus image = new ImagePlus("F:\\UTCN\\Dicom\\heart.dcm\\MR-MONO2-8-16x-heart");
        image.show();
        StackConverter instance = new StackConverter();
        ImagePlus expResult = null;
        ImagePlus result = instance.convertToProjection(image);
        result.show();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}

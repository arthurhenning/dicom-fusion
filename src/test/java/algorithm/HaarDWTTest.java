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

package algorithm;

import ij.ImagePlus;
import junit.framework.TestCase;

/**
 *
 * @author Arthur Henning
 */
public class HaarDWTTest extends TestCase {

    public HaarDWTTest(String testName) {
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
     * Test of haar1D method, of class HaarDWT.
     */
    public void testHaar1D() {
        System.out.println("haar1D");
        float[] data = null;
        HaarDWT instance = null;
        instance.haar1D(data);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of haar2D method, of class HaarDWT.
     */
    public void testHaar2D() {
        System.out.println("haar2D");
        ImagePlus in = new ImagePlus("F:\\test_images\\mri.jpg");
        in.show();
        HaarDWT instance = new HaarDWT(2);
        instance.haar2D(in);
        in.show();
        instance.inverseHaar2D(in);
        in.show();
    }

    /**
     * Test of inverseHaar1D method, of class HaarDWT.
     */
    public void testInverseHaar1D() {
        System.out.println("inverseHaar1D");
        float[] data = null;
        HaarDWT instance = null;
        instance.inverseHaar1D(data);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inverseHaar2D method, of class HaarDWT.
     */
    public void testInverseHaar2D() {
        System.out.println("inverseHaar2D");
        ImagePlus in = null;
        HaarDWT instance = null;
        instance.inverseHaar2D(in);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}

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

import io.DicomIO;
import ij.IJ;
import ij.ImagePlus;
import ij.plugin.DICOM;
import junit.framework.TestCase;

/**
 *
 * @author Arthur Henning
 */
public class DicomIOTest extends TestCase {

    public DicomIOTest(String testName) {
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
     * Test of open method, of class DicomIO.
     */
    public void testOpen() {
        System.out.println("open");
        DICOM dcm = new DICOM();
        dcm.open("F:\\Dicom\\dicom.dcm");
        if (dcm.getWidth() == 0) {
            IJ.log("F:\\Dicom\\d1.dcm");
        } else {
            dcm.show();
        }
    }

    /**
     * Test of getImage method, of class DicomIO.
     */
    public void testGetImage() {
        System.out.println("getImage");
        DicomIO instance = null;
        ImagePlus expResult = null;
        ImagePlus result = instance.getImage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveImage method, of class DicomIO.
     */
    public void testSaveImage() {
        System.out.println("saveImage");
        DicomIO instance = null;
        boolean expResult = false;
        boolean result = instance.saveImage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveProperties method, of class DicomIO.
     */
    public void testSaveProperties() {
        System.out.println("saveProperties");
        DicomIO instance = null;
        boolean expResult = false;
        boolean result = instance.saveProperties();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDicom method, of class DicomIO.
     */
    public void testGetDicom() {
        System.out.println("getDicom");
        DicomIO instance = null;
        DICOM expResult = null;
        DICOM result = instance.getDicom();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDicom method, of class DicomIO.
     */
    public void testSetDicom() {
        System.out.println("setDicom");
        DICOM dicom = null;
        DicomIO instance = null;
        instance.setDicom(dicom);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getImagePath method, of class DicomIO.
     */
    public void testGetImagePath() {
        System.out.println("getImagePath");
        DicomIO instance = null;
        String expResult = "";
        String result = instance.getImagePath();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setImagePath method, of class DicomIO.
     */
    public void testSetImagePath() {
        System.out.println("setImagePath");
        String imagePath = "";
        DicomIO instance = null;
        instance.setImagePath(imagePath);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOutputFolder method, of class DicomIO.
     */
    public void testGetOutputFolder() {
        System.out.println("getOutputFolder");
        DicomIO instance = null;
        String expResult = "";
        String result = instance.getOutputFolder();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setOutputFolder method, of class DicomIO.
     */
    public void testSetOutputFolder() {
        System.out.println("setOutputFolder");
        String outputFolder = "";
        DicomIO instance = null;
        instance.setOutputFolder(outputFolder);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}

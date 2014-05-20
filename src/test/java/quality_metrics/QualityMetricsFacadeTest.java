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
import fusion_method.HaarWaveletFusion;
import fusion_method.LaplacianPyrFusion;
import fusion_method.SimpleAverageFusion;
import fusion_method.SimpleMaximumFusion;
import fusion_method.SimpleMinimumFusion;
import ij.ImagePlus;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author Arthur Henning
 */
public class QualityMetricsFacadeTest extends TestCase {

    public QualityMetricsFacadeTest(String testName) {
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
     * Test of addInputImages method, of class QualityMetricsFacade.
     */
    public void testAddInputImages() {
        System.out.println("addInputImages");
        QualityMetricsInput input = null;
        QualityMetricsFacade instance = new QualityMetricsFacade();
        instance.addInputImages(input);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addFusionMethod method, of class QualityMetricsFacade.
     */
    public void testAddFusionMethod() {
        System.out.println("addFusionMethod");
        FusionMethod method = null;
        QualityMetricsFacade instance = new QualityMetricsFacade();
        instance.addFusionMethod(method);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of calculateQualityMetric method, of class QualityMetricsFacade.
     */
    public void testCalculateQualityMetric() throws FileNotFoundException, WriteException {
        System.out.println("calculateQualityMetric");

        QualityMetricsFacade instance = new QualityMetricsFacade();

        ImagePlus perfectImage = new ImagePlus("target/test-classes/mri.jpg");
        ImagePlus image1 = new ImagePlus("target/classes/mri_soft_2.jpg");
        ImagePlus image2 = new ImagePlus("target/classes/mri_hard_2.jpg");

        instance.addInputImages(new QualityMetricsInput(perfectImage, image1, image2));

        ImagePlus image1a = new ImagePlus("target/classes/mri_soft.jpg");
        ImagePlus image2a = new ImagePlus("target/classes/mri_hard.jpg");

        instance.addInputImages(new QualityMetricsInput(perfectImage, image1a, image2a));

        ImagePlus image1b = new ImagePlus("target/classes/mri_left_blurred.jpg");
        ImagePlus image2b = new ImagePlus("target/classes/mri_right_blurred.jpg");

        instance.addInputImages(new QualityMetricsInput(perfectImage, image1b, image2b));

        instance.addFusionMethod(new SimpleAverageFusion());
        instance.addFusionMethod(new SimpleMinimumFusion());
        instance.addFusionMethod(new SimpleMaximumFusion());
        instance.addFusionMethod(new HaarWaveletFusion(2, new SimpleMaximumFusion()));
        instance.addFusionMethod(new LaplacianPyrFusion());

        instance.calculateQualityMetric();

        WritableWorkbook workbook = null;
        File workbookFile = new File("quality_metrics/");
        boolean ok = workbookFile.mkdirs();
        try {
            workbook = Workbook.createWorkbook(new File("quality_metrics/results.xls"));
        } catch (IOException ex) {
            Logger.getLogger(QualityMetricsFacadeTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        WritableSheet sheet;

        PrintWriter pw = new PrintWriter(new FileOutputStream("quality_metrics/results.txt"));

        int sheetIndex = 0;
        for (ArrayList<QualityMetricsOutput> outputArray : instance.getResults()) {

            sheet = workbook.createSheet("Results " + sheetIndex, sheetIndex);

            int index = 0;
            for (QualityMetricsOutput output : outputArray) {
                pw.println(output.toString());
                Label label = new Label(0, index, output.getResultImage().getTitle());
                Number mse = new Number(1, index, output.getMse());
                Number psnr = new Number(2, index, output.getPsnr());
                try {
                    sheet.addCell(label);
                    sheet.addCell(mse);
                    sheet.addCell(psnr);
                } catch (WriteException ex) {
                    Logger.getLogger(QualityMetricsFacadeTest.class.getName()).log(Level.SEVERE, null, ex);
                }
                index++;
            }
            pw.println();
            sheetIndex++;
        }

        try {
            workbook.write();
            workbook.close();
        } catch (IOException ex) {
            Logger.getLogger(QualityMetricsFacadeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        pw.close();

        fail("The test case is a prototype.");
    }

    /**
     * Test of getResults method, of class QualityMetricsFacade.
     */
    public void testGetResults() {
        System.out.println("getResults");
        QualityMetricsFacade instance = new QualityMetricsFacade();
        ArrayList<QualityMetricsOutput> expResult = null;
        ArrayList<ArrayList<QualityMetricsOutput>> result = instance.getResults();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}

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
package io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import quality_metrics.QualityMetricsOutput;

/**
 *
 * @author Arthur Henning
 */
public class ExcelResultsWriter implements QMResultsWriter {

    private String path;
    private String filename;

    public void setOutputFolder(String path) {
        this.path = path;
    }

    public void setOutputFile(String filename) {
        this.filename = filename;
    }

    public void write(ArrayList<ArrayList<QualityMetricsOutput>> results) throws IOException, WriteException {

        File workbookFile = new File(path);
        boolean ok = workbookFile.mkdirs();

        if (!ok) {
            throw new IOException("Exception while creating directories.");
        }

        WritableWorkbook workbook = Workbook.createWorkbook(new File(this.path + "/" + this.filename + ".xls"));

        int sheetIndex = 0;
        for (ArrayList<QualityMetricsOutput> outputArray : results) {

            // create new sheet for pair of input images
            WritableSheet sheet = workbook.createSheet("Results " + sheetIndex, sheetIndex);

            int rowIndex = 0;
            for (QualityMetricsOutput output : outputArray) {

                Label label = new Label(0, rowIndex, output.getResultImage().getTitle());
                jxl.write.Number mse = new jxl.write.Number(1, rowIndex, output.getMse());
                jxl.write.Number psnr = new jxl.write.Number(2, rowIndex, output.getPsnr());

                sheet.addCell(label);
                sheet.addCell(mse);
                sheet.addCell(psnr);

                rowIndex++;
            }
            sheetIndex++;
        }

        workbook.write();
        workbook.close();

    }

}

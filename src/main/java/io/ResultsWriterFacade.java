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

import exception.DicomFusionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.write.WriteException;
import quality_metrics.QualityMetricsOutput;

/**
 *
 * @author Arthur Henning
 */
public class ResultsWriterFacade {

    public static final String DEFAULT_PATH = "quality_metrics";
    public static final String DEFAULT_FILENAME = "results";

    private ArrayList<QMResultsWriter> resultsWriters;
    private String path;
    private String filename;

    public ResultsWriterFacade() {
        resultsWriters = new ArrayList<QMResultsWriter>();
        this.path = ResultsWriterFacade.DEFAULT_PATH;
        this.filename = ResultsWriterFacade.DEFAULT_FILENAME;
    }

    public ResultsWriterFacade(String path, String filename) {
        resultsWriters = new ArrayList<QMResultsWriter>();
        this.path = path;
        this.filename = filename;
    }

    public void addResultsWriter(QMResultsWriter writer) {
        this.resultsWriters.add(writer);
    }

    public void addDefaultValues() {
        this.addResultsWriter(new TextResultsWriter());
        this.addResultsWriter(new ExcelResultsWriter());
    }

    public void writeResults(ArrayList<ArrayList<QualityMetricsOutput>> results) throws DicomFusionException {

        throw new DicomFusionException("test");
//        for (QMResultsWriter resultsWriter : resultsWriters) {
//            resultsWriter.setOutputFolder(path);
//            resultsWriter.setOutputFile(filename);
//            try {
//                resultsWriter.write(results);
//            } catch (IOException ex) {
//                throw new DicomFusionException(ex.getMessage());
//            } catch (WriteException ex) {
//                throw new DicomFusionException(ex.getMessage());
//            }
//        }
    }

}

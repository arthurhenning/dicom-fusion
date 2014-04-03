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
import ij.WindowManager;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import java.awt.Color;

/**
 *
 * @author Arthur Henning
 */
public class HaarDWT {

    private static int level;

    public HaarDWT(int level) {
        HaarDWT.level = level;
    }

    /**
     * http://unix4lyfe.org/haar/
     *http://www.codeproject.com/Articles/683663/Discrete-Haar-Wavelet-Transformation
     *
     * @param in
     * @param row
     * @param width
     */
    public void haar1D(float[] data) {
        float[] temp = new float[data.length];

        int h = data.length / 2;
        for (int i = 0; i < h; i++) {
            int k = i * 2;
            temp[i] = (data[k] + data[k + 1]) / 2;
            temp[i + h] = (data[k] - data[k + 1]) / 2;
        }

        for (int i = 0; i < data.length; i++) {
            data[i] = temp[i];
        }
    }

    /**
     *
     * @param in
     * @param level
     */
    public void haar2D(ImagePlus in) {
        ImageProcessor processor = in.getProcessor();
        float[][] data = processor.getFloatArray();
        int rows = in.getHeight();
        int cols = in.getWidth();

        float[] row;
        float[] col;

        for (int k = 0; k < level; k++) {
            int lev = (int) Math.pow(2.0, (double) k);

            int levCols = cols / lev;
            int levRows = rows / lev;

            row = new float[levCols];
            for (int i = 0; i < levRows; i++) {
                for (int j = 0; j < row.length; j++) {
                    row[j] = data[j][i];
                }
                haar1D(row);
                for (int j = 0; j < row.length; j++) {
                    data[j][i] = row[j];
                }
            }

            col = new float[levRows];
            for (int j = 0; j < levCols; j++) {
                for (int i = 0; i < col.length; i++) {
                    col[i] = data[j][i];
                }
                haar1D(col);
                for (int i = 0; i < col.length; i++) {
                    data[j][i] = col[i];
                }
            }
        }
        processor.setFloatArray(data);
    }

    public void inverseHaar1D(float[] data) {
        float[] temp = new float[data.length];

        int h = data.length / 2;
        for (int i = 0; i < h; i++) {
            int k = i * 2;
            temp[k] = data[i] + data[i + h];
            temp[k + 1] = data[i] - data[i + h];
        }

        for (int i = 0; i < data.length; i++) {
            data[i] = temp[i];
        }
    }

    public void inverseHaar2D(ImagePlus in) {
        ImageProcessor processor = in.getProcessor();
        float[][] data = processor.getFloatArray();
        int rows = in.getHeight();
        int cols = in.getWidth();

        float[] row;
        float[] col;

        for (int k = level - 1; k >= 0; k--) {
            int lev = (int) Math.pow(2.0, (double) k);

            int levCols = cols / lev;
            int levRows = rows / lev;

            row = new float[levCols];
            for (int i = 0; i < levRows; i++) {
                for (int j = 0; j < row.length; j++) {
                    row[j] = data[j][i];
                }
                inverseHaar1D(row);
                for (int j = 0; j < row.length; j++) {
                    data[j][i] = row[j];
                }
            }

            col = new float[levRows];
            for (int j = 0; j < levCols; j++) {
                for (int i = 0; i < col.length; i++) {
                    col[i] = data[j][i];
                }
                inverseHaar1D(col);
                for (int i = 0; i < col.length; i++) {
                    data[j][i] = col[i];
                }
            }
        }
        processor.setFloatArray(data);
    }
}

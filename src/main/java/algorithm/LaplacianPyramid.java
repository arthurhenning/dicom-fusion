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
import ij.plugin.ImageCalculator;
import ij.process.ImageProcessor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Laplacian Pyramid algorithm. Lossy algorithm in terms of image information.
 *
 * * @author Arthur Henning
 */
public class LaplacianPyramid {

    /**
     * The arrayList representing the Laplacian pyramid.
     */
    private List<ImagePlus> laplacianPyramid;

    /**
     * The arrayList representing the Gaussian pyramid.
     */
    private List<ImagePlus> gaussianPyramid;

    /**
     * Sigma used for Gaussian filter kernel size.
     */
    private final double sigma;

    /**
     * Resolution level.
     */
    private final int level;

    /**
     * Constructor.
     *
     * @param level {int}, the resolution level
     * @param sigma {double}, the Gaussian filter kernel size.
     */
    public LaplacianPyramid(int level, double sigma) {
        this.level = level;
        this.sigma = sigma;
        laplacianPyramid = new ArrayList<ImagePlus>();
        gaussianPyramid = new ArrayList<ImagePlus>();
    }

    /**
     * Calculates the Gaussian pyramid.
     *
     * @param in {ImagePlus}, the image of which the pyramid will be calculated
     */
    public void calcGaussianPyramid(ImagePlus in) {
        gaussianPyramid.add(new ImagePlus("Gaussian 0", in.getImage()));
        for (int i = 1; i < level; i++) {
            ImagePlus previous = gaussianPyramid.get(i - 1).duplicate();
            ImageProcessor iP = previous.getProcessor();
            int width = previous.getWidth();

            iP.blurGaussian(sigma);
            ImageProcessor iPResized = iP.resize(width / 2);
            ImagePlus next = new ImagePlus("Gaussian " + i, iPResized.getBufferedImage());
            gaussianPyramid.add(next);
        }
    }

    /**
     * Calculates the Laplacian piramid, using the Gaussian pyramid.
     *
     * @param in {ImagePlus}, the image of which the pyramid will be calculated
     */
    public void calcLaplacianPyramid(ImagePlus in) {

        // calculate Gaussian pyramid
        calcGaussianPyramid(in);

        // add the same last image
        ImagePlus last = gaussianPyramid.get(level - 1).duplicate();
        last.setTitle("Laplacian " + (level - 1));
        laplacianPyramid.add(last);

        for (int i = level - 1; i > 0; i--) {
            ImagePlus gaussian = gaussianPyramid.get(i).duplicate();
            ImageProcessor processor = gaussian.getProcessor();

            // size *= 2
            processor.setInterpolationMethod(ImageProcessor.BICUBIC);
            ImageProcessor processorResized = processor.resize(gaussian.getWidth() * 2);
            gaussian = new ImagePlus("GaussianResized " + i, processorResized);

            // low pass filter
            processorResized.blurGaussian(sigma);

            //difference between current image and previous
            ImagePlus current = gaussianPyramid.get(i - 1);
            ImageCalculator calculator = new ImageCalculator();
            ImagePlus difference = calculator.run("Subtract create", current, gaussian);
            difference.setTitle("Laplacian " + (i - 1));

            // store laplacian image
            laplacianPyramid.add(difference);
        }

        // reverse list for correct order
        Collections.reverse(laplacianPyramid);
    }

    /**
     * Reconstruct image from Laplacian pyramid.Inverse Laplacian Pyramid
     * transform.
     *
     * @return {ImagePlus}, the reconstructed image
     */
    public ImagePlus reconstrLaplacianPyramid() {
        ImagePlus image = laplacianPyramid.get(level - 1);
        ImageProcessor processor = image.getProcessor();
        int width = image.getWidth();
        for (int i = level - 1; i > 0; i--) {

            // size *= 2
            width *= 2;

            processor.setInterpolationMethod(ImageProcessor.BICUBIC);
            ImageProcessor processorResized = processor.resize(width);
            ImagePlus imageResized = new ImagePlus("Temp", processorResized);

            // low pass filter
            processorResized.blurGaussian(sigma);

            // sum between current image and previous
            ImagePlus previous = laplacianPyramid.get(i - 1);
            ImageCalculator calculator = new ImageCalculator();
            image = calculator.run("Add create", previous, imageResized);
        }
        return image;
    }

    /**
     * Gets the Laplacian Pyramid.
     *
     * @return {ArrayList<ImagePlus>}, the Laplacian pyramid
     */
    public List<ImagePlus> getLaplacianPyramid() {
        return laplacianPyramid;
    }

    /**
     * Sets the Laplacian Pyramid.
     *
     * @param laplacianPyramid {ArrayList<ImagePlus>}, the Laplacian pyramid
     */
    public void setLaplacianPyramid(List<ImagePlus> laplacianPyramid) {
        this.laplacianPyramid = laplacianPyramid;
    }

    /**
     * Gets the Gaussian Pyramid.
     *
     * @return {ArrayList<ImagePlus>}, the Gaussian pyramid
     */
    public List<ImagePlus> getGaussianPyramid() {
        return gaussianPyramid;
    }

    /**
     * Sets the Gaussian Pyramid.
     *
     * @param gaussianPyramid {ArrayList<ImagePlus>}, the Gaussian pyramid
     */
    public void setGaussianPyramid(List<ImagePlus> gaussianPyramid) {
        this.gaussianPyramid = gaussianPyramid;
    }

    /**
     * Gets the sigma.
     *
     * @return {double}, the sigma
     */
    public double getSigma() {
        return sigma;
    }

    /**
     * Gets the resolution level.
     *
     * @return {int}, the level
     */
    public int getLevel() {
        return level;
    }

}

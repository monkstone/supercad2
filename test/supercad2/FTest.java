/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supercad2;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.PApplet;
import static processing.core.PConstants.P3D;
import processing.core.PImage;
import processing.core.PVector;

public class FTest extends PApplet {

    Mode cadSoftware;
    boolean record = false;
    boolean displayImage = false;

    @Override
    public void setup() {
        size(800, 600, P3D);
    }

    @Override
    public void draw() {

        if (record) {
            beginRaw("supercad2." + cadSoftware.className, "output." + cadSoftware.ext);
        }
        if (displayImage) {
            PImage img = loadImage(System.getProperty("user.home") + File.separator + "output.png");
            background(img);
        } else {
            fTest();
        }

        if (record) {
            endRaw();
            record = false;
        }
    }

    public void configureLights() {
        lights();
        ambientLight(100, 100, 100);
        directionalLight(5, 0, 0, -1, -1, -1); // colored directional
        directionalLight(0, 0, 5, 1, -1, -1);  // lights essential
        directionalLight(5, 5, 0, 1, 1, 1);  // for 3D illusion
    }

    PVector midPoint(PVector a, PVector b) {
        PVector result = PVector.add(a, b);
        result.div(2);
        return result;
    }

    public void fTest() {  // encapsulate initial processing sketch in a function
        background(0);
        translate(width / 2, height / 2, -width / 3);
        configureLights();
        rotateY(QUARTER_PI);
        fill(255, 0, 0);
        translate(0, -60, 0);
        box(120);
        translate(0, -120, 0);
        box(120);
        translate(120, 0, 0);
        box(120);
        translate(-120, -120, 0);
        box(120);
        translate(0, -120, 0);
        box(120);
        translate(120, 0, 0);
        box(120);
    }

    @Override
    public void keyPressed() {
        switch (key) {
            case 'r':
                cadSoftware = Mode.RHINO;
                break;
            case 's':
                cadSoftware = Mode.SKETCH_UP;
                break;
            case 'a':
                cadSoftware = Mode.AUTOLISP;
                break;
            case 'p':
                cadSoftware = Mode.POVRAY2;
                break;
            case 'm':
                cadSoftware = Mode.MAYA;
                break;
            case 'o':
                cadSoftware = Mode.OBJ;
                break;
            case 'c':
                cadSoftware = Mode.ARCHICAD;
                break;
            case 't':
                noLoop();
                String[] ini = {"/usr/bin/povray", sketchPath("output.ini")};
                ProcessBuilder pb = new ProcessBuilder(ini);
                pb.inheritIO();
                try {
                    pb.start();
                } catch (IOException ex) {
                    Logger.getLogger(FTest.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
        record = true;
    }

    public static void main(String[] args) {
        PApplet.main("supercad2.FTest");
    }
}

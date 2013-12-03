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

    Status status;
    Mode cadSoftware;


    @Override
    public void setup() {
        size(800, 600, P3D);
        status = Status.START;
    }

    @Override
    public void draw() {
        if (status == Status.TRACED) {
            PImage img = loadImage(System.getProperty("user.home") + File.separator + "output.png");
            background(img);
        } else {
            if (status == Status.RECORD) {
                beginRaw("supercad2." + cadSoftware.className, "output." + cadSoftware.ext);
                status = Status.RECORDING;
            }
            fTest();
            if (status == Status.RECORDING) {
                endRaw();                
                status = Status.RECORDED;
            }
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
                status = Status.RECORD;
                break;
            case 's':
                cadSoftware = Mode.SKETCH_UP;
                status = Status.RECORD;
                break;
            case 'a':
                cadSoftware = Mode.AUTOLISP;
                status = Status.RECORD;                
                break;
            case 'p':
                if (status == Status.START){
                    cadSoftware = Mode.POVRAY2;
                    status = Status.RECORD;
                } 
                break;
            case 'm':
                cadSoftware = Mode.MAYA;
                status = Status.RECORD;
                break;
            case 'o':
                cadSoftware = Mode.OBJ;
                status = Status.RECORD;
                break;
            case 'c':
                cadSoftware = Mode.ARCHICAD;
                status = Status.RECORD;
                break;
            case 't':
                if (status == Status.RECORDED) {
                    String[] ini = {"/usr/bin/povray", sketchPath("output.ini")};
                    ProcessBuilder pb = new ProcessBuilder(ini);
                    Process povray;
                    pb.inheritIO();
                    try {
                        povray = pb.start();
                        status = Status.TRACING;
                        if (status == Status.TRACING && povray.waitFor() == 0) {
                            status = Status.TRACED;
                        }
                    } catch (IOException | InterruptedException ex) {
                        Logger.getLogger(FTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

        }
    }

    public static void main(String[] args) {
        PApplet.main("supercad2.FTest");
    }
}

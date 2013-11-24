/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supercad2;

import processing.core.PApplet;
import static processing.core.PConstants.P3D;
import processing.core.PVector;

public class FTest extends PApplet {


    Mode cadSoftware;
    boolean record = false;

    @Override
    public void setup() {
        size(800, 600, P3D);
    }

    @Override
    public void draw() {
        background(0);
        configureLights();
        //translate(width / 2, height / 2, -300);
        //noStroke();
        rotateY(QUARTER_PI);
        if (record) {
            beginRaw("supercad2." + cadSoftware.className, "output." + cadSoftware.ext);
        }
        fTest();

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
        translate(width / 2, height / 2, -width / 3);
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
                cadSoftware = Mode.POVRAY;
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
        }
        record = true;
    }

    public static void main(String[] args) {
        PApplet.main("supercad2.FTest");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supercad2;

import processing.core.PApplet;
import static processing.core.PConstants.P3D;
import processing.core.PVector;

public class Test extends PApplet {

    PVector[] pts = { // points of the unit tetrahedron
        new PVector(-0.5f, -0.5f, -0.5f),
        new PVector(0.5f, 0.5f, -0.5f),
        new PVector(-0.5f, 0.5f, 0.5f),
        new PVector(0.5f, -0.5f, 0.5f)
    };

    int TETRA_SIZE = 600;
    float ROTATE_STEP = 0.001f;
    Mode cadSoftware;
    boolean record = false;

    @Override
    public void setup() {
        size(1280, 720, P3D);
    }

    @Override
    public void draw() {
        background(0);
        configureLights();
        translate(width / 2, height / 2, -600);
        noStroke();

        if (record) {
            beginRaw("supercad2." + cadSoftware.className, "output." + cadSoftware.ext);
        }
        rotateX(PI);
        // rotateZ(frameCount * ROTATE_STEP);
        // rotateY(frameCount * ROTATE_STEP);
        // rotateX(frameCount * ROTATE_STEP);
        drawSierpinski(pts);

        if (record) {
            endRaw();
            record = false;
        }
    }

    public void configureLights() {
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

    public void drawSierpinski(PVector[] pts) {
        if (pts[0].dist(pts[1]) < 0.1) {                 // limits recursion on relative size
            drawTetrahedron(pts, TETRA_SIZE); // render the tetrahedra
        } else {
            PVector av = midPoint(pts[0], pts[1]); // a tetrahedron midpoint vertices
            PVector bv = midPoint(pts[0], pts[2]); // b
            PVector cv = midPoint(pts[0], pts[3]); // b
            PVector dv = midPoint(pts[1], pts[2]); // d
            PVector ev = midPoint(pts[1], pts[3]); // e
            PVector fv = midPoint(pts[3], pts[2]);  // e
            PVector[] aa = {
                pts[0],
                av,
                bv,
                cv
            };
            PVector[] bb = {
                av,
                ev,
                dv,
                pts[1]
            };
            PVector[] cc = {
                cv,
                ev,
                fv,
                pts[3]
            };
            PVector[] dd = {
                bv,
                dv,
                fv,
                pts[2]
            };
            drawSierpinski(aa); // calculate further inner tetrahedra coordinates
            drawSierpinski(bb);
            drawSierpinski(cc);
            drawSierpinski(dd);
        }
    }

    public void drawTetrahedron(PVector[] pts, float sz) {
        fill(255);
        beginShape(TRIANGLES);

        vertex(pts[0].x * sz, pts[0].y * sz, pts[0].z * sz);  // 1
        vertex(pts[1].x * sz, pts[1].y * sz, pts[1].z * sz);  // 2  
        vertex(pts[2].x * sz, pts[2].y * sz, pts[2].z * sz);  // 3

        vertex(pts[2].x * sz, pts[2].y * sz, pts[2].z * sz);  // 3
        vertex(pts[0].x * sz, pts[0].y * sz, pts[0].z * sz);  // 1  
        vertex(pts[3].x * sz, pts[3].y * sz, pts[3].z * sz);  // 4  

        vertex(pts[3].x * sz, pts[3].y * sz, pts[3].z * sz);  // 4  
        vertex(pts[2].x * sz, pts[2].y * sz, pts[2].z * sz);  // 3
        vertex(pts[1].x * sz, pts[1].y * sz, pts[1].z * sz);  // 2

        vertex(pts[1].x * sz, pts[1].y * sz, pts[1].z * sz);  // 2  
        vertex(pts[3].x * sz, pts[3].y * sz, pts[3].z * sz);  // 4
        vertex(pts[0].x * sz, pts[0].y * sz, pts[0].z * sz);  // 1

        endShape();
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
        PApplet.main("superCAD.Test");
    }
}

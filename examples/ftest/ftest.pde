import java.util.logging.Level;
import java.util.logging.Logger;
import supercad2.Mode;
import supercad2.Status;
import supercad2.*;


Mode cad;
Status status;

void setup() {
  size(800, 600, P3D);
  status = Status.START;
}

void draw() {  
  if (status == Status.TRACED) {         
    PImage img = loadImage(System.getProperty("user.home") + File.separator + "output.png");
    background(img);
  } 
  else {
    if (status == Status.RECORD) {
      beginRaw("supercad2." + cad.className, "output." + cad.ext);
      status = Status.RECORDING;
    }

    fTest();

    if (status == Status.RECORDING) {
      endRaw();
      status = Status.RECORDED;
    }
  }
}

void configureLights() {
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

void fTest() {  // encapsulate initial processing sketch in a function
  translate(width / 2, height / 2, -width / 3);
  background(0);
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

void keyPressed() {
  switch (key) {
  case 'r':
    cad = Mode.RHINO;
    status = Status.RECORD;
    break;
  case 's':
    cad = Mode.SKETCH_UP;
    status = Status.RECORD;
    break;
  case 'a':
    cad = Mode.AUTOLISP;
    status = Status.RECORD;
    break;
  case 'p':
    cad = Mode.POVRAY2;
    status = Status.RECORD;
    break;
  case 'm':
    cad = Mode.MAYA;
    status = Status.RECORD;
    break;
  case 'o':
    cad = Mode.OBJ;
    status = Status.RECORD;
    break;
  case 'c':
    cad = Mode.ARCHICAD;
    status = Status.RECORD;
    break;
  case 't':         // start povray
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
      } 
      catch (IOException  ex) {
        Logger.getLogger(ftest.class.getName()).log(Level.SEVERE, null, ex);
      }
      catch (InterruptedException ex) {
        Logger.getLogger(ftest.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    break;
  }
}


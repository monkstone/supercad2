
import supercad2.*;


Mode cad;
boolean record = false;

void setup() {
  size(800, 600, P3D);
}

void draw() {
  background(0);
  configureLights();
  //translate(width / 2, height / 2, -300);
  //noStroke();
  rotateY(QUARTER_PI);
  if (record) {
    beginRaw("supercad2." + cad.className, "output." + cad.ext);
  }
  fTest();

  if (record) {
    endRaw();
    record = false;
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
    break;
  case 's':
    cad = Mode.SKETCH_UP;
    break;
  case 'a':
    cad = Mode.AUTOLISP;
    break;
  case 'p':
    cad = Mode.POVRAY;
    break;
  case 'm':
    cad = Mode.MAYA;
    break;
  case 'o':
    cad = Mode.OBJ;
    break;
  case 'c':
    cad = Mode.ARCHICAD;
    break;
  }
  record = true;
}


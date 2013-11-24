package supercad2;

/**
 *
 * @author Martin Prout
 */
public enum Mode {

    ARCHICAD("ArchiCAD", "gdl"),
    AUTOLISP("AutoLISP", "lsp"),
    AUTOLISP2("AutoLISP2", "lsp"),
    MAYA("Maya", "mel"),
    OBJ("ObjFile", "obj"),
    POVRAY("PovRAY", "pov"),
    RHINO("Rhino", "rvb"),
    SKETCH_UP("SketchUP", "rb");
    String className;
    String ext;

    Mode(String className, String ext) {
        this.className = className;
        this.ext = ext;
    }

}

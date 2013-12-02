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
    POVRAY2("PovRAY2", "ini"),
    RHINO("Rhino", "rvb"),
    SKETCH_UP("SketchUP", "rb");
    public String className;
    public String ext;

    Mode(String className, String ext) {
        this.className = className;
        this.ext = ext;
    }

}

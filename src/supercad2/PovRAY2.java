package supercad2;

/**
 * supercad2 By Martin Prout, based on a original idea by Guillaume LaBelle
 *
 * This code is provided as is, without any warranty.
 */
public class PovRAY2 extends Raw {

    final String declare = "#declare %s = ";
    final String format_vec3 = "<%.4f, %.4f, %.4f>";
    final String format_named_vec3 = "<%.4f, %.4f, %.4f>";

    /* (non-Javadoc)
     * @see superCAD.Raw#writeHeader()
     */
    @Override
    protected void writeHeader() {
        writeDirtyHeader();
    }

    /* (non-Javadoc)
     * @see superCAD.Raw#writeFooter()
     */
    @Override
    protected void writeFooter() {
        //No Footer
        System.out.println(SuperCAD.tag + "PovRAY2 (Done)");
    }

    /* (non-Javadoc)
     * @see superCAD.Raw#writeLine()
     */
    @Override
    protected void writeLine(int index1, int index2) {
        writer.print("blob { threshold .65 cylinder {");
        writer.print(toPovRAYAsPoint3D(vertices[index1]) + ",");
        writer.print(toPovRAYAsPoint3D(vertices[index2]));
        writer.println(",P5W 1   pigment { color rgb <1.0, 0.9, 0.8> }  texture{BeigeLwD} } }");

    }

    protected void writeLine2(int index1, int index2) {
        writer.print("cylinder {");
        writer.print(toPovRAYAsPoint3D(vertices[index1]) + ",");
        writer.print(toPovRAYAsPoint3D(vertices[index2]));
        writer.println(",P5W texture{BeigeLwD} }");

    }

    private String toPovRAYAsPoint3D(float[] vertex) {
        return "<" + toStringComa(vertex) + ">";
    }

    /**
     *
     * @param vertex
     * @return
     */
    @Override
    protected String toStringComa(float[] vertex) {
        return String.format(format_vec3, vertex[X], -vertex[Y], -vertex[Z]);
    }

    /* (non-Javadoc)
     * @see superCAD.Raw#writeTriangle()
     */
    @Override
    protected void writeTriangle() {
        writer.print("triangle {");
        writer.print(toPovRAYAsPoint3D(vertices[0]) + ",");
        writer.print(toPovRAYAsPoint3D(vertices[1]) + ",");
        writer.print(toPovRAYAsPoint3D(vertices[2]));
        writer.println(" texture{BeigeT}}");
        vertexCount = 0;
    }

     private void writeDirtyHeader() {
        printHeader();
        writer.println("");
        printInclude("colors.inc");
        declareCamera("default_camera", "perspective", 60, (-height / 2.0) / Math.tan(PI * 30.0 / 180.0), (double) width / height);        
    }

    public void declareCamera(String name, String type, int fov, double location, double aspectRatio) {
        writer.println(String.format(declare, name + "camera {"));
        writer.println(type);
        writer.println(String.format("  angle %d", fov));
        writer.println(String.format(format_named_vec3, "  location ", 0.0, 0.0, location));
        writer.println(String.format("  right", aspectRatio));
        writer.println(String.format(format_named_vec3, "  look_at ", 0.0, 0.0, 0.0) + "}");
    }

    public void printHeader() {
        writer.println("#version 3.7;");
        writer.println("global_settings {  assumed_gamma 1.0 }");
    }

    public void printInclude(String inc) {
        writer.println(String.format("#include \"%s\";", inc));
    }
}

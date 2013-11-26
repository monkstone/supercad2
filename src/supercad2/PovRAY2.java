package supercad2;

/**
 * supercad2 By Martin Prout, based on a original idea by Guillaume LaBelle
 *
 * This code is provided as is, without any warranty.
 */
public class PovRAY2 extends Raw {

    final String declare = "#declare ";
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
        StringBuilder sb = new StringBuilder(200);
        sb.append("blob { threshold .65 cylinder {");
        sb.append(toPovVec3D(vertices[index1])).append(',');
        sb.append(toPovVec3D(vertices[index2]));
        sb.append(",P5W 1   pigment { color rgb <1.0, 0.9, 0.8> }  texture{BeigeLwD} } }\n");
        writer.append(sb);
    }

    protected void writeLine2(int index1, int index2) {
        StringBuilder sb = new StringBuilder(100);
        sb.append("cylinder {").append(toPovVec3D(vertices[index1])).append(',');
        sb.append(toPovVec3D(vertices[index2]));
        writer.append(sb.append(",P5W texture{BeigeLwD} }\n"));

    }

    private StringBuilder toPovVec3D(float[] vertex) {
        StringBuilder sb = new StringBuilder(100);
        sb.append('<').append(vertex[X]).append(", ").append(-vertex[Y]).append(", ");
        return sb.append(-vertex[Z]).append('>');
    }

    private StringBuilder toPovVec3D(double x, double y, double z) {
        StringBuilder sb = new StringBuilder(100);
        sb.append('<').append(x).append(", ").append(y).append(", ");
        return sb.append(z).append('>');
    }

//    private StringBuilder toPovVec3D(int[] vertex) {
//        StringBuilder sb = new StringBuilder(100);
//        sb.append('<').append(vertex[X]).append(", ").append(-vertex[Y]).append(", ");
//        return sb.append(-vertex[Z]).append('>');
//    }


    /* (non-Javadoc)
     * @see superCAD.Raw#writeTriangle()
     */
    @Override
    protected void writeTriangle() {
        StringBuilder sb = new StringBuilder(300);
        sb.append("triangle {").append(toPovVec3D(vertices[0])).append(',');
        sb.append(toPovVec3D(vertices[1])).append(',').append(toPovVec3D(vertices[2]));
        writer.append(sb.append('\n').append(" texture{BeigeT}}\n"));
        vertexCount = 0;
    }

     private void writeDirtyHeader() {
        printHeader();
        writer.println("");
        writer.append(include("colors.inc"));
        declareCamera("default_camera", "perspective", 60, (-height / 2.0) / Math.tan(PI * 30.0 / 180.0), (double) width / height);        
    }

    public void declareCamera(String name, String type, int fov, double location, double aspectRatio) {
        StringBuilder sb = new StringBuilder(200);
        sb.append(declare).append(name).append(" = camera {\n");
        sb.append(type).append("  angle ").append(fov).append('\n');
        sb.append("  location ").append(toPovVec3D(0.0, 0.0, location)).append('\n');
        sb.append("  right ").append(aspectRatio).append('\n');
        sb.append("  location ").append(toPovVec3D(0.0, 0.0, location)).append("}\n");
        writer.append(sb);
    }

    public void printHeader() {
        writer.println("#version 3.7;");
        writer.println("global_settings {  assumed_gamma 1.0 }");
    }

    public StringBuilder include(String inc) {
        return new StringBuilder(100).append("#include \"").append(inc).append("\";\n");
    }
}

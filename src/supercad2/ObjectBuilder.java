/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supercad2;

import java.text.DecimalFormat;

/**
 *
 * @author foo
 */
public class ObjectBuilder {

    static final String AMBIENT = "ambient ";
    static final String DIFFUSE = "diffuse ";
    static final String PHONG = "phong ";
    static final String PHONG_SIZE = "phong_size ";
    static final String FOV = "FOV";
    static final String MACRO = "#macro ";
    static final String END_MACRO = "#end ";
    static final String GLOBAL = "global_settings{\n    assumed_gamma ";
    static final String GLOBAL_RADIOSITY = "global_settings{\n    assumed_gamma 1.0\n";
    static final String RADIOSITY = "    radiosity{\n    pretrace_start 0.04\n"
            + "    pretrace_end 0.01\n    count 200\n    recursion_limit 3\n    nearest_count 10\n"
            + "    error_bound 0.5\n  }\n}\n";
    static final String ZDEPTH = "ZDEPTH";
    static final String ASPECT_RATIO = "ASPECT_RATIO";
    static final String WIDTH = "WIDTH";
    static final String HEIGHT = "HEIGHT";
    static final String POV_DEFINE = "#declare ";
    static final String POV_INCLUDE = "#include \"";
    static final String VERSION = "#version ";
    static final char QUOTE = '\"';
    static final String IF_DEFINE = "#ifndef (";
    static final String END_DEFINE = "#end\n\n";
    static final String END_CURLY = "}\n";
    static final String END_CURL = "}";
    static final String SEMI = ";\n";
    static final String CLOSE = ")\n";
    static final String INI_DEFINE = "Define=";
    static final String EOL = System.lineSeparator();
    static final char EQUAL = '=';
    static final char SPC = ' ';
    static final String TAB = "   ";
    DecimalFormat df2, df1, df4;

    public ObjectBuilder() {
        df4 = new DecimalFormat("#.####");
        df2 = new DecimalFormat("#.##");
        df1 = new DecimalFormat("#.#");
    }

    protected StringBuilder povDefine(StringBuilder sb, String name, String value) {
        sb.append(IF_DEFINE).append(name).append(CLOSE).append(TAB).append(POV_DEFINE).append(name);
        sb.append(EQUAL).append(value).append(SEMI);
        return sb.append(END_DEFINE);
    }

    private void povInclude(StringBuilder sb, String include) {
        sb.append(POV_INCLUDE).append(include).append(QUOTE).append(EOL);
    }

    public StringBuilder version(StringBuilder sb, double vers) {
        return sb.append(VERSION).append(vers).append(SEMI).append(EOL);
    }

    public StringBuilder global(StringBuilder sb, double gamma) {
        return sb.append(GLOBAL).append(df1.format(gamma)).append(END_CURLY).append(EOL);
    }

    public StringBuilder globalRadiosity(StringBuilder sb) {
        return sb.append(GLOBAL_RADIOSITY).append(RADIOSITY).append(EOL);
    }
    public StringBuilder includes(StringBuilder sb, String[] include) {
        for (String inc : include) {
            povInclude(sb, inc);
        }
        return sb.append(EOL);
    }

    public StringBuilder include(StringBuilder sb, String include) {        
            povInclude(sb, include);
        return sb.append(EOL);
    }

    private StringBuilder vector(StringBuilder sb, String name, String a, String b, String c) {
        sb.append(name).append(" <").append(a).append(", ").append(b).append(", ").append(c);
        return sb.append(">");
    }

    private StringBuilder vector4(StringBuilder sb, String name, String a, String b, String c, String ft) {
        sb.append(name).append(" <").append(a).append(", ").append(b).append(", ").append(c).append(", ").append(ft);
        return sb.append(">");
    }

    private StringBuilder vector5(StringBuilder sb, String name, String a, String b, String c, String f, String t) {
        sb.append(name).append(" <").append(a).append(", ").append(b).append(", ").append(c).append(", ").append(f);
        return sb.append(", ").append(f).append(">");
    }

    protected StringBuilder toPovVec3D(StringBuilder sb, float[] vertex) {
        String x = df2.format(vertex[0]);
        String y = df2.format(-vertex[1]);
        String z = df2.format(-vertex[2]);
        sb.append(x).append(", ").append(y).append(", ");
        return sb.append(z);
    }

    protected StringBuilder toPovVec3D(StringBuilder sb, double x, double y, double z) {
        sb.append('<').append(df4.format(x)).append(", ").append(df4.format(y)).append(", ");
        return sb.append(df4.format(z)).append('>');
    }

    public StringBuilder declareCamera(StringBuilder sb, String name) {
        sb.append(POV_DEFINE).append(name).append(EQUAL).append(" camera {\n");
        sb.append(TAB).append("perspective\n").append(TAB).append("angle ").append(FOV).append(EOL);
        vector(sb.append(TAB), "location", "0", "0", ZDEPTH).append(EOL);
        sb.append(TAB).append("right ").append(ASPECT_RATIO).append(EOL);
        vector(sb.append(TAB), "look_at", "0", "0", "0").append(EOL);
        return sb.append(END_CURLY).append(EOL);
    }

    public StringBuilder declareTexture(StringBuilder sb, String name, String pigment, String finish) {
        sb.append(POV_DEFINE).append(name).append(EQUAL).append(" texture {\n");
        sb.append(TAB).append("pigment{").append(pigment).append(END_CURLY);
        sb.append(TAB).append("finish{").append(finish).append(END_CURLY);
        return sb.append(END_CURLY);
    }

    public StringBuilder declareColor(StringBuilder sb, String name, double r, double g, double b) {
        sb.append(POV_DEFINE).append(name).append(EQUAL);
        String red = df2.format(r);
        String green = df2.format(g);
        String blue = df2.format(b);
        vector(sb, " rgb", red, green, blue);
        return sb.append(SEMI);
    }

    public StringBuilder declareColor(StringBuilder sb, String name, double r, double g, double b, double f, double t) {
        sb.append(POV_DEFINE).append(name).append(EQUAL);
        String red = df2.format(r);
        String green = df2.format(g);
        String blue = df2.format(b);
        String filter = df2.format(f);
        String transmit = df2.format(t);
        vector5(sb, " rgbft", red, green, blue, filter, transmit);
        return sb.append(SEMI);
    }

    public StringBuilder declareColorF(StringBuilder sb, String name, double r, double g, double b, double f) {
        sb.append(POV_DEFINE).append(name).append(EQUAL);
        String red = df2.format(r);
        String green = df2.format(g);
        String blue = df2.format(b);
        String filter = df2.format(f);
        vector4(sb, " rgbf", red, green, blue, filter);
        return sb.append(SEMI);
    }

    public StringBuilder declareColorT(StringBuilder sb, String name, double r, double g, double b, double t) {
        sb.append(POV_DEFINE).append(name).append(EQUAL);
        String red = df2.format(r);
        String green = df2.format(g);
        String blue = df2.format(b);
        String transmit = df2.format(t);
        vector4(sb, " rgbt", red, green, blue, transmit);
        return sb.append(SEMI);
    }

    public StringBuilder declarePigment(StringBuilder sb, String name, double r, double g, double b) {
        sb.append(POV_DEFINE).append(name).append(EQUAL).append(" pigment { ");
        String red = df2.format(r);
        String green = df2.format(g);
        String blue = df2.format(b);
        vector(sb, "rgb", red, green, blue);
        return sb.append(END_CURL).append(EOL);
    }

    public StringBuilder declarePigment(StringBuilder sb, String name, String color) {
        sb.append(POV_DEFINE).append(name).append(EQUAL).append(" pigment { ");
        return sb.append(color).append(END_CURL).append(EOL);
    }

    public StringBuilder declareFinish(StringBuilder sb, String name, double ambient, double diffuse) {
        sb.append(POV_DEFINE).append(name).append(EQUAL).append(" finish {");
        sb.append(AMBIENT).append(ambient).append(SPC).append(DIFFUSE).append(diffuse);
        return sb.append(END_CURL).append(EOL);
    }

    public StringBuilder declareFinish(StringBuilder sb, String name, double ambient, double diffuse, double phongSize, double phong) {
        sb.append(POV_DEFINE).append(name).append(EQUAL).append(" finish {");
        sb.append(AMBIENT).append(ambient).append(SPC).append(DIFFUSE).append(diffuse).append(SPC);
        sb.append(PHONG_SIZE).append(ambient).append(SPC).append(PHONG).append(diffuse);
        return sb.append(END_CURL).append(EOL);
    }

    public StringBuilder aspectRatio(StringBuilder sb, float width, float height) {
        String ratio = df2.format(width / height);
        return povDefine(sb, ASPECT_RATIO, ratio);
    }

    public StringBuilder zdepth(StringBuilder sb, double height, double fov) {
        String zdepth = df1.format(-height / (2.0 * Math.tan(fov)));
        return povDefine(sb, ZDEPTH, zdepth);
    }

    /**
     *
     * @param sb String builder
     * @param fov Angle in radians
     * @return formatted angle String in degrees
     */
    public StringBuilder fov(StringBuilder sb, double fov) {
        String cfov = df1.format(fov * 90 / Math.PI);
        return povDefine(sb, FOV, cfov);
    }

    public StringBuilder macro(StringBuilder sb, String name, String values, String logic) {
        sb.append(MACRO).append(name).append(" (").append(values).append(" )\n");
        return sb.append(logic).append(EOL).append(END_MACRO).append(EOL).append(EOL);
    }

}

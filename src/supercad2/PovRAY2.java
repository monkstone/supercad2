package supercad2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * superCAD by Guillaume LaBelle (gll@spacekit.ca)
 * http://LaBelle.spaceKIT.ca/superCAD
 *
 * This code is provided as is, without any warranty.
 */
public class PovRAY2 extends Raw {

    ObjectBuilder obj;
    PrintWriter dataWriter;
    PrintWriter povWriter;
    File dataFile;
    File povFile;

    public PovRAY2() {
        try {
            obj = new ObjectBuilder();
            dataFile = new File(System.getProperty("user.home") + File.separator + "data.inc");
            povFile = new File(System.getProperty("user.home") + File.separator + "output.pov");
            dataWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(dataFile, true), "UTF-8"));
            povWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(povFile, true), "UTF-8"));
        } catch (UnsupportedEncodingException | FileNotFoundException ex) {
            Logger.getLogger(PovRAY2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /* (non-Javadoc)
     * @see superCAD.Raw#writeHeader()
     */

    @Override
    protected void writeHeader() {
        StringBuilder sb = new StringBuilder(100);
        String povString = povFile.getAbsolutePath();
        String povimage = povString.replace("pov", "png");
        sb.append("Display=On").append(ObjectBuilder.EOL);
        sb.append("Quality=").append(9).append(ObjectBuilder.EOL);
        sb.append("Height=").append(height).append(ObjectBuilder.EOL);
        sb.append("Width=").append(width).append(ObjectBuilder.EOL);
        sb.append("Input_File_Name=\"").append(povString).append(ObjectBuilder.QUOTE).append(ObjectBuilder.EOL);
        sb.append("Output_File_Name=\"").append(povimage).append(ObjectBuilder.QUOTE).append(ObjectBuilder.EOL);
        writer.println(sb);
        writer.flush();
        writer.close();
        writeDirtyHeader();
    }

    /* (non-Javadoc)
     * @see superCAD.Raw#writeFooter()
     */
    @Override
    protected void writeFooter() {
        dataWriter.flush();
        dataWriter.close();
        System.out.println(SuperCAD.tag + "PovRAY2 (Done)");
    }

    /* (non-Javadoc)
     * @see superCAD.Raw#writeLine()
     */
    @Override
    protected void writeLine(int index1, int index2) {
        StringBuilder sb = new StringBuilder(200);
        sb.append("my_line (");
        obj.toPovVec3D(sb, vertices[index1]);
        sb.append(',');
        obj.toPovVec3D(sb, vertices[index2]);
        sb.append(")\n");
        dataWriter.append(sb);
    }

    protected void writeLine2(int index1, int index2) {
        StringBuilder sb = new StringBuilder(100);
        sb.append("my_line (");
        obj.toPovVec3D(sb, vertices[index1]);
        sb.append(',');
        obj.toPovVec3D(sb, vertices[index2]);
        dataWriter.append(")\n");
    }

    /* (non-Javadoc)
     * @see superCAD.Raw#writeTriangle()
     */
    @Override
    protected void writeTriangle() {
        StringBuilder sb = new StringBuilder(300);
        sb.append("my_triangle (");
        obj.toPovVec3D(sb, vertices[0]);
        sb.append(',');
        obj.toPovVec3D(sb, vertices[1]);
        sb.append(',');
        obj.toPovVec3D(sb, vertices[2]);
        dataWriter.append(sb.append(", ").append("Texture1 )\n"));
        vertexCount = 0;
    }

    //TODO: As said, dirty header is dirty. It should be moved to an external file.
    //    but now, I avoid file access problems...
    private void writeDirtyHeader() {

        StringBuilder sb = new StringBuilder(700);
        obj.version(sb, 3.7);
        //obj.global(sb, 1.0);
        obj.globalRadiosity(sb);
        String[] includes = {"colors.inc", "textures.inc", "functions.inc"};
        obj.includes(sb, includes);
        obj.aspectRatio(sb, width, height);
        obj.fov(sb, 0.5235988);              // fov of 30 degrees in radians
        obj.zdepth(sb, height, 0.5235988);
        obj.declareCamera(sb, "default_camera");
        obj.declareColor(sb, "Color0", 0.8, 0.1, 0.1);
        obj.declareColor(sb, "Color1", 0.8, 0.1, 0.1);
        obj.declarePigment(sb, "RedP", "Color0");
        obj.declarePigment(sb, "BlueP", "Color1");
        obj.declareFinish(sb, "Finish0", 0.1, 0.8);
        obj.declareFinish(sb, "Finish1", 0.5, 0.5, 0.5, 0.5);
        obj.declareTexture(sb, "Texture0", "BlueP", "Finish0");
        povWriter.append(obj.declareTexture(sb, "Texture1", "RedP", "Finish1"));
        povWriter.println("//  ------------------------------------------------------------");
        povWriter.println("//  ------------------------------------------------------------");
        povWriter.println("//\t --( TEXTURES )--");
        povWriter.println("//  ------------------------------------------------------------");
        povWriter.println("");
        povWriter.println("#declare GrayCaro =\ttexture{ pigment{checker color rgb<1,1,1>*1.2 color rgb<0.25,0.15,0.1> scale <1,1,1>*2}");
        povWriter.println("\t\t\t\t\t//normal {bumps 0.75 scale 0.025}");
        povWriter.println("\t\t\t\t\tfinish {ambient 0.1 diffuse 0.3}");
        povWriter.println("\t\t\t\t\t}");
        povWriter.println("");
        povWriter.println("#declare GrayCaroR =\ttexture{ pigment{checker color rgb<1,1,1>*1.2 color rgb<0.25,0.15,0.1> scale <1,1,1>*2}");
        povWriter.println("\t\t\t\t\t//normal {bumps 0.05 scale 0.02}");
        povWriter.println("\t\t\t\t\tfinish {ambient 0.1 diffuse 0.8 reflection { 0.2 }} // REFLEX-------(oo)");
        povWriter.println("\t\t\t\t\t}");
        povWriter.println("");
        povWriter.println("#declare WhiteR =\ttexture{ pigment{color rgb<.9,.9,.8>}");
        povWriter.println("\t\t\t\t\t//normal {bumps 0.05 scale 0.02}");
        povWriter.println("\t\t\t\t\tfinish {ambient 0.1 diffuse 0.7 reflection { 0.2 }} // REFLEX-------(oo)");
        povWriter.println("\t\t\t\t\t}");
        povWriter.println("");
        povWriter.println("#declare GrayCaroS =\t   texture{ pigment{checker color rgb<1,1,1>*1.2 color rgb<0.25,0.15,0.1> scale <1,1,1>*0.1}");
        povWriter.println("\t\t\t\t\t//normal {bumps 0.75 scale 0.025}");
        povWriter.println("\t\t\t\t\tfinish {ambient 0.1 diffuse 0.8}");
        povWriter.println("\t\t\t\t\t}");
        povWriter.println("");
        povWriter.println("#declare GrayNoise =\t   texture{ pigment{color rgb<0.7,0.7,0.7>}");
        povWriter.println("\t\t\t\tnormal {bumps 0.75 scale 0.03}");
        povWriter.println("\t\t\t\t\tfinish {ambient 0.1 diffuse 0.8}");
        povWriter.println("\t\t\t\t\t}");
        povWriter.println("");
        povWriter.println("");
        povWriter.println("");
        povWriter.println("#declare BeigeC = pigment {color rgb<1,0.90,0.6>}");
        povWriter.println("#declare BeigeLight = pigment{color rgb<1,0.95,0.8>}");
        povWriter.println("#declare Brun = pigment{color  rgb<1,0.75,0.4>*0.3}");
        povWriter.println("#declare BeigeCc = pigment {BeigeC}");
        povWriter.println("");
        povWriter.println("");
        povWriter.println("#declare Beige =\t   texture{ pigment{BeigeCc}");
        povWriter.println("\t\t\t\t\tfinish {ambient 0.1");
        povWriter.println("\t\t\t\t\t\tdiffuse 0.9");
        povWriter.println("\t\t\t\t\t\tbrilliance 0.5");
        povWriter.println("\t\t\t\t\t\tphong 0.5");
        povWriter.println("\t\t\t\t\t\tphong_size 60");
        povWriter.println("\t\t\t\t\t\tspecular 0.5");
        povWriter.println("\t\t\t\t\t\troughness 0.05");
        povWriter.println("\t\t\t\t\t\treflection { 0.1 }");
        povWriter.println("\t\t\t\t\t\t}");
        povWriter.println("\t\t\t\t\t}");
        povWriter.println("// Beige LOWRes");
        povWriter.println("#declare BeigeLw =\t   texture{ pigment{BeigeCc}");
        povWriter.println("\t\t\t\t\tfinish {ambient 0.1");
        povWriter.println("\t\t\t\t\t\tdiffuse 0.9");
        povWriter.println("\t\t\t\t\t\t//brilliance 0.5");
        povWriter.println("\t\t\t\t\t\tphong 0.3");
        povWriter.println("\t\t\t\t\t\tphong_size 3");
        povWriter.println("\t\t\t\t\t\t//specular 0.5");
        povWriter.println("\t\t\t\t\t\t//roughness 0.05");
        povWriter.println("\t\t\t\t\t\t//reflection { 0.1 }");
        povWriter.println("\t\t\t\t\t\t}");
        povWriter.println("\t\t\t\t\t}");
        povWriter.println("");
        povWriter.println("#declare RedT =\t\ttexture{ pigment{color rgbf<1,0.5,0.1,0.5>}");
        povWriter.println("\t\t\t\t\tfinish {ambient 1");
        povWriter.println("\t\t\t\t\t\tdiffuse 0.5");
        povWriter.println("\t\t\t\t\t\t//brilliance 0.5");
        povWriter.println("\t\t\t\t\t\t//phong 0.3");
        povWriter.println("\t\t\t\t\t\t//phong_size 3");
        povWriter.println("\t\t\t\t\t\tspecular 0.5");
        povWriter.println("\t\t\t\t\t\troughness 0.02");
        povWriter.println("\t\t\t\t\t\t//reflection { 0.1 }");
        povWriter.println("\t\t\t\t\t\t}");
        povWriter.println("\t\t\t\t\t}");
        povWriter.println("");
        povWriter.println("");
        povWriter.println("");
        povWriter.println("");
        povWriter.println("//  ------------------------------------------------------------");
        povWriter.println("//  ------------------------------------------------------------");
        povWriter.println("//\t --( ECLAIRAGES ET ENVIRONNEMENT )--");
        povWriter.println("//  ------------------------------------------------------------");
        povWriter.println("");
        povWriter.println("// --( SOLEIL )--");
        povWriter.println("light_source{<1500,2500,-2500> color White}");
        povWriter.println("");
        povWriter.println("// --( CIEL )--");
        povWriter.println("plane{<0,1,0>,1 hollow");
        povWriter.println("\t   texture{ pigment{ bozo turbulence 0.076");
        povWriter.println("\t\t\t color_map { [0.5 rgb <0.70, 0.70, 0.7>]");
        povWriter.println("\t\t\t\t\t [0.6 rgb <0.75,0.75,0.75>]");
        povWriter.println("\t\t\t\t\t [1.0 rgb <0.85,0.85,0.85>]}");
        povWriter.println("\t\t\t   }");
        povWriter.println("\t\tfinish {ambient 1 diffuse 0} }");
        povWriter.println("\t   scale 10000}");
        povWriter.println("");
        povWriter.println("// --( FOG )--");
        povWriter.println("fog{fog_type   2");
        povWriter.println("\tdistance   50");
        povWriter.println("\tcolor\t  White");
        povWriter.println("\tfog_offset 0.1");
        povWriter.println("\tfog_alt\t2.0");
        povWriter.println("\tturbulence 0.8}");
        povWriter.println("");
        povWriter.println("// --( SOL )--");
        povWriter.println("#declare Plane_1 = plane{ <0,1,0>, 0");
        povWriter.println("\t   texture{ GrayCaroR }");
        povWriter.println("\t }");
        povWriter.println("");
        povWriter.println("#declare Plane_0 = plane { <0,1,0>, 0");
        povWriter.println("\ttexture{ GrayNoise }");
        povWriter.println("\t  }");
        povWriter.println("");
        povWriter.println("#declare Plane_2 = plane { <0,1,0>, -1");
        povWriter.println("\ttexture{ WhiteR }");
        povWriter.println("\t  }");
        povWriter.println("");
        povWriter.println("");
        povWriter.println("Plane_2");
        povWriter.println("");
        povWriter.println("");
        povWriter.println("");
        povWriter.println("");
        povWriter.println("//  ------------------------------------------------------------");
        povWriter.println("//  ------------------------------------------------------------");
        povWriter.println("//\t --( ECLAIRAGES ET ENVIRONNEMENT )--");
        povWriter.println("//  ------------------------------------------------------------");
        povWriter.println("");
        povWriter.println("#declare NODE = 1;");
        povWriter.println("");
        povWriter.println("");
        povWriter.println("//  ------------------------------------------------------------");
        povWriter.println("//  ------------------------------------------------------------");
        povWriter.println("//\t --( P5 draw starts here: )--");
        povWriter.println("//  ------------------------------------------------------------");
        povWriter.println("#declare P5W = .1;\t\t //change this for wireframe size");
        povWriter.println("");
        sb = new StringBuilder(300);
        obj.macro(sb,
                "my_line",
                "x1, y1, z1, x2, y2, z2", // these are macro parameters
                "blob { threshold 0.65 cylinder { <x1, y1, z1>,<x2, y2, z2>, P5W 1 pigment{ Color0 } texture{ Texture0 } } }"
        );
        obj.macro(sb,
                "my_triangle",
                "x1, y1, z1, x2, y2, z2, x3, y3, z3, texture0", // these are macro parameters
                "triangle{<x1, y1, z1>,<x2, y2,z2>,<x3, y3, z3> \n"
                + "     texture{ texture0 } } "
        );
        obj.include(sb, dataFile.getAbsolutePath());
        povWriter.println(sb);
        povWriter.flush();
        povWriter.close();
    }

    

}

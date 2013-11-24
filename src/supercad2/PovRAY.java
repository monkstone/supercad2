package supercad2;

/**
 * superCAD by Guillaume LaBelle (gll@spacekit.ca)
 * http://LaBelle.spaceKIT.ca/superCAD
 * 
 * This code is provided as is, without any warranty. 
 */


public class PovRAY extends Raw {

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
    System.out.println(SuperCAD.tag+"PovRAY (Done)");
  }

  /* (non-Javadoc)
   * @see superCAD.Raw#writeLine()
   */
  @Override
  protected void writeLine(int index1, int index2) {
    writer.print("blob { threshold .65 cylinder {");
    writer.print(toPovRAYAsPoint3D(vertices[index1])+",");   
    writer.print(toPovRAYAsPoint3D(vertices[index2])); 
    writer.println(",P5W 1   pigment { color rgb <1.0, 0.9, 0.8> }  texture{BeigeLwD} } }");

  }
  
  protected void writeLine2(int index1, int index2) {
    writer.print("cylinder {");
    writer.print(toPovRAYAsPoint3D(vertices[index1])+",");   
    writer.print(toPovRAYAsPoint3D(vertices[index2])); 
    writer.println(",P5W texture{BeigeLwD} }");

  }
  
  
  private String toPovRAYAsPoint3D(float[] vertex){
    return "<"+toStringComa(vertex)+">";
  }   

    /**
     *
     * @param vertex
     * @return
     */
    @Override
  protected String toStringComa(float[] vertex){
    return (float)(vertex[X])+","+(float)(-vertex[Y])+","+(float)(-vertex[Z]);
  }
  
  

  
  /* (non-Javadoc)
   * @see superCAD.Raw#writeTriangle()
   */
  @Override
  protected void writeTriangle() {
    writer.print("triangle {");
    writer.print(toPovRAYAsPoint3D(vertices[0])+",");
    writer.print(toPovRAYAsPoint3D(vertices[1])+",");   
    writer.print(toPovRAYAsPoint3D(vertices[2]));   
    writer.println(" texture{BeigeT}}");
    vertexCount = 0;
  }
  
  //TODO: As said, dirty header is dirty. It should be moved to an external file.
  //    but now, I avoid file access problems...
  private void writeDirtyHeader(){
    writer.println("// Persistence of Vision Ray Tracer Scene Description File");
    writer.println("// File: output.pov");
    writer.println("// Vers: 3.7");
    writer.println("// Desc: [PovRAY exporter] superCAD");
    writer.println("// Date: Hiver 2003");
    writer.println("//   Hiver 2013");
    writer.println("// Auth: Processing.org [exported by superCAD]");
    //writer.println("// Mail: gll@spacekit.ca");
    writer.println("");
    writer.println("#debug \"\\n\\n\"");
    writer.println("#debug \"\\n\\n\"");
    writer.println("");
    writer.println("#debug concat( \"\\n--------------------------------------------------------\\n\",");
    writer.println("\t\t   \"Clk-(\",str(clock,0, 3),\"/\",str(final_clock,0, 3),\")-\\n\",");
    writer.println("\t\t   \"Frm-(\",str(frame_number,0, 3),\"/\",str(final_frame,0, 3),\")-\\n\",");
    writer.println("\t\t   \"--------------------------------------------------------\\n\\n\")");
    writer.println("");
    writer.println("");
    writer.println("");
    writer.println("// [HISTORY]");
    writer.println("");
    writer.println("// V0.00a");
    writer.println("");
    writer.println("");
    writer.println("");
    writer.println("//  ------------------------------------------------------------");
    writer.println("//  ------------------------------------------------------------");
    writer.println("//\t --( PREPROCESSOR )--");
    writer.println("//  ------------------------------------------------------------");
    writer.println("");
    writer.println("//--------------------------------------------------------------------------");
    writer.println("#version 3.7;");
    writer.println("global_settings {  assumed_gamma 1.0 }");
    writer.println("//--------------------------------------------------------------------------");
    writer.println("");
    writer.println("#include \"colors.inc\"");
    writer.println("#include \"textures.inc\"");
    writer.println("#include \"glass.inc\"");
    writer.println("#include \"metals.inc\"");
    writer.println("#include \"golds.inc\"");
    writer.println("#include \"stones.inc\"");
    writer.println("#include \"woods.inc\"");
    writer.println("#include \"shapes.inc\"");
    writer.println("#include \"shapes2.inc\"");
    writer.println("#include \"functions.inc\"");
    writer.println("");
    writer.println("");
    writer.println("");
    writer.println("//  ------------------------------------------------------------");
    writer.println("//  ------------------------------------------------------------");
    writer.println("//\t --( CAMERA )--");
    writer.println("//  ------------------------------------------------------------");
    writer.println("");
    writer.println("#declare Camera_0 = camera {perspective angle 60\t  // front view");
    String location = "\t\t\t\tlocation  <0.0, 0.0 , %.3f>";
    writer.println(String.format(location, (-height / 2.0) / Math.tan(PI * 30.0 / 180.0)));
    //writer.println("\t\t\t\tlocation  <0.0 , 200.0 ,-600.0>");
    String right = "\t\t\t\tright\t x * %.4f";
    writer.println(String.format(right, (float)width / height));
    //writer.println("\t\t\t\tright\t x*image_width/image_height");
    writer.println("\t\t\t\tlook_at   <0.0, 0.0, 0.0>}");
    writer.println("#declare Camera_1 = camera {ultra_wide_angle angle 90\t   // diagonal view");
    writer.println("\t\t\t\tlocation  <200.0, 200.5,-300.0>");
    writer.println("\t\t\t\tright\t x*image_width/image_height");
    writer.println("\t\t\t\tlook_at   <0.0 , 1.0 , 0.0>}");
    writer.println("#declare Camera_2 = camera {ultra_wide_angle angle 90\t  //right side view");
    writer.println("\t\t\t\tlocation  <300.0 , 100.0 , 0.0>");
    writer.println("\t\t\t\tright\t x*image_width/image_height");
    writer.println("\t\t\t\tlook_at   <0.0 , 1.0 , 0.0>}");
    writer.println("#declare Camera_3 = camera {ultra_wide_angle angle 90\t\t// top view");
    writer.println("\t\t\t\tlocation  <0.0 , 300.0 ,-0.001>");
    writer.println("\t\t\t\tright\t x*image_width/image_height");
    writer.println("\t\t\t\tlook_at   <0.0 , 1.0 , 0.0>}");
    writer.println("camera{Camera_0}");
    writer.println("");
    writer.println("");
    writer.println("//  ------------------------------------------------------------");
    writer.println("//  ------------------------------------------------------------");
    writer.println("//\t --( TEXTURES )--");
    writer.println("//  ------------------------------------------------------------");
    writer.println("");
    writer.println("#declare GrayCaro =\ttexture{ pigment{checker color rgb<1,1,1>*1.2 color rgb<0.25,0.15,0.1> scale <1,1,1>*2}");
    writer.println("\t\t\t\t\t//normal {bumps 0.75 scale 0.025}");
    writer.println("\t\t\t\t\tfinish {ambient 0.1 diffuse 0.3}");
    writer.println("\t\t\t\t\t}");
    writer.println("");
    writer.println("#declare GrayCaroR =\ttexture{ pigment{checker color rgb<1,1,1>*1.2 color rgb<0.25,0.15,0.1> scale <1,1,1>*2}");
    writer.println("\t\t\t\t\t//normal {bumps 0.05 scale 0.02}");
    writer.println("\t\t\t\t\tfinish {ambient 0.1 diffuse 0.8 reflection { 0.2 }} // REFLEX-------(oo)");
    writer.println("\t\t\t\t\t}");
    writer.println("");
    writer.println("#declare WhiteR =\ttexture{ pigment{color rgb<.9,.9,.8>}");
    writer.println("\t\t\t\t\t//normal {bumps 0.05 scale 0.02}");
    writer.println("\t\t\t\t\tfinish {ambient 0.1 diffuse 0.7 reflection { 0.2 }} // REFLEX-------(oo)");
    writer.println("\t\t\t\t\t}");
    writer.println("");
    writer.println("#declare GrayCaroS =\t   texture{ pigment{checker color rgb<1,1,1>*1.2 color rgb<0.25,0.15,0.1> scale <1,1,1>*0.1}");
    writer.println("\t\t\t\t\t//normal {bumps 0.75 scale 0.025}");
    writer.println("\t\t\t\t\tfinish {ambient 0.1 diffuse 0.8}");
    writer.println("\t\t\t\t\t}");
    writer.println("");
    writer.println("#declare GrayNoise =\t   texture{ pigment{color rgb<0.7,0.7,0.7>}");
    writer.println("\t\t\t\tnormal {bumps 0.75 scale 0.03}");
    writer.println("\t\t\t\t\tfinish {ambient 0.1 diffuse 0.8}");
    writer.println("\t\t\t\t\t}");
    writer.println("");
    writer.println("");
    writer.println("");
    writer.println("#declare BeigeC = pigment {color rgb<1,0.90,0.6>}");
    writer.println("#declare BeigeLight = pigment{color rgb<1,0.95,0.8>}");
    writer.println("#declare Brun = pigment{color  rgb<1,0.75,0.4>*0.3}");
    writer.println("#declare BeigeCc = pigment {BeigeC}");
    writer.println("");
    writer.println("");
    writer.println("#declare Beige =\t   texture{ pigment{BeigeCc}");
    writer.println("\t\t\t\t\tfinish {ambient 0.1");
    writer.println("\t\t\t\t\t\tdiffuse 0.9");
    writer.println("\t\t\t\t\t\tbrilliance 0.5");
    writer.println("\t\t\t\t\t\tphong 0.5");
    writer.println("\t\t\t\t\t\tphong_size 60");
    writer.println("\t\t\t\t\t\tspecular 0.5");
    writer.println("\t\t\t\t\t\troughness 0.05");
    writer.println("\t\t\t\t\t\treflection { 0.1 }");
    writer.println("\t\t\t\t\t\t}");
    writer.println("\t\t\t\t\t}");
    writer.println("// Beige LOWRes");
    writer.println("#declare BeigeLw =\t   texture{ pigment{BeigeCc}");
    writer.println("\t\t\t\t\tfinish {ambient 0.1");
    writer.println("\t\t\t\t\t\tdiffuse 0.9");
    writer.println("\t\t\t\t\t\t//brilliance 0.5");
    writer.println("\t\t\t\t\t\tphong 0.3");
    writer.println("\t\t\t\t\t\tphong_size 3");
    writer.println("\t\t\t\t\t\t//specular 0.5");
    writer.println("\t\t\t\t\t\t//roughness 0.05");
    writer.println("\t\t\t\t\t\t//reflection { 0.1 }");
    writer.println("\t\t\t\t\t\t}");
    writer.println("\t\t\t\t\t}");
    writer.println("// Beige LOW DARK");
    writer.println("#declare BeigeLwD =\t   texture{ pigment{Brun}");
    writer.println("\t\t\t\t\tfinish {ambient 0.1");
    writer.println("\t\t\t\t\t\tdiffuse 0.9");
    writer.println("\t\t\t\t\t\t//brilliance 0.5");
    writer.println("\t\t\t\t\t\tphong 0.3");
    writer.println("\t\t\t\t\t\tphong_size 3");
    writer.println("\t\t\t\t\t\t//specular 0.5");
    writer.println("\t\t\t\t\t\t//roughness 0.05");
    writer.println("\t\t\t\t\t\t//reflection { 0.1 }");
    writer.println("\t\t\t\t\t\t}");
    writer.println("\t\t\t\t\t}");
    writer.println("");
    writer.println("");
    writer.println("");
    writer.println("#declare BeigeT =\t\ttexture{ pigment{color rgbf<1,0.95,0.8,0.7>}");
    writer.println("\t\t\t\t\tfinish {ambient 0.1");
    writer.println("\t\t\t\t\t\tdiffuse 0.5");
    writer.println("\t\t\t\t\t\tbrilliance 10");
    writer.println("\t\t\t\t\t\t//phong 0.3");
    writer.println("\t\t\t\t\t\t//phong_size 3");
    writer.println("\t\t\t\t\t\tspecular 0.7");
    writer.println("\t\t\t\t\t\troughness 0.1");
    writer.println("\t\t\t\t\t\t//reflection { 0.1 }");
    writer.println("\t\t\t\t\t\t}");
    writer.println("\t\t\t\t\t}");
    writer.println("");
    writer.println("");
    writer.println("#declare RedT =\t\ttexture{ pigment{color rgbf<1,0.5,0.1,0.5>}");
    writer.println("\t\t\t\t\tfinish {ambient 1");
    writer.println("\t\t\t\t\t\tdiffuse 0.5");
    writer.println("\t\t\t\t\t\t//brilliance 0.5");
    writer.println("\t\t\t\t\t\t//phong 0.3");
    writer.println("\t\t\t\t\t\t//phong_size 3");
    writer.println("\t\t\t\t\t\tspecular 0.5");
    writer.println("\t\t\t\t\t\troughness 0.02");
    writer.println("\t\t\t\t\t\t//reflection { 0.1 }");
    writer.println("\t\t\t\t\t\t}");
    writer.println("\t\t\t\t\t}");
    writer.println("");
    writer.println("");
    writer.println("");
    writer.println("");
    writer.println("//  ------------------------------------------------------------");
    writer.println("//  ------------------------------------------------------------");
    writer.println("//\t --( ECLAIRAGES ET ENVIRONNEMENT )--");
    writer.println("//  ------------------------------------------------------------");
    writer.println("");
    writer.println("// --( SOLEIL )--");
    writer.println("light_source{<1500,2500,-2500> color White}");
    writer.println("");
    writer.println("// --( CIEL )--");
    writer.println("plane{<0,1,0>,1 hollow");
    writer.println("\t   texture{ pigment{ bozo turbulence 0.076");
    writer.println("\t\t\t color_map { [0.5 rgb <0.70, 0.70, 0.7>]");
    writer.println("\t\t\t\t\t [0.6 rgb <0.75,0.75,0.75>]");
    writer.println("\t\t\t\t\t [1.0 rgb <0.85,0.85,0.85>]}");
    writer.println("\t\t\t   }");
    writer.println("\t\tfinish {ambient 1 diffuse 0} }");
    writer.println("\t   scale 10000}");
    writer.println("");
    writer.println("// --( FOG )--");
    writer.println("fog{fog_type   2");
    writer.println("\tdistance   50");
    writer.println("\tcolor\t  White");
    writer.println("\tfog_offset 0.1");
    writer.println("\tfog_alt\t2.0");
    writer.println("\tturbulence 0.8}");
    writer.println("");
    writer.println("// --( SOL )--");
    writer.println("#declare Plane_1 = plane{ <0,1,0>, 0");
    writer.println("\t   texture{ GrayCaroR }");
    writer.println("\t }");
    writer.println("");
    writer.println("#declare Plane_0 = plane { <0,1,0>, 0");
    writer.println("\ttexture{ GrayNoise }");
    writer.println("\t  }");
    writer.println("");
    writer.println("#declare Plane_2 = plane { <0,1,0>, -1");
    writer.println("\ttexture{ WhiteR }");
    writer.println("\t  }");
    writer.println("");
    writer.println("");
    writer.println("Plane_2");
    writer.println("");
    writer.println("");
    writer.println("");
    writer.println("");
    writer.println("//  ------------------------------------------------------------");
    writer.println("//  ------------------------------------------------------------");
    writer.println("//\t --( ECLAIRAGES ET ENVIRONNEMENT )--");
    writer.println("//  ------------------------------------------------------------");
    writer.println("");
    writer.println("#declare NODE = 1;");
    writer.println("");
    writer.println("");
    writer.println("//  ------------------------------------------------------------");
    writer.println("//  ------------------------------------------------------------");
    writer.println("//\t --( P5 draw starts here: )--");
    writer.println("//  ------------------------------------------------------------");
    writer.println("#declare P5W = .1;\t\t //change this for wireframe size");
    writer.println("");
  }



}

package supercad2;

/**
 * superCAD by Guillaume LaBelle (gll@spacekit.ca)
 * http://LaBelle.spaceKIT.ca/superCAD
 * 
 * This code is provided as is, without any warranty. 
 */

public class SketchUP extends Raw {

  /* (non-Javadoc)
   * @see superCAD.Raw#writeHeader()
   */
  @Override
  protected void writeHeader() {
    String out = "#! /usr/bin/ruby -w\n#\n#Exported by superCAD\n";
    
    out +="#superCAD exporter for SketchUP\n";
        
    //GLOBAL HERE
    
    out += "model = Sketchup.active_model\n";
    out += "layers = model.layers\n";
    out += "layers.add \"OOGLayer\"\n";
    out += "activelayer = model.active_layer=layers[1]\n";
    out += "layer=model.active_layer\n\n";
    out += "entities = model.active_entities\n";
    
    writer.println(out);
  }

  /* (non-Javadoc)
   * @see superCAD.Raw#writeFooter()
   */
  @Override
  protected void writeFooter() {
    writer.println("\n####END OF FILE");
    System.out.println(SuperCAD.tag+"SketchUP (Done)");
  }

  /* (non-Javadoc)
   * @see superCAD.Raw#writeLine()
   */
  @Override
  protected void writeLine(int index1, int index2) {
    writer.println("entities.add_edges [");
    writer.println("\t"+toSketchUpAsPoint3D(vertices[index1])+",");
    writer.println("\t"+toSketchUpAsPoint3D(vertices[index2]));
    writer.println("]");

  }
  
  private String toSketchUpAsPoint3D(float[] vertex){
    return "Geom::Point3d.new("+toStringComa(vertex)+")";
  }   

  /* (non-Javadoc)
   * @see superCAD.Raw#writeTriangle()
   */
  @Override
  protected void writeTriangle() {
    writer.println("entities.add_face [");
    writer.println("\t"+toSketchUpAsPoint3D(vertices[0])+",");
    writer.println("\t"+toSketchUpAsPoint3D(vertices[1])+",");   
    writer.println("\t"+toSketchUpAsPoint3D(vertices[2]));   
    writer.println("]");
    vertexCount = 0;
  }


}

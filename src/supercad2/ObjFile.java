package supercad2;

/**
 * superCAD by Guillaume LaBelle (gll@spacekit.ca)
 * http://LaBelle.spaceKIT.ca/superCAD
 * 
 * This code is provided as is, without any warranty. 
 */

public class ObjFile extends Raw {

  /* (non-Javadoc)
   * @see superCAD.Raw#writeHeader()
   */
  @Override
  protected void writeHeader() {   
    writer.println("#Exported with superCAD");
    writer.println("#http://labelle.spacekit.ca/supercad");
    writer.println("#http://anar.ch");
    writer.println("\n");
    writer.println("o superCAD");
  }

  /* (non-Javadoc)
   * @see superCAD.Raw#writeFooter()
   */
  @Override
  protected void writeFooter() {
    writer.println("\n\n# END OF FILE");
    System.out.println(SuperCAD.tag+"ObjFile (Done)");
  }

  /* (non-Javadoc)
   * @see superCAD.Raw#writeLine()
   */
  @Override
  protected void writeLine(int index1, int index2){

    writer.println(toObj(vertices[index1]));
    writer.println(toObj(vertices[index2]));
    writer.println("l -2 -1\n");
  }

  // public void newLayer(String layerName){
  // writer.println("Command");
  // writer.println("");
  // }


  private String toObj(float[] vertex){
    return "v "+toStringComa(vertex);
  }   
  
  protected String toStringComa(float[] vertex){
    return (float)vertex[X]+" "+(float)vertex[Y]+" "+(float)vertex[Z];
  }
    

  /* (non-Javadoc)
   * @see superCAD.Raw#writeTriangle()
   */
  @Override
  protected void writeTriangle() {
    writer.println(toObj(vertices[0]));
    writer.println(toObj(vertices[1]));
    writer.println(toObj(vertices[2]));
    writer.println("f -3 -2 -1\n");
    vertexCount = 0;
  }


}

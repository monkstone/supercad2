package supercad2;

/**
 * superCAD by Guillaume LaBelle + Julien Nembrini
 * http://LaBelle.spaceKIT.ca/superCAD
 * 
 * This code is provided as is, without any warranty. 
 */

public class Maya extends Raw {

  /* (non-Javadoc)
   * @see superCAD.Raw#writeHeader()
   */
  @Override
  protected void writeHeader() {
    writer.println("print (\"superCAD: Maya (Starting)\");");
  }

  /* (non-Javadoc)
   * @see superCAD.Raw#writeFooter()
   */
  @Override
  protected void writeFooter() {
    writer.println("print (\"superCAD: Maya (Done)\")");
    System.out.println(SuperCAD.tag+"Maya (Done)");
  }

  /* (non-Javadoc)
   * @see superCAD.Raw#writeLine()
   */
  @Override
  protected void writeLine(int index1, int index2) {
      writer.print("curve -d 1 ");
      writer.print("-p "+toMaya(vertices[index1])+" ");
      writer.print("-p "+toMaya(vertices[index2])+";\n");
  }
  
  
//  public void newLayer(String layerName){
//    writer.println("Command");
//    writer.println("");
//  }
  
  private String toMaya(float[] vertex){
    return toStringComa(vertex);
  }   
  
  protected String toStringComa(float[] vertex){
    return (float)vertex[X]+" "+(float)vertex[Y]+" "+(float)vertex[Z];
  }
    

  /* (non-Javadoc)
   * @see superCAD.Raw#writeTriangle()
   */
  @Override
  protected void writeTriangle() {
    writer.print("polyCreateFacet ");
    writer.print("-p "+toMaya(vertices[0])+" ");
    writer.print("-p "+toMaya(vertices[1])+" ");
    writer.print("-p "+toMaya(vertices[2])+";\n");
    vertexCount = 0;
  }


}

package supercad2;

/**
 * superCAD by Julien Nembrini
 * http://LaBelle.spaceKIT.ca/superCAD
 * 
 * This code is provided as is, without any warranty. 
 */

public class ArchiCAD extends Raw {

  /* (non-Javadoc)
   * @see superCAD.Raw#writeHeader()
   */
  @Override
  protected void writeHeader(){
    writer.println("PRINT \""+SuperCAD.tag+"ArchiCAD (Starting)\"");
  }

  /* (non-Javadoc)
   * @see superCAD.Raw#writeFooter()
   */
  @Override
  protected void writeFooter(){
    writer.println("PRINT \""+SuperCAD.tag+"ArchiCAD (Done)\"");
    System.out.println(SuperCAD.tag+"ArchiCAD (Done)");
  }

  /* (non-Javadoc)
   * @see superCAD.Raw#writeLine()
   */
  @Override
  protected void writeLine(int index1, int index2){
    // LIN_ x1, y1, z1, x2, y2, z2
    writer.print("LIN_ ");
    writer.print(toArchiCAD(vertices[index1])+", ");
    writer.print(toArchiCAD(vertices[index2])+"\n");
  }

  private String toArchiCAD(float[] vertex){
    return toStringComa(vertex);
  }

  protected String toStringComa(float[] vertex){
    return (float)vertex[X]+", "+(float)vertex[Y]+", "+(float)vertex[Z];
  }

  /* (non-Javadoc)
   * @see superCAD.Raw#writeTriangle()
   */
  @Override
  protected void writeTriangle(){
    // PLANE n, x1, y1, z1, ... xn, yn, zn
    writer.print("PLANE 3, ");
    writer.print(toArchiCAD(vertices[0])+", ");
    writer.print(toArchiCAD(vertices[1])+", ");
    writer.print(toArchiCAD(vertices[2])+"\n");
    vertexCount = 0;
  }


}

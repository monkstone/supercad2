package supercad2;

/**
 * superCAD by Guillaume LaBelle (gll@spacekit.ca)
 * http://LaBelle.spaceKIT.ca/superCAD
 * 
 * This code is provided as is, without any warranty. 
 */

public class Rhino extends Raw {

  /* (non-Javadoc)
   * @see superCAD.Raw#writeHeader()
   */
  @Override
  protected void writeHeader(){
    String out = "Option Explicit\n";
    out += "'CADExporter for Rhino\n";

    // GLOBAL HERE

    out += "\nDim x\n";
    out += "Dim y\n\n";

    out += "Call Main()\n";
    out += "Sub Main()\n";
    out += "\tCall rhino.EnableRedraw(False)\n";

    writer.println(out);
  }

  /* (non-Javadoc)
   * @see superCAD.Raw#writeFooter()
   */
  @Override
  protected void writeFooter(){
    writer.println("\n\tCall rhino.EnableRedraw(True)");  //Dimitry Demin proposal
    writer.println("\nEnd Sub");
    System.out.println(SuperCAD.tag+"Rhino (Done)");
  }

  /* (non-Javadoc)
   * @see superCAD.Raw#writeLine()
   */
  @Override
  protected void writeLine(int index1, int index2){
    writer.print("x = Array(");
    writer.print("\t"+toRhinoAsPoint3D(vertices[index1])+",");
    writer.print("\t"+toRhinoAsPoint3D(vertices[index2])+")");
    writer.print("\ny = Rhino.AddPolyline(x)\n");

  }

  // public void newLayer(String layerName){
  // writer.println("Command");
  // writer.println("");
  // }
  
  private String toRhinoAsPoint3D(float[] vertex){
    return "Array("+toStringComa(vertex)+")";
  }

  /* (non-Javadoc)
   * @see superCAD.Raw#writeTriangle()
   */
  @Override
  protected void writeTriangle(){
    writer.print("x = Array(\t");
    writer.print("\t"+toRhinoAsPoint3D(vertices[0])+",");
    writer.print("\t"+toRhinoAsPoint3D(vertices[1])+",");
    writer.print("\t"+toRhinoAsPoint3D(vertices[2])+",");
    writer.print("\t"+toRhinoAsPoint3D(vertices[0])+")");
    writer.print("\ny = Rhino.AddPolyline(x)\n");
    writer.print("\tIf Not IsNull(y) Then rhino.MeshPolyline(y)"); //By Dimitry Demin (Thanks!)
    //writer.print("\trhino.MeshPolyline(y)\n");
    writer.print("\trhino.DeleteObject(y)\n");
    vertexCount = 0;
  }


}

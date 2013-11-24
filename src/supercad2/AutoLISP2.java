package supercad2;
import java.util.ArrayList;

/**
 * superCAD by Guillaume LaBelle (gll@spacekit.ca)
 * http://LaBelle.spaceKIT.ca/superCAD
 * 
 * This code is provided as is, without any warranty. 
 */


public class AutoLISP2 extends Raw {
  
  

  protected void writeHeader() {
    writer.println("(prompt \"----------------------------------------------------\\n\")" );
    writer.println("(prompt \"---------------------------------------(iLoveP5)----\\n\")" );
    writer.println("(prompt \"----------------------------------------------------\\n\")" );
    writer.println("");
    writer.println("(prompt \"\\nGuillaume Labelle [drawThis]   Version00 superCAD\")" );
    writer.println("(prompt \"\\n--PLEASE TURN OFF OSNAP BEFORE EXECUTION\")" );
    writer.println("(prompt \"\\n--TYPE [drawThis] to START\")" );
    writer.println("(setenv \"CmdHistLines\" \"4096\")" );
    writer.println("");
    writer.println("(defun c:drawThis ()");
    writer.println("");   
  }


  protected void writeFooter() {
    writer.println("");
    writer.println("(mlayer \"x (superCAD)\" \"1\" \"continuous\" )");
    writer.println("");
    writer.println(";*--( Exit )--");
    writer.println("");
    writer.println("(command \"_zoom\" \"E\" )");
    writer.println("");

    writer.println("");
    writer.println("; ---------- ReInitze ----------");
    writer.println("");
    writer.println("(prompt \"\\nGuillaume Labelle [drawThis]   Version00 superCAD - (DONE)\")" );
    writer.println("(setvar \"blipmode\" old_blipmode)" );
    writer.println("(setvar \"Cmdecho\" old_cmdecho)" );
    writer.println("(setenv \"CmdHistLines\" \"4096\")" );
    writer.println("(princ)" );
    writer.println(")" );

    writer.println("");
    writer.println("; ---------- Fonctions ----------");
    writer.println("");
    writer.println("(defun mlayer (name color ltype)" );
    writer.println("\t(command \"_-layer\" \"M\" name )");
    writer.println("\t(if color (command \"c\" color name ))");
    writer.println("\t(if ltype (command \"lt\" ltype name ))");
    writer.println("\t(command \"\")");
    writer.println("(princ)");
    writer.println(")" );
    System.out.println(SuperCAD.tag+"AutoLISP (Done)");
  }


  protected void writeLine(int index1, int index2) {
    writer.print("(Command  \"line\" ");
    writer.print(toAutoLISPAsPoint3D(vertices[index1]));   
    writer.print(toAutoLISPAsPoint3D(vertices[index2])); 
    writer.println(" \"\" )");

  }
  
  
  private String toAutoLISPAsPoint3D(float[] vertex){
    return " \""+toStringComa(vertex)+"\"";
  }   
  


  protected void writeTriangle() {
    writer.print("(Command  \"_3dpoly\" ");
    writer.print(toAutoLISPAsPoint3D(vertices[0]));
    writer.print(toAutoLISPAsPoint3D(vertices[1]));   
    writer.print(toAutoLISPAsPoint3D(vertices[2]));   
    writer.println(" \"c\" )");
    vertexCount = 0;
  }


  /* (non-Javadoc)
   * @see processing.core.PGraphics3D#box(float, float, float)
   */
  @Override
  // OPT this isn't the least bit efficient
  //     because it redraws lines along the vertices
  //     ugly ugly ugly!
  public void box(float w, float h, float d) {
    float x1 = -w/2f; float x2 = w/2f;
    float y1 = -h/2f; float y2 = h/2f;
    float z1 = -d/2f; float z2 = d/2f;

    float[] v1 = {x1,y1,z1};
    float[] v2 = {x2,y2,z2};
    
    writer.print("(Command  \"_Box\" ");
    writer.print(toAutoLISPAsPoint3D(v1));
    writer.print(toAutoLISPAsPoint3D(v2));
    writer.println(" \"\" )");
  }


  /* (non-Javadoc)
   * @see processing.core.PGraphics3D#translate(float, float, float)
   */
  @Override
  public void translate(float tx, float ty, float tz){
    float[] v1 = {0,0,0};
    float[] v2 = {tx,ty,tz};
    
    writer.print("(Command  \"_Move\" ");
    writer.print(toAutoLISPAsPoint3D(v1));
    writer.print(toAutoLISPAsPoint3D(v2));
    writer.println(" \"\" )");
  }
  

  /* (non-Javadoc)
   * @see processing.core.PGraphics3D#pushMatrix()
   */
  @Override
  public void pushMatrix(){
    // TODO Auto-generated method stub
    super.pushMatrix();
  }


  ArrayList<String> selections = new ArrayList<String>();
  ArrayList<String> transforms = new ArrayList<String>();
  /* (non-Javadoc)
   * @see processing.core.PGraphics3D#popMatrix()
   */
  @Override
  public void popMatrix(){
    // TODO Auto-generated method stub
    super.popMatrix();
  }
  
  
  
  

}
 

package supercad2;

/**
 * superCAD by Guillaume LaBelle (gll@spacekit.ca)
 * A modification of RawDXF for a CAD exporter inside processing.org.
 * RawDXF is part of the processing.org project (see original header bellow).
 * http://LaBelle.spaceKIT.ca/superCAD
 * 
 * Raw is an abstract class from which are derived all methods in superCAD. 
 * 
 * This code is provided as is, without any warranty. 
 */


/* 
 * RawDXF - Code to write DXF files with beginRaw/endRaw
 * An extension for the Processing project - http://processing.org
 * <p/>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Lesser General
 * Public License along with the Processing project; if not,
 * write to the Free Software Foundation, Inc., 59 Temple Place,
 * Suite 330, Boston, MA  02111-1307  USA
 */


import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.opengl.PGraphics3D;

public abstract class Raw extends PGraphics3D {

  int         currentLayer = 0;

  File        file;
  PrintWriter writer;

  public Raw(){}


  @Override
  public void setPath(String path){
    this.path = path;
    if(path!=null){
      file = new File(path);
      if( !file.isAbsolute())
        file = null;
    }
    if(file==null){
      throw new RuntimeException(SuperCAD.tag+"superCAD requires an absolute path "+"for the location of the output file.");
    }else{
      System.out.println(SuperCAD.tag+"<"+path+"> (open)");
    }
  }


  @Override
  protected void allocate(){
    setLayer(0);
  }


  @Override
  public void dispose(){
    writeFooter();

    writer.flush();
    writer.close();
    writer = null;
  }


  @Override
  public boolean displayable(){
    return false; // just in case someone wants to use this on its own
  }

  @Override
  public void beginDraw(){
    if(writer==null){
        try {
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
        } catch (UnsupportedEncodingException | FileNotFoundException ex) {
            Logger.getLogger(Raw.class.getName()).log(Level.SEVERE, null, ex);
        }

      writeHeader();
    }
  }


  @Override
  public void endDraw(){}

  public void setLayer(int layer){
    currentLayer = layer;
  }

  abstract void writeHeader();
  abstract void writeFooter();
  public void println(String what){
    writer.println(what);
  }


  abstract void writeLine(int index1, int index2);
  abstract void writeTriangle();

  @Override
  public void beginShape(int kind){
    shape = kind;

    if( (shape!=LINES)&& (shape!=TRIANGLES)&& (shape!=POLYGON)){
      String err = SuperCAD.tag+"superCAD can only be used with beginRaw(), "+"because it only supports lines and triangles"
          +"More to come in next updates... Stay tuned...";
      throw new RuntimeException(err);
    }

    if( (shape==POLYGON)&&fill){
      throw new RuntimeException(SuperCAD.tag+"superCAD only supports non-filled shapes.");
    }

    vertexCount = 0;
  }


  protected String toStringComa(float[] vertex){
    return (float)vertex[X]+","+(float)vertex[Y]+","+(float)vertex[Z];
  }


  @Override
  public void vertex(float x, float y){
    vertex(x,y,0);
  }


  @Override
  public void vertex(float x, float y, float z){
    float vertex[] = vertices[vertexCount];

    vertex[X] = x; // note: not mx, my, mz like PGraphics3
    vertex[Y] = y;
    vertex[Z] = z;

    if(fill){
      vertex[R] = fillR;
      vertex[G] = fillG;
      vertex[B] = fillB;
      vertex[A] = fillA;
    }

    if(stroke){
      vertex[SR] = strokeR;
      vertex[SG] = strokeG;
      vertex[SB] = strokeB;
      vertex[SA] = strokeA;
      vertex[SW] = strokeWeight;
    }

    if(textureImage!=null){ // for the future?
      vertex[U] = textureU;
      vertex[V] = textureV;
    }
    vertexCount++;

    if( (shape==LINES)&& (vertexCount==2)){
      writeLine(0,1);
      vertexCount = 0;

    }
    else
      if( (shape==TRIANGLES)&& (vertexCount==3)){
        writeTriangle();
      }
  }


  @Override
  public void endShape(int mode){
    if(shape==POLYGON){
      for (int i = 0; i<vertexCount-1; i++){
        writeLine(i,i+1);
      }
      if(mode==CLOSE){
        writeLine(vertexCount-1,0);
      }
    }

  }


  /**
   * @param layerName
   */
  public void newLayer(String layerName){
    System.err.println(SuperCAD.tag+"[newLayer] not implemented in this format");
  }

}
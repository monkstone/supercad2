package superCAD;

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
import processing.core.*;
import processing.opengl.PGraphics3D;

public abstract class Raw extends PGraphics3D {

  int         currentLayer = 0;

  File        file;
  PrintWriter writer;

  public Raw(){}


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


  protected void allocate(){
    setLayer(0);
  }


  public void dispose(){
    writeFooter();

    writer.flush();
    writer.close();
    writer = null;
  }


  public boolean displayable(){
    return false; // just in case someone wants to use this on its own
  }

  public void beginDraw(){
    if(writer==null){
      try{
        writer = new PrintWriter(new FileWriter(file));
      }
      catch (IOException e){
        throw new RuntimeException(e); // java 1.4+
      }
      writeHeader();
    }
  }


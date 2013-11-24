package supercad2;

import processing.core.PApplet;


/**
 * superCAD by Guillaume LaBelle (gll@spacekit.ca)
 * http://LaBelle.spaceKIT.ca/superCAD
 * 
 * This code is provided as is, without any warranty. 
 */

public class SuperCAD {
  
  static private PApplet p5; 
  static protected final String tag = "[SuperCAD]: ";

  public SuperCAD(){}  
  
  public SuperCAD(PApplet p5){
    this.p5 = p5;
  }
  
  static protected Raw              raw;
  static private SuperCAD           exporter;
  static public int                 exportMode            = 0;
  /** Default filenName for exports */
  static public String              fileName              = "output";
  static private int                recordedFrame         = -1;
  static public boolean             lazyMode              = true;
  
  static final protected String[][] tokens   = {
    {"Rhino", "rvb", "r"}, 
    {"SketchUP", "rb", "s"},
    {"AutoLISP", "lsp", "a"}, 
    {"PovRAY", "pov", "p"}, 
    {"Maya", "mel", "m"}, 
    {"ObjFile", "obj", "o"}, 
    {"ArchiCAD", "rvb", "r"}, 
    {"Rhino", "gdl", "c"}
  };
  
  static final public byte RHINO    = 0;
  static final public byte SKETCHUP = 1;
  static final public byte AUTOLISP = 2;
  static final public byte POVRAY   = 3;
  static final public byte MAYA     = 4;
  static final public byte OBJFILE  = 5;
  static final public byte ARCHICAD = 6;  
  
  static public void init(PApplet p5){
    exporter = new SuperCAD(p5);
    p5.registerPre(exporter);    
    p5.registerDraw(exporter);
    SuperCAD.formats();
  }
  
  static private PApplet getP5(){
    if(p5==null)
      System.err.println(tag+"You must add SuperCAD.init(this); into Processing's setup() method.");
    return p5;
  }  
  
  /**
   * Set a default filename for outputs.
   * @param fileName
   */
  static public void fileName(String fileName){
    SuperCAD.fileName = fileName;
  }

  /**
   * Change the exporter mode to various formats. You could use either the name
   * of the exporter (case is not important) Ex: "autolisp" or you could use the
   * extension Ex: "rvb" (for rhino) or you could use simply the Key Ex: "r" for
   * Rhino (same as mapped keyboard as SuperCAD.keyCheck();)
   * 
   * <pre>
   * [Exporter] [EXT] [KEY]
   * AutoLISP -- lsp -- a
   * PovRAY   -- pov -- p
   * Rhino    -- rvb -- r
   * SketchUP -- rb  -- s
   * Maya     -- mel -- m
   * ObjFile  -- obj -- o
   * ArchiCAD -- gdl -- c</pre>
   * @param exportMode
   */
  public static void mode(String exportMode){
    int id = parse(exportMode);
    if(id>=0)
      SuperCAD.exportMode = id;
    else
      SuperCAD.exportMode = 0;
  }  
  
  public static void mode(int exportMode){    
    SuperCAD.exportMode = exportMode;
  }
  
  static private int parse(String s){

    for (int i = 0; i<tokens.length; i++)
      for (int j = 0; j<tokens[i].length; j++)
        if(s.toLowerCase().equals(tokens[i][j].toLowerCase()))
          return i;

    System.err.println(tag+"<"+s+"> is not a recognized export format.");
    return -1;
  }  
  
  
  /////////////////////////////////////////////////////////////
  //LOOP MANIPULATIONS
  /////////////////////////////////////////////////////////////

  /**
   * Automatically starts recording at the beginning of a frame. Unless explicit
   * use of SuperCAD.begin(); By default, the recorder will record everything
   * drawn in the draw loop.
   */
  public void pre(){
    if(lazyMode)
      if(isRecording())
        record();
  }
  
  /**
   * Automatically close the file if not explicitly done.
   */  
  public void draw(){
    SuperCAD.stop();
  }    
  
  /**
   * Explicitly start the recorder. The recorder is always (unless you activated
   * the SuperCAD.contiousRecordingMode = true) turned off when SuperCAD.stop();
   * is called. This should be only helpful when someone wants to export many
   * files per draw loop.
   */
  public static void recordNextFrame(){
    recordedFrame = p5.frameCount+1;
  }
  
  /**
   * @param string
   */
  public static void recordNextFrame(String exportMode){
    mode(exportMode);
    recordNextFrame();
  }
  
  /** Start recording directly with SuperCAD.mode */
  public static void record(){
    recordWithFileName(fileName);
  }
  
  static private boolean isRecording(){
    return (recordedFrame == getP5().frameCount);
  }
  
  /**
   * Set to given exportMode and start recording directly. Used in draw() loop.
   * @param string
   */
  public static void record(String exportMode){
    mode(exportMode);
    recordWithFileName(fileName);
  }
  
  public static void record(String exportMode, String fileName){
    mode(exportMode);
    recordWithFileName(fileName);
  }
  
  static private void recordWithFileName(String fileName){
    raw = (Raw)getP5().beginRaw("superCAD."+tokens[exportMode][0],fileName+"."+tokens[exportMode][1]);
  }

  /**
   * begin the export loop. You could specify the name of the file by using
   * begin(fileName); otherwise the default SuperCAD.fileName will be used.
   */
  public static void loop(){
    if(isRecording())
      record();
  }
  
  public static void loop(String exportMode){
    if(isRecording())
        record(exportMode);
  }  
  
  public static void loop(String exportMode, String fileName){
    if(isRecording())
        record(exportMode,fileName);
  }  

  public static void stop(){
    //if(isRecording())
        getP5().endRaw();
  }

  /**
   * <pre>
   * [Exporter] [EXT] [KEY]
   * AutoLISP -- lsp -- a
   * PovRAY   -- pov -- p
   * Rhino    -- rvb -- r
   * SketchUP -- rb  -- s
   * Maya     -- mel -- m
   * ObjFile  -- obj -- o
   * ArchiCAD -- gdl -- c</pre>
   */
  public static void keyCheck(){
    //Check is valid key
    boolean isValid = false;
    for(int i=0; i<tokens.length; i++)
      if((getP5().key+"").equals(tokens[i][2]))
        isValid = true;
    
    if(!isValid)
      return;
    
    int id = parse(getP5().key+"");
    if(id!= -1){
      exportMode = id;
      SuperCAD.recordNextFrame();
    }
  }


  public static void formats(){
    System.out.println(tag+"\n[Exporter] [EXT] [KEY]\nAutoLISP -- lsp -- a\nPovRAY   -- pov -- p\nRhino    -- rvb -- r\nSketchUP -- rb  -- s\nMaya     -- mel -- m\nObjFile  -- obj -- o\nArchiCAD -- gdl -- c");
  }
  
  public static void help(){
    formats();
  }
  

  
  /** Create a new layer (if implemented in the export format)
     * @param layerName 
     * return raw
     */
  static public void newLayer(String layerName){
    if(isRecording()){}
    //  raw.newLayer(layerName);
  }

}

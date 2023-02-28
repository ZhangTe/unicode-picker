package jfxform;

import java.awt.GraphicsEnvironment;
import java.nio.charset.Charset;
import java.util.Locale;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Charcode extends Tiles {
	String X;
	int Code;
	//Tiles TPane;
	/**
	 * CHAR LABEL
	 */
	Text XarL;
	/**
	 * CODE LABEL
	 */
	Label CodeL;
	static final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
	public static final java.awt.Font[] FONTS = graphicsEnvironment.getAllFonts();
	/*public static String []FONTS_prefer  = {
			"Arial"
			};*/
	public static Font[] xarFonts;
	public static Font[] bigFonts;
	static final String  CHARSETN       = "UTF-16";
	static final Charset CHARSET        = Charset.forName(CHARSETN);
    static final Charset DEFAULTCHARSET = Charset.defaultCharset();
    static final int CHARMAX            = 0x10FFFF;
    static final int CHARMIT            = 0x323AF; // update 2022-09 unicode char table version 15
    static final int CHARMIN            = 0x0;
    public static double FONTSIZE       = 34;
    public static double BIGSIZE        = 90;
    private          int fontidx        = 0;
    
    public static int FONT_CHOOSED      = 0;
    //public static java.awt.Font[] FCheck;
    
    // The following fonts may cause runtime exception, so it should be removed.
    static final String [] UNUSEDFONTS = {"HanaMinA Regular"};
    static final String [] RECOMMENDFONTS = {"TH-Tshyn-P0","TH-Tshyn-P1","TH-Tshyn-P2","TH-Tshyn-P16"};
    
    
    static {
    	//FCheck = new java.awt.Font[FONTS_prefer.length];
    	int []recommand_idx = new int [RECOMMENDFONTS.length];
    	for(int i = 0 ; i<recommand_idx.length; i ++) {
    		recommand_idx[i]=-1;
    	}
    	
    	xarFonts = new Font[FONTS.length/*+FONTS_prefer.length*/+1];
    	bigFonts = new Font[FONTS.length/*+FONTS_prefer.length*/+1];
    	for(int i = 0; i < FONTS.length; i ++) {
    		//FCheck[i] = new java.awt.Font(FONTS[i].getFontName(), 0,(int)FONTSIZE);
    		// Because javafx cannot read the non-English font name
    		// The font name that read from awt should be changed into English
    		String fontname = FONTS[i].getFontName(Locale.ENGLISH);
    		/**
    		 * Clear bad fonts that may cause Runtime exception
    		 */
    		for(int k = 0; k < UNUSEDFONTS.length; k ++) {
    			if (fontname.contains(UNUSEDFONTS[k])) {
    				fontname = "DONT_USE";
    				FONTS[i] = new java.awt.Font(fontname , 0, 0);
    				
    				break;
    			}
    		}
    		for(int k = 0; k < RECOMMENDFONTS.length; k ++) {
    			if (recommand_idx[k]==-1 && fontname.contains(RECOMMENDFONTS[k] )) {
    				recommand_idx[k]=i;
    				break;
    			}
    		}
    		xarFonts[i] = new Font(fontname, FONTSIZE);
			bigFonts[i] = new Font(fontname, BIGSIZE);
			//FONTS[i].ge
			
			
		}
   
    	xarFonts[xarFonts.length-1] = new Font("default", FONTSIZE);
    	bigFonts[bigFonts.length-1] = new Font("default", BIGSIZE);
    	// swap Recommend Font to front 
    	for(int i = 0, j = 0 ; j < RECOMMENDFONTS.length; j++) {
			if (recommand_idx[j]>=0) {
	    		java.awt.Font awtfonttmp = FONTS[i];
	    		FONTS[i]=FONTS[recommand_idx[j]];
	    		FONTS[recommand_idx[j]] = awtfonttmp;
	    		
	    		Font xarfonttmp=xarFonts[i], bigfonttmp=bigFonts[i];
	    		xarFonts[i]=xarFonts[recommand_idx[j]];
	    		
	    		xarFonts[recommand_idx[j]]=xarfonttmp;
	    		
	    		bigFonts[i]=bigFonts[recommand_idx[j]];
	    		bigFonts[recommand_idx[j]]=bigfonttmp;
	    		i++;
			}
		}
    	for(int i = 0; i < FONTS.length; i ++) {
    		System.out.println(i + " :" +FONTS[i]+"  en:"+FONTS[i].getFontName(Locale.ENGLISH)
    				+" fx: family:" + xarFonts[i].getFamily() +" ; Name:" + xarFonts[i].getName());
		}
    	
    }
    
    
    
	public Charcode () {
		XarL = new Text();
		CodeL= new Label();
		this.getChildren().addAll(XarL, CodeL);
		//XarL.setFont(xarFont);
		XarL.setLayoutX(Tiles.getTileWidth()/10);
		XarL.setLayoutY(Tiles.getTileHeight()/1.2);
		
		
	}
	
	public void setTile( int code ) {
		char[] chars = Character.toChars(code);
		X = new String(chars, 0, chars.length);
		XarL.setText(X);
		Code = code;
		this.setWhite();
		if ( Code % 0x100 <= 0x7F ) {
			if (Code % 0x1000 <= 0x7F)
				this.setCyon();
			else this.setGrey();
		}
		setDisplayFont(FONT_CHOOSED);
	}
	
	void autochooseFont() {
		
		/**
		 * check if the preferred font can display the character
		 */
		/*for(; i < FCheck.length; i ++ ) {
			if(FCheck[i].canDisplayUpTo(X) == -1 )
				break;
			
		}*/
		//fontidx = i;
		/*if(i < FCheck.length) {
			fontidx=FONTS.length+i;
			//XarL.setFont(xarFont[fontidx]);
		}
		else {
			/**
			 * check if the other font can display the character
			 */
		
		int i = 0;
		for(i = 0; i < FONTS.length;  i ++) {
			if(xarFonts[i].getFamily().equalsIgnoreCase("System")) {
				continue;
			}
			if(FONTS[i].canDisplayUpTo(X) == -1 )
				break;
		}
		
		if(i < FONTS.length ) {
			fontidx=i;
			//XarL.setFont(xarFont[i]);
		}
		else {
			fontidx = xarFonts.length-1;
			//XarL.setFont(xarFont[fontidx]);
			if(!Character.isDefined(Code)) this.setPink();
		}
			
		//}
		
	}
	
	public int getFontIndex() {
		return fontidx;
	}
	
	public void setDisplayFont(int x) {
		fontidx=x;
		if(FONTS[x].canDisplayUpTo(X) != -1 || xarFonts[x].getFamily().equals("System")) {
			autochooseFont();
		}
			
		String name_ = xarFonts[fontidx].getFamily();
		CodeL.setText(getHexCode() + "\n" + name_.substring(0,(name_.length()>6)?6:name_.length()));
		XarL.setFont(xarFonts[fontidx]);
	}
	
	
	public void setLocal(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	
	
	public String getChar() {
		
		return X;
	}
	
	public String getHexCode() {
		return Integer.toHexString(Code);
	}
	public int getCode() {
		return Code;
	}
}

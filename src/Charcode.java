
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
                     int fontidx        = 0;
    
    public static int FONT_CHOOSED      = 0;
    //public static java.awt.Font[] FCheck;
    
    // The following fonts may cause runtime exception, so it should be removed.
    static final String [] UNUSEDFONTS = {"HanaMinA Regular"};
    
    static {
    	//FCheck = new java.awt.Font[FONTS_prefer.length];
    	
    	
    	
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
    			if (fontname.equalsIgnoreCase(UNUSEDFONTS[k])) {
    				fontname = "DONT_USE";
    				FONTS[i] = new java.awt.Font(fontname , 0, 0);
    				
    				break;
    			}
    		}
    		xarFonts[i] = new Font(fontname, FONTSIZE);
			bigFonts[i] = new Font(fontname, BIGSIZE);
			//FONTS[i].ge
		}
    	/*for(int i = 0; i < FONTS_prefer.length; i ++) {
    		//FCheck[i] = new java.awt.Font(FONTS_prefer[i], 0,(int)FONTSIZE);
    		xarFont[FONTS.length+i] = new Font(FONTS_prefer[i], FONTSIZE);
			bigFont[FONTS.length+i] = new Font(FONTS_prefer[i], BIGSIZE);
		}*/
    	xarFonts[xarFonts.length-1] = new Font("default", FONTSIZE);
    	bigFonts[bigFonts.length-1] = new Font("default", BIGSIZE);
    	//for(int i = 0; i < FONTS.length+1; i ++) {
    		//FCheck[i] = new java.awt.Font(FONTS[i].getFontName(), 0,(int)FONTSIZE);
    		
    		// Because javafx cannot read the non-English font name
    		// The font name that read from awt should be changed into English
    		
			//FONTS[i].ge
		//}
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
		setFont(FONT_CHOOSED);
	}
	
	void autochooseFont() {
		int i = 0;
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
	
	public void setFont(int x) {
		fontidx=x;
		if(FONTS[x].canDisplayUpTo(X) != -1 ) {
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

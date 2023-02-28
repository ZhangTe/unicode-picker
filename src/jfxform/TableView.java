package jfxform;
import java.util.Locale;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TableView{
	/**
	 * Layout size setting to 1 as 1080p screen.
	 * set to 0.5 for 540p
	 */
	double LAYOUT_SIZE = 0.5;
	final Stage stage;
	
	int colMax;
	int rowMax;
	Scene scene;
	public Scene getTableScene() {
		return scene;
	}
	
	/**
	 * For controller Layout
	 */
	public static double controllerspan = 30;
	
	int startAt= 0x0;
	Charcode [] chars;
	AnchorPane table;
	Charcode focused;
	public TableView(Stage stage_) {
		stage = stage_;
		createScene();
		//this.getChildren().add(table)
		redraw();
	}
	
	public void createScene() {
		
		colMax = (int) (32*LAYOUT_SIZE);
		rowMax = (int) (16*LAYOUT_SIZE);
		table = new AnchorPane();
		scene = new Scene(table,colMax*Tiles.getTileWidth()*Tiles.getSpanRatio()+300,rowMax*Tiles.getTileHeight()*Tiles.getSpanRatio()+20);
		chars = new Charcode[colMax*rowMax];
		init();
		for (int i = 0; i < colMax*rowMax; i ++ ) {
			chars[i]=new Charcode();
			table.getChildren().addAll(chars[i]);
			chars[i].setOnMouseClicked(charcodeOnclickHandler);
		}
		
	}
	
	
	public EventHandler charcodeOnclickHandler = new EventHandler(){
		@Override
		public void handle(Event args) {
			Charcode cc = (Charcode) (args.getSource());
			focusTo(cc);
			String s = cc.getChar();
			if(INChb.isSelected())
				InTf.setText(InTf.getText() + s);
			XL.setText(s);
			
			XL.setFont(Charcode.bigFonts[cc.getFontIndex()]);
		    Image XLM = XL.snapshot(null, null);
		    
		    //cc_.setTile(cc.getCode());
		    //cc_.setScaleX(6);
		    //cc_.setScaleY(6);
		    //table.getChildren().add(cc_);
		    Image XLM2= cc.snapshot(null, null);
		    
		    
		    //for debug
		    int canplay = Charcode.FONTS[Charcode.FONT_CHOOSED].canDisplayUpTo(s);
		    if (cc.fontidx < Charcode.FONTS.length) {
			    System.out.println(s + " font :" +cc.fontidx + ": " + Charcode.FONTS[cc.fontidx] 
			    		+ "; choosed font"+Charcode.FONT_CHOOSED + " " + Charcode.FONTS[Charcode.FONT_CHOOSED] 
			    		+ "(en=" +  Charcode.FONTS[Charcode.FONT_CHOOSED].getFamily(Locale.ENGLISH) +") can play " + canplay);
			    System.out.println(cc.fontidx + " :" +Charcode.FONTS[cc.fontidx]+"  en:"+Charcode.FONTS[cc.fontidx].getFontName(Locale.ENGLISH)
	    				+" fx: family:" + Charcode.xarFonts[cc.fontidx].getFamily() +" ; Name:" + Charcode.xarFonts[cc.fontidx].getName());
			    //----
		    }
		    xarView1.setImage(XLM);
		    xarView2.setImage(XLM2);
		    
		}
	};
	/**
	 * Focus to null to unfocus
	 * @param c the tile to focus
	 */
	void focusTo (Charcode c) {
		if(focused!=null)
			focused.setDeFocus();
		focused = c;
		if (c != null)
			focused.setOnFocus();;
	}
	public void redraw() {
		for (int i = 0; i < chars.length ; i ++ ) {
			chars[i].setTile(i+startAt);
			chars[i].setLocal(i%colMax, i/colMax);
		}
	}
	
	public void changeFont(int f) {
		System.out.print(Charcode.FONTS[f].getFontName());
		XL.setFont(Charcode.bigFonts[f]);
		/*InTf.setStyle(".text-field{\r\n"
				+ "   -fx-font-family: \"" + Charcode.xarFont[f].getName() +  "\";\r\n"
				+ "}");*/
		for (int i = 0; i < chars.length; i ++ ) {
			chars[i].setDisplayFont(f);
		}
		Charcode.FONT_CHOOSED = f;
		//Charcode.FONTS_prefer[0]=Charcode.FONTS[f].getFontName();
	}
	
	
	//int rollDown = 10;
	final Slider PageSlider = new Slider(Charcode.CHARMIN,Charcode.CHARMIT,Charcode.CHARMIN); 
	
	Button DownBTN,UpBTN,JumpToBtn,SearchBtn,RefreshBtn,ViewSizeLargerBTN,ViewSizeSmallerBTN;
	TextField JumpToTf, RollDownTf, InTf;
	ChoiceBox<String> FontSelCb;
	Text      XL;
	ImageView xarView1, xarView2;
	CheckBox  INChb;
	Label labelFontSel,labelScroll,labelSearch,labelInput;
	public static void printbin (int num){
		System.out.println(num + "binary:");
	    for(int i=31;i>=0;i--){
	        System.out.print((num & 1 << i) == 0 ? "0":"1");
	    }	
		System.out.println();
	    
	}
	void init() {
		double layoutx = colMax*Tiles.getTileWidth()*Tiles.getSpanRatio()+50;
		double layouty = Tiles.getTileHeight()/2;
		
		
		labelFontSel = new Label("Font Select     @unicodepicker-"+Main.ABOUT);
		labelFontSel.setLayoutX(layoutx);
		labelFontSel.setLayoutY(layouty/2);
		
		FontSelCb = new ChoiceBox<String>();
		for (int i = 0 ; i < Charcode.FONTS.length; i ++)
			FontSelCb.getItems().add(Charcode.FONTS[i].getFamily()+" "
					+ Charcode.FONTS[i].getFamily(Locale.ENGLISH)+" "
					+Charcode.FONTS[i].getFontName() + " " 
					+Charcode.FONTS[i].getName());
		
		
		FontSelCb.setLayoutX(layoutx);
		FontSelCb.setLayoutY(layouty);
		 // add a listener 
		FontSelCb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() { 
  
            // if the item of the list is changed 
            public void changed(ObservableValue ov, Number value, Number new_value) 
            { 
            	int fonid = new_value.intValue();
            	System.out.println(new_value.toString() + ":" + Charcode.FONTS[fonid] + " awt fontName en:" +  Charcode.FONTS[fonid].getFontName(Locale.ENGLISH)
            			+ " fontname nonlocal:" +  Charcode.FONTS[fonid].getFontName() + " font-name:" +  Charcode.FONTS[fonid].getName());
            	System.out.println("fx: family:" + Charcode.xarFonts[fonid].getFamily() +" ; Name:" + Charcode.xarFonts[fonid].getName());
            	changeFont(fonid);
            } 
        }); 
		
		
		labelSearch = new Label("Search, character or hex-number");
		labelSearch.setLayoutX(layoutx);
		labelSearch.setLayoutY(layouty+controllerspan * 0.8);
		
		
		JumpToTf = new TextField("");
		JumpToBtn = new Button("JumpTo");
		JumpToTf.setLayoutX(layoutx);
		JumpToTf.setLayoutY(layouty+controllerspan*1.3);
		JumpToBtn .setLayoutX(layoutx);
		JumpToBtn.setLayoutY(layouty+controllerspan*2);
		
		
		
		JumpToBtn.setOnAction(new EventHandler(){
			@Override
			public void handle(Event args) {
				int parseInt = -1;
				String argstr = JumpToTf.getText();
				try {
					parseInt = Integer.parseInt(argstr,16);
					
				} catch (Exception e) {
					if (!argstr.equals("")) {
						//String x = argstr.substring(0, 1);
						
						try {
							parseInt = argstr.codePointAt(0);
							/* byte [] b = x.getBytes("utf16");
							int t = 0;
							for ( int i = 0; i < b.length ; i++ ) {
								t = t | ((b[i] & 0xFF) << ((b.length-i-1) * 8));
								System.out.println("b[" + i + "]");
								printbin(b[i]);
								System.out.print("t=");
								printbin(t);
								
								
							}*/
							//System.out.println(Integer.toHexString(argstr.codePointAt(0)));
							//System.out.println(Integer.toHexString(t));
							//printbin(t);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} finally {
						}
					}
				}
				if (parseInt != -1) {
					int index = parseInt % colMax;
					pageTo(parseInt);
					focusTo(chars[index]);
				}
			}
			
		});
		
		
		labelScroll = new Label("Scroll\nlines");
		labelScroll.setLayoutX(layoutx-Tiles.RECTWIDTH*0.8);
		labelScroll.setLayoutY(layouty+controllerspan * 3);
		
		RollDownTf = new TextField("4");
		RollDownTf.setLayoutX(layoutx);
		RollDownTf.setLayoutY(layouty+controllerspan*3);
		UpBTN= new Button("Up");
		UpBTN.setLayoutX(layoutx);
		UpBTN.setLayoutY(layouty+controllerspan*4);
		UpBTN.setOnAction(new EventHandler(){
			@Override
			public void handle(Event args) {
				scroll(true);
			}
			
		});
		
		DownBTN= new Button("Down");
		DownBTN.setLayoutX(layoutx);
		DownBTN.setLayoutY(layouty+controllerspan*5);
		DownBTN.setOnAction(new EventHandler(){
			@Override
			public void handle(Event args) {
				
				scroll(false);
			}
			
		});
		
		ViewSizeLargerBTN= new Button("⤡");
		ViewSizeLargerBTN.setLayoutX(layoutx+Tiles.RECTWIDTH*4);
		ViewSizeLargerBTN.setLayoutY(layouty+controllerspan*5);
		ViewSizeLargerBTN.setOnAction(new EventHandler(){
			@Override
			public void handle(Event args) {
				if (LAYOUT_SIZE >= 0.5) {
					LAYOUT_SIZE /= 2;
				}
				else {
					LAYOUT_SIZE = 2;
				}
				createScene();
				stage.setScene(scene);
				redraw();
			}
			
		});
		ViewSizeLargerBTN= new Button("⬊");
		ViewSizeLargerBTN.setLayoutX(layoutx+Tiles.RECTWIDTH*4.5);
		ViewSizeLargerBTN.setLayoutY(layouty+controllerspan*5);
		ViewSizeLargerBTN.setOnAction(new EventHandler(){
			@Override
			public void handle(Event args) {
				if (LAYOUT_SIZE < 2) {
					LAYOUT_SIZE *= 2;
				}
				else {
					LAYOUT_SIZE = 0.25;
				}
				createScene();
				stage.setScene(scene);
				redraw();
			}
			
		});
		ViewSizeSmallerBTN= new Button("⬉");
		ViewSizeSmallerBTN.setLayoutX(layoutx+Tiles.RECTWIDTH*4);
		ViewSizeSmallerBTN.setLayoutY(layouty+controllerspan*5);
		ViewSizeSmallerBTN.setOnAction(new EventHandler(){
			@Override
			public void handle(Event args) {
				if (LAYOUT_SIZE > 0.25) {
					LAYOUT_SIZE /= 2;
				}
				else {
					LAYOUT_SIZE = 2;
				}
				createScene();
				stage.setScene(scene);
				redraw();
			}
			
		});
		
		labelInput = new Label("Enable to input what you click");
		labelInput.setLayoutX(layoutx);
		labelInput.setLayoutY(layouty+controllerspan * 6.5);
		
		InTf = new TextField("");
		InTf.setLayoutX(layoutx);
		InTf.setLayoutY(layouty+controllerspan*7);
		/*InTf.setStyle(".text-field{\r\n"
				+ "   -fx-font: \"" + Charcode.xarFont[1].getName() +  "\";\r\n"
				+ "}");*/
		
		INChb = new CheckBox("In?");
		INChb.textFillProperty().setValue(Color.WHITE);
		INChb.setLayoutX(layoutx - controllerspan);
		INChb.setLayoutY(layouty+controllerspan*6.5);
		
		XL = new Text("");
		XL.setScaleX(2* ((LAYOUT_SIZE>1)?1:LAYOUT_SIZE));
		XL.setScaleY(2* ((LAYOUT_SIZE>1)?1:LAYOUT_SIZE));
		XL.setFont(Charcode.bigFonts[0]);
	    Image XLM = XL.snapshot(null, null);
	    
	    xarView1 = new ImageView(XLM);
	    //xarView1.setScaleX(2.3* LAYOUT_SIZE );
	    //xarView1.setScaleY(2.3* LAYOUT_SIZE );
	    xarView1.setLayoutX(layoutx - Tiles.RECTWIDTH*0.5);
	    xarView1.setLayoutY(layouty+controllerspan*8 );
	    
	    double table_height = Tiles.RECTHEIGHT * rowMax * Tiles.SPANRATIO;
	    double table_width = Tiles.RECTWIDTH * colMax * Tiles.SPANRATIO;
	    
	    
	    
	    xarView2 = new ImageView(XLM);
	    xarView2.setScaleX(5 * ((LAYOUT_SIZE>1)?1:LAYOUT_SIZE) );
	    xarView2.setScaleY(5 * ((LAYOUT_SIZE>1)?1:LAYOUT_SIZE) );
	    xarView2.setLayoutX(layoutx+Tiles.RECTWIDTH*2.6);
	    xarView2.setLayoutY(table_height - Tiles.RECTHEIGHT * 4 * ((LAYOUT_SIZE>1)?1:LAYOUT_SIZE));// set smaller with small screen
	    
	    
	    
	    
	    PageSlider.setRotate(90);
	    PageSlider.setLayoutX(table_width - table_height/2 + Tiles.RECTWIDTH*0.1);
	    PageSlider.setLayoutY(table_height / 2 - Tiles.RECTHEIGHT*0.1);
	    //PageSlider.setW
	    PageSlider.setPrefWidth(table_height);
	    
	    PageSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				pageTo((int)(PageSlider.getValue()));
			}
        });
	    
		table.getChildren().addAll(FontSelCb,JumpToTf,JumpToBtn,
				RollDownTf,UpBTN,DownBTN,ViewSizeLargerBTN,ViewSizeSmallerBTN,InTf,
				INChb,
				xarView1,xarView2,
				PageSlider,
				labelFontSel,labelScroll,labelSearch,labelInput);
		
		table.setOnScroll(new EventHandler<ScrollEvent>() {
		      @Override
		      public void handle(ScrollEvent event) {
		    	  scroll(event.getDeltaY()>0);
		          event.consume();
		      }
		    });
	}
	
	/**
	 * for this program only.
	 * control panel should be at the right of the form
	 * with fixed x-position.
	 * @param n
	 * @param id
	 * @param height
	 */
	/*void addController(Node n, int layoutx, int controller_id) {
		n.setLayoutX(layoutx);
		n.setLayoutY(controller_id*controllerspan);
		//this.getChildren()
	}*/
	
	
	void scroll(boolean up) {
		try {
			int parseInt = Integer.parseInt(RollDownTf.getText());
			startAt = startAt + (up?-1:1)*parseInt * colMax;
			pageTo(startAt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void pageTo(int page) {
		focusTo(null);
		startAt = page;
		startAt = startAt - startAt % colMax;
		if (startAt > Charcode.CHARMAX-colMax*rowMax+1) 
			startAt = Charcode.CHARMAX-colMax*rowMax+1;
		else if (startAt < Charcode.CHARMIN) 
			startAt = Charcode.CHARMIN;
		PageSlider.setValue(page);
		redraw();
	}
	
}

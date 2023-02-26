import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
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

public class TableView extends AnchorPane{
	/**
	 * Layout size setting to 1 as 1080p screen.
	 * set to 0.5 for 540p
	 */
	public static final double LAYOUT_SIZE = 1;
	public static int colMax = (int) (32*LAYOUT_SIZE);
	public static int rowMax = (int) (16*LAYOUT_SIZE);
	public static double controllerspan = 30;
	int startAt= 0x0;
	Charcode [] chars;
	AnchorPane table;
	Charcode focused;
	public TableView() {
		table = new AnchorPane();
		this.getChildren().add(table);
		chars = new Charcode[colMax*rowMax];
		init();
		
		for (int i = 0; i < colMax*rowMax; i ++ ) {
			chars[i]=new Charcode();
			table.getChildren().addAll(chars[i]);
			chars[i].setOnMouseClicked(charcodeOnclickHandler);
		}
		
		redraw();
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
			
			XL.setFont(Charcode.bigFont[cc.getFontIndex()]);
		    Image XLM = XL.snapshot(null, null);
		    
		    //cc_.setTile(cc.getCode());
		    //cc_.setScaleX(6);
		    //cc_.setScaleY(6);
		    //table.getChildren().add(cc_);
		    Image XLM2= cc.snapshot(null, null);
		    
		    /*
		    //for debug
		    int canplay = Charcode.FONTS[Charcode.FONT_CHOOSED].canDisplayUpTo(s);
		    if (cc.fontidx < Charcode.FONTS.length)
		    System.out.println(s + " font :" +cc.fontidx + ": " + Charcode.FONTS[cc.fontidx] 
		    		+ "; choosed font"+Charcode.FONT_CHOOSED + " " + Charcode.FONTS[Charcode.FONT_CHOOSED] +  " can play " + canplay);
		    else System.out.println("cannot display");
		    //----*/
		    xarView1.setImage(XLM);
		    xarView2.setImage(XLM2);
		    
		}
	};
	void focusTo (Charcode c) {
		if(focused!=null)
			focused.setDeFocus();
		focused = c;
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
		XL.setFont(Charcode.bigFont[f]);
		/*InTf.setStyle(".text-field{\r\n"
				+ "   -fx-font-family: \"" + Charcode.xarFont[f].getName() +  "\";\r\n"
				+ "}");*/
		for (int i = 0; i < chars.length; i ++ ) {
			chars[i].setFont(f);
		}
		Charcode.FONT_CHOOSED = f;
		//Charcode.FONTS_prefer[0]=Charcode.FONTS[f].getFontName();
	}
	
	
	//int rollDown = 10;
	final Slider PageSlider = new Slider(Charcode.CHARMIN,Charcode.CHARMIT,Charcode.CHARMIN); 
	
	Button DownBTN,UpBTN,JumpToBtn,SearchBtn,RefreshBtn;
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
		double layoutx = TableView.colMax*Tiles.getTileWidth()*Tiles.getSpanRatio()+50;
		double layouty = Tiles.getTileHeight()/2;
		
		
		labelFontSel = new Label("Font Select");
		labelFontSel.setLayoutX(layoutx);
		labelFontSel.setLayoutY(layouty/2);
		
		FontSelCb = new ChoiceBox<String>();
		for (int i = 0 ; i < Charcode.FONTS.length; i ++)
			FontSelCb.getItems().add(Charcode.FONTS[i].getFontName());
		
		
		FontSelCb.setLayoutX(layoutx);
		FontSelCb.setLayoutY(layouty);
		 // add a listener 
		FontSelCb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() { 
  
            // if the item of the list is changed 
            public void changed(ObservableValue ov, Number value, Number new_value) 
            { 
            	System.out.println(new_value.toString() + ":" + Charcode.FONTS[new_value.intValue()]);
            	changeFont(new_value.intValue());
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
					int index = parseInt % TableView.colMax;
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
		XL.setScaleX(2* LAYOUT_SIZE);
		XL.setScaleY(2* LAYOUT_SIZE);
		XL.setFont(Charcode.bigFont[0]);
	    Image XLM = XL.snapshot(null, null);
	    
	    xarView1 = new ImageView(XLM);
	    //xarView1.setScaleX(2.3* LAYOUT_SIZE );
	    //xarView1.setScaleY(2.3* LAYOUT_SIZE );
	    xarView1.setLayoutX(layoutx);
	    xarView1.setLayoutY(layouty+controllerspan*8 );
	    
	    double table_height = Tiles.RECTHEIGHT * TableView.rowMax * Tiles.SPANRATIO;
	    double table_width = Tiles.RECTWIDTH * TableView.colMax * Tiles.SPANRATIO;
	    
	    
	    
	    xarView2 = new ImageView(XLM);
	    xarView2.setScaleX(5 * LAYOUT_SIZE );
	    xarView2.setScaleY(5 * LAYOUT_SIZE );
	    xarView2.setLayoutX(layoutx+Tiles.RECTWIDTH*2.6);
	    xarView2.setLayoutY(table_height - Tiles.RECTHEIGHT * 4 * LAYOUT_SIZE);// set smaller with small screen
	    
	    
	    
	    
	    PageSlider.setRotate(90);
	    PageSlider.setLayoutX(table_width - table_height/2);
	    PageSlider.setLayoutY(table_height / 2);
	    //PageSlider.setW
	    PageSlider.setPrefWidth(table_height);
	    
	    PageSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				pageTo((int)(PageSlider.getValue()));
			}
        });
	    
		this.getChildren().addAll(FontSelCb,JumpToTf,JumpToBtn,
				RollDownTf,UpBTN,DownBTN,InTf,
				INChb,
				xarView1,xarView2,
				PageSlider,
				labelFontSel,labelScroll,labelSearch,labelInput);
		
		this.setOnScroll(new EventHandler<ScrollEvent>() {
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

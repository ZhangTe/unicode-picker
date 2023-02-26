import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TableView extends AnchorPane{
	public static int colMax = 0x20;
	public static int rowMax = 0x10;
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
	
	public static void printbin (int num){
		System.out.println(num + "binary:");
	    for(int i=31;i>=0;i--){
	        System.out.print((num & 1 << i) == 0 ? "0":"1");
	    }	
		System.out.println();
	    
	}
	void init() {
		FontSelCb = new ChoiceBox<String>();
		for (int i = 0 ; i < Charcode.FONTS.length; i ++)
			FontSelCb.getItems().add(Charcode.FONTS[i].getFontName());
		double layoutx = TableView.colMax*Tiles.getTileWidth()*Tiles.getSpanRatio()+50;
		double layouty = Tiles.getTileHeight();
		double controllerspan = 30;
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
		
		InTf = new TextField("");
		InTf.setLayoutX(layoutx);
		InTf.setLayoutY(layouty+controllerspan*6);
		/*InTf.setStyle(".text-field{\r\n"
				+ "   -fx-font: \"" + Charcode.xarFont[1].getName() +  "\";\r\n"
				+ "}");*/
		
		INChb = new CheckBox("In?");
		INChb.textFillProperty().setValue(Color.WHITE);
		INChb.setLayoutX(layoutx - controllerspan);
		INChb.setLayoutY(layouty+controllerspan*6);
		
		XL = new Text("");
		XL.setFont(Charcode.bigFont[0]);
	    Image XLM = XL.snapshot(null, null);
	    
	    xarView1 = new ImageView(XLM);
	    xarView1.setLayoutX(layoutx+Tiles.RECTWIDTH*1);
	    xarView1.setLayoutY(layouty+controllerspan*10); // set smaller with small screen
	    xarView1.setScaleX(2.3);
	    xarView1.setScaleY(2.3);
		
	    xarView2 = new ImageView(XLM);
	    xarView2.setLayoutX(layoutx+Tiles.RECTWIDTH*2);
	    xarView2.setLayoutY(layouty+controllerspan*22);// set smaller with small screen
	    xarView2.setScaleX(5);
	    xarView2.setScaleY(5);
	    
	    PageSlider.setRotate(90);
	    PageSlider.setLayoutX(layoutx-Tiles.RECTWIDTH*11);
	    PageSlider.setLayoutY(layouty+controllerspan*15);
	    PageSlider.setPrefWidth(Tiles.RECTWIDTH*20);
	    
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
				PageSlider);
		
		this.setOnScroll(new EventHandler<ScrollEvent>() {
		      @Override
		      public void handle(ScrollEvent event) {
		    	  scroll(event.getDeltaY()>0);
		          event.consume();
		      }
		    });
	}
	
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

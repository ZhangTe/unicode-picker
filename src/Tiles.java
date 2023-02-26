import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
public class Tiles extends AnchorPane{
	static final double STROKEWITH = 2;
	static final Color  FILLCOLOR  = Color.rgb(240,240,240);;
	static final Color  FOCUSCOLOR = Color.ORANGE;
	static double RECTWIDTH = 45;
	static double RECTHEIGHT= 60;
	static double SPANRATIO = 1.05;
	Rectangle rctg = new Rectangle(RECTWIDTH,RECTHEIGHT);
	public Tiles(){
		rctg.setFill(FILLCOLOR);
		rctg.setStroke(FILLCOLOR);
		rctg.setStrokeWidth(STROKEWITH);
		this.getChildren().addAll(rctg);
	}
	/*public void setBackGroundColor(Color c) {
		rctg.setFill(c);
	}*/
	public void setPink() {
		rctg.setFill(Color.rgb(240, 220, 220));
	}
	public void setWhite() {
		rctg.setFill(Color.rgb(240, 240, 240));
	}
	public void setX(int x){
		this.xPos = x;
		this.setLayoutX(x*RECTWIDTH*SPANRATIO);
	}
	public void setY(int y){
		this.yPos = y;
		this.setLayoutY(y*RECTHEIGHT*SPANRATIO);
	}
	public static double getTileWidth() {
		return RECTWIDTH;
	}
	public static double getTileHeight() {
		return RECTHEIGHT;
	}
	public static double getSpanRatio() {
		return SPANRATIO;
	}
	
	public int getXPos(){
		return this.xPos;
	}
	public int getYPos(){
		return this.yPos;
	}
	public void setOnFocus(){
		this.focused = true;
		rctg.setStroke(FOCUSCOLOR);
	}
	public void setDeFocus(){
		this.focused = false;
		//rctg.setStrokeWidth(2);
		rctg.setStroke(FILLCOLOR);
		//setOpacity(1);
	}
	public boolean tilefocused(){
		return focused;
	}
	private int xPos = 0;
	private int yPos = 0;
	private boolean focused = false;
	public int tileid;
	public boolean isCleared = false;
}

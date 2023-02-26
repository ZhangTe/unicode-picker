import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Locale;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CodeTable extends Application{
	
	
	
	public static void main(String []args) {
		 GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    Font[] fontNames = graphicsEnvironment.getAllFonts();
		    for (Font s : fontNames) {
		        System.out.println(s);
		    }
		//System.out.println(Charcode.FONTS[570].getFamily(Locale.ENGLISH));
		launch(args);
		  
	}
	
	public static void printBox()
	{
	    for (int i=0x1FFFF;i<=0x10FFFF;i++)
	    {
	    	char[] chars = Character.toChars(i);
	    	String cstr  = new String(chars, 0, 2);
	        System.out.printf("0x%x : %s\n",i, cstr);
	    }
	}
	
	@Override
	public void start(final Stage stage) throws Exception {
		// TODO Auto-generated method stub
		TableView table = new TableView();
		Scene scene = new Scene(table,TableView.colMax*Tiles.getTileWidth()*Tiles.getSpanRatio()+300,TableView.rowMax*Tiles.getTileHeight()*Tiles.getSpanRatio());
		scene.setFill(Color.BLACK);
		stage.setScene(scene);
		stage.setX(2);
		stage.setY(5);
		stage.setTitle("Unicode Charset Table");
		stage.show();
	}
}

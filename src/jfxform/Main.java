package jfxform;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	public final static String ABOUT = "jre19";
	
	
	public static void main(String []args) {
		launch(args);
		//java.awt.Font f = new java.awt.Font("Segoe UI", 1, 1);
		//javafx.scene.text.Font f = new javafx.scene.text.Font(userAgentStylesheet, 0);
		//System.out.print(f.toString());		//*/
	}
	
	@Override
	public void start(final Stage stage) throws Exception {
		// TODO Auto-generated method stub
		TableView table = new TableView(stage);
		//scene.setFill(Color.BLACK);
		stage.setScene(table.scene);
		stage.setX(2);
		stage.setY(5);
		stage.setTitle("Unicode Charset Table");
		stage.show();
	}
}

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
	public final static String ABOUT = "jre8";
	
	
	public static void main(String []args) {
		launch(args);
		  
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

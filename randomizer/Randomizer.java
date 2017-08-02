package randomizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import randomizer.common.data.Gui;
import randomizer.fates.singletons.FatesData;
import randomizer.fates.singletons.FatesGui;


public class Randomizer extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Gui.getInstance().setMainStage(primaryStage);
			FatesData.getInstance();
			FatesGui.getInstance();
			Parent root = FXMLLoader.load(this.getClass().getResource("gui/fxml/Verification.fxml"));
			Scene scene = new Scene(root,320,260);
			scene.getStylesheets().add(getClass().getResource("gui/jmetro/JMetroLightTheme.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("common/assets/charlotte.png")));
			primaryStage.setTitle("Randomizer Verification");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
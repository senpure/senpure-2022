package com.senpure.io.generator.ui;

import com.senpure.base.AppEvn;
import com.senpure.io.generator.habit.HabitUtil;
import com.senpure.io.generator.ui.view.MainController;
import com.senpure.io.generator.ui.view.MainView;
import com.senpure.io.generator.util.NoteUtil;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;
import java.io.File;
import java.net.URL;

/**
 * UiBoot
 *
 * @author senpure
 * @time 2019-07-05 16:01:04
 */
@SpringBootApplication
public class UiBoot extends AbstractJavaFxApplicationSupport {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public static void main(String[] args) {


        launch(UiBoot.class, MainView.class,
                args

        );
    }

    @Override
    public void start(Stage stage) throws Exception {
        super.start(stage);
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            logger.error("出现错误", e);
            if (AppEvn.isWindowsOS()) {
                File logFile = new File(AppEvn.getClassRootPath(), "logs/generator.log");
                if (!AppEvn.classInJar(getClass())) {
                    logFile = new File(System.getProperty("user.dir"), "logs/generator.log");
                }
                NoteUtil.openNote(logFile, (int) (e.getStackTrace().length * 1.6));
            }
        });
    }

    @Override
    public void beforeShowingSplash(Stage splashStage) {
        URL url = getClass().getResource("/icon.png");
        if (url != null) {
            Image icon = new Image(url.toExternalForm());
            splashStage.getIcons().clear();
            splashStage.getIcons().addAll(icon);
        }

        // splashStage.initOwner(GUIState.getStage());
        // splashStage.setAlwaysOnTop(true);
        // splashStage.setIconified(true);
        splashStage.setTitle("Start up....");

    }

    @PreDestroy
    public void destroy() {
        try {
            mainController.updateConfig();
            HabitUtil.saveHabit(HabitUtil.getHabit());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

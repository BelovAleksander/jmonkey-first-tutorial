package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import static mygame.config.AppConfig.initSettings;
import mygame.models.CubeChaserState;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public void init() {
        AppSettings appSettings = new AppSettings(true);
        initSettings(appSettings);

    }

    public static void main(String[] args) {
        Main app = new Main();
        app.init();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(100f);
        CubeChaserState state = new CubeChaserState();
        stateManager.attach(state);
        setCursorVisible();
    }

    @Override
    public void simpleUpdate(float tpf) {
        System.out.println("Chase counter: "
                + stateManager.getState(CubeChaserState.class).getCounter());
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    private void setCursorVisible() {
        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);
    }
}

package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static mygame.config.AppConfig.initSettings;
import mygame.handlers.MyActionListener;
import mygame.handlers.MouseLookMoveListener;
import mygame.handlers.MousePointerAnalogListener;
import mygame.models.AppContext;
import mygame.models.Size;
import mygame.service.CreatorService;
import static mygame.utils.Utils.*;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 *
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    private CreatorService creatorService;
    private AppContext appContext;
    private Geometry scaredCube;
    private final static String DEF_NAME = "Common/MatDefs/Misc/Unshaded.j3md";

    private final static Trigger TRIGGER_COLOR = new KeyTrigger(KeyInput.KEY_SPACE);
    private final static Trigger TRIGGER_ROTATE = new MouseButtonTrigger(MouseInput.BUTTON_LEFT);
    private final static Trigger TRIGGER_COLOR2 = new KeyTrigger(KeyInput.KEY_C);

    private final static String MAPPING_COLOR = "mappingColor";
    private final static String MAPPING_ROTATE = "mappingRotate";

    private final Map<String, Geometry> geometryMap = new HashMap<>();

    private ActionListener actionListener;
    private AnalogListener analogListener;

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
        appContext = new AppContext(inputManager, assetManager, rootNode, cam, geometryMap);
        this.creatorService = new CreatorService(appContext, "Common/MatDefs/Misc/Unshaded.j3md");
        initListeners();
        //createObjects();
        //attachCenterMark();
        //createRandomStaticCubes(40);
        createRandomScaredCubes(40);
        setCursorVisible();
        flyCam.setMoveSpeed(100f);
    }

    @Override
    public void simpleUpdate(float tpf) {

    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    private void attachNodes(Node... nodes) {
        for (Node node : nodes) {
            super.rootNode.attachChild(node);
        }
    }

    private void attachCenterMark() {
        Geometry c = creatorService.myBox("center mark",
                Vector3f.ZERO, ColorRGBA.White);
        c.scale(4);
        c.setLocalTranslation(settings.getWidth() / 2,
                settings.getHeight() / 2, 0);
        guiNode.attachChild(c); // attach to 2D user interface
    }

    private void setCursorVisible() {
        flyCam.setDragToRotate(true);
        inputManager.setCursorVisible(true);
    }

    private void createObjects() {
        this.creatorService = new CreatorService(appContext, "Common/MatDefs/Misc/Unshaded.j3md");
        Geometry floor = creatorService.createBox("floor", new Size(33, 33, -1), ColorRGBA.Orange);
        Geometry base = creatorService.createBox("Base", new Size(2, 1, 0.5f), ColorRGBA.Yellow);
        Geometry tower1 = creatorService.createBox("Tower1", new Size(0.3f, 0.3f, 0.3f), ColorRGBA.Green);
        Geometry tower2 = creatorService.createBox("Tower2", new Size(0.3f, 0.3f, 0.3f), ColorRGBA.Green);
        tower1.move(3, 0, 0);
        tower2.move(-3, 0, 0);
        Geometry creep1 = creatorService.createBox("Creep1", new Size(0.1f, 0.1f, 0.1f), ColorRGBA.Black);
        Geometry creep2 = creatorService.createBox("Creep2", new Size(0.1f, 0.1f, 0.1f), ColorRGBA.Black);
        Geometry creep3 = creatorService.createBox("Creep3", new Size(0.1f, 0.1f, 0.1f), ColorRGBA.Black);
        Geometry creep4 = creatorService.createBox("Creep4", new Size(0.1f, 0.1f, 0.1f), ColorRGBA.Black);
        Geometry creep5 = creatorService.createBox("Creep5", new Size(0.1f, 0.1f, 0.1f), ColorRGBA.Black);
        creep1.move(0, -3, 0);
        creep3.move(1f, -3, 0);
        creep4.move(-1f, -3, 0);
        creep2.move(1f, -3.5f, 0);
        creep5.move(-1f, -3.5f, 0);

        Node playerNode = new Node();
        Node towerNode = new Node();
        Node creepNode = new Node();

        attachToNode(creepNode, creep1, creep2, creep3, creep4, creep5);
        attachToNode(towerNode, tower1, tower2);

        attachNodes(playerNode, towerNode, creepNode);
        attachGeometries(rootNode, base, floor);
        putGeometriesIntoMap(geometryMap, creep1, creep2, creep3, creep4, creep5);
    }

    private void createRandomStaticCubes(int amount) {
        List<Geometry> boxes = creatorService.makeCubes(amount);
        attachGeometries(rootNode, boxes);
        putGeometriesIntoMap(geometryMap, boxes);
        scaredWhiteCube();
    }

    private void createRandomScaredCubes(int amount) {
        List<Geometry> boxes = creatorService.makeScaredCubes(amount);
        attachGeometries(rootNode, boxes);
        putGeometriesIntoMap(geometryMap, boxes);
    }

    private void initListeners() {
        //analogListener = new MouseLookMoveListener(context);
        actionListener = new MyActionListener(MAPPING_COLOR, appContext);
        //analogListener = new MousePointerAnalogListener(appContext, MAPPING_ROTATE);

        inputManager.addMapping(MAPPING_COLOR, TRIGGER_COLOR, TRIGGER_COLOR2);
        inputManager.addMapping(MAPPING_ROTATE, TRIGGER_ROTATE);
        inputManager.addListener(actionListener, new String[]{MAPPING_COLOR});
        inputManager.addListener(analogListener, new String[]{MAPPING_ROTATE});
    }

    private void scaredWhiteCube() {
        System.out.println("Distance: "
                + cam.getLocation().distance(scaredCube.getLocalTranslation()));
        if (cam.getLocation().distance(scaredCube.getLocalTranslation()) < 10) {
            scaredCube.move(cam.getDirection());
        }
        scaredCube = creatorService.myBox("Scared Cube", Vector3f.ZERO, ColorRGBA.White);
        rootNode.attachChild(scaredCube);
        putGeometriesIntoMap(geometryMap, scaredCube);
    }
}

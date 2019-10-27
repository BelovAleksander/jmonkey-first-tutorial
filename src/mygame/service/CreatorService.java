/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.service;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.List;
import mygame.controllers.CubeChaserControl;
import mygame.models.AppContext;
import mygame.models.Size;
import static mygame.utils.Utils.getMaterial;


/**
 *
 * @author whiterabbit
 */
public class CreatorService {
    private final AssetManager assetManager;
    private final Camera cam;
    private final Node rootNode;
    private final String defName;
    private static Box mesh = new Box(Vector3f.ZERO, 1, 1, 1);
    private final AppContext context;
    
    public CreatorService(AppContext context, String defName) {
        this.assetManager = context.getAssetManager();
        this.defName = defName;
        this.rootNode = context.getRootNode();
        this.cam = context.getCamera();
        this.context = context;
    }
        
    public Geometry createBox(String name, Size size, ColorRGBA color) {
        Box box = new Box(size.getX(), size.getY(), size.getZ());
        Geometry geometry = new Geometry(name, box);
        Material material = getMaterial(assetManager, defName, color);
        geometry.setMaterial(material);
        return geometry;
    }
    
    public Geometry myBox(String name, Vector3f loc, ColorRGBA color) {
        Geometry geom = new Geometry(name, mesh);
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geom.setMaterial(mat);
        geom.setLocalTranslation(loc);
        return geom;
    }
    
    public List<Geometry> makeCubes(int number) {
        List<Geometry> result = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Vector3f loc = new Vector3f(
                    FastMath.nextRandomInt(-20, 20),
                    FastMath.nextRandomInt(-20, 20),
                    FastMath.nextRandomInt(-20, 20));
            result.add(myBox("box" + 1, loc, ColorRGBA.randomColor()));
        }
        return result;
    }
    
    public List<Geometry> makeScaredCubes(int number) {
        List<Geometry> result = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Vector3f loc = new Vector3f(
                    FastMath.nextRandomInt(-20, 20),
                    FastMath.nextRandomInt(-20, 20),
                    FastMath.nextRandomInt(-20, 20));
            Geometry box = myBox("box" + 1, loc, ColorRGBA.randomColor());
                box.addControl(new CubeChaserControl(context));
            result.add(box);
        }
        return result;
    }
}

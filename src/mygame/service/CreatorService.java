/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.service;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import mygame.models.Size;
import static mygame.utils.Utils.getMaterial;

/**
 *
 * @author whiterabbit
 */
public class CreatorService {
    private final AssetManager assetManager;
    private final String defName;
    private static Box mesh = new Box(Vector3f.ZERO, 1, 1, 1);
    
    public CreatorService(AssetManager assetManager, String defName) {
        this.assetManager = assetManager;
        this.defName = defName;
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
}

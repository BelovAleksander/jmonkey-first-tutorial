/*
 * Copyright 2019 whiterabbit.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mygame.models;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.ArrayList;
import java.util.List;
import mygame.controllers.CubeChaserControl;
import static mygame.utils.Utils.attachGeometries;

/**
 *
 * @author whiterabbit
 */
public class CubeChaserState extends AbstractAppState {
    private int counter = 0;
    private SimpleApplication app;
    private Camera cam;
    private Node rootNode;
    private AssetManager assetManager;
    private final Ray ray = new Ray();
    private final static Box MESH = new Box(Vector3f.ZERO, 1, 1, 1);

    @Override
    public void update(float tpf) {
        CollisionResults results = new CollisionResults();
        ray.setOrigin(cam.getLocation());
        ray.setDirection(cam.getDirection());
        rootNode.collideWith(ray, results);
        if (results.size() > 0) {
            Geometry target = results.getClosestCollision().getGeometry();
            if (target.getControl(CubeChaserControl.class) != null) {
                if (cam.getLocation().distance(target.getLocalTranslation()) < 10) {
                    target.move(cam.getDirection());
                    System.out.println(target.getControl(
                            CubeChaserControl.class).hello()
                            + " and I am running away from " + cam.getLocation());
                    counter++;
                }
            }
        }
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (SimpleApplication) app;
        this.cam = this.app.getCamera();
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        makeCubes(40);
    }

    public void makeCubes(int number) {
        List<Geometry> result = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            Vector3f loc = new Vector3f(
                    FastMath.nextRandomInt(-20, 20),
                    FastMath.nextRandomInt(-20, 20),
                    FastMath.nextRandomInt(-20, 20));
            Geometry box = myBox("box" + 1, loc, ColorRGBA.randomColor());
            result.add(box);
            if (FastMath.nextRandomInt(1, 4) == 4) {
                box.addControl(new CubeChaserControl());
            }

        }
        attachGeometries(rootNode, result);
    }

    public Geometry myBox(String name, Vector3f loc, ColorRGBA color) {
        Geometry geom = new Geometry(name, MESH);
        Material mat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geom.setMaterial(mat);
        geom.setLocalTranslation(loc);
        return geom;
    }
    
    
    public int getCounter() {
        return counter;
    }
}

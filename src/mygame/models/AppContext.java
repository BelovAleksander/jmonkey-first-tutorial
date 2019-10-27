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

import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import java.util.Map;

/**
 *
 * @author whiterabbit
 */
public class AppContext {
    private final InputManager inputManager;
    private final AssetManager assetManager;
    private final Node rootNode;
    private final Camera camera;
    private final Map<String, Geometry> geometryMap;

    public AppContext(InputManager inputManager, AssetManager assetManager, 
            Node rootNode, Camera camera, Map<String, Geometry> geometryMap) {
        this.inputManager = inputManager;
        this.rootNode = rootNode;
        this.camera = camera;
        this.geometryMap = geometryMap;
        this.assetManager = assetManager;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public Node getRootNode() {
        return rootNode;
    }

    public Camera getCamera() {
        return camera;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public Map<String, Geometry> getGeometryMap() {
        return geometryMap;
    }

    @Override
    public String toString() {
        return "AppContext{" + "inputManager=" + inputManager + ", assetManager=" + assetManager + ", rootNode=" + rootNode + ", camera=" + camera + ", geometryMap=" + geometryMap + '}';
    }
}

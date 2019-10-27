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
package mygame.utils;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author whiterabbit
 */
public class Utils {
    
    
    public static Material getMaterial(AssetManager assetManager,
            String defName, ColorRGBA color) {
        Material material = new Material(assetManager, defName);
        material.setColor("Color", color);
        return material;
    }
    
    public static void attachGeometries(Node rootNode, Geometry... geometries) {
        for (Geometry g : geometries) {
            rootNode.attachChild(g);
        }
    }

    public static void attachGeometries(Node rootNode, List<Geometry> geometries) {
        for (Geometry g : geometries) {
            rootNode.attachChild(g);
        }
    }
    
    public static void attachToNode(Node node, Geometry... geometries) {
        for (Geometry g : geometries) {
            node.attachChild(g);
        }
    }

    public static void putGeometriesIntoMap(Map<String, Geometry> geometryMap, Geometry... geometries) {
        for (Geometry g : geometries) {
            geometryMap.put(g.getName(), g);
        }
    }
    
    public static void putGeometriesIntoMap(Map<String, Geometry> geometryMap,
            List<Geometry> geometries) {
        for (Geometry g : geometries) {
            geometryMap.put(g.getName(), g);
        }
    }
}

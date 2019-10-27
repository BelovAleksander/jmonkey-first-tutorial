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
package mygame.handlers;

import com.jme3.input.controls.ActionListener;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import java.util.Map;
import mygame.models.AppContext;

/**
 *
 * @author whiterabbit
 */
public class MyActionListener implements ActionListener {
    private final String MAPPING_COLOR;
    private final Map<String, Geometry> geometryMap;

    public MyActionListener(String mappingColor, AppContext context) {
        this.MAPPING_COLOR = mappingColor; 
        this.geometryMap = context.getGeometryMap();
    }
    
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals(MAPPING_COLOR) && !isPressed) {
            System.out.println("You triggered: " + name);
            for (String k : geometryMap.keySet()) {
                System.out.println(k);
                System.out.println(geometryMap.get(k));
                geometryMap.get(k)
                        .getMaterial().setColor("Color", ColorRGBA.randomColor());
            }

        }
    }
}

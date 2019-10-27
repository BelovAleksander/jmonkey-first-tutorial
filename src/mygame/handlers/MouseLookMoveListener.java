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

import com.jme3.collision.CollisionResults;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import mygame.models.AppContext;

/**
 *
 * @author whiterabbit
 */
public class MouseLookMoveListener implements AnalogListener {
    private final Node rootNode;
    private final Camera cam;
    
    public MouseLookMoveListener(AppContext context) {
        this.cam = context.getCamera();
        this.rootNode = context.getRootNode();
    }
    
    @Override
    public void onAnalog(String name, float intensity, float tpf) {
        System.out.println("You triggered: " + name);
        CollisionResults results = new CollisionResults();
        Ray ray = new Ray(cam.getLocation(), cam.getDirection());
        rootNode.collideWith(ray, results);

        for (int i = 0; i < results.size(); i++) {
            float dist = results.getCollision(i).getDistance();
            Vector3f pt = results.getCollision(i).getContactPoint();
            String target = results.getCollision(i).getGeometry().
                    getName();
            System.out.println("Selection: #" + i + ": " + target
                    + " at " + pt + ", " + dist + " WU away.");
        }
        if (results.size() > 0) {
            System.out.println("OOOOOOOOOOOOOOOOOOOOOOO");
            Geometry target = results.getCollision(1).getGeometry();
            System.out.println("THIS IS " + target);
            if (target.getName().contains("Tower")) {
                System.out.println("TTTTTTTTTTTTTTTTTTT");
                target.move(intensity, intensity, 0); // rotate left
            } else if (target.getName().contains("Creep")) {
                System.out.println("LLLLLLLLLLLLLL");
                target.move(-intensity, -intensity, 0); // rotate right
            }
        } else {
            System.out.println("Selection: Nothing");
        }
    }
}

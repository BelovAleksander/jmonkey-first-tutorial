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
import com.jme3.input.InputManager;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import mygame.models.AppContext;

/**
 *
 * @author whiterabbit
 */
public class MousePointerAnalogListener implements AnalogListener {

    private final String MAPPING_ROTATE;
    private final InputManager inputManager;
    private final Camera cam;
    private final Node rootNode;

    public MousePointerAnalogListener(AppContext context, String action) {
        this.cam = context.getCamera();
        this.MAPPING_ROTATE = action;
        this.inputManager = context.getInputManager();
        this.rootNode = context.getRootNode();
    }

    @Override
    public void onAnalog(String name, float intensity, float tpf) {
        if (name.equals(MAPPING_ROTATE)) {
            CollisionResults results = new CollisionResults();
            Vector2f click2d = inputManager.getCursorPosition();
            Vector3f click3d = cam.getWorldCoordinates(
                    new Vector2f(click2d.getX(), click2d.getY()), 0f);
            Vector3f dir = cam.getWorldCoordinates(
                    new Vector2f(click2d.getX(), click2d.getY()), 1f).
                    subtractLocal(click3d);
            Ray ray = new Ray(click3d, dir);
            rootNode.collideWith(ray, results);
            if (results.size() > 0) {
                Geometry target = results.getCollision(1).getGeometry();
                target.rotate(0, -intensity, 0); // rotate left
            }
        } else {
            System.out.println("Selection: Nothing");
        }
    }
}

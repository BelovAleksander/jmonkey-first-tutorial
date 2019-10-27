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
package mygame.controllers;

import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import mygame.models.AppContext;

/**
 *
 * @author whiterabbit
 */
public class CubeChaserControl extends AbstractControl {
    private final Camera cam;
    private final Node rootNode;
    private final Ray ray = new Ray();
    
    
    public CubeChaserControl(AppContext context) {
        this.cam = context.getCamera();
        this.rootNode = context.getRootNode();
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        CollisionResults results = new CollisionResults();
        ray.setOrigin(cam.getLocation());
        ray.setDirection(cam.getDirection());
        rootNode.collideWith(ray, results);
        if (results.size() > 0) {
            Geometry target = results.getClosestCollision().getGeometry();
            if (target.equals(spatial)) {
                if (cam.getLocation().distance(spatial.getLocalTranslation())
                        < 10) {
                    spatial.move(cam.getDirection());
                }
            }
        }
    }

    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public Control cloneForSpatial(Spatial spatial) {
        throw new UnsupportedOperationException(
                "Not supported yet.");
    }
}

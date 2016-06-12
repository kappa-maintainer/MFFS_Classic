package com.mffs.model.items.modeConf;

import codechicken.lib.vec.Vector3;
import com.mffs.api.IFieldInteraction;
import com.mffs.api.IProjector;
import com.mffs.api.render.ModelPlane;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Calclavia
 */
public class ModeTube extends ModeCube {

    @Override
    public Set<Vector3> getExteriorPoints(IFieldInteraction projector) {
        Set<Vector3> fieldBlocks = new HashSet();
        ForgeDirection direction = projector.getDirection();
        Vector3 posScale = projector.getPositiveScale();
        Vector3 negScale = projector.getNegativeScale();

        for (double x = -negScale.x; x <= posScale.x; x += 0.5F) {
            for (double z = -negScale.z; z <= posScale.z; z += 0.5F) {
                for (double y = -negScale.y; y <= posScale.y; y += 0.5F) {
                    if ((direction != ForgeDirection.UP) && (direction != ForgeDirection.DOWN) && (y == -(int) Math.floor(negScale.y) || (y == (int) Math.floor(posScale.y)))) {
                        fieldBlocks.add(new Vector3(x, y, z));


                    } else if ((direction != ForgeDirection.NORTH) && (direction != ForgeDirection.SOUTH) && ((z == -(int) Math.floor(negScale.z)) || (z == (int) Math.floor(posScale.z)))) {
                        fieldBlocks.add(new Vector3(x, y, z));


                    } else if ((direction != ForgeDirection.WEST) && (direction != ForgeDirection.EAST) && ((x == -(int) Math.floor(negScale.x)) || (x == (int) Math.floor(posScale.x)))) {
                        fieldBlocks.add(new Vector3(x, y, z));
                    }
                }
            }
        }
        return fieldBlocks;
    }

    @SideOnly(Side.CLIENT)

    public void render(IProjector projector, double x, double y, double z, float f, long ticks) {
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        GL11.glTranslatef(-0.5F, 0.0F, 0.0F);
        ModelPlane.INSTNACE.render();
        GL11.glTranslatef(1.0F, 0.0F, 0.0F);
        ModelPlane.INSTNACE.render();
        GL11.glTranslatef(-0.5F, 0.0F, 0.0F);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.5F, 0.0F, 0.0F);
        ModelPlane.INSTNACE.render();
        GL11.glTranslatef(-1.0F, 0.0F, 0.0F);
        ModelPlane.INSTNACE.render();
    }
}

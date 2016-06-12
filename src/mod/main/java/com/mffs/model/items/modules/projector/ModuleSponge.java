package com.mffs.model.items.modules.projector;

import codechicken.lib.vec.Vector3;
import com.mffs.api.IProjector;
import com.mffs.model.items.modules.ItemModule;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;

import java.util.Set;

/**
 * @author Calclavia
 */
public class ModuleSponge extends ItemModule {

    /**
     * Initialize constructor so we can set attributes.
     */
    public ModuleSponge() {
        super();
        setMaxStackSize(1);
    }

    /**
     * Calls this on projection.
     *
     * @param projector The projector interface.
     * @param fields    A set of fields that are projected.
     * @return
     */
    @Override
    public boolean onProject(IProjector projector, Set<Vector3> fields) {
        World world;
        if (projector.getTicks() % 60L == 0L) {
            world = ((TileEntity) projector).getWorldObj();

            if (!world.isRemote) {
                for (Vector3 point : projector.getInteriorPoints()) {
                    Block block = world.getBlock((int) Math.floor(point.x), (int) Math.floor(point.y), (int) Math.floor(point.z));

                    if (((block instanceof BlockLiquid)) || ((block instanceof BlockFluidBase))) {
                        world.setBlock((int) Math.floor(point.x), (int) Math.floor(point.y), (int) Math.floor(point.z), Blocks.air);
                    }
                }
            }
        }
        return super.onProject(projector, fields);
    }

    @Override
    public boolean requireTicks(ItemStack moduleStack) {
        return true;
    }
}

package com.learnchemistry.createsciences.item;

import com.learnchemistry.createsciences.cuboid.CuboidParticleToolHooks;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class CuboidParticleWandItem extends Item {
    public CuboidParticleWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (context.isSecondaryUseActive()) {
            if (level.isClientSide()) {
                CuboidParticleToolHooks.openSettingsScreen();
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        if (level.isClientSide()) {
            CuboidParticleToolHooks.spawnTestingLiquid(context.getClickLocation(), context.getClickedFace());
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (level.isClientSide()) {
            CuboidParticleToolHooks.openSettingsScreen();
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}

package com.lothrazar.customgamerules.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.lothrazar.customgamerules.GameRuleMod;
import com.lothrazar.customgamerules.RuleRegistry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

@Mixin(Biome.class)
public class BiomeAntiFreezeMixin {

  @Inject(at = @At("HEAD"), method = "doesWaterFreeze(Lnet/minecraft/world/IWorldReader;Lnet/minecraft/util/math/BlockPos;Z)Z", cancellable = true)
  public void doesWaterFreeze(IWorldReader worldIn, BlockPos water, boolean mustBeAtEdge, CallbackInfoReturnable<Boolean> info) {
    //if disable == true, then  
    if (worldIn instanceof World &&
        RuleRegistry.isEnabled((World) worldIn, RuleRegistry.disableBiomeFreezeIce)) {
      info.setReturnValue(false);
      GameRuleMod.info("BiomeAntiFreezeMixin mixin success and disableBiomeFreezeIce=true");
    }
  }
}

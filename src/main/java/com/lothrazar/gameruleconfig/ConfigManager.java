package com.lothrazar.gameruleconfig;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraft.world.GameRules;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;

public class ConfigManager {

  public static List<GameRuleBoolWrapper> boolList = new ArrayList<>();
  public static List<GameRuleIntWrapper> intList = new ArrayList<>();
  private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
  private static ForgeConfigSpec COMMON_CONFIG;
  public static BooleanValue KEEP_EXP;
  static {
    initConfig();
  }

  private static void initConfig() {
    COMMON_BUILDER.comment("General settings").push(GameRuleMod.MODID);
    boolList.add(new GameRuleBoolWrapper(GameRules.DO_FIRE_TICK, false));
    boolList.add(new GameRuleBoolWrapper(GameRules.MOB_GRIEFING, false));
    boolList.add(new GameRuleBoolWrapper(GameRules.KEEP_INVENTORY, false));
    boolList.add(new GameRuleBoolWrapper(GameRules.DO_MOB_SPAWNING, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.DO_MOB_LOOT, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.DO_TILE_DROPS, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.DO_ENTITY_DROPS, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.COMMAND_BLOCK_OUTPUT, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.NATURAL_REGENERATION, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.DO_DAYLIGHT_CYCLE, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.LOG_ADMIN_COMMANDS, false));
    boolList.add(new GameRuleBoolWrapper(GameRules.SHOW_DEATH_MESSAGES, true));
    intList.add(new GameRuleIntWrapper(GameRules.RANDOM_TICK_SPEED, 3));
    boolList.add(new GameRuleBoolWrapper(GameRules.SEND_COMMAND_FEEDBACK, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.REDUCED_DEBUG_INFO, false));
    // trigger internal
    boolList.add(new GameRuleBoolWrapper(GameRules.SPECTATORS_GENERATE_CHUNKS, false));
    intList.add(new GameRuleIntWrapper(GameRules.SPAWN_RADIUS, 10));
    boolList.add(new GameRuleBoolWrapper(GameRules.DISABLE_ELYTRA_MOVEMENT_CHECK, true));
    intList.add(new GameRuleIntWrapper(GameRules.MAX_ENTITY_CRAMMING, 24));
    boolList.add(new GameRuleBoolWrapper(GameRules.DO_WEATHER_CYCLE, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.DO_LIMITED_CRAFTING, false));
    intList.add(new GameRuleIntWrapper(GameRules.MAX_COMMAND_CHAIN_LENGTH, 65536));
    boolList.add(new GameRuleBoolWrapper(GameRules.ANNOUNCE_ADVANCEMENTS, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.DISABLE_RAIDS, false));
    boolList.add(new GameRuleBoolWrapper(GameRules.DO_INSOMNIA, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.DO_IMMEDIATE_RESPAWN, false));
    //
    boolList.add(new GameRuleBoolWrapper(GameRules.DROWNING_DAMAGE, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.FALL_DAMAGE, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.FIRE_DAMAGE, true));
    boolList.add(new GameRuleBoolWrapper(GameRules.field_230127_D_, true));//patrol
    boolList.add(new GameRuleBoolWrapper(GameRules.field_230128_E_, true));//trader
    for (GameRuleBoolWrapper rule : boolList) {
      rule.config = COMMON_BUILDER.comment("Default value for this gamerule")
          .define("default." + rule.getName(),
              rule.getDefaultValue());
    }
    for (GameRuleIntWrapper rule : intList) {
      rule.config = COMMON_BUILDER.comment("Default value for this gamerule")
          .defineInRange("default." + rule.getName(),
              rule.getDefaultValue(), 0, 999999999);
    }
    KEEP_EXP = COMMON_BUILDER.comment("If the keepInventory gamerule is true"
        + " and keepExperience is false, you no longer keep any experience it is gone on death.")
        .define("change." + GameRules.KEEP_INVENTORY.getName() + ".keepExperience", false);
    COMMON_BUILDER.pop();
    COMMON_CONFIG = COMMON_BUILDER.build();
  }

  public ConfigManager(Path path) {
    final CommentedFileConfig configData = CommentedFileConfig.builder(path)
        .sync()
        .autosave()
        .writingMode(WritingMode.REPLACE)
        .build();
    configData.load();
    COMMON_CONFIG.setConfig(configData);
  }
}

package data.hullmods;


import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.impl.hullmods.BaseLogisticsHullMod;
import org.magiclib.util.MagicIncompatibleHullmods;

public class Cast_Armor extends BaseLogisticsHullMod {

	public static float CORONA_EFFECT_MULT = 0f;

	public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
		if (index == 0) return "" + (int) Math.round(ARMOR_MULT * 100f) + "%";
		if (index == 1) return "" + (int) Math.round((1f - CORONA_EFFECT_MULT) * 100f) + "%";
		return null;
	}

	public static float ARMOR_MULT = 1.3f;

	public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getEffectiveArmorBonus().modifyMult(id, ARMOR_MULT);
		stats.getMinArmorFraction().modifyMult(id, ARMOR_MULT);
		MagicIncompatibleHullmods.removeHullmodWithWarning(stats.getVariant(), "solar_shielding", "Cast_Armor");
		MagicIncompatibleHullmods.removeHullmodWithWarning(stats.getVariant(), "heavyarmor", "Cast_Armor");
		MagicIncompatibleHullmods.removeHullmodWithWarning(stats.getVariant(), "converted_hangar", "Cast_Armor");
		MagicIncompatibleHullmods.removeHullmodWithWarning(stats.getVariant(), "ablative_armor", "Cast_Armor");
	}

}

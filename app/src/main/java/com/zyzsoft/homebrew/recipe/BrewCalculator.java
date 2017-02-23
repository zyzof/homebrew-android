package com.zyzsoft.homebrew.recipe;

import android.util.FloatMath;

import com.zyzsoft.homebrew.data.SrmColours;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BrewCalculator {
	
	public static float calculateOriginalGravity(Recipe recipe) {
		Map<Fermentable, Float> fermentableQuantities = recipe.getFermentables();	//Quantity in kg
		float volume = recipe.getMashVolume();	//Litres
		float brewhouseEfficiency = recipe.getBrewhouseEfficiency();
        if (brewhouseEfficiency > 1) {  //Assume entered as a whole number
            brewhouseEfficiency /= 100.f;
        }
		float gravity = 0;
		
		//Sum gravity values
		for (Fermentable fermentable : fermentableQuantities.keySet()) {
			float gravityGramsPerLitre = fermentable.getGravity() - 1;	//1 comes from the water, so remove.
            float quantityGrams = fermentableQuantities.get(fermentable) * 1000;
			float gravityContribution = (gravityGramsPerLitre * quantityGrams) / volume;
			gravity += gravityContribution;
		}
		
		//Scale by efficiency value
		gravity = gravity * brewhouseEfficiency;
		return gravity + 1;
	}
	
	public static float calculateBrewhouseEfficiency(float actualOriginalGravity, Recipe recipe) {
		return 0.0f;
	}
	
	/**
	 * Cheers to John Palmer: http://www.howtobrew.com/section1/chapter5-5.html
	 * @param recipe
	 * @return
	 */
	public static float calculateIBU(Recipe recipe) {
		float volume = recipe.getBoilVolume();
		float og = recipe.getOriginalGravity();
		
		float ibu = 0.0f;
		
		List<Hop> hops = recipe.getHops();
		for (Hop hop : hops) {
			float alphaAcidContent = hop.getAlphaAcidContent();
			float quantityGrams = hop.getQuantityGrams();
			
			float utilisation = getHopUtilisation(hop, og);
			float alphaAcidContribution = alphaAcidContent * quantityGrams;
			
			float ibuContribution = alphaAcidContribution * utilisation * 10 / volume;	//10 is to get to mg/L.
			
			ibu += ibuContribution;
		}
		
		return ibu;
	}
	
	private static float getHopUtilisation(Hop hop, float originalGravity) {
		int boilMinutes = hop.getBoilMinutes();
		
		float gravityFactor = getGravityFactor(originalGravity);
		float timeFactor = getTimeFactor(boilMinutes);
		
		float hopUtilisation = gravityFactor * timeFactor;
		
		return hopUtilisation;
	}
	
	private static float getGravityFactor(float originalGravity) {
		return (float) (1.65 * Math.pow(0.000125f, originalGravity - 1));
	}
	
	private static float getTimeFactor(int boilMinutes) {
		float MAX_UTILISATION_CONTROL = 4.15f;
		
		return (float) ((1 - Math.pow(Math.E, -0.04 * boilMinutes)) / MAX_UTILISATION_CONTROL);
	}
	
	public static float calculateSRM(Recipe recipe) {
		Map<Fermentable, Float> fermentableQuantities = recipe.getFermentables();   //KG
		float volume = recipe.getMashVolume();
		
		float lovibond = 0;		
		for (Fermentable fermentable : fermentableQuantities.keySet()) {
			float lovibondGramsPerLitre = fermentable.getDegreesLovibond();
            float quantityGrams = fermentableQuantities.get(fermentable) * 1000;
			float lovibondContribution = (lovibondGramsPerLitre * quantityGrams) / volume;
			lovibond += lovibondContribution; 
		}
		
		//float srm = lovibond * 1.35f - 0.6f;	//Cheers to Biermann on http://www.homebrewtalk.com/f14/lovibond-srm-28230/
        double srm = 1.4922 * (Math.pow(lovibond, 0.6859)); //Sorry Biermann. Use the Morey equation: http://brewwiki.com/index.php/Estimating_Color
		return (float)srm;
	}

    public static int getSrmColour(float srmValue) {
        Map<Integer, Integer> srmColoursByThreshold = SrmColours.ColourThresholds;

        List<Integer> srmColourThresholds = new ArrayList<Integer>();
        srmColourThresholds.addAll(srmColoursByThreshold.keySet());

        int colour = -1;

        for (int i = 0; i < srmColourThresholds.size() - 1; i++) {
            int thresh1 = srmColourThresholds.get(i);
            int thresh2 = srmColourThresholds.get(i + 1);

            if (srmValue < thresh1) {
                colour = srmColoursByThreshold.get(thresh1);
                break;
            } else if (srmValue >= thresh1 && srmValue < thresh2) {
                //TODO: linear interpolation between colour values
                colour = srmColoursByThreshold.get(thresh2);
                break;
            } else if (srmValue > thresh2) {
                colour = srmColoursByThreshold.get(thresh2);
            }
        }
        return colour;
    }
	
	public static float calculatePostBoilVolume(float preBoilVolume, float boilTime) {
		return 0.0f;
	}
	
	public static float calculateEstimateFinalGravity(float originalGravity, float yeastAttenuation) {
		return 1.005f;   //TODO
	}
	
	public static float calculateABV(float originalGravity, float finalGravity) {
		return (originalGravity - finalGravity) * 131;
	}
}

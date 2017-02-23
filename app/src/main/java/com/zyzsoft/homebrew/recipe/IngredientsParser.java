package com.zyzsoft.homebrew.recipe;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.content.res.AssetManager;

public class IngredientsParser {
	private static final float POUNDS_PER_GALLON_TO_GRAMS_PER_LITRE = 119.826427f;    //US
    //private static final float POUNDS_PER_GALLON_TO_GRAMS_PER_LITRE = 99.7763314f;      //Imperial

    private static List<Fermentable> s_fermentables;

	public static List<Fermentable> readFermentables(Context context) {
        if (s_fermentables != null) {
            return s_fermentables;
        }
		s_fermentables = new ArrayList<Fermentable>();
		AssetManager assetMgr = context.getAssets();
		
		InputStream fermentablesXml;
		DocumentBuilderFactory docBuilderFactory;
		DocumentBuilder docBuilder;
		Document doc = null;
		
		try {
			fermentablesXml = assetMgr.open("fermentables.xml");
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(fermentablesXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		doc.getDocumentElement().normalize();
		
		NodeList fermentableNodes = doc.getElementsByTagName("fermentable");
		
		for (int i = 0; i < fermentableNodes.getLength(); i++) {
			Element el = (Element)fermentableNodes.item(i);
			
			String name = el.getElementsByTagName("name").item(0).getTextContent();
			
			float gravityImperial = Float.parseFloat(el.getElementsByTagName("gravity").item(0).getTextContent());
			float gravity = ((gravityImperial - 1) / POUNDS_PER_GALLON_TO_GRAMS_PER_LITRE) + 1;	//Convert lbs/gal to g/L
			
			float lovibondImperial = Float.parseFloat(el.getElementsByTagName("lovibond").item(0).getTextContent());
			float lovibond = lovibondImperial / POUNDS_PER_GALLON_TO_GRAMS_PER_LITRE;
			
			String description = el.getElementsByTagName("description").item(0).getTextContent();
			
			Fermentable f = new Fermentable(name, gravity, lovibond, description);
			s_fermentables.add(f);
		}
		
		Collections.sort(s_fermentables, new Comparator<Fermentable>() {
            @Override
            public int compare(Fermentable fermentable, Fermentable fermentable2) {
                //Ensure "Select..." goes to top:
                if (fermentable.getName().equals("Select...")) {
                    return -1;
                } else if (fermentable2.getName().equals("Select...")) {
                    return 1;
                }
                return fermentable.getName().compareTo(fermentable2.getName());
            }
        });

        return s_fermentables;
	}
}

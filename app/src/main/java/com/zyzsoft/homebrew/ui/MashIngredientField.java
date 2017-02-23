package com.zyzsoft.homebrew.ui;

import java.text.DecimalFormat;
import java.util.List;

import com.zyzsoft.homebrew.R;
import com.zyzsoft.homebrew.RecipeManager;
import com.zyzsoft.homebrew.R.id;
import com.zyzsoft.homebrew.recipe.Fermentable;
import com.zyzsoft.homebrew.recipe.IngredientsParser;
import com.zyzsoft.homebrew.recipe.Recipe;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class MashIngredientField extends LinearLayout {

    private static final DecimalFormat FLOAT_FORMAT = new DecimalFormat("#.###");
	
	private Context _context;
	private MashIngredientsEditor _container;
    private ArrayAdapter<Fermentable> _adapter;
    private List<Fermentable> _fermentables;
    private Spinner _fermentablesDropdown;
	private Fermentable _fermentableMostRecentlySelected;
	private EditText _quantityField;
	private float _quantityMostRecentlyPresent;
	private ImageButton _removeIngredientButton;
	
	public MashIngredientField(Context context) {
	    super(context);
	    _context = context;
	}
	
	public MashIngredientField(Context context, AttributeSet attribs) {
		super(context, attribs);
		_context = context;
	}

	/*public MashIngredientField(Context context, AttributeSet attrs, int defStyle) {
	    super(context, attrs, defStyle);
	    _context = context;
	}*/

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        Utils.initKeyboardDismissEvents(this);
    }
	
	/**
	 * Necessary since we can't call findViewById until we've inflated an instance of this class.
	 */
	public void initialiseFields(MashIngredientsEditor containingEditor) {
		_container = containingEditor;
		
		initMashIngredientsDropdown();
		initQuantitySelector();
		initRemoveButton();
	}

    public void initialiseListeners() {
        initMashIngredientsDropdownListener();
        initQuantitySelectorListener();
        initRemoveButtonListener();
    }

    private void initMashIngredientsDropdown() {
		
		_fermentablesDropdown = (Spinner)findViewById(R.id.mashIngredientsDropdown);//new Spinner(_context);

		_fermentables = IngredientsParser.readFermentables(_context);
		_adapter = new ArrayAdapter<Fermentable>(
				_context, android.R.layout.simple_spinner_item, _fermentables
		);
		_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		_fermentablesDropdown.setAdapter(_adapter);
	}

    private void initMashIngredientsDropdownListener() {
        _fermentablesDropdown.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (position == 0) {	//0 is a placeholder for the "Select an item" item.
                    return;
                }
                Fermentable selectedFermentable = _fermentables.get(position);
                float quantity = getQuantityValue();

                Recipe currentRecipe = RecipeManager.getCurrentRecipe();
                currentRecipe.removeFermentable(_fermentableMostRecentlySelected, quantity);
                currentRecipe.addFermentable(selectedFermentable, quantity);
                _container.updateMashStats();

                _fermentableMostRecentlySelected = selectedFermentable;
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                //do nothing.
            }
        });
    }
	
	private void initQuantitySelector() {
		_quantityField = (EditText)findViewById(R.id.mashIngredientsQuantity);//new EditText(_context);
	}

    private void initQuantitySelectorListener() {
        final MashIngredientField self = this;
        _quantityField.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (_fermentablesDropdown.getSelectedItemPosition() == 0) {
                        return;
                    }
                    float newQuantity = getQuantityValue();
                    Recipe currentRecipe = RecipeManager.getCurrentRecipe();
                    Fermentable currentlySelectedFermentable = self.getSelectedFermentable();
                    currentRecipe.removeFermentable(currentlySelectedFermentable, _quantityMostRecentlyPresent);
                    currentRecipe.addFermentable(currentlySelectedFermentable, newQuantity);
                    _container.updateMashStats();

                    _quantityMostRecentlyPresent = newQuantity;
                } catch (NumberFormatException nfe) {

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {}

            public void afterTextChanged(Editable s) {}
        });
    }


    private void initRemoveButtonListener() {
        final MashIngredientField self = this;
        _removeIngredientButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                ((ViewGroup)getParent()).removeView(self);
                RecipeManager.getCurrentRecipe().removeFermentable(
                        self.getSelectedFermentable(), self.getQuantityValue());
                _container.updateMashStats();
            }
        });
    }
	
	public float getQuantityValue() {
        float quantity = 0.f;
        try {
            quantity = Float.parseFloat(_quantityField.getText().toString());
        } catch (NumberFormatException nfe) { }

        return quantity;
	}
	
	public Fermentable getSelectedFermentable() {
		return (Fermentable)_fermentablesDropdown.getSelectedItem();
	}
	
	private void initRemoveButton() {
		_removeIngredientButton = (ImageButton)findViewById(R.id.removeIngredient);
	}

    public void setSelectedFermentable(Fermentable fermentable) {
        int indexOfFermentable = _adapter.getPosition(fermentable);
        _fermentablesDropdown.setSelection(indexOfFermentable);
        _fermentableMostRecentlySelected = fermentable;
    }

    public void setQuantity(Float quantity) {
        _quantityField.setText(FLOAT_FORMAT.format(quantity));
        _quantityMostRecentlyPresent = quantity;
    }
}

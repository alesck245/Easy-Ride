package br.com.henrique.ride.util;

import java.text.NumberFormat;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MonetaryMask implements TextWatcher {
	  
	private final EditText mText;
	private NumberFormat mFormatter;
	private boolean isUpdating;
	
	public MonetaryMask(EditText mText) {
		this.mText = mText;
		this.mFormatter = NumberFormat.getCurrencyInstance();
	}
 
	@Override
	public void afterTextChanged(Editable s) {
		
	}
 
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
	}
 
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		 if (isUpdating) {
			 isUpdating = false;
			 return;
		 } 
		 isUpdating = true;
		 String str = s.toString();
		 str = str.replaceAll("[^\\d]", ""); 
		 try {
			 str = this.mFormatter.format(Double.parseDouble(str) / 100);
			 this.mText.setText(str);
			 this.mText.setSelection(this.mText.getText().length());
		 } 
		 catch(NumberFormatException e) {
			 s = "";
		 }
	}
}
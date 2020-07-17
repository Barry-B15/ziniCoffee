package com.example.android.zinicoffee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {  //ActionBarActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }


    /**
     * This method is called when the ORDER button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        CheckBox matchaCheckBox = (CheckBox) findViewById(R.id.matcha);
        boolean hasMatcha = matchaCheckBox.isChecked();

        EditText userNameText = (EditText) findViewById(R.id.enter_name);
        String buyerName = userNameText.getText().toString();

        Log.v("MainActivity", "Has Matcha " + hasMatcha);
        //this log prints in the Android monitor, "has whippped cream true or false"

        // Calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate, hasMatcha);

        // Display the order summary on the screen
        String priceMessage = createOrderSummary(buyerName, price, hasWhippedCream, hasChocolate,
                hasMatcha);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, emailId); //we don't need this line
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, buyerName));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the total price of the order.
     * // $5 is the price per cup of coffee
     * //@param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate, boolean addMatcha) {
        int basePrice = 5; //price for 1 cup of coffee
        if (addWhippedCream) {
            basePrice += 1; // add $1 extra for whipped cream
        }
        if (addChocolate) {
            basePrice += 2; // add $2 extra for chocolate
        }
        if (addMatcha) {
            basePrice += 3; // add $3 extra for matcha
        }

        // calculate total order price by multiplying the quantity
        // int price = quantity * 5;
        return quantity * basePrice;
    }

    /**
     * This method creates a summary of the order.
     *
     * @return total price
     * @param addWhippedCream states if whipped cream is added or not
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate,
                                      boolean addMatcha) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_matcha, addMatcha);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(price));
        priceMessage +=  "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}

/*
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
*/

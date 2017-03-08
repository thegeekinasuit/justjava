package com.thegeekinasuit.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int numberOfCoffees = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedCreamCheckBox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocholateCheckBox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        String nameBuyer = nameEditText.getText().toString();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, nameBuyer);
//        displayMessage(priceMessage);
        String emailAddress[] = {"thegeekinasuit@gmail.com"};
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL,emailAddress);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Coffee Order for " + nameBuyer);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * This method calculates the price of the order
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = 5;
        if (hasWhippedCream) {
            price += 1;
        }
        if (hasChocolate) {
            price += 1;
        }
        price = price * numberOfCoffees;
        return price;
    }

    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String nameBuyer) {
        return "Name: " + nameBuyer +
                "\nAdd Whipped Cream?: " + hasWhippedCream +
                "\nAdd Chocolate?: " + hasChocolate +
                "\nQuantity: " + numberOfCoffees +
                "\nTotal: $" + price +
                "\nThank you!";
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void incrementOrder(View view) {
        if (numberOfCoffees != 100) {
            numberOfCoffees = numberOfCoffees + 1;
            displayQuantity(numberOfCoffees);
        }
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrementOrder(View view) {
        if (numberOfCoffees != 1) {
            numberOfCoffees = numberOfCoffees - 1;
            displayQuantity(numberOfCoffees);
        }
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}
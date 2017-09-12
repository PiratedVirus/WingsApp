package com.saurabh.wings2017;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.anwarshahriar.calligrapher.Calligrapher;

public class CustomList extends ArrayAdapter<String> {

    SweetAlertDialog pDialog;
    int cart_sum =0 ;

    private final Activity context;
    private final ArrayList userName_list;
    private final ArrayList eventName_list;
    private final ArrayList eventID_list;
    private final ArrayList eventPrice_list;
    private final ArrayList uniqueID_list;
    private final ArrayList eventLocation;
    TextView t, add_event;
    ImageView emptycart,explore,checkout;
    public static final String PHP_GET_CART = "https://scouncilgeca.com/wingsapp/getCartData.php";
    public static final String PHP_DELETE_CART = "https://scouncilgeca.com/wingsapp/deleteEventCart.php";





    public CustomList(Activity context,
                      ArrayList userName_list, ArrayList eventName_list, ArrayList eventID_list, ArrayList eventPrice_list, ArrayList uniqueID_list, ArrayList eventLocation, TextView t,
                                ImageView emptycart , ImageView explore, ImageView checkout, TextView add_event) {
        super(context, R.layout.cart_list, userName_list);
        this.context = context;
        this.userName_list = userName_list;
        this.eventName_list = eventName_list;
        this.eventID_list = eventID_list;
        this.eventPrice_list = eventPrice_list;
        this.uniqueID_list = uniqueID_list;
        this.eventLocation = eventLocation;
        this.t = t;
        this.emptycart = emptycart;
        this.explore = explore;
        this.checkout = checkout;
        this.add_event = add_event;
        Log.e("PV","bochya"+userName_list+eventName_list);
    }





    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        Log.e("PV","xyz");
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView= inflater.inflate(R.layout.cart_list, null, true);
        rowView.setMinimumHeight(80);
        rowView.setBackgroundColor(Color.WHITE);

        Calligrapher calligrapher = new Calligrapher(getContext());
        calligrapher.setFont((Activity) getContext(), "fonts/mont.ttf", true);







        cart_sum = 0;
        TextView CartEventName = (TextView) rowView.findViewById(R.id.CartEventName);
        TextView CartEventInfo = (TextView) rowView.findViewById(R.id.CartEventInfo);
        TextView CartEventLocation = (TextView) rowView.findViewById(R.id.CartEventLocation);
        final TextView pricetag = (TextView) rowView.findViewById(R.id.PriceTag);
        final TextView uniqueID = (TextView) rowView.findViewById(R.id.uniqueID);
        TextView remove = (TextView)rowView.findViewById(R.id.removeBtn);


        Typeface mont = Typeface.createFromAsset(getContext().getAssets(),  "fonts/mont.ttf");
        CartEventInfo.setTypeface(mont);
        CartEventName.setTypeface(mont);
        CartEventLocation.setTypeface(mont);
        pricetag.setTypeface(mont);
        remove.setTypeface(mont);



        for(int i=0;i<eventPrice_list.size();i++)
        {
            Log.e("PV","sum="+eventPrice_list.get(i));
            cart_sum+=Integer.parseInt(String.valueOf(eventPrice_list.get(i)));
        }


        rowView.setBackgroundColor(Color.WHITE);
        CartEventInfo.setText((CharSequence)eventID_list.get(position));
        CartEventName.setText((CharSequence)eventName_list.get(position));
        CartEventLocation.setText((CharSequence)eventLocation.get(position));
        pricetag.setText((CharSequence)eventPrice_list.get(position));
        uniqueID.setText((CharSequence)uniqueID_list.get(position));



        rowView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e("BC","RowView Clicked ==="+uniqueID_list.get(position)+"price="+Integer.parseInt((String) pricetag.getText()));




               // cart_sum = cart_sum - Integer.parseInt((String) pricetag.getText());
                //total.setText("Amount   ₹" + String.valueOf(cart_sum));


                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Cancel Event !")
                        .setContentText("Do your really want to cancel this event?")
                        .setConfirmText("DELETE")
                        .setCancelText("Go back")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                eventName_list.remove(position);
                                eventID_list.remove(position);
                                eventPrice_list.remove(position);
                                userName_list.remove(position);

                                sweetAlertDialog.dismissWithAnimation();



                                pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                                pDialog.setTitleText("Please wait!");
                                pDialog.setContentText("Deleting Event from Cart...");
                                pDialog.setCancelable(false);
                                pDialog.show();


                                // Log.e("PV","BOcha is " +uniqueID);
                                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                        PHP_DELETE_CART,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                            }
                                        },
                                        new Response.ErrorListener(){
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                                            }
                                        }){
                                    public static final String TAG = "PV";

                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {

                                        Map<String,String> params = new HashMap<>();
                                        String temp = ((TextView) rowView.findViewById(R.id.uniqueID)).getText().toString();
                                        params.put("uniqueId", temp);

                                        return params;
                                    }


                                };

                                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                requestQueue.add(stringRequest);
                                requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<String>() {
                                    @Override
                                    public void onRequestFinished(Request<String> request) {
                                        pDialog.dismissWithAnimation();

                                    }
                                });

                                cart_sum = cart_sum - Integer.parseInt((String) pricetag.getText());
                                Log.e("Sum ", "cart sum after deleteion = "+cart_sum);
                                t.setText("Amount   ₹" + String.valueOf(cart_sum));
                                uniqueID_list.remove(position);
                                notifyDataSetChanged();

                                if(cart_sum==0)
                                {
                                    emptycart.setVisibility(View.VISIBLE);
                                    explore.setVisibility(View.VISIBLE);
                                    add_event.setVisibility(View.VISIBLE);
                                    checkout.setVisibility(View.GONE);
                                    t.setVisibility(View.GONE);
                                }

                            }
                        })
                        .show();

                return true;
            }
        });

        Log.e("PV", "yes"+userName_list.get(position)+eventName_list.get(position));
        return rowView;
    }

}
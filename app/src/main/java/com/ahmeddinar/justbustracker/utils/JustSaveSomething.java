package com.ahmeddinar.justbustracker.utils;

import android.util.Log;
import android.widget.TextView;

import com.ahmeddinar.justbustracker.rest.RestCient;
import com.ahmeddinar.justbustracker.rest.model.BusLocation;
import com.ahmeddinar.justbustracker.rest.service.ApiService;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;

/**
 * Created by Ahmed Dinar on 6/29/2016.
 */
public class JustSaveSomething {

    TextView textView;

    public void nothing(){
        RestCient client = new RestCient();
        ApiService service = client.getApiService();


        Observable<List<BusLocation>> call = service.get();


        // Asynchronously execute HTTP request
      /*  call.enqueue(new Callback<List<BusLocation>>() {


            @Override
            public void onResponse(Response<List<BusLocation>> response, Retrofit retrofit) {


                // isSuccess is true if response code => 200 and <= 300
                if (!response.isSuccess()) {
                    // print response body if unsuccessful
                    try {
                        textView.setText(" unsuccess1\n" + response.errorBody().string());
                    } catch (IOException e) {
                        textView.setText(" unsuccess\n" + e.getMessage());
                    }
                    return;
                }

                List<BusLocation> res = response.body();

                if (res == null) {
                    textView.setText("bus Locationres null");
                    return;
                }

                for(BusLocation bus : res) {
                    textView.setText( textView.getText() + " " + bus.getId() + ". " + bus.getLatitude() + " " + bus.getLongitude() + "\n" );
                }



            }



            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure",t.toString());
                textView.setText("onFailure " + t.getMessage());
            }

        });*/
    }

}

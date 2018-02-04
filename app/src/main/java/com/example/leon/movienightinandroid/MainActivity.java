package com.example.leon.movienightinandroid;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.leon.movienightinandroid.databinding.ActivityMainBinding;
import com.example.leon.movienightinandroid.di.component.DaggerMainActivityComponent;
import com.example.leon.movienightinandroid.di.component.MainActivityComponent;
import com.example.leon.movienightinandroid.di.module.ActivityModule;



public class MainActivity extends AppCompatActivity {

    /*@Inject
    UrlContracts.TheMovieService movieService;
*/

    private MainActivityComponent activityComponent;
    public MainActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerMainActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(DemoApplication.get(this).getComponent())
                    .build();
        }
        return activityComponent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        User user = new User(BuildConfig.ACCESS_TOKEN_V4_AUTH, BuildConfig.API_KEY);
        binding.setUser(user);



    /*    movieService.listMovies(2).enqueue(new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {
                Page page = response.body();
                List<Movie> movies = page.results;
                for (Movie movie: movies) {
                    System.out.println(movie.original_title);
                }
            }

            @Override
            public void onFailure(Call<Page> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });*/

     /*   @Provides
    @ApplicationContext
    UrlContracts.TheMovieService provideMovieService() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl httpUrl = original.url();

                    HttpUrl newHttpUrl = httpUrl.newBuilder().addQueryParameter(UrlContracts.API_KEY_QUERY, BuildConfig.API_KEY).build();
                    Request.Builder requestBuilder = original.newBuilder().url(newHttpUrl);
                    Request request = requestBuilder.build();

                    return chain.proceed(request);
                }).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlContracts.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UrlContracts.TheMovieService movieService = retrofit.create(UrlContracts.TheMovieService.class);
        return movieService;
    }*/

    }



}

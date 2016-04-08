package uk.co.ribot.androidboilerplate.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by adhamenaya on 4/7/2016.
 */
public class Application {

    public static class Name{
        public String label;
    }
    public static class Image{
        public String label;
        public int height;
    }
    public static class Summary{
        public String label;
    }
    public static class Price{
        public Attributes attributes = new Attributes();
    }
    public static class Attributes{
        public String amount;
        public String currency;
    }
    public static class IdAttributes{

        @SerializedName("im:id")
        public String id;
    }
    public static class Id{
        public String label;

        @SerializedName("attributes")
        public IdAttributes idAttributes = new IdAttributes();
    }

    /*------------- Attributes of class application ------------------*/

    public Id id = new Id();

    @SerializedName("im:name")
    public Name name = new Name();

    @SerializedName("im:image")
    public ArrayList<Image> images;

    @SerializedName("summary")
    public Summary summary = new Summary();

    @SerializedName("im:price")
    public Price price = new Price();

    public Category category = new Category();
}
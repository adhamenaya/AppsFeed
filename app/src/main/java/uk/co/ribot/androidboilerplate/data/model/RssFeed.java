package uk.co.ribot.androidboilerplate.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adhamenaya on 4/7/2016.
 */
public class RssFeed {

    public class Feed{
        public ArrayList<Application> entry = new ArrayList<Application>();

        public List<Application> getApplications(){
            return entry;
        }
    }

    public Feed feed = new Feed();
}

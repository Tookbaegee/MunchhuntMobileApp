package munchhunt.munchhuntproject.Profile;

import android.support.annotation.NonNull;

import java.util.Comparator;

public class UserNameComparator implements Comparator<String> {


    @Override
    public int compare(String s, String t1) {
        return s.compareTo(t1);
    }
}

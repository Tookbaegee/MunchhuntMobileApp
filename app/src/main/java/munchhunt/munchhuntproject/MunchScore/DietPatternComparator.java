package munchhunt.munchhuntproject.MunchScore;

import java.util.Comparator;

import munchhunt.munchhuntproject.Objects.DietPattern;

public class DietPatternComparator implements Comparator<DietPattern> {
    @Override
    public int compare(DietPattern o1, DietPattern o2) {

        return  o2.getRestriction() - o1.getRestriction();
    }
}

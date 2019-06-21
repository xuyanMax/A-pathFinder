package entity;

import java.util.Comparator;

public class nodeGlengthComparator implements Comparator<location> {

    @Override
    public int compare(location a, location b) {
        //getFsteps()
        if (a.getFSteps() > b.getFSteps())
            return 1;
        else if (a.getFSteps() < b.getFSteps())
            return -1;

        else return 0;
    }

}

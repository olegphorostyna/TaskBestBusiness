package com.bestbusiness.android.task;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Fervill on 14.06.2018.
 */

public class Model {
    List<Integer> collection;
    public static Model sModel;

    private Model() {
        collection = new LinkedList<>();
    }

    public static Model getModel() {
        if (sModel != null)
            return sModel;
        else {
            sModel = new Model();
            return sModel;
        }
    }

    /**
     * Determine how to insert entry into model list based on
     * active page of ViewPager. <br/>  <br/>
     * If last page of ViewPager is active
     * next natural order value will be appended to end of a list. If selected
     * page is not last - next entry is evaluated for natural order (current+1==next) and
     * if this true  - list will naturally grow. In other case (current+1!=next)- value will be inserted between
     * unordered pages to restore numerical sequence.
     * In all cases  - this meth will return position of newly created entry.
     *
     * @param viewPagerPosition - position from which val will be inserted
     * @return int - position of newly added entry
     */
    public int add(int viewPagerPosition) {
        if (collection.size() - 1 > viewPagerPosition) {
            int nextEntry = collection.get(viewPagerPosition + 1);
            if (nextEntry - collection.get(viewPagerPosition) != 1) {
                collection.add(viewPagerPosition + 1, viewPagerPosition + 1);
                return viewPagerPosition + 1;
            } else {
                collection.add(collection.get(collection.size()-1)+1);
                return collection.size() - 1;
            }
        } else {
            //initial adding
            if(collection.size()!=0){
                collection.add(collection.get(collection.size()-1)+1);
            }else {
                collection.add(collection.size());
            }
            return collection.size() - 1;
        }

    }

    public int remove(int entry) {
        if (collection.size() > 0) {
            int deletedElement = collection.remove(entry);
            return deletedElement;
        }
        return -1;
    }

    public int getItem(int entry) {
        return collection.get(entry);
    }

    public int itemCount() {
        return collection.size();
    }
}

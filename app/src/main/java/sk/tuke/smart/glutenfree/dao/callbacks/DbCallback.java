package sk.tuke.smart.glutenfree.dao.callbacks;

import android.support.annotation.Nullable;

import com.parse.ParseException;

import java.util.List;

import sk.tuke.smart.glutenfree.pojo.Podniky;

/**
 * Created by Hawk on 26.11.2017.
 */

@FunctionalInterface
public interface DbCallback<T> {
    /**
     * Check if e is not null first.
     * @param podniky retrieved data
     * @param e null when correct data
     */
    void call(List<T> podniky,@Nullable ParseException e);
}

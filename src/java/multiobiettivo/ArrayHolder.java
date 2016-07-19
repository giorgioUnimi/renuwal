/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multiobiettivo;

import java.util.Arrays;

public  final class ArrayHolder<T> {

    private final T[] t;

    public ArrayHolder(T[] t) {
        this.t = t;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Arrays.hashCode(this.t);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ArrayHolder<T> other = (ArrayHolder<T>) obj;
        if (!Arrays.equals(this.t, other.t)) {
            return false;
        }
        return true;
    }
    
    
     public T[] getArray()
    {
        return t;
    }
}
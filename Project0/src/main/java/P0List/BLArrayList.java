package P0List;

import java.util.Arrays;

public class BLArrayList<E> implements CustomListInterface{
    private int size = 0;
    private static final int s = 2;
    private static final Object[] emptyarray = {};
    Object[] a = new Object[s];


    @Override
    public void grow(int size) {
        int newSize = size * s;
        Object[] temp = Arrays.copyOf(a,newSize);
        a = temp;
    }

    @Override
    public void add(Object o) {
        int n = size + 1;
        if(size == a.length){
            grow(size);
        } else{
            a[n] = o;
        }

    }

    @Override
    public void add(Object o, int index) {
        if(size == a.length) {
            grow(size);
        }
        for(int i = size - 1; i >= index; i--){
            a[i+1] = a[i];
        }
        a[index] = o;

    }

    @Override
    public Object get(int index) {
        Object element = a[index];
        return element;
    }

    @Override
    public void remove(int index) {
        //remove objects at an index by setting the value at that index equal to void
        //and then moving everything over (like add at index but in the opposite direction)

    }

    @Override
    public void clear() {
        a = emptyarray;
    }

    @Override
    public int contains(Object o) {
        return 0;
    }
}

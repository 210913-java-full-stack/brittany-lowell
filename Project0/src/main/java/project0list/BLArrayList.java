package project0list;

import java.util.Arrays;

public class BLArrayList<E> implements CustomListInterface{
    private int size; //size of the array
    public int len; //This variable allows the user to access size outside of this class
    private static final int s = 2; // Default length of the array
    Object[] a = new Object[s]; //Creating an array


/*
Both versions of the add() method would possibly need to increment. The grow() method reduces redundancy in
the class
*/
    @Override
    public Object[] grow() {
        int newSize = size + s;
        a = Arrays.copyOf(a,newSize);
        return a;
    }
/*
This method adds one element at the end of the array
 */
    @Override
    public void add(Object o) {
        int n = size; //n will always be 1 less than size
//if the array is full, then call the grow() method and increase the size of the array
        if(size == a.length){
            grow();
        }
        a[n] = o;
        size++;
        len = size; //Making sure that len always equals size

    }
/*
This method adds an element at a specific index and moves all of the other elements to the right
Ex: a[1] becomes a[2]
 */
    @Override
    public void add(Object o, int index) {
        if(size == a.length) {
            grow();
        }
//       System.arraycopy is equal to the following statement:
//        for(int i = index; i<size; i++){
//            a[i+1] = a[i];
//        }
        if (size - index >= 0) System.arraycopy(a, index, a, index + 1, size - index);
        a[index] = o;
        size++;
        len = size;

    }
//This method gets the specified index and returns the element
    @Override
    public Object get(int index) {
        if(a == null){
            String none = "This array is empty.";
            return none;
        }
        if(index >= a.length || index < 0) {
            throw new IndexOutOfBoundsException(index + " is out of bounds.");
        }
        Object o = a[index];
        return o;
    }
/*
Removes the element at the specific index and moves everything to the left.
Moves the last index to the right and then removes it so that there are no duplicates
 */
    @Override
    public void remove(int index) {
        //Check if the given index in greater than the size of the array
        if(index >= size || index <0) {
            throw new IndexOutOfBoundsException(index + " is out of bounds.");
        }
        //Moves everything to the left starting at index - 1
        int move = size - index - 1;
        if (move > 0)
            System.arraycopy(a, index + 1, a, index, move);
        //decrements size and takes this element out of scope so that it can be garbage collected
        a[--size] = null;
        len = size;
    }

    //Removes all of the elements in the array
    @Override
    public void clear() {
        a = null;
        size = 0;
        len = 0;
    }

    /*
    Checks to see if the array contains the element. If the element is present, returns the element.
    If it does not, then return -1.
     */
    @Override
    public int contains(Object o) {
        if(a == null) {
            System.out.println("The array is empty.");
            return -1;
        }

        for(int i = 0; i<size; i++){
            if(a[i] == o){
                System.out.println("The index of " + o + " is " + i + ".");
                return i;
            }
        }
        System.out.println(o + " is not present in the array");
        return -1;
    }
}

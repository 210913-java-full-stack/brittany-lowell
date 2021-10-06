package project0list;

import java.util.Arrays;

public class BLArrayList<E> implements CustomListInterface<E>{
    private int size; //size of the array
    public int len; //This variable allows the user to access size outside this class
    private static final int s = 2; // Default length of the array
    Object[] a = new Object[s]; //Creating an array


/*
Both versions of the add() method would possibly need to increment. The grow() method reduces redundancy in
the class
*/
    @Override
    public void grow() {
        int newSize = size + s;
        a = Arrays.copyOf(a,newSize);
    }
/*
This method adds one element at the end of the array
 */
    @Override
    public void add(E e) {
        int n = size; //n will always be 1 less than size
//if the array is full, then call the grow() method and increase the size of the array
        if(size == a.length){
            grow();
        }
        a[n] = e;
        size++;
        len = size; //Making sure that len always equals size

    }
/*
This method adds an element at a specific index and moves all other elements to the right
Ex: a[1] becomes a[2]
 */
    @Override
    public void add(E e, int index) {
        if(size == a.length) {
            grow();
        }
/*
System.arraycopy is equal to the following statement:
        for(int i = index; i<size; i++){
            a[i+1] = a[i];
       }
 */
        if (size - index >= 0) System.arraycopy(a, index, a, index + 1, size - index);
        a[index] = e;
        size++;
        len = size;

    }
//This method gets the specified index and returns the element
    @Override
    public E get(int index) {
        if(a == null){
            throw new IndexOutOfBoundsException("This array is empty.");
        }
        if(index >= a.length || index < 0) {
            throw new IndexOutOfBoundsException(index + " is out of bounds.");
        }
        Object e = a[index];
        return (E) e;
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

    //Clears the array and creates a new array with the default size
    @Override
    public void clear() {
        a = new Object[s];
        size = 0;
        len = 0;
    }

    /*
    Checks to see if the array contains the element. If the element is present, returns the element.
    If it does not, then return -1.
     */
    @Override
    public int contains(E e) {
        if(a == null) {
            System.out.println("The array is empty.");
            return -1;
        }

        for(int i = 0; i<size; i++){
            if(a[i] == e){
                System.out.println("The index of " + e + " is " + i + ".");
                return i;
            }
        }
        System.out.println(e + " is not present in the array");
        return -1;
    }


}

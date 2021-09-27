package P0List;

import java.util.Arrays;

public interface CustomListInterface<E> {

        void grow(int size);

        void add(E e);

        void add(E e, int index);

        E get(int index);

        void remove(int index);

        void clear();

        int contains(E e);

    }

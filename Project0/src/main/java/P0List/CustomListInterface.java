public interface CustomListInterface {

        void add(E e);

        void add(E e, int index);

        E get(int index);

        void remove(int index);

        void clear();

        int contains(E e);

    }

}

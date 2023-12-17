package week10.githubCode;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class GenericIterator<T> implements Iterator<T> {
    private List<T> elements;
    private int currentIndex;

    public GenericIterator(List<T> elements)
    {
        this.elements=elements;
    }
    public boolean hasNext()
    {
        if(currentIndex< elements.size())
        {
            return currentIndex< elements.size();
        }
    }
    public T next()
    {
        if(!hasNext())
        {
            throw new NoSuchElementException();
        }
        return elements.get(currentIndex++);
    }
    public static void main(String[] args) {
        List<Movie> movies = Arrays.asList(
                new Movie("Ljeto u zlatnoj dolini", "Becir", 10),
                new Movie("Kod amidze Idriza", "Becir", 10),
                new Movie("Zaba", "Becir", 7)
        );
        GenericIterator iterator=new GenericIterator(movies);
        int count=0;
        while(iterator.hasNext())
        {
            System.out.println(iterator.next());
            count++;
        }
    }


}

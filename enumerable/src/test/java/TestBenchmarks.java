import com.devshorts.enumerable.Enumerable;
import com.devshorts.enumerable.data.Action;
import com.devshorts.enumerable.data.Box;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

public class TestBenchmarks {
    @Test
    public void BenchMark(){
        List<Integer> jit = asList(1);

        BenchReduce(jit);
        BenchMap(jit);
        BenchMin(jit);
        BenchSorted(jit);
        BenchDistinct(jit);
        BenchFilter(jit, 0, 1);

        System.out.println("START");

        for(int x = 1; x < 100000000; x *= 10){
            List<Integer> data = new LinkedList<Integer>();

            for(int j = 0; j < x; j++){
                data.add(j);
            }


            System.out.println("=== " + x + " ===");
            BenchReduce(data);
            BenchMap(data);
            BenchMin(data);
            BenchSorted(data);
            BenchDistinct(data);
            BenchFilter(data, 0, x);
        }
    }

    private void BenchFilter(List<Integer> data, Integer start, Integer end) {
        System.out.println("-- filter --");

        long streamTime = TimeAndRun(() -> data.stream().filter(i -> i > start && i < end/2).toArray());

        long enumTime = TimeAndRun(() -> Enumerable.init(data).filter(i -> i > start && i < end/2).toList());

        System.out.println("Streams:  " + streamTime);
        System.out.println("EnumTime: " + enumTime);
    }

    private void BenchDistinct(List<Integer> data) {
        System.out.println("-- distinct --");

        long streamTime = TimeAndRun(() -> data.stream().distinct().toArray());

        long enumTime = TimeAndRun(() -> Enumerable.init(data).distinct().toList());

        System.out.println("Streams:  " + streamTime);
        System.out.println("EnumTime: " + enumTime);
    }


    private void BenchSorted(List<Integer> data){
        System.out.println("-- order --");

        long streamTime = TimeAndRun(() -> data.stream().sorted().toArray());

        long enumTime = TimeAndRun(() -> Enumerable.init(data).order().toList());

        System.out.println("Streams:  " + streamTime);
        System.out.println("EnumTime: " + enumTime);
    }


    private void BenchMin(List<Integer> data){
        System.out.println("-- min --");

        long streamTime = TimeAndRun(() -> data.stream().min(Integer::compareTo));

        long enumTime = TimeAndRun(() -> Enumerable.init(data).min(Integer::compareTo));

        System.out.println("Streams:  " + streamTime);
        System.out.println("EnumTime: " + enumTime);
    }

    private void BenchMap(List<Integer> data){
        System.out.println("-- map --");

        long streamTime = TimeAndRun(() -> data.stream().map(Box::new).toArray());

        long enumTime = TimeAndRun(() -> Enumerable.init(data).map(Box::new).toList());

        System.out.println("Streams:  " + streamTime);
        System.out.println("EnumTime: " + enumTime);
    }

    private void BenchReduce(List<Integer> data){
        System.out.println("-- reduce --");
        long streamTime = TimeAndRun(() -> data.stream().reduce((acc, elem) -> acc + elem));

        long enumTime = TimeAndRun(() -> Enumerable.init(data).foldWithFirst((acc, elem) -> acc + elem));

        System.out.println("Streams:  " + streamTime);
        System.out.println("EnumTime: " + enumTime);
    }

    private long TimeAndRun(Action action){
        long t = System.nanoTime();

        action.exec();

        return (System.nanoTime() - t)/1000000;
    }

}

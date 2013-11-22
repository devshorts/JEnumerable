import com.devshorts.enumerable.Enumerable;
import com.devshorts.enumerable.data.*;
import org.junit.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static junit.framework.Assert.*;

public class TestEnumerable {
    @Test
    public void Distinct(){
        List<Integer> ids = Enumerable.init(asList(1, 1, 1, 2, 3, 4, 5, 6, 6, 7))
                                      .distinct()
                                      .toList();

        assertEquals(ids, asList(1,2,3,4,5,6,7));
    }

    @Test
    public void Zip(){
        Enumerable<Integer> ids = Enumerable.init(asList(1, 2, 3, 4, 5));

        List<String> expectZipped = asList("11", "22", "33", "44", "55");

        assertEquals(expectZipped,
                     ids.zip(ids, (f, s) -> f.toString() + s.toString()).toList());
    }

    @Test
    public void Order(){
        assertEquals(asList(1, 2, 3, 4, 5),
                     Enumerable.init(asList(5, 4, 3, 2, 1))
                                .orderBy(i -> i)
                                .toList());
    }

    @Test
    public void OrderDesc(){
        assertEquals(asList(5, 4, 3, 2, 1),
                Enumerable.init(asList(1, 2, 3, 4, 5))
                        .orderByDesc(i -> i)
                        .toList());
    }

    @Test
    public void Order2(){
        assertEquals(asList(1, 2, 3, 4, 5),
                Enumerable.init(asList(5, 4, 3, 2, 1))
                        .orderBy(i -> i, Integer::compareTo)
                        .toList());
    }

    @Test
    public void OrderDesc2(){
        assertEquals(asList(5, 4, 3, 2, 1),
                Enumerable.init(asList(1, 2, 3, 4, 5))
                        .orderByDesc(i -> i, Integer::compareTo)
                        .toList());
    }

    @Test
    public void Map(){
        assertEquals(asList("5", "4", "3", "2", "1"),
                Enumerable.init(asList(5, 4, 3, 2, 1))
                        .map(i -> i.toString())
                        .toList());
    }

    @Test
    public void FlatMap(){
        assertEquals(asList("5", "4", "3", "2", "1"),
                Enumerable.init(asList(asList("5"), asList("4"), asList("3"), asList("2"), asList("1")))
                        .flatMap(i -> i)
                        .map(i -> i.toString())
                        .toList());
    }

    @Test
    public void Skip(){
        assertEquals(asList("4", "3", "2", "1"),
                Enumerable.init(asList("5", "4", "3", "2", "1"))
                        .skip(1)
                        .toList());
    }

    @Test
    public void SkipWhile(){
        assertEquals(asList("4", "3", "2", "1"),
                Enumerable.init(asList("5", "4", "3", "2", "1"))
                        .skipWhile(i -> i.equals("5"))
                        .toList());
    }

    @Test
    public void Take(){
        assertEquals(asList("5"),
                Enumerable.init(asList("5", "4", "3", "2", "1"))
                        .take(1)
                        .toList());
    }

    @Test
    public void TakeWhile(){
        assertEquals(asList("5"),
                Enumerable.init(asList("5", "4", "3", "2", "1"))
                        .takeWhile(i -> i.equals("5"))
                        .toList());
    }

    @Test
    public void Nth(){
        assertEquals("3",
                Enumerable.init(asList("5", "4", "3", "2", "1"))
                          .nth(3));
    }

    @Test
    public void First(){
        assertEquals("5",
                Enumerable.init(asList("5", "4", "3", "2", "1"))
                          .first());
    }

    @Test
    public void Last(){
        assertEquals("1",
                Enumerable.init(asList("5", "4", "3", "2", "1"))
                        .last());
    }

    @Test
    public void Filter(){
        assertEquals(asList(4, 3, 2),
                Enumerable.init(asList(5, 4, 3, 2, 1))
                           .filter(i -> i > 1 && i < 5)
                            .toList());
    }

    @Test
    public void Any(){
        assertTrue(Enumerable.init(asList(5, 4, 3, 2, 1)).any(i -> i > 1 && i < 5));
    }

    @Test
    public void AnyFalse(){
        assertFalse(Enumerable.init(asList(5, 4, 3, 2, 1)).any(i -> i > 10));
    }

    @Test
    public void All(){
        assertTrue(Enumerable.init(asList(5, 4, 3, 2, 1)).all(i -> i < 10));
    }

    @Test
    public void AllFalse(){
        assertFalse(Enumerable.init(asList(5, 4, 3, 2, 1)).all(i -> i > 10));
    }

    @Test
    public void Fold(){
        assertEquals((Integer) (5 + 4 + 3+ 2 + 1 + 10),
                Enumerable.init(asList(5, 4, 3, 2, 1))
                        .fold((acc, elem) -> acc + elem, 10));
    }

    @Test
    public void FoldWithSeed(){
        assertEquals((Integer) (5 + 4 + 3+ 2 + 1),
                Enumerable.init(asList(5, 4, 3, 2, 1))
                        .foldWithFirst((acc, elem) -> acc + elem));
    }

    @Test
    public void Yield(){
        Box<Integer> i = new Box<>(0);
        Enumerable<Integer> ints = Enumerable.generate(() -> {
            if(i.elem < 10){
                return Yieldable.yield(i.elem, () -> i.elem++);
            }
            return Yieldable.yieldBreak();
        });

        assertEquals(ints.toList(), asList(0,1,2,3,4,5,6,7,8,9));
    }

    @Test
    public void YieldBang(){
        Box<Integer> i = new Box<>(0);
        Enumerable<Integer> tenTime = Enumerable.generate(() -> {
            if(i.elem < 5){
                return Yieldable.yield(i.elem, () -> {
                    i.elem++;
                });
            }
            return Yieldable.yieldBreak();
        });

        Box<Integer> j = new Box(0);

        Enumerable<Integer> hundred = Enumerable.generate(() -> {
            if (j.elem < 5) {
                return Yieldable.bang(tenTime, () -> {
                    i.elem = 0;
                    j.elem++;
                });
            }
            return Yieldable.yieldBreak();
        });

        List<Integer> x = hundred.toList();

        assertEquals(x.size(), 25);
    }

    @Test
    public void DistinctUnion(){
        assertEquals(asList(1,2,3,4,5,6,7,8,9),
                Enumerable.init(asList(5, 4, 3, 2, 1))
                          .distinctUnion(asList(5, 4, 3, 5, 5, 5, 5, 6, 7, 8, 9)).toList());
    }

    @Test
    public void Intersect(){
        assertEquals(asList(9, 2, 1),
                Enumerable.init(asList(5, 4, 3, 9, 2, 1))
                        .intersect(asList(1,1,1,2, 6, 7, 8, 9)).toList());
    }

    @Test
    public void Except(){
        assertEquals(asList(3, 4, 5),
                Enumerable.init(asList(5, 4, 3, 9, 2, 1))
                        .orderBy(i -> i)
                        .except(asList(1,1,1,2, 6, 7, 8, 9)).toList());
    }

    @Test
    public void ToString(){
        assertEquals(asList('a', 'b', 'c'),
                    Enumerable.init("abc").toList());
    }

    @Test
    public void Iter(){
        List<Integer> l = new ArrayList<>();
        Enumerable.init(asList(1,2,30)).iter(l::add).toList();

        assertEquals(asList(1,2,30), l);
    }

    @Test
    public void Iteri(){
        List<Integer> l = new ArrayList<>();
        List<Integer> idx = new ArrayList<>();

        Enumerable.init(asList(1,2,30)).iteri((i, e) -> {
            idx.add(i);
            l.add(e);
        }).toList();

        assertEquals(asList(1,2,30), l);
        assertEquals(asList(0,1,2), idx);
    }

    @Test
    public void DistinctBy(){
        class T {
            public T(Integer x){
                B = x;
            }
            public Integer B;

            @Override
            public boolean equals(Object o){
                if(!(o instanceof T)) return false;

                return ((T)o).B.equals(B);
            }
        }

        assertEquals(
                asList(new T(0), new T(1), new T(4)),
                Enumerable.init(asList(new T(0), new T(1), new T(1), new T(4)))
                          .distinctBy(i -> i.B)
                          .toList());
    }


    @Test
    public void FirstOrDefault(){
        assertEquals(null, Enumerable.init(asList()).first());
    }

    @Test
    public void LastOrDefault(){
        assertEquals(null, Enumerable.init(asList()).last());
    }


    @Test
    public void Windowed(){
        assertEquals(asList(asList(1, 2), asList(2, 3), asList(3, 4)),
                Enumerable.init(asList(1, 2, 3, 4))
                        .windowed(2)
                        .toList());
    }

    @Test
    public void WindowedInfinite(){
        Box<Integer> i = new Box(1);
        List<List<Integer>> is = Enumerable.generate(() -> Yieldable.yield(i.elem, () -> i.elem++))
                .take(4)
                .windowed(2)
                .toList();

        assertEquals(asList(asList(1, 2), asList(2, 3), asList(3, 4)), is);
    }

    @Test
     public void Tails(){
        assertEquals(asList(asList(1, 2, 3, 4), asList(2, 3, 4), asList(3, 4), asList(4)),
                Enumerable.init(asList(1,2,3,4))
                        .tails()
                        .toList());
    }

    @Test
    public void Pairwise(){
        assertEquals(asList(new Tuple<>(1,2), new Tuple<>(2, 3), new Tuple<>(3, 4)),
                Enumerable.init(asList(1,2,3,4))
                        .pairwise()
                        .toList());
    }

    @Test
    public void PairwiseEmpty(){
        assertEquals(asList(),
                Enumerable.init(asList(1))
                        .pairwise()
                        .toList());
    }

    @Test
    public void GroupNeighbors(){
        assertEquals(asList(asList(1,1,1),
                asList(2),
                asList(3,3),
                asList(4),
                asList(1,1),
                asList(5),
                asList(6)),
                        Enumerable.init(asList(1,1,1,2,3,3,4,1,1,5,6))
                                  .groupRuns()
                                  .toList());
    }

    @Test
    public void RunTimeEncodeExample(){
        assertEquals(asList("2a", "2b", "1c", "1d", "1e", "2f", "1g"),
                        Enumerable.init("aabbcdeffg")
                                  .groupRuns()
                                  .map(l -> l.size() + l.get(0).toString())
                                  .toList());
    }

    @Test
    public void GroupNeighbors2(){
        assertEquals(asList(asList(5), asList(1,1,1)),
                Enumerable.init(asList(5,1,1,1))
                        .groupRuns()
                        .toList());
    }

    @Test
    public void GroupNeighbors3(){
        assertEquals(asList(),
                Enumerable.init(asList())
                        .groupRuns()
                        .toList());
    }

    @Test
    public void Intersperse(){
        assertEquals(asList("1",",","2",",","3"),
                Enumerable.init("123")
                        .map(i -> Character.toString(i))
                        .intersperse(",").toList());
    }

    @Test
    public void Intercalate(){
        assertEquals(asList("1","a","b","c","2","a","b","c","3"),
                Enumerable.init("123")
                        .map(i -> Character.toString(i))
                        .intercalate(asList("a", "b", "c")).toList());
    }

    @Test
    public void ToDictionary(){
        HashMap<String, String> s = new HashMap<>();

        s.put("1", "1");
        s.put("a", "a");
        s.put("b", "b");
        s.put("c", "c");
        s.put("2", "2");
        s.put("3", "3");

        assertEquals(s,
                Enumerable.init("123")
                        .map(i -> Character.toString(i))
                        .intercalate(asList("a", "b", "c")).toDictionary());
    }

    @Test
    public void ToGroupedDictionary(){
        HashMap<String, List<String>> s = new HashMap<>();

        s.put("1", asList("1"));
        s.put("a", asList("a","a"));
        s.put("b", asList("b","b"));
        s.put("c", asList("c","c"));
        s.put("2", asList("2"));
        s.put("3", asList("3"));

        assertEquals(s,
                Enumerable.init("123")
                        .map(i -> Character.toString(i))
                        .intercalate(asList("a", "b", "c"))
                        .toGroupedDictionary());
    }

    @Test
    public void Range(){
        assertEquals(asList(0,1,2,3,4,5,6,7,8,9,10),
                        Enumerable.range(0, 10).toList());

    }

    @Test
    public void Range2(){
        assertEquals(asList(0, 5, 10),
                Enumerable.range(0, 10, 5).toList());

    }

    @Test
    public void Min(){
        assertEquals((Integer)0,
                    Enumerable.range(0, 10).min());
    }

    @Test
    public void Min2(){
        assertEquals((Integer)0,
                Enumerable.range(0, 10).min(Integer::compareTo));
    }

    @Test
    public void Max(){
        assertEquals((Integer)10,
                Enumerable.range(0, 10).max(Integer::compareTo));
    }

    @Test
    public void Max2(){
        assertEquals((Integer)10,
                Enumerable.range(0, 10).max());
    }

    @Test
    public void MinBy(){
        assertEquals(new Box(0),
                Enumerable.range(0, 10)
                        .map(Box::new)
                        .minBy(i -> i.elem, Integer::compareTo));
    }

    @Test
    public void MaxBy(){
        assertEquals(new Box(10),
                Enumerable.range(0, 10)
                        .map(Box::new)
                        .maxBy(i -> i.elem, Integer::compareTo));
    }

    @Test
    public void MinByDefaultComparer(){
        assertEquals(new Box(0),
                    Enumerable.range(0, 10)
                              .map(Box::new)
                              .minBy(i -> i.elem));
    }

    @Test
    public void MaxByDefaultComparer(){
        assertEquals(new Box(10),
                Enumerable.range(0, 10)
                        .map(Box::new)
                        .maxBy(i -> i.elem));
    }
}

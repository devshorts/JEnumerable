Java Enumerable
----

This is an implementation of deferred lazy collections using Java.

The goal, to provide

- Map
- Filter
- FlatMap
- OrderBy
- Take
- TakeWhile
- Skip
- SkipWhile
- Iter
- Iteri
- DistinctUnion
- Intersect
- Except
- First
- Last
- Nth
- Fold
- FoldWithDefaultSeed
- Any
- All
- Yield/Yield break (and infinite sequence generation)

The functionality already exists with java 8 streams, but this is fun to do :)

Initial benchmarks show that JEnumerable is comporable in speed to JDK8 streams. JDK8 streams, however, does parallelized optimizations which this does not, so can be bettter performant sometimes.  

Examples
---

```java
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
        i.elem++;                                                                                                                               
        if(i.elem < 10){                                                                                                                        
            return Yieldable.yield(i.elem);                                                                                                     
        }                                                                                                                                       
        return Yieldable.yieldBreak();                                                                                                          
    });                                                                                                                                         
                                                                                                                                                
    assertEquals(ints.toList(), asList(1,2,3,4,5,6,7,8,9));                                                                                     
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
```


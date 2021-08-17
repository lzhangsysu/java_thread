# java_thread

This repo contains code for learning Java Threads.

## Thread1: Starting Threads
demo1 and demo2 shows 2 main ways to start a new thread in Java: ```Extends``` a ```Thread``` or ```Implements``` a ```Runnable```. demo3 shows start a new thread anonymously in only part of the code.
### note:
Implementing the Runnable interface is a better choice because it more flexibility than extending the Thread class. In addition, as of Java 8, a lambda expression can be used to create a thread:
```
Thread t = new Thread(() -> {
    // do something
});
```
A lambda expression can only be used in the place of a functional interface.

## Thread2: Volatile
Here shows how to use boolean flag to achieve basic thread communication, and ```volatile``` keyword.
### note:
Why need ```volatile``` keyword?

In the code, main thread and new thread shares the boolean flag ```running```. Main thread is writing to the flag, but new thread is only reading the flag. In this case, some systems might cache the value of the variable within the new thread, and as a result, it won't see the change of value.

Using ```volatile``` keyword prevents threads from caching variables that are not changed within the thread.

## Thread3: Synchronized
Here shows how to use ```synchronized``` keyword to prevent threads from messing up each other's work.
### note:
Output ```count``` will be ```0``` if executing ```println``` right after ```start()```.

Using ```join()``` will wait till thread finishes before executing next command.
However, the ```count``` output is not always correct. It is because ```count++``` is indeed 3 steps: ```count = count + 1```.
Therefore, when two threads read and write the same variable, sometimes increment gets skipped before it is saved.

Using ```synchronized``` keyword prevents threads interleaving. It is because every Java object has an intrinsic lock. Using ```synchronized``` keyword makes the program calling the intrinsic lock. Every object can only acquire one intrinsic lock at a time.
Therefore, if one thread acquired intrinsic lock, the other thread must wait until the intrinsic lock to be released.

```synchronized``` key word also guarantees the variable is visible to all threads, therefore ```volatile``` keyword is not needed.

## Thread4: Multiple Locks
Without ```synchronized``` keyword, list1 and list2 sizes are incorrect due to thread interleaving;
Adding ```synchronized``` keyword to ```stageOne``` and ```stageTwo``` methods generate correct result, but takes twice the time. It is because one thread needs to wait for the intrinsic lock to be released.

Since ```stageOne``` and ```stageTwo``` are independent: they write to different data, we can create separate locks to synchronize these 2 methods separately.

Here we use ```Object``` as lock object, and wrap around code blocks to create synchronized code blocks. It functions similar to synchronized methods.
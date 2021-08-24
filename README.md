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

## Thread5: Thread Pools
Use thread pools to manage lots of threads to complete lots of similar tasks.

## Thread6: Countdown Latches
```CountDownLatch``` class is one of the classes that are thread-safe and no need to worry about thread synchronization. ```CountDownLatch``` counts down from a given number, and lets one or more threads wait until the latch reaches 0.

The purpose of a latch is only to make one or more threads wait until some other threads have completed, or have performed some operations a certain number of times. Threads can make the latch count down, then when zero is reached, any threads that are waiting on the latch will run.

## Thread7: Producer-Consumer Design Pattern
Producer-Consumer is the situation where one or more threads are producing data items and adding them to a shared data store of some kind while one or more other threads process those items, removing them from the data store.

Here ArrayBlockingQueue Java class is used for thread-safe adding and removing of items.

## Thread8: Wait and Notify
```wait()``` and ```notify()``` are methods from Java Object class. They can only be called with synchronized code block.
Here, ```this``` object itself is used as intrinsic lock. 
```wait()``` will hand over control of lock to another thread. 
```notify()``` will notify the waiting thread, so it can wake up and re-acquire the lock. It does NOT automatically relinquish the control of the lock.
If want to notify all waiting threads, can use ```notifyAll()```.

## Thread9: Producer-Consumer with Low-Level Synchronization
Here we use the low level wait and notify synchronization to implement the producer-consumer pattern.

## Thread10: Re-entrant Locks
Reentrant lock can be used to lock and unlock a thread multiple times. It can be used to replace the ```synchronized``` keyword.
Within reentrant lock, can use ```await()``` and ```signal()``` to achieve wait and notify. These methods are not from ```Lock``` class, but from ```Condition``` class (within Lock).

## Thread11: Deadlock
Deadlock can occur when locks are locked in different order. 
When first thread requires lock1, then second thread requires lock2; Then first thread requires lock2 and second thread requires lock1, then neither thread can proceed, because each thread needs the lock the other thread possessed. 
It occurs not only with re-entrant locks, but also nested synchronized blocks.

To solve deadlocks:
1. Can always lock locks in same order;
2. Use ```tryLock()``` of ```ReentrantLock``` class to write a method that can safely acquire any number of locks in any order without causing deadlock.

## Thread12: Semaphore
Semaphore allows for controlling how many threads can access a resource simultaneously.
```
Semaphore sem = new Semaphore(1, true);  // 1: available permits; true: whichever thread to call acquire() first will be the first to get the next available permit
sem.release(); // increment available permits
sem.acquire(); // decrement available permits
System.out.println("Available permits: " + sem.availablePermits());
```
In semaphore, ```acquire()``` will wait if no available permits, therefore it can be used as a lock, with the advantage that it can be unlocked from threads other than where you lock them.

The usual use case of semaphore, is that it allows you to control how many threads can access a resource simultaneously.



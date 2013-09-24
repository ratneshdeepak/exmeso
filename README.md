External Merge Sort
======

This is a small library that implements [External Merge Sort](http://en.wikipedia.org/wiki/External_sorting) in Java. It is pretty flexible as it takes either an InputStream or an Iterable&lt;T&gt; as input and returns the sorted result as MergeIterator&lt;T&gt;. 

Persistence is handled by an implementation of the SortHandler&lt;T&gt; interface, of which there are currently two implementations:

* JacksonSort&lt;T&gt; - serialization and deserialization using the [Jackson](http://jackson.codehaus.org/) library.
* KryoSort&lt;T&gt; - serialization and deserialization using the [Kryo](https://code.google.com/p/kryo/) library.

See the [ExternalMergeSortTest](https://github.com/grove/exmeso/blob/master/exmeso-jackson/src/test/java/org/geirove/exmeso/jackson/ExternalMergeSortTest.java) class for code examples.

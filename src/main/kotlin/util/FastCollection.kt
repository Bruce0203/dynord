package util

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

typealias FastArrayList<T> = CopyOnWriteArrayList<T>
typealias FastHashMap<K, V> = ConcurrentHashMap<K, V>


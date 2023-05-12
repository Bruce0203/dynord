package util

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap
import java.util.concurrent.CopyOnWriteArrayList

typealias FastArrayList<T> = CopyOnWriteArrayList<T>
typealias FastHashMap<K, V> = ConcurrentLinkedHashMap<K, V>

internal fun <K, V> newSafeMap(): FastHashMap<K, V> = ConcurrentLinkedHashMap.Builder<K, V>()
    .maximumWeightedCapacity(Long.MAX_VALUE).build()

internal fun <T> newSafeList(): FastArrayList<T> = CopyOnWriteArrayList<T>()
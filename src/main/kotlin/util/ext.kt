package util

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap
import java.util.concurrent.CopyOnWriteArrayList

internal fun <K, V> newSafeMap() =
    ConcurrentLinkedHashMap.Builder<K, V>().maximumWeightedCapacity(Long.MAX_VALUE).build()

internal fun <T> newSafeList() = CopyOnWriteArrayList<T>()
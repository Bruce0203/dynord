package io.github.bruce0203.dynord.util

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.CopyOnWriteArraySet

typealias FastHashSet<T> = CopyOnWriteArraySet<T>
typealias FastArrayList<T> = CopyOnWriteArrayList<T>
typealias FastHashMap<K, V> = ConcurrentHashMap<K, V>

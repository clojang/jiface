(ns jiface.erlang.types
  (:require [jiface.erlang :as erlang]
            [jiface.util :as util])
  (:import [com.ericsson.otp.erlang
            OtpInputStream
            OtpErlangObject
            OtpErlangPid])
  (:refer-clojure :exclude [atom boolean byte char double int list float long
                            map ref short]))

;;; >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
;;; Data types constructors
;;; >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

(defn atom
  "Constructor for an Erlang atom data type. `arg` may be any of the following:

  * a `java.lang.String`
  * a `boolean`
  * an `OtpInputStream` (buffer)"
  [arg]
  (erlang/create :atom arg))

(defn binary
  "Constructor for an Erlang binary data type where `arg` can be any of the
  following:

  * a `byte` array,
  * an arbitrary Java `Object`
  * an `OtpInputStream` (buffer)"
  [arg]
  (erlang/create :binary arg))

(defn bitstr
  "Constructor for an Erlang bitstring data type. It may take either one or two
  arguments. If one is passed, they be of the following types:

  * a `byte` array,
  * an arbitrary Java `Object`
  * an `OtpInputStream` (buffer)

  If two are passed, the first must be a `byte` array and the second must be an
  integer representing the pad bits."
  ([arg]
    (erlang/create :bitstr arg))
  ([byte-array pad-bits]
    (erlang/create :bitstr byte-array pad-bits)))

(defn boolean
  "Constructor for an Erlang boolean (atom) data type. `arg` may be either of
  the following:

  * a `boolean`
  * an `OtpInputStream` (buffer)"
  [arg]
  (erlang/create :boolean arg))

(defn byte
  "See the docstring for ``#'types/long``. `arg` may be either of the
  following:

  * a `byte`
  * an `OtpInputStream` (buffer)

  Note that Erlang itself does not distringuish integer types, however
  JInterface provides a means of mapping between Java integer types and Erlang
  with the following classes:

  * `OtpErlangByte`
  * `OtpErlangChar`
  * `OtpErlangInt`
  * `OtpErlangLong`
  * `OtpErlangShort`
  * `OtpErlangUInt`
  * `OtpErlangUShort`

  As such, `jiface` supplies the following constructors:

  * `types/byte`
  * `types/char`
  * `types/int`
  * `types/long`
  * `types/short`
  * `types/uint`
  * `types/ushort`

  `OtpErlangUInt` and `OtpErlangUShort` (and thus `types/unit` and `types/ushort`)
  are provided for Corba compatibility."
  [arg]
  (erlang/create :byte arg))

(defn char
  "See the docstring for ``#'types/long``. `arg` may be either of the
  following:

  * a `char`
  * an `OtpInputStream` (buffer)

  Note that Erlang itself does not distringuish integer types, however
  JInterface provides a means of mapping between Java integer types and Erlang
  with the following classes:

  * `OtpErlangByte`
  * `OtpErlangChar`
  * `OtpErlangInt`
  * `OtpErlangLong`
  * `OtpErlangShort`
  * `OtpErlangUInt`
  * `OtpErlangUShort`

  As such, `jiface` supplies the following constructors:

  * `types/byte`
  * `types/char`
  * `types/int`
  * `types/long`
  * `types/short`
  * `types/uint`
  * `types/ushort`

  `OtpErlangUInt` and `OtpErlangUShort` (and thus `types/unit` and `types/ushort`)
  are provided for Corba compatibility."
  [num]
  (erlang/create :char num))

(defn double
  "Provides a Java representation of Erlang double. `arg` may be either of the
  following:

  * a `double`
  * an `OtpInputStream` (buffer)"
  [num]
  (erlang/create :double num))

(defn external-fun
  "Erlang external function representation. If a single argument is passed, it
  must be an `OtpInputStream`. If three arguments are passed, they must be:

  * the module (`String`)
  * the function (`String`)
  * the function's arity (`Integer`)."
  ([buf]
    (erlang/create :external-fun buf))
  ([mod func arity]
    (erlang/create :external-fun mod func arity)))

(defn float
  "Provides a Java representation of Erlang float. `arg` may be either of the
  following:

  * a `float`
  * an `OtpInputStream` (buffer)"
  [num]
  (erlang/create :float num))

(defn fun
  "Erlang internal function representation. If a single argument is passed, it
  must be an `OtpInputStream`. If five arguments are passed, they must be:

  * an `OtpErlangPid`
  * a module (`String`)
  * an index (`Long`)
  * `uniq` (`Long`)
  * an array of `OtpErlangObject`s

  If eight arguments are passed, they must be:

  * an `OtpErlangPid`
  * a module (`String`)
  * function arity (`Integer`)
  * an md5 as an array of `byte`s
  * an index (`Integer`)
  * an old index (`Long`)
  * `uniq` (`Long`)
  * an array of `OtpErlangObject`s"
  ([buf]
    (erlang/create :fun buf))
  ([^OtpErlangPid pid ^String module ^Long index ^Long uniq free-vars]
    (erlang/create :fun pid module index uniq free-vars))
  ([^OtpErlangPid pid ^String module ^Integer arity md5 ^Integer index
    ^Long old-index ^Long uniq free-vars]
    (erlang/create :fun pid module arity md5 index old-index uniq free-vars)))

(defn int
  "Provides a Java representation of an Erlang integer. `arg` may be either of the
  following:

  * an `Integer`
  * an `OtpInputStream` (buffer)

  Note that Erlang itself does not distringuish integer types, however
  JInterface provides a means of mapping between Java integer types and Erlang
  with the following classes:

  * `OtpErlangByte`
  * `OtpErlangChar`
  * `OtpErlangInt`
  * `OtpErlangLong`
  * `OtpErlangShort`
  * `OtpErlangUInt`
  * `OtpErlangUShort`

  As such, `jiface` supplies the following constructors:

  * `types/byte`
  * `types/char`
  * `types/int`
  * `types/long`
  * `types/short`
  * `types/uint`
  * `types/ushort`

  `OtpErlangUInt` and `OtpErlangUShort` (and thus `types/unit` and `types/ushort`)
  are provided for Corba compatibility."
  [arg]
  (erlang/create :int arg))

(defn list
  "Provides a Java representation of Erlang lists. Lists are created from
  zero or more arbitrary Erlang terms. If no argument is provided, an empty
  list is returned. If one argument is provided, it may be one of the
  following types:

  * an `OtpErlangObject` (a single-item list will be returned)
  * an array of `OtpErlangObject`s
  * a `String` (a list of Erlang integers will be returned, repsresenting
    Uncode code points
  * an `OtpInputStream` (buffer)

  If two arguments are passed, they must be of the following types:

  * an array of `OtpErlangObject`s
  * a last tail `OtpErlangObject`

  If three arguments are passed, a new list will be created from a subset of
  the passed list, the arguments being:

  * an array of `OtpErlangObject`s
  * a starting index (`Integer`)
  * the number of items to include (`Integer`), starting with the element at
    the given index

  The arity of the list is the number of elements it contains."
  ([]
    (erlang/create :list))
  ([args]
    (erlang/create :list args))
  ([elems ^OtpErlangObject last-tail]
    (erlang/create :list elems last-tail))
  ([elems ^Integer index ^Integer count]
    (erlang/create :list elems index count)))

(defn long
  "Provides a Java representation of an Erlang integer. `arg` may be one of the
  following:

  * a `long`
  * a `java.math.BigInteger`
  * an `OtpInputStream` (buffer)

  Note that Erlang itself does not distringuish integer types, however
  JInterface provides a means of mapping between Java integer types and Erlang
  with the following classes:

  * `OtpErlangByte`
  * `OtpErlangChar`
  * `OtpErlangInt`
  * `OtpErlangLong`
  * `OtpErlangShort`
  * `OtpErlangUInt`
  * `OtpErlangUShort`

  As such, `jiface` supplies the following constructors:

  * `types/byte`
  * `types/char`
  * `types/int`
  * `types/long`
  * `types/short`
  * `types/uint`
  * `types/ushort`

  `OtpErlangUInt` and `OtpErlangUShort` (and thus `types/unit` and `types/ushort`)
  are provided for Corba compatibility."
  [arg]
  (erlang/create :long arg))

(defn map
  "Provides a Java representation of Erlang maps. Maps are created from one
  or more arbitrary Erlang terms. If no argument is provided, an empty map
  is returned. If a sing argument is provided, it must be of type
  `OtpInputStream`. If two arguments are passed, they must be:

  * an array of `OtpErlangObject`s representing the map keys
  * an array of `OtpErlangObject`s representing the corresponding map values

  Alternatively, a \"sub-map\" may be created by providing six arguments:

  * an array of `OtpErlangObject`s representing the map keys
  * the starting index of the map keys (`Integer`)
  * the number of keys to include (`Integer`), starting with the element at
    the given index
  * an array of `OtpErlangObject`s representing the corresponding map values
  * the starting index of the map values (`Integer`)
  * the number of values to include (`Integer`), starting with the element at
    the given index

  The arity of the map is the number of elements it contains. The keys and
  values can be retrieved as arrays and the value for a key can be
  queried."
  ([]
    (erlang/create :map))
  ([^OtpInputStream buf]
    (erlang/create :map buf))
  ([ks vs]
    (erlang/create :map ks vs))
  ([ks ^Integer kstart ^Integer kcount vs ^Integer vstart ^Integer vcount]
    (erlang/create :map ks kstart kcount vs vstart vcount)))

(defn object
  "This is a psuedo-constructor: `OtpErlangObject` doesn't provide a
  constructor method, but the object itself is needed for creating tuples,
  so this function simply returns `OtpErlangObject`."
  []
  com.ericsson.otp.erlang.OtpErlangObject)

(defn pid
  "Provides a Java representation of an Erlang PID. PIDs represent Erlang
  processes and consist of a nodename and a number of integers.

  If one argument is passed, it must be of type `OtpInputStream`. If four
  arguments are passed, they must be:

  * the node (`String`)
  * the pid number (`Integer`)
  * the pid serial (`Integer`)
  * the pid creation (`Integer`)"
  ([^OtpInputStream buf]
    (erlang/create :pid buf))
  ([^String node ^Integer id ^Integer serial ^Integer creation]
    (erlang/create :pid node id serial creation)))

(defn port
  "Provides a Java representation of an Erlang Port.

  If one argument is passed, it must be of type `OtpInputStream`. If three
  arguments are passed, they must be:

  * the node (`String`)
  * the port id (`Integer`)
  * the pid creation (`Integer`)

  If four arguments are passed, they must be:

  * the tag (`Integer`)
  * the node (`String`)
  * the port id (`Integer`)
  * the port creation (`Integer`)"
  ([^OtpInputStream buf]
    (erlang/create :port buf))
  ([^String node ^Integer id ^Integer creation]
    (erlang/create :port node id creation))
  ([^Integer tag ^String node ^Integer id ^Integer creation]
    (erlang/create :port tag node id creation)))

(defn ref
  "Provides a Java representation of an Erlang ref.

  If one argument is passed, it must be of type `OtpInputStream`. If three
  arguments are passed, they must be:

  * the node (`String`)
  * an array of `Integer`s
  * the ref creation (`Integer`)

  If four arguments are passed, they must be:

  * the tag (`Integer`)
  * the node (`String`)
  * an array of `Integer`s
  * the pid creation (`Integer`)"
  ([^OtpInputStream buf]
    (erlang/create :ref buf))
  ([^String node ids ^Integer creation]
    (erlang/create :ref node ids creation))
  ([^Integer tag ^String node ids ^Integer creation]
    (erlang/create :ref tag node ids creation)))

(defn short
  "Provides a Java representation of an Erlang integer. `arg` may be either of the
  following:

  * a `short`
  * an `OtpInputStream` (buffer)

  Note that Erlang itself does not distringuish integer types, however
  JInterface provides a means of mapping between Java integer types and Erlang
  with the following classes:

  * `OtpErlangByte`
  * `OtpErlangChar`
  * `OtpErlangInt`
  * `OtpErlangLong`
  * `OtpErlangShort`
  * `OtpErlangUInt`
  * `OtpErlangUShort`

  As such, `jiface` supplies the following constructors:

  * `types/byte`
  * `types/char`
  * `types/int`
  * `types/long`
  * `types/short`
  * `types/uint`
  * `types/ushort`

  `OtpErlangUInt` and `OtpErlangUShort` (and thus `types/unit` and `types/ushort`)
  are provided for Corba compatibility."
  [arg]
  (erlang/create :short arg))

(defn string
  "Provides a Java representation of Erlang strings. Supplied argument may be
  any of:

  * `String`
  * `OtpErlangList`
  * `OtpInputStream` (buffer)"
  [arg]
  (erlang/create :string arg))

(defn tuple
  "Provides a Java representation of Erlang tuples. Tuples are created from
  one or more arbitrary Erlang terms. If a single argument is passed, it may
  be:
  * an `OtpErlangObject`, in which case a unary tuple will be created
  * an array of `OtpErlangObject`s, in which case the tuple will have the same
  arity as the array that is passed
  * an `OtpInputStream` (buffer)

  Alternatively, a tuple may be created from a subset of an array of
  `OtpErlangObject`s by passing the array, the starting index (0-based), and
  the count (number of elements to include).

  The arity of the tuple is the number of elements it contains. Elements are
  indexed from 0 to (arity-1) and can be retrieved individually by using the
  appropriate index."
  ([arg]
    (erlang/create :tuple arg))
  ([^OtpErlangObject array ^Integer index ^Integer count]
    (erlang/create :tuple array index count)))

(defn uint
  "See the docstring for ``#'types/int``."
  [arg]
  (erlang/create :uint arg))

(defn ushort
  "See the docstring for ``#'types/short``."
  [arg]
  (erlang/create :ushort arg))

;;; >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
;;; Error handling
;;; >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

(util/add-err-handler #'erlang/create
  [java.lang.IllegalArgumentException,
   java.lang.InstantiationException]
  "[ERROR] could not instantiate object")

(ns jiface.erlang.types
  (:require [jiface.erlang :as erlang]
            [jiface.util :as util])
  (:import [com.ericsson.otp.erlang])
  (:refer-clojure :exclude [atom boolean byte char double int list float long
                            map ref short]))

;;; >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
;;; Data types constructors
;;; >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

(defn object
  "This is a psuedo-constructor: `OtpErlangObject` doesn't provide a
  constructor method, but the object itself is needed for creating tuples,
  so this function simply returns `OtpErlangObject`."
  []
  com.ericsson.otp.erlang.OtpErlangObject)

(defn atom
  "Constructor for an Erlang atom data type."
  [arg]
  (erlang/create :atom arg))

(defn bitstr
  "Constructor for an Erlang bitstring data type."
  ([byte-array]
    (erlang/create :bitstr byte-array))
  ([byte-array pad-bits]
    (erlang/create :bitstr byte-array pad-bits)))

(defn boolean
  "Constructor for an Erlang boolean (atom) data type."
  [bool]
  (erlang/create :boolean bool))

(defn tuple
  "Provides a Java representation of Erlang tuples. Tuples are created from
  one or more arbitrary Erlang terms.

  The arity of the tuple is the number of elements it contains. Elements are
  indexed from 0 to (arity-1) and can be retrieved individually by using the
  appropriate index."
  [args]
  (erlang/create :tuple args))

(defn list
  "Provides a Java representation of Erlang lists. Lists are created from
  zero or more arbitrary Erlang terms.

  The arity of the list is the number of elements it contains."
  ([]
    (erlang/create :list))
  ([args]
    (erlang/create :list args)))

(defn string
  "Provides a Java representation of Erlang strings."
  [str]
  (erlang/create :string str))

(defn map
  "Provides a Java representation of Erlang maps. Maps are created from one
  or more arbitrary Erlang terms.

  The arity of the map is the number of elements it contains. The keys and
  values can be retrieved as arrays and the value for a key can be
  queried."
  ([]
    (erlang/create :map))
  ([buf]
    (erlang/create :map buf))
  ([ks vs]
    (erlang/create :map ks vs))
  ([ks kstart kcount vs vstart vcount]
    (erlang/create :map ks kstart kcount vs vstart vcount)))

(defn long
  "Provides a Java representation of Erlang integral types. Erlang does not
  distinguish between different integral types, however this class and its
  subclasses OtpErlangByte, OtpErlangChar, OtpErlangInt , and OtpErlangShort
  attempt to map the Erlang types onto the various Java integral types. Two
  additional classes, OtpErlangUInt and OtpErlangUShort are provided for
  Corba compatibility. See the documentation for IC for more information."
  [num]
  (erlang/create :long num))

(defn byte
  "See the docstring for ``#'types/long``."
  [num]
  (erlang/create :byte num))

(defn char
  "See the docstring for ``#'types/long``."
  [num]
  (erlang/create :char num))

(defn int
  "See the docstring for ``#'types/long``."
  [num]
  (erlang/create :int num))

(defn short
  "See the docstring for ``#'types/long``."
  [num]
  (erlang/create :short num))

(defn uint
  "See the docstring for ``#'types/long``."
  [num]
  (erlang/create :uint num))

(defn ushort
  "See the docstring for ``#'types/long``."
  [num]
  (erlang/create :ushort num))

(defn double
  "Provides a Java representation of Erlang floats and doubles. Erlang defines
  only one floating point numeric type, however this class and its subclass
  ``OtpErlangFloat`` are used to provide representations corresponding to the
  Java types ``Double`` and ``Float``."
  [num]
  (erlang/create :double num))

(defn float
  "See the docstring for ``#'types/double``."
  [num]
  (erlang/create :float num))

(defn pid
  "Provides a Java representation of an Erlang PID. PIDs represent Erlang
  processes and consist of a nodename and a number of integers."
  [node id serial creation]
  (erlang/create :pid node id serial creation))

(defn port
  "Provides a Java representation of an Erlang Port."
  ([buf]
    (erlang/create :port buf))
  ([node id creation]
    (erlang/create :port node id creation))
  ([tag node id creation]
    (erlang/create :port tag node id creation)))

(defn ref
  "Provides a Java representation of an Erlang ref."
  ([buf]
    (erlang/create :ref buf))
  ([node ids creation]
    (erlang/create :ref node ids creation))
  ([tag node ids creation]
    (erlang/create :ref tag node ids creation)))

;;; >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
;;; Error handling
;;; >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

(util/add-err-handler #'erlang/create
  [java.lang.IllegalArgumentException,
   java.lang.InstantiationException]
  "[ERROR] could not instantiate object")

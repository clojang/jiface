(ns jiface.erlang.tuple
  (:require [potemkin :refer [import-vars]]
            [jiface.erlang.object :as object]
            [jiface.util :as util])
  (:import [com.ericsson.otp.erlang OtpErlangTuple])
  (:refer-clojure :exclude [hash]))

(defprotocol ErlangTuple
  (get-arity [this]
    "Get the arity of the tuple.")
  (size [this]
    "Alias for `get-arity`, taken from the Erlang world for getting the
    size of a tuple.")
  (get-size [this]
    "Alias for `get-arity`.")
  (get-element [this index]
    "Get the specified element from the tuple.")
  (get-elements [this]
    "Get all the elements from the tuple as an array."))

(def behaviour
  {:get-arity (fn [this] (.arity this))
   :get-size (fn [this] (.arity this))
   :get-element (fn [this ^Integer index] (.elementAt this index))
   :get-elements (fn [this] (.elements this))})

(extend OtpErlangTuple object/ErlangObject object/behaviour)
(extend OtpErlangTuple ErlangTuple behaviour)

;;; Aliases

(import-vars
  [object
   ;; object-behaviour
   bind
   clone
   decode
   encode
   equal?
   hash
   match
   ->str])

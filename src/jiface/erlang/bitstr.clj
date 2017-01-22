(ns jiface.erlang.bitstr
  (:require [potemkin :refer [import-vars]]
            [jiface.erlang.object :as object]
            [jiface.util :as util])
  (:import [com.ericsson.otp.erlang
             OtpErlangBistr])
  (:refer-clojure :exclude [hash]))

(defprotocol ErlangBitstr
  (get-binary-value [this]
    "Get the byte array from a bitstr, padded with zero bits in the little end
    of the last byte.")
  (get-object [this]
    "Get the java Object from the bitstr.")
  (pad-bits [this]
    "Get the number of pad bits in the last byte of the bitstr.")
  (get-size [this]
    "Get the size in whole bytes of the bitstr, rest bits in the last byte not
    counted."))

(def behaviour
  {:get-binary-value (fn [this] (.binaryValue this))
   :get-object (fn [this] (.getObject this))
   :pad-bits (fn [this] (.pad_bits this))
   :get-size (fn [this] (.size this))})

(extend OtpErlangBistr object/ErlangObject object/behaviour)
(extend OtpErlangBistr ErlangBitstr behaviour)

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

(ns jiface.erlang.string
  (:require
    [jiface.erlang.object :as object]
    [potemkin :refer [import-vars]])
  (:import
    (com.ericsson.otp.erlang OtpErlangString))
  (:refer-clojure :exclude [hash new]))

(defprotocol ErlangString
  (valid-code-point? [this code-point]
    "Validate a code point according to Erlang definition; Unicode 3.0.")
  (->code-points [this str]
    "Create Unicode code points from a String.")
  (get-value [this]
    "Get the actual string contained in this object."))

(def behaviour
  {:valid-code-point? (fn [this ^Integer integer]
                        (.isValidCodePoint this integer))
   :->code-points (fn [this ^String string]
                    (.stringToCodePoints this string))
   :get-value (fn [this]
                (.stringValue this))})

(extend OtpErlangString object/ErlangObject object/behaviour)
(extend OtpErlangString ErlangString behaviour)

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

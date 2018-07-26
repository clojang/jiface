(ns jiface.erlang.pid
  (:require
    [jiface.erlang.object :as object]
    [potemkin :refer [import-vars]])
  (:import
    (com.ericsson.otp.erlang OtpErlangPid))
  (:refer-clojure :exclude [get hash keys remove]))

(defprotocol ErlangPid
  (compare-to [this other]
    "From `compareTo` in interface `java.lang.Comparable`.")
  (get-creation-num [this]
    "Get the creation number from the PID.")
  (get-id [this]
    "Get the id number from the PID.")
  (get-node [this]
    "Get the node name from the PID.")
  (get-serial-num [this]
    "Get the serial number from the PID."))

(def behaviour
  {:compare-to (fn [this ^java.lang.Object other] (.compareTo this other))
   :get-creation-num (fn [this] (.creation this))
   :get-id (fn [this] (.id this))
   :get-node (fn [this] (.node this))
   :get-serial-num (fn [this] (.serial this))})

(extend OtpErlangPid object/ErlangObject object/behaviour)
(extend OtpErlangPid ErlangPid behaviour)

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

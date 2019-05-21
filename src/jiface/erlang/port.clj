(ns jiface.erlang.port
  (:require
    [potemkin :refer [import-vars]]
    [jiface.erlang.object :as object])
  (:import
    (com.ericsson.otp.erlang OtpErlangPort))
  (:refer-clojure :exclude [get hash keys remove]))

(defprotocol ErlangPort
  (get-creation-num [this]
    "Get the creation number from the port.")
  (get-id [this]
    "Get the id number from the port.")
  (get-node [this]
    "Get the node name from the port."))

(def behaviour
  {:get-creation-num (fn [this] (.creation this))
   :get-id (fn [this] (.id this))
   :get-node (fn [this] (.node this))})

(extend OtpErlangPort object/ErlangObject object/behaviour)
(extend OtpErlangPort ErlangPort behaviour)

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

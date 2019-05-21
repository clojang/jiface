(ns jiface.erlang.ref
  (:require
    [potemkin :refer [import-vars]]
    [jiface.erlang.object :as object])
  (:import
    (com.ericsson.otp.erlang OtpErlangRef))
  (:refer-clojure :exclude [get hash keys remove]))

(defprotocol ErlangRef
  (get-creation-num [this]
    "Get the creation number from the ref.")
  (get-id [this]
    "Get the id number from the ref.")
  (get-ids [this]
    "Get the array of id numbers from the ref.")
  (get-node [this]
    "Get the node name from the ref.")
  (new-style? [this]
    "Determine whether this is a new style ref"))

(def behaviour
  {:get-creation-num (fn [this] (.creation this))
   :get-id (fn [this] (.id this))
   :get-ids (fn [this] (.ids this))
   :get-node (fn [this] (.node this))
   :new-style? (fn [this] (.isNewRef this))})

(extend OtpErlangRef object/ErlangObject object/behaviour)
(extend OtpErlangRef ErlangRef behaviour)

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

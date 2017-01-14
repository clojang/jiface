(ns jiface.util
  (:require [clojure.string :as string]
            [dire.core :refer [with-handler!]])
  (:import [clojure.lang Reflector]
           [com.ericsson.otp.erlang]))

(defn get-hostname
  "Get the hostname for the machine that this JVM is running on.

  Uses the `java.net.InetAddress` methods `getLocalHost` and
  `getHostName`."
  []
  (-> (java.net.InetAddress/getLocalHost)
      (.getHostName)))

(defn add-err-handler
  "A wrapper for generating a specific dire error handler."
  ([handled-fn excep]
    (add-err-handler
      handled-fn
      excep
      "[ERROR] There was a problem!"))
  ([handled-fn excep msg]
    (with-handler! handled-fn
      excep
      (fn [e & args]
        (println msg)
        (println (str {:args args :errors e}))))))

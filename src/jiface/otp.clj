(ns jiface.otp
  (:require [jiface.core :as jiface])
  (:import [clojure.lang Keyword]))

(defn create [& args]
  "Common function for node instantiation.

  Having a single function which is ultimately responsible for creating
  objects allows us to handle instantiation errors easily, adding one handler
  for `#'init` instead of a bunch of handlers, one for each type of node."
  (jiface/dynamic-init :otp args))

;;; Aliases

(def init #'create)

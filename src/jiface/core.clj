(ns jiface.core
  (:require [clojure.string :as string]
            [clojure.tools.logging :as log]
            [clojusc.twig :as twig]
            [dire.core :refer [with-handler!]])
  (:import [clojure.lang Keyword Reflector]
           [com.ericsson.otp.erlang]))

;; XXX support the following keys:
;; [-d|-debug] [DbgExtra...] [-port No] [-daemon] [-relaxed_command_check]
(defn start-epmd
  "Start the Erlang Port Mapper Daemon external (OS) process needed by
  JInterface for creating nodes and communicating with other nodes."
  []
  'TBD)

(defn name->class-name
  "A helper function for use when creating Erlang class wrappers."
  [^Keyword type-key]
  (case type-key
    ;; Types
    :external-fun "ExternalFun"
    :list-sublist "List$SubList"
    :object-hash  "Object$Hash"
    :uint         "UInt"
    :ushort       "UShort"
    ;; OTP
    :local-node   "LocalNode"
    ;; Everything else
    (string/capitalize (name type-key))))

(defn key->prefix
  [key]
  (case key
    :otp "Otp"
    :erlang "OtpErlang"))

(defn name->class
  "A helper function for use when defining constructor macros."
  [^Keyword prefix ^Keyword name-key]
  (->> name-key
       (name->class-name)
       (str "com.ericsson.otp.erlang." (key->prefix prefix))
       (symbol)))

(defn dynamic-init
  "Dynamically instantiates classes based upon a transformation function and
  a symbol used by the transformation function to create a class name that is
  ultimately resolvable."
  [prefix [name-part & args]]
  (Reflector/invokeConstructor
    (resolve (name->class prefix name-part))
    (into-array Object args)))

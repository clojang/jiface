(ns jiface.repl
  "A development namespace that imports other useful namespaces for easy
  prototyping, &c. The intended use is for this to be the initial namespace
  when running `lein repl` from the Clojang project directory."
  (:require
    [clojure.core.match :refer [match]]
    [clojure.tools.namespace.repl :as repl]
    [dire.core :refer [with-handler! with-finally!]]
    [jiface.core :as jiface]
    [jiface.erlang :as erlang]
    [jiface.erlang.types :as types]
    [jiface.exceptions :as exceptions]
    [jiface.otp :as otp]
    [jiface.otp.connection :as connection]
    [jiface.otp.messaging :as messaging]
    [jiface.otp.nodes :as nodes]
    [jiface.otp.streams :as streams]
    [jiface.util :as util]
    [trifl.java :refer [show-methods]])
  (:import
    (clojure.lang Keyword Reflector)
    (com.ericsson.otp.erlang)))

(def reload #'repl/refresh)

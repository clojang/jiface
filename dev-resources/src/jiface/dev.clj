(ns ^{:doc
  "A development namespace that imports other useful namespaces for easy
  prototyping, &c. The intended use is for this to be the initial namespace
  when running ``lein repl`` from the Clojang project directory."}
  jiface.dev
  (:require [clojure.core.match :refer [match]]
            [clojure.tools.logging :as log]
            [clojure.tools.namespace.repl :as repl]
            [dire.core :refer [with-handler! with-finally!]]
            [jiface.erlang.types :as types]
            [jiface.otp.connection :as connection]
            [jiface.otp.messaging :as messaging]
            [jiface.otp.nodes :as nodes]
            [jiface.util :as util]))

(def reload #'repl/refresh)

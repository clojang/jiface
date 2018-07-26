(defn get-banner
  []
  (try
    (str
      (slurp "resources/text/banner.txt")
      ;(slurp "resources/text/loading.txt")
      )
    ;; If another project can't find the banner, just skip it.
    (catch Exception _ "")))

(defn get-prompt
  [ns]
  (str "\u001B[35m[\u001B[34m"
       ns
       "\u001B[35m]\u001B[33m λ\u001B[m=> "))

(defproject clojang/jiface "0.7.0-SNAPSHOT"
  :description "A Clojure-idiomatic wrapper around Erlang's JInterface"
  :url "https://github.com/clojang/jiface"
  :scm {
    :name "git"
    :url  "https://github.com/clojang/jiface"}
  :license {
    :name "Apache License, Version 2.0"
    :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [
    [org.clojure/clojure "1.9.0"]
    [org.clojure/core.match "0.3.0-alpha4"]
    [org.clojure/core.memoize "0.7.1"]
    [clojang/erlang-jinterface "1.9"]
    [dire "0.5.4"]
    [potemkin "0.4.5"]]
  :repl-options {:init-ns jiface.dev}
  :codox {
    :project {:name "jiface"}
    :themes [:clojang]
    :output-path "docs/current"
    :doc-paths ["resources/docs"]
    :namespaces [#"^jiface\.erlang"
                 #"^jiface\.otp"
                 jiface.util]
    :metadata {:doc/format :markdown}}
  :profiles {
    :ubercompile {
      :aot :all}
    :docs {
      :aot :all
      :dependencies [
        [clojang/codox-theme "0.2.0-SNAPSHOT"]]
      :plugins [
        [lein-codox "0.10.4"]
        [lein-simpleton "1.3.0"]]}
    :lint {
      :source-paths ^:replace ["src"]
      :test-paths ^:replace []
      :plugins [
        [jonase/eastwood "0.2.8"]
        [lein-ancient "0.6.15"]
        [lein-bikeshed "0.5.1"]
        [lein-kibit "0.1.6"]
        [venantius/yagni "0.1.4"]]}
    :test {
      :aot :all
      :plugins [
        [lein-ltest "0.3.0"]]
      :source-paths ["test"]
      :test-selectors {
        :default :unit
        :unit :unit
        :system :system
        :integration :integration}}
    :dev {
      :dependencies [
        [clojusc/trifl "0.3.0"]
        [org.clojure/math.numeric-tower "0.0.4"]
        [org.clojure/tools.namespace "0.2.10"]]
      :source-paths ["dev-resources/src"]
      :repl-options {
        :init-ns jiface.repl
        :prompt ~get-prompt
        :init ~(println (get-banner))}}}
  :aliases {
    ;; Dev Aliases
    "repl" ["do"
      ["clean"]
      ["repl"]]
    "ubercompile" ["do"
      ["clean"]
      ["with-profile" "+ubercompile" "compile"]]
    "check-vers" ["with-profile" "+lint" "ancient" "check" ":all"]
    "check-jars" ["with-profile" "+lint" "do"
      ["deps" ":tree"]
      ["deps" ":plugin-tree"]]
    "check-deps" ["do"
      ["check-jars"]
      ["check-vers"]]
    "kibit" ["with-profile" "+lint" "kibit"]
    "eastwood" ["with-profile" "+lint" "eastwood" "{:namespaces [:source-paths]}"]
    "lint" ["do"
      ["kibit"]
      ;["eastwood"]
      ]
    "ltest" ["with-profile" "+test" "ltest"]})

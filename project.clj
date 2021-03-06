(defproject clojang/jiface "0.7.0"
  :description "A Clojure-idiomatic wrapper around Erlang's JInterface"
  :url "https://github.com/clojang/jiface"
  :scm {
    :name "git"
    :url  "https://github.com/clojang/jiface"}
  :license {
    :name "Apache License, Version 2.0"
    :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [
    [org.clojure/clojure "1.10.0"]
    [org.clojure/core.match "0.3.0"]
    [org.clojure/core.memoize "0.7.1"]
    [clojang/erlang-jinterface "1.10-1"]
    [dire "0.5.4"]
    [potemkin "0.4.5"]]
  :javac-options ["-target" "1.8" "-source" "1.8"]
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
        [lein-codox "0.10.78"]
        [lein-simpleton "1.3.0"]]}
    :lint {
      :source-paths ^:replace ["src"]
      :test-paths ^:replace []
      :plugins [
        [jonase/eastwood "0.3.5"]
        [lein-ancient "0.6.15"]
        [lein-bikeshed "0.5.2"]
        [lein-kibit "0.1.6"]]}
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
        [clojusc/trifl "0.4.2"]
        [org.clojure/math.numeric-tower "0.0.4"]
        [org.clojure/tools.namespace "0.2.10"]]
      :source-paths ["dev-resources/src"]
      :aot [clojure.tools.logging.impl]}}
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
    "test" ["with-profile" "+test" "ltest"]})

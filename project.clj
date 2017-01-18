(defproject clojang/jiface "0.3.0"
  :description "A Clojure-idiomatic wrapper around Erlang's JInterface"
  :url "https://github.com/clojang/jiface"
  :scm {
    :name "git"
    :url  "https://github.com/clojang/jiface"}
  :license {
    :name "Apache License, Version 2.0"
    :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [
    [org.clojure/clojure "1.8.0"]
    [org.clojure/core.match "0.3.0-alpha4"]
    [org.clojure/core.memoize "0.5.9"]
    [clojang/erlang-jinterface "1.7.1"]
    [clojusc/twig "0.3.0"]
    [dire "0.5.4"]
    [potemkin "0.4.3"]]
  :plugins [
    [lein-codox "0.10.2"]
    [lein-simpleton "1.3.0"]]
  :repl-options {:init-ns jiface.dev}
  :codox {
    :project {:name "jiface"}
    :themes [:rdash]
    :output-path "docs/master/current"
    :doc-paths ["docs/source"]
    :namespaces [#"^jiface\.erlang"
                 #"^jiface\.otp"
                 jiface.util]
    :metadata {:doc/format :markdown}}
  :profiles {
    :uber {
      :aot :all}
    :docs {
      :aot :all
      :dependencies [[codox-theme-rdash "0.1.1"]]}
    :testing {
      :aot :all
      :source-paths ["test"]
      :test-selectors {
        :default :unit
        :unit :unit
        :system :system
        :integration :integration}}
    :dev {
      :dependencies [
        [org.clojure/math.numeric-tower "0.0.4"]
        [org.clojure/tools.namespace "0.2.11"]]
      :source-paths ["dev-resources/src"]
      :aot [clojure.tools.logging.impl]}})

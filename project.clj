(defproject clojang/jiface "0.4.0-SNAPSHOT"
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
    [clojusc/twig "0.3.2-SNAPSHOT"]
    [dire "0.5.4"]
    [potemkin "0.4.3"]]
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
    :uberjar {
      :aot :all}
    :docs {
      :aot :all
      :dependencies [[clojang/codox-theme "0.1.0-SNAPSHOT"]]
      :plugins [
        [lein-codox "0.10.3"]
        [lein-simpleton "1.3.0"]]}
    :test {
      :aot :all
      :dependencies [
        [clojusc/trifl "0.1.0-SNAPSHOT"]]
      :plugins [
        [lein-ancient "0.6.10"]
        [jonase/eastwood "0.2.3" :exclusions [org.clojure/clojure]]
        [lein-bikeshed "0.4.1"]
        [lein-kibit "0.1.5" :exclusions [org.clojure/clojure]]
        [venantius/yagni "0.1.4"]]
      :source-paths ["test"]
      :test-selectors {
        :default :unit
        :unit :unit
        :system :system
        :integration :integration}}
    :dev {
      :dependencies [
        [org.clojure/math.numeric-tower "0.0.4"]
        [org.clojure/tools.namespace "0.2.10"]]
      :source-paths ["dev-resources/src"]
      :aot [clojure.tools.logging.impl]}})

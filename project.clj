(defproject jiface "0.1.0-dev"
  :description "Erlang's JInterface in Idiomatic Clojure"
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
    [org.erlang.otp/jinterface "1.6.1"]
    [dire "0.5.4"]
    [potemkin "0.4.3"]]
  :plugins [
    [lein-codox "0.9.4"]
    [lein-simpleton "1.3.0"]]
  :repl-options {:init-ns jiface.dev}
  :test-selectors {
    :default :unit
    :unit :unit
    :system :system
    :integration :integration}
  :codox {
    :output-path "docs/master/current"
    :doc-paths ["docs/source"]
    :namespaces [#"^jiface\.(?!test)"]
    :metadata {:doc/format :markdown}}
  :profiles {
    :testing {
      :aot :all
      :dependencies [
        [org.clojure/math.numeric-tower "0.0.4"]]
      :source-paths ["test"]}
    :dev {
      :dependencies [
        [org.clojure/tools.namespace "0.2.11"]
        [twig "0.1.6"]]
      :source-paths ["dev-resources/src"]
      :aot [clojure.tools.logging.impl]}})

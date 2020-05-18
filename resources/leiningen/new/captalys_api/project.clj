(defproject {{name}} "0.1.2-SNAPSHOT"
  :description "Provide some study around API building"
  :url "http://gitlab.com/captalys/{{name}}"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.namespace "0.3.1"]
                 [org.clojure/tools.logging "0.5.0"]
                 [metosin/reitit "0.4.1"]
                 [metosin/ring-http-response "0.9.1"]
                 [http-kit "2.4.0-alpha3"]
                 [clj-http "3.10.0"]
                 [com.fasterxml.jackson.core/jackson-core "2.10.0"]
                 [org.postgresql/postgresql "42.2.5"]
                 [seancorfield/next.jdbc "1.0.10"]
                 [com.layerware/hugsql-core "0.5.1"]
                 [com.layerware/hugsql-adapter-next-jdbc "0.5.1"]
                 [org.clojure/test.check "0.10.0"]
                 [hikari-cp "2.8.0"]
                 [camel-snake-kebab "0.4.1"]
                 [cheshire "5.9.0"]
                 [mount "0.1.9"]
                 [aero "1.1.3"]
                 [migratus "1.2.7"]]
  :main {{name}}.server
  :target-path "target/%s"
  :uberjar-name "{{name}}-standalone.jar"
  :profiles {:dev {:source-paths ["src" "dev"]
                   :plugins [[lein-ring "0.12.5"]
                             [refactor-nrepl "2.4.0"]
                             [cider/cider-nrepl "0.25.0-SNAPSHOT"]
                             [jonase/eastwood "0.3.6"]
                             [lein-ancient "0.6.15"]
                             [lein-cloverage "1.1.2"]]}
             :staging {:source-paths ["src"]}
             :production {:source-paths ["src"]}
             :uberjar {:aot :all}}
  :repl-options {:init-ns user})

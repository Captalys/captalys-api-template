(ns {{name}}.services.database
  (:require [mount.core :as mount]
            [next.jdbc :as jdbc]
            [next.jdbc.prepare :as prepare]
            [next.jdbc.result-set :as rs]
            [next.jdbc.date-time]
            [clojure.set :refer [rename-keys]]
            [hugsql.core :as hugsql]
            [next.jdbc.connection :as connection]
            [hugsql.adapter.next-jdbc :as next-adapter]
            [{{name}}.services.config :refer [config]]
            [hikari-cp.core :as hikari]
            [cheshire.core :as json])
  (:import [org.postgresql.util PGobject]))

;;; extending next.jdbc to deal with JSONB fields
;;; More on https://github.com/seancorfield/next-jdbc/blob/master/doc/tips-and-tricks.md
;;; and https://web.archive.org/web/20161024231548/http://hiim.tv/clojure/2014/05/15/clojure-postgres-json/
(defn ->pgobject
  "Transforms Clojure data to a PGobject that contains the data as JSON."
  [x]
  (let [pgtype (or (:pgtype (meta x)) "jsonb")]
    (doto (PGobject.)
      (.setType pgtype)
      (.setValue (json/generate-string x)))))

(defn <-pgobject
  "Transform PGobject containing jsonb value to Clojure data."
  [^org.postgresql.util.PGobject v]
  (let [type (.getType v)
        value (.getValue v)]
    (if (#{"jsonb" "json"} type)
      (with-meta (json/parse-string value) {:pgtype type})
      value)))

(extend-protocol prepare/SettableParameter
  clojure.lang.IPersistentMap
  (set-parameter [m s i]
    (.setObject s i (->pgobject m)))

  clojure.lang.IPersistentVector
  (set-parameter [v s i]
    (.setObject s i (->pgobject v))))

(extend-protocol rs/ReadableColumn
  org.postgresql.util.PGobject
  (read-column-by-label [^org.postgresql.util.PGobject v _]
    (<-pgobject v))
  (read-column-by-index [^org.postgresql.util.PGobject v _2 _3]
    (<-pgobject v)))

(mount/defstate datasource
  :start (let [cfg-hikari (rename-keys (:postgresql config)
                                       {:dbname :database-name
                                        :host :server-name
                                        :port :port-number
                                        :dbtype :adapter
                                        :user :username})]
           (hugsql/set-adapter! (next-adapter/hugsql-adapter-next-jdbc))
           (hikari/make-datasource cfg-hikari))
  :stop (hikari/close-datasource datasource))

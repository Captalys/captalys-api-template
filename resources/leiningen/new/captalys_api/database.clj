(ns {{name}}.services.database
  (:require [mount.core :as mount]
            [next.jdbc :as jdbc]
            [{{name}}.services.config :refer [config]]))

(mount/defstate datasource
  :start (jdbc/get-datasource (:postgresql config))
  :stop identity)

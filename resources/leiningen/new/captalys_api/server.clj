(ns {{name}}.server
    (:require [clojure.tools.logging :as log]
              [mount.core :as mount]
              [{{name}}.services.web :as web]
              [{{name}}.services.config :as config]
              [{{name}}.services.database :as database])
    (:gen-class))

(defn -main []
  "Entry point function to start the whole application."
  (mount/start #'config/config
               #'database/datasource
               #'web/server)
  (log/info ::api.flying))

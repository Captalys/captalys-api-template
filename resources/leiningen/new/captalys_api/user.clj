(ns user
  (:require [mount.core :as mount]
            [clojure.tools.namespace.repl :as tn]
            [{{name}}.services.web :as web]
            [{{name}}.services.config :as config]
            [{{name}}.services.database :as database]))

(defn start []
  (mount/start #'config/config
               #'database/datasource
               #'web/server))

(defn stop []
  (mount/stop))

(defn refresh []
  (stop)
  (tn/refresh))

(defn go []
  (start)
  :ready)

(defn reset []
  (stop)
  (tn/refresh :after 'user/go))

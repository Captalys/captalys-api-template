(ns {{name}}.core
    (:require [{{name}}.services.database :refer [datasource]]
              [clojure.string :as cstr]
              [next.jdbc :as jdbc]
              [next.jdbc.sql :as sql]))

(def data-example [{:id 1 :values 10 :letters "coding"}
                   {:id 2 :values 390 :letters "about"}
                   {:id 3 :values 112 :letters "talking"}
                   {:id 4 :values 23 :letters "are"}
                   {:id 5 :values 2 :letters "we"}
                   {:id 6 :values 0 :letters "now"}])

(defn insert-data->example-table
  "Insert multiple data into the database using a transaction to rollback if error happens."
  [data]
  (jdbc/with-transaction [tx datasource]
    (let [columns (keys (first data))
          values (map vals data)]
      (sql/insert-multi! tx "example_real" columns values))))

(defn query-data-by-id
  "Query database by id."
  [id]
  (sql/get-by-id datasource "example_real" id))

(defn sum-all-values
  "Sum all the values in the provided data"
  [data]
  (reduce + (map :values data)))

(defn join-all-letters
  "Join all the letters in the data by a specified delimiter."
  [data delimiter]
  (cstr/join delimiter (reverse (map :letters data))))
